package com.dto.response;

import com.model.Avalicao;
import com.model.Disciplina;
import com.model.Pessoa;

import java.time.Instant;
import java.time.LocalDate;

public record AvaliacaoResponseDTO(
        long id,
        double nota,
        LocalDate data,
        Long disciplinaId,
        Long pesooaId
) {

    public AvaliacaoResponseDTO(Avalicao avaliacao){
        this(
                avaliacao.getId(),
                avaliacao.getNota(),
                avaliacao.getData(),
                avaliacao.getDisciplina().getId(),
                avaliacao.getPessoa().getId()
        );
    }
}
