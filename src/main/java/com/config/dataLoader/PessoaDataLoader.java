package com.config.dataLoader;

import com.github.javafaker.Faker;
import com.model.Pessoa;
import com.model.Turma;
import com.repository.PessoaRepository;
import com.repository.TurmaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;
import java.util.Locale;

@Configuration
public class PessoaDataLoader {

    @Bean
    @Order(4)
    CommandLineRunner initPessoa(PessoaRepository repository,
                                 TurmaRepository turmaRepository) {
        return args -> {
            if (repository.count() == 0) {
                Faker faker = new Faker(Locale.forLanguageTag("pt-Br"));

                List<Turma> turmas = turmaRepository.findAll();

                if (turmas.isEmpty()) {
                    throw new IllegalStateException(
                            "Não existem turmas cadastradas."
                    );
                }

                for (int i = 0; i < 500; i++) {
                    Pessoa pessoa = new Pessoa();
                    pessoa.setAtivo(faker.bool().bool());
                    pessoa.setName(faker.name().fullName());
                    pessoa.setIdade(faker.number().numberBetween(5,70));
                    Turma turma = turmas.get(
                            faker.number().numberBetween(0, turmas.size())
                    );

                    pessoa.setTurma(turma);

                    repository.save(pessoa);

                }

                System.out.println("✅ Banco de pessoas populado com 200 registros!");
            } else {
                System.out.println("ℹ️ Banco de pessoas já contém dados, não foi necessário repopular.");
            }
        };
    }
}
