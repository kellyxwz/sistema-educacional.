package com.config.dataLoader;

import com.github.javafaker.Faker;
import com.model.Disciplina;
import com.model.Professor;
import com.repository.DisciplinaRepository;
import com.repository.ProfessorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;
import java.util.Locale;

@Configuration
public class DisciplinaDataLoader {

    @Bean
    @Order(5)
    CommandLineRunner initDisciplina(
            DisciplinaRepository disciplinaRepository,
            ProfessorRepository professorRepository) {

        return args -> {

            if (disciplinaRepository.count() == 0) {

                Faker faker = new Faker(
                        Locale.forLanguageTag("pt-BR")
                );

                List<Professor> professores =
                        professorRepository.findAll();

                if (professores.isEmpty()) {
                    throw new IllegalStateException(
                            "Não existem professores cadastrados para associar às disciplinas."
                    );
                }

                for (int i = 0; i < 100; i++) {

                    Disciplina disciplina = new Disciplina();

                    disciplina.setNome(
                            faker.name().fullName()
                    );

                    disciplina.setCargaHoraria(
                            faker.number().numberBetween(20, 100)
                    );

                    disciplina.setAtivo(
                            faker.bool().bool()
                    );

                    Professor professor = professores.get(
                            faker.number().numberBetween(
                                    0,
                                    professores.size()
                            )
                    );

                    disciplina.setProfessor(professor);

                    disciplinaRepository.save(disciplina);
                }

                System.out.println(
                        "✅ Banco de disciplinas populado com 100 registros!"
                );

            } else {

                System.out.println(
                        "ℹ️ Banco de disciplinas já contém dados, não foi necessário repopular."
                );
            }
        };
    }

}
