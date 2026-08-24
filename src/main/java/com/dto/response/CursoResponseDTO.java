package com.dto.response;

import com.model.Curso;

public record CursoResponseDTO(
        Long id,
        String nome,
        String descricao,
        Integer cargaHoraria
) {

    public CursoResponseDTO(Curso curso){
        this(
                curso.getId(),
                curso.getNome(),
                curso.getDescricao(),
                curso.getCargaHoraria()
        );
    }
}
