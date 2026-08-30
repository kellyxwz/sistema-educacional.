package com.dto.request;

import java.time.Instant;
import java.time.LocalDate;

public record AvaliacaoRequestDTO(
        double nota,
        LocalDate data
) {
}
