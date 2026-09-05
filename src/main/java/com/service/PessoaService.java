package com.service;

import com.dto.request.PessoaRequestDTO;
import com.dto.response.PessoaResponseDTO;

import com.model.Pessoa;
import com.pagination.Pagination;
import com.repository.PessoaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }


    public Page<PessoaResponseDTO> buscaAvancada(int page,
                                                 int size,
                                                 String sortBy,
                                                 String direction,
                                                 String name,
                                                 Integer idade,
                                                 String email){

        Pageable pageable = Pagination.create(page, size, sortBy, direction);

        Specification<Pessoa> spec = Specification.unrestricted();

        if (name != null){
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("name"), "%" + name + "%"));
        }

        if (name != null){
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("idade"), idade));
        }

        if (name != null){
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("email"), "%" + email + "%"));
        }

        return pessoaRepository.findAll(spec, pageable).map(PessoaResponseDTO::new);

    }

    public List<PessoaResponseDTO> findAll(){
        return pessoaRepository.findAll().stream().map(PessoaResponseDTO :: new).toList();
    }

    public PessoaResponseDTO findById(long id){
        Pessoa pessoa = pessoaRepository.findById(id).orElseThrow(() -> new RuntimeException("Pessoa não encontrada com o id: " + id));
        return new PessoaResponseDTO(pessoa);
    }

    public PessoaResponseDTO create(PessoaRequestDTO requestDTO){
        Pessoa pessoa = pessoaRepository.save(toEntity(requestDTO));

        return new PessoaResponseDTO(pessoa);
    }

    public PessoaResponseDTO update(PessoaRequestDTO requestDTO, long id){
        Pessoa pessoa = pessoaRepository.findById(id).orElseThrow(()->new RuntimeException("pessoa não encontrada"));

        updateData(pessoa,requestDTO);

        return new PessoaResponseDTO(pessoa);
    }

    public void deleteById(long id){
        pessoaRepository.deleteById(id);
    }

    private void updateData(Pessoa pessoa, PessoaRequestDTO dto){
        pessoa.setName(dto.nome());
        pessoa.setEmail(dto.email());
        pessoa.setIdade(dto.idade());

    }

    public static Pessoa toEntity(PessoaRequestDTO dto){
        Pessoa pessoa = new Pessoa();
        pessoa.setName(dto.nome());
        pessoa.setEmail(dto.email());
        pessoa.setIdade(dto.idade());
        pessoa.setAtivo(true);
        return pessoa;
    }


}
