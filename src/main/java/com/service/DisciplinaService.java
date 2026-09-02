package com.service;

import com.dto.request.DisciplinaRequestDTO;
import com.dto.response.DisciplinaResponseDTO;
import com.model.Disciplina;
import com.pagination.Pagination;
import com.repository.DisciplinaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    public Page<DisciplinaResponseDTO> buscaAvancada(int page,
                                          int size,
                                          String sortBy,
                                          String direction,
                                          String nome,
                                          Integer cargaHoraria
                                          ){

        Pageable pageable = Pagination.create(page, size, sortBy, direction);

        Specification<Disciplina> spec = Specification.unrestricted();

        if (nome != null) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("nome"), "%" + nome + "%"));
        }

        if(cargaHoraria!=null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("carga"), cargaHoraria));
        }
        return disciplinaRepository.findAll(spec, pageable).map(DisciplinaResponseDTO :: new);

    }

    public List<DisciplinaResponseDTO> findAll(){
        return disciplinaRepository.findAll().stream().map(DisciplinaResponseDTO :: new).toList();
    }

    public DisciplinaResponseDTO findById(long id){
        Disciplina disciplina = disciplinaRepository.findById(id).orElseThrow(() -> new RuntimeException("Disciplina não encontrada com o id: " + id));
        return new DisciplinaResponseDTO(disciplina);
    }

    public DisciplinaResponseDTO create(DisciplinaRequestDTO requestDTO){
        Disciplina disciplina = disciplinaRepository.save(toEntity(requestDTO));

        return new DisciplinaResponseDTO(disciplina);
    }

    public DisciplinaResponseDTO update(DisciplinaRequestDTO requestDTO, long id){
        Disciplina disciplina = disciplinaRepository.findById(id).orElseThrow(()->new RuntimeException("Disciplina não encontrada"));

        updateData(disciplina,requestDTO);

        return new DisciplinaResponseDTO(disciplina);
    }

    public void deleteById(long id){
        disciplinaRepository.deleteById(id);
    }

    private void updateData(Disciplina disciplina, DisciplinaRequestDTO dto){
        disciplina.setNome(dto.nome());
        disciplina.setCargaHoraria(dto.cargaHoraria());
    }

        public static Disciplina toEntity(DisciplinaRequestDTO dto){
        Disciplina disciplina = new Disciplina();
        disciplina.setNome(dto.nome());
        disciplina.setCargaHoraria(dto.cargaHoraria());
        disciplina.setAtivo(true);
        return disciplina;
    }


}
