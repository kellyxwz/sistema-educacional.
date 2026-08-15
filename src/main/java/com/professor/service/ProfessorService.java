package com.professor.service;

import com.professor.dto.request.ProfessorRequestDTO;
import com.professor.dto.response.ProfessorResponseDTO;
import com.professor.model.Professor;
import com.professor.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public List<ProfessorResponseDTO> findAll(){
        return professorRepository.findAll().stream().map(ProfessorResponseDTO:: new).toList();
    }

    public ProfessorResponseDTO create(ProfessorRequestDTO requestDTO){
        Professor professor = toEntity(requestDTO);
        Professor savedProf =professorRepository.save(professor);

        return new ProfessorResponseDTO(savedProf);
    }

    public void delete(String id){
        professorRepository.deleteById(id);
    }

    public ProfessorResponseDTO update(String id, ProfessorRequestDTO requestDTO){
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
