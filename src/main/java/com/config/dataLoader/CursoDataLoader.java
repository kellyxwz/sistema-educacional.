package com.config.dataLoader;

import com.github.javafaker.Faker;
import com.model.Curso;
import com.repository.CursoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Locale;

@Configuration
public class CursoDataLoader {

    @Bean
    @Order(3)
    CommandLineRunner initCurso(CursoRepository repository) {

        return args -> {

            if (repository.count() == 0) {

                Faker faker = new Faker(
                        Locale.forLanguageTag("pt-BR")
                );

                for (int i = 0; i < 500; i++) {

                    Curso curso = new Curso();

                    curso.setAtivo(
                            faker.bool().bool()
                    );

                    curso.setDescricao(
                            faker.lorem().sentence(5)
                    );

                    curso.setCargaHoraria(
                            faker.number().numberBetween(10, 60)
                    );

                    repository.save(curso);
                }

                System.out.println(
                        "✅ Banco de cursos populado com 500 registros!"
                );

            } else {

                System.out.println(
                        "ℹ️ Banco de cursos já contém dados, não foi necessário repopular."
                );
            }
        };
    }
}
