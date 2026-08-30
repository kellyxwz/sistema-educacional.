package com.dto.response;

import com.model.Avalicao;

import java.time.Instant;
import java.time.LocalDate;

public record AvaliacaoResponseDTO(
        long id,
        double nota,
        LocalDate data
) {

    public AvaliacaoResponseDTO(Avalicao avalicao){
        this(
                avalicao.getId(),
                avalicao.getNota(),
                avalicao.getData()
        );
    }

}
