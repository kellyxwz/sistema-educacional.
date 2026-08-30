package com.model;

import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Avalicao {

    @Id
    @GeneratedValue
    private Long id;

    private double nota;
    private Instant data;
    private boolean ativo;

}
