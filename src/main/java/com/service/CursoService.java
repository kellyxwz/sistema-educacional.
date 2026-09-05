package com.service;

import com.dto.request.CursoRequestDTO;
import com.dto.response.CursoResponseDTO;


import com.model.Curso;
import com.pagination.Pagination;
import com.repository.CursoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public Page<CursoResponseDTO> buscaAvancada(int page,
                                                int size,
                                                String sortBy,
                                                String direction,
                                                String nome,
                                                String descricao,
                                                Integer cargaHoraria){

        Pageable pageable = Pagination.create(page, size, sortBy, direction);

        Specification<Curso> spec = Specification.unrestricted();


        if (nome != null) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("nome"), "%" + nome + "%"));
        }

        if(descricao!=null) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("descricao"), "%" + descricao + "%"));
        }

        if(cargaHoraria !=null){
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("cargaHoraria"), cargaHoraria));
        }

        return cursoRepository.findAll(spec, pageable).map(CursoResponseDTO :: new);

    }

    public List<CursoResponseDTO> findAll(){
        return cursoRepository.findAll().stream().map(CursoResponseDTO :: new).toList();
    }

    public CursoResponseDTO findById(long id){
        Curso curso = cursoRepository.findById(id).orElseThrow(() -> new RuntimeException("Curso não encontrada com o id: " + id));
        return new CursoResponseDTO(curso);
    }

    public CursoResponseDTO create(CursoRequestDTO requestDTO){
        Curso curso = cursoRepository.save(toEntity(requestDTO));

        return new CursoResponseDTO(curso);
    }

    public CursoResponseDTO update(CursoRequestDTO requestDTO, long id){
        Curso curso = cursoRepository.findById(id).orElseThrow(()->new RuntimeException("curso não encontrada"));

        updateData(curso,requestDTO);

        return new CursoResponseDTO(curso);
    }

    public void deleteById(long id){
        cursoRepository.deleteById(id);
    }

    private void updateData(Curso curso, CursoRequestDTO dto){
        curso.setNome(dto.nome());
        curso.setDescricao(dto.descricao());
        curso.setCargaHoraria(dto.cargaHoraria());
    }

    public static Curso toEntity(CursoRequestDTO dto){
        Curso curso = new Curso();
        curso.setNome(dto.nome());
        curso.setDescricao(dto.descricao());
        curso.setCargaHoraria(dto.cargaHoraria());
        curso.setAtivo(true);
        return curso;
    }


}
