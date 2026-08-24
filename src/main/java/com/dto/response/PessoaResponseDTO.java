package com.dto.response;

import com.model.Pessoa;

public record PessoaResponseDTO (
        Long id,
        String name,
        Integer idade,
        String email

){

    public PessoaResponseDTO(Pessoa pessoa){
        this(
                pessoa.getId(),
                pessoa.getName(),
                pessoa.getIdade(),
                pessoa.getEmail()
        );
    }

}
