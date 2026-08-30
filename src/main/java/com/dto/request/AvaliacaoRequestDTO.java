package com.dto.request;

import java.time.LocalDate;

public record AvaliacaoRequestDTO(
        double nota,
        LocalDate data,
        Long turmaId,
        Long pesooaId
) {
}
