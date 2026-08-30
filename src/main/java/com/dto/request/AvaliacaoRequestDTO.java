package com.dto.request;

import java.time.Instant;

public record AvaliacaoRequestDTO(
        double nota,
        Instant data
) {
}
