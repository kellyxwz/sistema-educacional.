package com.dto.request;

import java.time.LocalDate;

public record TurmaRequestDTO(
        String nome,
        Integer ano
) {
}
