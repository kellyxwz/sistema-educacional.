package com.service;

import com.dto.request.MatriculaRequestDTO;
import com.dto.response.MatriculaResponseDTO;
import com.model.Matricula;
import com.repository.MatriculaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;

    public MatriculaService(MatriculaRepository matriculaRepository) {
        this.matriculaRepository = matriculaRepository;
    }

    public List<MatriculaResponseDTO> findAll(){
        return matriculaRepository.findAll().stream().map(MatriculaResponseDTO :: new).toList();
    }

    public MatriculaResponseDTO findById(long id){
        Matricula matricula = matriculaRepository.findById(id).orElseThrow(() -> new RuntimeException("Matricula não encontrada com o id: " + id));
        return new MatriculaResponseDTO(matricula);
    }

    public MatriculaResponseDTO create(MatriculaRequestDTO requestDTO){
        Matricula matricula = matriculaRepository.save(toEntity(requestDTO));

        return new MatriculaResponseDTO(matricula);
    }

    public MatriculaResponseDTO update(MatriculaRequestDTO requestDTO, long id){
        Matricula matricula = matriculaRepository.findById(id).orElseThrow(()->new RuntimeException("Matricula não encontrada"));

        updateData(matricula,requestDTO);

        return new MatriculaResponseDTO(matricula);
    }

    public void deleteById(long id){
        matriculaRepository.deleteById(id);
    }

    private void updateData(Matricula matricula, MatriculaRequestDTO dto){
        matricula.setDataMatricula(dto.dataMatricula());
    }

        public static Matricula toEntity(MatriculaRequestDTO dto){
            Matricula matricula = new Matricula();
            matricula.setDataMatricula(dto.dataMatricula());
            matricula.setAtivo(true);
        return matricula;
    }


}
