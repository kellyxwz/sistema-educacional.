package com.config;

import com.model.*;
import com.repository.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataLoader implements CommandLineRunner {

    private final TurmaRespository turmaRepository;
    private final ProfessorRepository professorRepository;
    private final CursoRepository cursoRepository;
    private final PessoaRepository pessoaRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final MatriculaRepository matriculaRepository;
    private final AvalicaoRepository avaliacaoRepository;

    public DataLoader(
            TurmaRespository turmaRepository,
            ProfessorRepository professorRepository,
            CursoRepository cursoRepository,
            PessoaRepository pessoaRepository,
            DisciplinaRepository disciplinaRepository,
            AvalicaoRepository avaliacaoRepository,
            MatriculaRepository matriculaRepository
    ) {
        this.turmaRepository = turmaRepository;
        this.professorRepository = professorRepository;
        this.cursoRepository = cursoRepository;
        this.pessoaRepository = pessoaRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.matriculaRepository = matriculaRepository;
        this.avaliacaoRepository = avaliacaoRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // Evita inserir os dados novamente toda vez
        // que a aplicação for iniciada.
        if (turmaRepository.count() > 0) {
            System.out.println("Banco já possui dados. DataLoader não executado.");
            return;
        }

        System.out.println("Iniciando carga dos CSVs...");

        Map<String, Turma> turmas = carregarTurmas();
        Map<String, Professor> professores = carregarProfessores();
        Map<String, Curso> cursos = carregarCursos();
        Map<String, Pessoa> pessoas = carregarPessoas(turmas);
        Map<String, Disciplina> disciplinas = carregarDisciplinas(professores);

        carregarMatriculas(cursos, pessoas);
        carregarAvaliacoes(disciplinas, pessoas);

        System.out.println("Carga dos CSVs concluída!");
    }

    private Map<String, Turma> carregarTurmas() throws Exception {

        Map<String, Turma> mapa = new HashMap<>();

        try (Reader reader = abrirArquivo("turmas.csv");
             CSVParser csv = CSVFormat.DEFAULT
                     .builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .get()
                     .parse(reader)) {

            for (CSVRecord registro : csv) {

                Turma turma = new Turma();

                turma.setNome(registro.get("nome"));
                turma.setAno(Integer.parseInt(registro.get("ano")));
                turma.setAtivo(true);

                turma = turmaRepository.save(turma);

                mapa.put(registro.get("referencia"), turma);
            }
        }

        return mapa;
    }

    private Map<String, Professor> carregarProfessores() throws Exception {

        Map<String, Professor> mapa = new HashMap<>();

        try (Reader reader = abrirArquivo("professores.csv");
             CSVParser csv = CSVFormat.DEFAULT
                     .builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .get()
                     .parse(reader)) {

            for (CSVRecord registro : csv) {

                Professor professor = new Professor();

                professor.setNome(registro.get("nome"));
                professor.setEspecialidade(registro.get("especialidade"));
                professor.setEmail(registro.get("email"));
                professor.setActive(true);

                professor = professorRepository.save(professor);

                mapa.put(registro.get("referencia"), professor);
            }
        }

        return mapa;
    }

    private Map<String, Curso> carregarCursos() throws Exception {

        Map<String, Curso> mapa = new HashMap<>();

        try (Reader reader = abrirArquivo("cursos.csv");
             CSVParser csv = CSVFormat.DEFAULT
                     .builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .get()
                     .parse(reader)) {

            for (CSVRecord registro : csv) {

                Curso curso = new Curso();

                curso.setNome(registro.get("nome"));
                curso.setDescricao(registro.get("descricao"));
                curso.setCargaHoraria(
                        Integer.parseInt(registro.get("carga_horaria"))
                );
                curso.setAtivo(true);

                curso = cursoRepository.save(curso);

                mapa.put(registro.get("referencia"), curso);
            }
        }

        return mapa;
    }

    private Map<String, Pessoa> carregarPessoas(
            Map<String, Turma> turmas
    ) throws Exception {

        Map<String, Pessoa> mapa = new HashMap<>();

        try (Reader reader = abrirArquivo("pessoas.csv");
             CSVParser csv = CSVFormat.DEFAULT
                     .builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .get()
                     .parse(reader)) {

            for (CSVRecord registro : csv) {

                Pessoa pessoa = new Pessoa();

                pessoa.setName(registro.get("nome"));
                pessoa.setIdade(
                        Integer.parseInt(registro.get("idade"))
                );
                pessoa.setEmail(registro.get("email"));
                pessoa.setAtivo(true);

                String turmaRef = registro.get("turma_ref");

                Turma turma = turmas.get(turmaRef);

                if (turma == null) {
                    throw new RuntimeException(
                            "Turma não encontrada: " + turmaRef
                    );
                }

                pessoa.setTurma(turma);

                pessoa = pessoaRepository.save(pessoa);

                mapa.put(registro.get("referencia"), pessoa);
            }
        }

        return mapa;
    }

    private Map<String, Disciplina> carregarDisciplinas(
            Map<String, Professor> professores
    ) throws Exception {

        Map<String, Disciplina> mapa = new HashMap<>();

        try (Reader reader = abrirArquivo("disciplinas.csv");
             CSVParser csv = CSVFormat.DEFAULT
                     .builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .get()
                     .parse(reader)) {

            for (CSVRecord registro : csv) {

                Disciplina disciplina = new Disciplina();

                disciplina.setNome(registro.get("nome"));

                disciplina.setCargaHoraria(
                        Integer.parseInt(
                                registro.get("carga_horaria")
                        )
                );

                disciplina.setAtivo(true);

                String professorRef = registro.get("professor_ref");

                Professor professor = professores.get(professorRef);

                if (professor == null) {
                    throw new RuntimeException(
                            "Professor não encontrado: " + professorRef
                    );
                }

                disciplina.setProfessor(professor);

                disciplina = disciplinaRepository.save(disciplina);

                mapa.put(registro.get("referencia"), disciplina);
            }
        }

        return mapa;
    }

    private void carregarMatriculas(
            Map<String, Curso> cursos,
            Map<String, Pessoa> pessoas
    ) throws Exception {

        try (Reader reader = abrirArquivo("matriculas.csv");
             CSVParser csv = CSVFormat.DEFAULT
                     .builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .get()
                     .parse(reader)) {

            for (CSVRecord registro : csv) {

                Matricula matricula = new Matricula();

                matricula.setDataMatricula(
                        LocalDate.parse(
                                registro.get("data_matricula")
                        )
                );

                String cursoRef = registro.get("curso_ref");
                String pessoaRef = registro.get("pessoa_ref");

                Curso curso = cursos.get(cursoRef);
                Pessoa pessoa = pessoas.get(pessoaRef);

                if (curso == null) {
                    throw new RuntimeException(
                            "Curso não encontrado: " + cursoRef
                    );
                }

                if (pessoa == null) {
                    throw new RuntimeException(
                            "Pessoa não encontrada: " + pessoaRef
                    );
                }

                matricula.setCurso(curso);
                matricula.setPessoa(pessoa);
                matricula.setAtivo(true);

                matriculaRepository.save(matricula);
            }
        }
    }

    private void carregarAvaliacoes(
            Map<String, Disciplina> disciplinas,
            Map<String, Pessoa> pessoas
    ) throws Exception {

        try (Reader reader = abrirArquivo("avaliacoes.csv");
             CSVParser csv = CSVFormat.DEFAULT
                     .builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .get()
                     .parse(reader)) {

            for (CSVRecord registro : csv) {

                Avalicao avaliacao = new Avalicao();

                avaliacao.setNota(
                        Double.parseDouble(
                                registro.get("nota")
                        )
                );

                avaliacao.setData(
                        LocalDate.parse(
                                registro.get("data")
                        )
                );

                String disciplinaRef =
                        registro.get("disciplina_ref");

                String pessoaRef =
                        registro.get("pessoa_ref");

                Disciplina disciplina =
                        disciplinas.get(disciplinaRef);

                Pessoa pessoa =
                        pessoas.get(pessoaRef);

                if (disciplina == null) {
                    throw new RuntimeException(
                            "Disciplina não encontrada: "
                                    + disciplinaRef
                    );
                }

                if (pessoa == null) {
                    throw new RuntimeException(
                            "Pessoa não encontrada: "
                                    + pessoaRef
                    );
                }

                avaliacao.setDisciplina(disciplina);
                avaliacao.setPessoa(pessoa);
                avaliacao.setAtivo(true);

                avaliacaoRepository.save(avaliacao);
            }
        }
    }

    private Reader abrirArquivo(String nomeArquivo) {

        var inputStream =
                getClass()
                        .getClassLoader()
                        .getResourceAsStream(
                                "data/" + nomeArquivo
                        );

        if (inputStream == null) {
            throw new RuntimeException(
                    "Arquivo não encontrado: data/" + nomeArquivo
            );
        }

        return new InputStreamReader(
                inputStream,
                StandardCharsets.UTF_8
        );
    }
}
