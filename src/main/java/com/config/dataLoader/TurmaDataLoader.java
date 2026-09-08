package com.config.dataLoader;

import com.github.javafaker.Faker;
import com.model.Turma;
import com.repository.TurmaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Locale;

@Configuration
public class TurmaDataLoader {

    @Bean
    @Order(1)
    CommandLineRunner initTurma(TurmaRepository repository){
        return args -> {

            if (repository.count() == 0) {

                Faker faker = new Faker(
                        Locale.forLanguageTag("pt-BR")
                );

                for (int i = 0; i < 20; i++) {

                    Turma turma = new Turma();

                    turma.setNome(
                            "Turma " + faker.number().numberBetween(1, 100));

                    turma.setAno(
                            faker.number().numberBetween(2020, 2027));

                    turma.setAtivo(faker.bool().bool());

                    repository.save(turma);
                }

                System.out.println(
                        "✅ Banco de turmas populado com 20 registros!"
                );

            } else {

                System.out.println(
                        "ℹ️ Banco de turmas já contém dados."
                );
            }
        };
    }

}
