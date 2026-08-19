package com.dto.response;

import com.model.Professor;

public record ProfessorResponseDTO(
        String id,
        String nome,
        String especialidade,
        String email
) {

    public ProfessorResponseDTO(Professor professor){
        this(
                professor.getId(),
                professor.getNome(),
                professor.getEspecialidade(),
                professor.getEmail()
        );
    }

}
