package com.service;

import com.dto.request.AvaliacaoRequestDTO;
import com.dto.response.AvaliacaoResponseDTO;
import com.model.Avaliacao;
import com.model.Disciplina;
import com.model.Pessoa;
import com.pagination.Pagination;
import com.repository.AvaliacaoRepository;
import com.repository.DisciplinaRepository;
import com.repository.PessoaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final PessoaRepository pessoaRepository;
    private final DisciplinaRepository disciplinaRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, PessoaRepository pessoaRepository, DisciplinaRepository disciplinaRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.pessoaRepository = pessoaRepository;
        this.disciplinaRepository = disciplinaRepository;
    }

    public Page<AvaliacaoResponseDTO> buscaAvancada(int page,
                                                    int size,
                                                    String sortBy,
                                                    String direction,
                                                    Double nota,
                                                    LocalDate data
                                                    ){

        Pageable pageable = Pagination.create(page, size, sortBy, direction);

        Specification<Avaliacao> spec = Specification.unrestricted();

        if (nota!=null){
            spec = spec.and(((root, query, cb) ->
                    cb.equal(root.get("nota"), nota) ));
        };

        if (data!=null){
            spec = spec.and((root, query, cb) -> cb.equal(root.get("data"), data));
        }

        return avaliacaoRepository.findAll(spec, pageable).map(AvaliacaoResponseDTO::new);

    }

    public List<AvaliacaoResponseDTO> findAll(){
        return avaliacaoRepository.findAll().stream().map(AvaliacaoResponseDTO :: new).toList();
    }

    public AvaliacaoResponseDTO findById(long id){
        Avaliacao avaliacao = avaliacaoRepository.findById(id).orElseThrow(() -> new RuntimeException("Avaliação não encontrada com o id: " + id));
        return new AvaliacaoResponseDTO(avaliacao);
    }

    public AvaliacaoResponseDTO create(AvaliacaoRequestDTO requestDTO){
        Avaliacao avaliacao = avaliacaoRepository.save(toEntity(requestDTO));

        Pessoa pessoa = pessoaRepository.findById(requestDTO.pessoaId()).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
        Disciplina disciplina = disciplinaRepository.findById(requestDTO.disciplinaId()).orElseThrow(()-> new RuntimeException("Disciplina não encontrada"));
        avaliacao.setPessoa(pessoa);
        avaliacao.setDisciplina(disciplina);

        return new AvaliacaoResponseDTO(avaliacao);
    }

    public AvaliacaoResponseDTO update(AvaliacaoRequestDTO requestDTO, long id){
        Avaliacao avaliacao = avaliacaoRepository.findById(id).orElseThrow(()->new RuntimeException("Avalliação não encontrada"));

        updateData(avaliacao,requestDTO);

        return new AvaliacaoResponseDTO(avaliacao);
    }

    public void deleteById(long id){
        avaliacaoRepository.deleteById(id);
    }

    private void updateData(Avaliacao avaliacao, AvaliacaoRequestDTO dto){
        avaliacao.setNota(dto.nota());
        avaliacao.setData(dto.data());
    }

        public static Avaliacao toEntity(AvaliacaoRequestDTO dto){
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setNota(dto.nota());
        avaliacao.setData(dto.data());
        avaliacao.setAtivo(true);
        return avaliacao;
    }


}
