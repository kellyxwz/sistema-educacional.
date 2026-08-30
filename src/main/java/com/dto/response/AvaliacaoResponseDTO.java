package com.dto.response;

import com.model.Avalicao;

import java.time.Instant;

public record AvaliacaoResponseDTO(
        long id,
        double nota,
        Instant data
) {

    public AvaliacaoResponseDTO(Avalicao avalicao){
        this(
                avalicao.getId(),
                avalicao.getNota(),
                avalicao.getData()
        );
    }

}
