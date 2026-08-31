package com.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "avaliacoes")
public class Avalicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double nota;
    private LocalDate data;
    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "pessoa_Id")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

}
