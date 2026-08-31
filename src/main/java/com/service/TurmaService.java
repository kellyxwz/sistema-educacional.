package com.service;

import com.dto.request.TurmaRequestDTO;
import com.dto.response.TurmaResponseDTO;
import com.model.Turma;
import com.pagination.Pagination;
import com.repository.TurmaRespository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TurmaService {

    private final TurmaRespository turmaRespository;

    public TurmaService(TurmaRespository turmaRespository) {
        this.turmaRespository = turmaRespository;
    }

    public Page<TurmaResponseDTO> buscaAvancada (int page,
                                                 int size,
                                                 String sortBy,
                                                 String direction,
                                                 String nome,
                                                 LocalDate data){

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

        if (data != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("data"), data)
            );
        }

        return turmaRespository.findAll(spec, pageable).map(TurmaResponseDTO::new);
    }


    public List<TurmaResponseDTO> findAll(){
        return turmaRespository.findAll().stream().map(TurmaResponseDTO :: new).toList();
    }

    public TurmaResponseDTO findById(long id){
        Turma turma = turmaRespository.findById(id).orElseThrow(() ->new RuntimeException( "Nenhum usuário encontrado com o id: " + id));
        return new TurmaResponseDTO(turma);
    }

    public TurmaResponseDTO create(TurmaRequestDTO requestDTO){
        Turma turma = toEntity(requestDTO);
        Turma newTurma = turmaRespository.save(turma);

        return new TurmaResponseDTO(newTurma);
    }

    public void deleteById(long id){
        turmaRespository.deleteById(id);
    }

    public TurmaResponseDTO update(long id, TurmaRequestDTO requestDTO){
        Turma turma = turmaRespository.findById(id).orElseThrow(()-> new RuntimeException("Turma não encontrada com id: "+id));
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
