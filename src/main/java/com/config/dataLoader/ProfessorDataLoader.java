package com.config.dataLoader;

import com.github.javafaker.Faker;
import com.model.Professor;
import com.repository.ProfessorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Locale;

@Configuration
public class ProfessorDataLoader {

    @Bean
    @Order(2)
    CommandLineRunner initProfessor(
            ProfessorRepository professorRepository) {

        return args -> {

            if (professorRepository.count() == 0) {

                Faker faker = new Faker(
                        Locale.forLanguageTag("pt-BR")
                );

                for (int i = 0; i < 100; i++) {

                    Professor professor = new Professor();

                    professor.setNome(
                            faker.name().fullName()
                    );

                    professor.setEmail(
                            faker.internet().emailAddress()
                    );

                    professor.setActive(
                            faker.bool().bool()
                    );

                    professorRepository.save(professor);
                }

                System.out.println(
                        "✅ Banco de professores populado com 100 registros!"
                );

            } else {

                System.out.println(
                        "ℹ️ Banco de professores já contém dados, não foi necessário repopular."
                );
            }
        };
    }

}
