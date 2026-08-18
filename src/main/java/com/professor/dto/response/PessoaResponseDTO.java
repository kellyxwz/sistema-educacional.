package com.professor.dto.response;

import com.professor.dto.request.PessoaRequestDTO;
import com.professor.model.Pessoa;

public record PessoaResponseDTO (
        String id,
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
