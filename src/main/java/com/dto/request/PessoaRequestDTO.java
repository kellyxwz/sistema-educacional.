package com.dto.request;

public record PessoaRequestDTO(
        String nome,
        Integer idade,
        String email
) {
}
