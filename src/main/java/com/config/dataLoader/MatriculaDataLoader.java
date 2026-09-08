package com.config.dataLoader;

import com.github.javafaker.Faker;
import com.model.Curso;
import com.model.Matricula;
import com.model.Pessoa;
import com.repository.CursoRepository;
import com.repository.MatriculaRepository;
import com.repository.PessoaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Configuration
public class MatriculaDataLoader {

    @Bean
    @Order(6)
    CommandLineRunner initMatricula(
            MatriculaRepository matriculaRepository,
            PessoaRepository pessoaRepository,
            CursoRepository cursoRepository) {

        return args -> {

            if (matriculaRepository.count() == 0) {

                Faker faker = new Faker(
                        Locale.forLanguageTag("pt-BR")
                );

                List<Pessoa> pessoas = pessoaRepository.findAll();
                List<Curso> cursos = cursoRepository.findAll();

                if (pessoas.isEmpty()) {
                    throw new IllegalStateException(
                            "Não existem pessoas cadastradas."
                    );
                }

                if (cursos.isEmpty()) {
                    throw new IllegalStateException(
                            "Não existem cursos cadastrados."
                    );
                }

                for (int i = 0; i < 500; i++) {

                    Matricula matricula = new Matricula();

                    matricula.setDataMatricula(
                            faker.date()
                                    .past(365, TimeUnit.DAYS)
                                    .toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                    );

                    matricula.setAtivo(
                            faker.bool().bool()
                    );

                    Pessoa pessoa = pessoas.get(
                            faker.number().numberBetween(
                                    0,
                                    pessoas.size()
                            )
                    );

                    Curso curso = cursos.get(
                            faker.number().numberBetween(
                                    0,
                                    cursos.size()
                            )
                    );

                    matricula.setPessoa(pessoa);
                    matricula.setCurso(curso);

                    matriculaRepository.save(matricula);
                }

                System.out.println(
                        "✅ Banco de matrículas populado com 500 registros!"
                );

            } else {

                System.out.println(
                        "ℹ️ Banco de matrículas já contém dados, não foi necessário repopular."
                );
            }
        };
    }

}
