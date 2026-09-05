package com.service;

import com.dto.request.ProfessorRequestDTO;
import com.dto.response.ProfessorResponseDTO;
import com.model.Professor;
import com.pagination.Pagination;
import com.repository.ProfessorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public Page<ProfessorResponseDTO> buscaAvancada(int page,
                                                    int size,
                                                    String sortBy,
                                                    String direction,
                                                    String nome,
                                                    String especialidade,
                                                    String email){

        Pageable pageable = Pagination.create(page, size, sortBy, direction);

        Specification<Professor> spec = Specification.unrestricted();

        if (nome != null){
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("nome"), "%" + nome + "%"));
        }
        if (especialidade != null){
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("especialidade "), "%" + especialidade  + "%"));
        }
        if (email != null){
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("email"), "%" + email + "%"));
        }

        return professorRepository.findAll(spec,pageable).map(ProfessorResponseDTO::new);
    }

    public List<ProfessorResponseDTO> findAll(){
        return professorRepository.findAll().stream().map(ProfessorResponseDTO:: new).toList();
    }

    public ProfessorResponseDTO findById(long id){
        Professor professor =  professorRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado com o is: "+id));
        return new ProfessorResponseDTO(professor);
    }

    public ProfessorResponseDTO create(ProfessorRequestDTO requestDTO){
        Professor professor = toEntity(requestDTO);
        Professor savedProf = professorRepository.save(professor);

        return new ProfessorResponseDTO(savedProf);
    }

    public void delete(long id){
        professorRepository.deleteById(id);
    }

    public ProfessorResponseDTO update(long id, ProfessorRequestDTO requestDTO){
        Professor professor = professorRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não existente com o id: " +id));
        updateData(professor, requestDTO);
        Professor newProfessor = professorRepository.save(professor);

        return new ProfessorResponseDTO(newProfessor);
    }


    private void updateData(Professor professor, ProfessorRequestDTO requestDTO){
        professor.setNome(requestDTO.nome());
        professor.setEspecialidade(requestDTO.especialidade());
        professor.setEmail(requestDTO.email());
    }

    public static Professor toEntity(ProfessorRequestDTO dto){
        Professor professor = new Professor();
        professor.setNome(dto.nome());
        professor.setEspecialidade(dto.especialidade());
        professor.setEmail(dto.email());
        professor.setActive(true);

        return professor;
    }

}
