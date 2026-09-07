package com.dto.response;

import com.model.Avaliacao;

import java.time.LocalDate;

public record AvaliacaoResponseDTO(
        long id,
        double nota,
        LocalDate data,
        Long disciplinaId,
        Long pesooaId
) {

    public AvaliacaoResponseDTO(Avaliacao avaliacao){
        this(
                avaliacao.getId(),
                avaliacao.getNota(),
                avaliacao.getData(),
                avaliacao.getDisciplina().getId(),
                avaliacao.getPessoa().getId()
        );
    }
}
