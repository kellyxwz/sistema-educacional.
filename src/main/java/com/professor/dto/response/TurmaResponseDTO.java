package com.professor.dto.response;

import com.professor.model.Turma;

public record TurmaResponseDTO(
        String id,
        String nome,
        Integer ano
) {
    public TurmaResponseDTO(Turma turma){
        this(
                turma.getId(),
                turma.getNome(),
                turma.getAno()
        );
    }
}
