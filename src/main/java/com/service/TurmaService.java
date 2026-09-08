package com.service;

import com.dto.request.TurmaRequestDTO;
import com.dto.response.TurmaResponseDTO;
import com.model.Turma;
import com.pagination.Pagination;
import com.repository.TurmaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurmaService {

    private final TurmaRepository turmaRepository;

    public TurmaService(TurmaRepository turmaRepository) {
        this.turmaRepository = turmaRepository;
    }

    public Page<TurmaResponseDTO> buscaAvancada (int page,
                                                 int size,
                                                 String sortBy,
                                                 String direction,
                                                 String nome,
                                                 Integer ano){

        Pageable pageable = Pagination.create(page, size, sortBy, direction);

        Specification<Turma> spec = Specification.unrestricted();

        if (nome != null && !nome.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(
                            cb.lower(root.get("nome")),
                            "%" + nome.toLowerCase() + "%"
                    )
            );
        }

        if (ano != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("data"), ano)
            );
        }

        return turmaRepository.findAll(spec, pageable).map(TurmaResponseDTO::new);
    }


    public List<TurmaResponseDTO> findAll(){
        return turmaRepository.findAll().stream().map(TurmaResponseDTO :: new).toList();
    }

    public TurmaResponseDTO findById(long id){
        Turma turma = turmaRepository.findById(id).orElseThrow(() ->new RuntimeException( "Nenhum usuário encontrado com o id: " + id));
        return new TurmaResponseDTO(turma);
    }

    public TurmaResponseDTO create(TurmaRequestDTO requestDTO){
        Turma turma = toEntity(requestDTO);
        Turma newTurma = turmaRepository.save(turma);

        return new TurmaResponseDTO(newTurma);
    }

    public void deleteById(long id){
        turmaRepository.deleteById(id);
    }

    public TurmaResponseDTO update(long id, TurmaRequestDTO requestDTO){
        Turma turma = turmaRepository.findById(id).orElseThrow(()-> new RuntimeException("Turma não encontrada com id: "+id));
        updateData(turma, requestDTO);
        return new TurmaResponseDTO(turma);
    }

    private void updateData(Turma turma, TurmaRequestDTO dto){
        turma.setNome(dto.nome());
        turma.setAno(dto.ano());
    }

    public static Turma toEntity(TurmaRequestDTO dto){

        Turma turma = new Turma();
        turma.setNome(dto.nome());
        turma.setAno(dto.ano());
        turma.setAtivo(true);

        return turma;
    }
}
