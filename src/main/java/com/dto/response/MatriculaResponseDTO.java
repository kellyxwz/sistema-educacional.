package com.dto.response;

import java.time.LocalDate;

public record MatriculaResponseDTO(
        Long id,
        LocalDate dataMatricula
) {
}
