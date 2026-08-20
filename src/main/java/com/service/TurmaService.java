package com.service;

import com.dto.request.TurmaRequestDTO;
import com.dto.response.TurmaResponseDTO;
import com.model.Turma;
import com.repository.TurmaRespository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurmaService {

    private final TurmaRespository turmaRespository;

    public TurmaService(TurmaRespository turmaRespository) {
        this.turmaRespository = turmaRespository;
    }

    public List<TurmaResponseDTO> findAll(){
        return turmaRespository.findAll().stream().map(TurmaResponseDTO :: new).toList();
    }

    public TurmaResponseDTO findById(String id){
        Turma turma = turmaRespository.findById(id).orElseThrow(() ->new RuntimeException( "Nenhum usuário encontrado com o id: " + id));
        return new TurmaResponseDTO(turma);
    }

    public TurmaResponseDTO create(TurmaRequestDTO requestDTO){
        Turma turma = toEntity(requestDTO);
        Turma newTurma = turmaRespository.save(turma);

        return new TurmaResponseDTO(newTurma);
    }

    public void deleteById(String id){
        turmaRespository.deleteById(id);
    }

    public TurmaResponseDTO update(String id, TurmaRequestDTO requestDTO){
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
