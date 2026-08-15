package com.professor.model;

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
@Document(collection = "professor")
public class Professor {

    @Id
    private Long id;

    private String nome;
    private String especialidade;
    private String email;
    private boolean  active;

}
