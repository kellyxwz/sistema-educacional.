package com.config.dataLoader;

import com.model.Avaliacao;
import com.model.Pessoa;
import com.repository.AvaliacaoRepository;
import com.repository.DisciplinaRepository;
import com.repository.PessoaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class AvaliacaoDataLoader {

    private final PessoaRepository pessoaRepository;
    private final DisciplinaRepository disciplinaRepository;

    public AvaliacaoDataLoader(PessoaRepository pessoaRepository, DisciplinaRepository disciplinaRepository) {
        this.pessoaRepository = pessoaRepository;
        this.disciplinaRepository = disciplinaRepository;
    }

    @Bean
    CommandLineRunner initAvaliacao(AvaliacaoRepository repository){
        return  args -> {
            repository.save(new Avaliacao(null, 8.5, LocalDate.of(2024,1,10), true, pessoaRepository.findById(1L).get(), disciplinaRepository.findById(1L).get()));
            repository.save(new Avaliacao(null, 7.0, LocalDate.of(2024,1,11) ,true, pessoaRepository.findById(2L).get(), disciplinaRepository.findById(1L).get()));
            };
        }

}
