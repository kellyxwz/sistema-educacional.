package com.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "curso")
public class Curso {

    @Id
    private String id;

    private String nome;
    private  String descricao;
    private Integer cargaHoraria;
    private boolean ativo;

}
