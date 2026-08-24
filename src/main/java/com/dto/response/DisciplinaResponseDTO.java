package com.dto.response;

import com.model.Disciplina;

public record DisciplinaResponseDTO(
        Long id,
        String nome,
        Integer cargaHoraria
) {

    public DisciplinaResponseDTO(Disciplina disciplina){
        this(
                disciplina.getId(),
                disciplina.getNome(),
                disciplina.getCargaHoraria()
        );
    }
}
