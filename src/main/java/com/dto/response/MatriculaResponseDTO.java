package com.dto.response;

import com.model.Matricula;

import java.time.LocalDate;

public record MatriculaResponseDTO(
        Long id,
        LocalDate dataMatricula
) {

    public MatriculaResponseDTO(Matricula matricula){
        this(
                matricula.getId(),
                matricula.getDataMatricula()
        );
    }
}
