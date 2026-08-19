package com.dto.request;

public record CursoRequestDTO(
        String nome,
        String descricao,
        Integer cargaHoraria
) {
}
