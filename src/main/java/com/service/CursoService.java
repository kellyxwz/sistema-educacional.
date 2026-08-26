package com.service;

import com.dto.request.CursoRequestDTO;
import com.dto.response.CursoResponseDTO;


import com.model.Curso;
import com.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }


    public List<CursoResponseDTO> findAll(){
        return cursoRepository.findAll().stream().map(CursoResponseDTO :: new).toList();
    }

    public CursoResponseDTO findById(long id){
        Curso curso = cursoRepository.findById(id).orElseThrow(() -> new RuntimeException("Curso não encontrada com o id: " + id));
        return new CursoResponseDTO(curso);
    }

    public CursoResponseDTO create(CursoRequestDTO requestDTO){
        Curso curso = cursoRepository.save(toEntity(requestDTO));

        return new CursoResponseDTO(curso);
    }

    public CursoResponseDTO update(CursoRequestDTO requestDTO, long id){
        Curso curso = cursoRepository.findById(id).orElseThrow(()->new RuntimeException("curso não encontrada"));

        updateData(curso,requestDTO);

        return new CursoResponseDTO(curso);
    }

    public void deleteById(long id){
        cursoRepository.deleteById(id);
    }

    private void updateData(Curso curso, CursoRequestDTO dto){
        curso.setNome(dto.nome());
        curso.setDescricao(dto.descricao());
        curso.setCargaHoraria(dto.cargaHoraria());
    }

    public static Curso toEntity(CursoRequestDTO dto){
        Curso curso = new Curso();
        curso.setNome(dto.nome());
        curso.setDescricao(dto.descricao());
        curso.setCargaHoraria(dto.cargaHoraria());
        curso.setAtivo(true);
        return curso;
    }


}
