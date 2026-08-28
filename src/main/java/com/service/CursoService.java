package com.service;

import com.dto.request.CursoRequestDTO;
import com.dto.response.CursoResponseDTO;


import com.dto.response.ProfessorResponseDTO;
import com.model.Curso;
import com.repository.CursoRepository;
import com.repository.ProfessorRepository;
import com.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public Page<CursoResponseDTO> buscaAvancada(
            String nome,
            String descricao,
            Integer cargaHoraria,
            Boolean ativo,
            int page,
            int size,
            String sortBy,
            String direction
    ){
        Pageable pageable = PaginationUtil.create(page, size, sortBy, direction);

        Specification<Curso> spec = Specification.unrestricted();

        if(nome != null && !nome.isBlank()){
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
        }
        if(descricao != null && !descricao.isBlank()){
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("descricao")), "%" + descricao.toLowerCase() + "%"));
        }
        if(cargaHoraria != null){
            spec = spec.and((root, query, cb) -> cb.equal(root.get("cargaHoraria"), cargaHoraria));
        }
        if (ativo != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("ativo"), ativo));
        }

        return cursoRepository.findAll(spec, pageable).map(CursoResponseDTO::new);

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
