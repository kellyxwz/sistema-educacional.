package com.dto.request;

import java.time.LocalDate;

public record MatriculaRequestDTO(
        LocalDate dataMatricula,
        Long cursoId,
        Long pessoaId
) {
}
