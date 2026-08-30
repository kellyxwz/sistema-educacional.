package com.controller;

import com.dto.request.MatriculaRequestDTO;
import com.dto.response.MatriculaResponseDTO;

import com.service.MatriculaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class MatriculaController {

    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @GetMapping
    public ResponseEntity<List<MatriculaResponseDTO>> findAll(){
        List<MatriculaResponseDTO> list = matriculaService.findAll();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatriculaResponseDTO> findById(@PathVariable long id){
        MatriculaResponseDTO matricula = matriculaService.findById(id);
        return ResponseEntity.ok(matricula);
    }

    @PostMapping
    public ResponseEntity<MatriculaResponseDTO> create(@RequestBody MatriculaRequestDTO requestDTO){
        MatriculaResponseDTO matricula = matriculaService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(matricula);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatriculaResponseDTO> update(@RequestBody MatriculaRequestDTO requestDTO, @PathVariable long id){
        MatriculaResponseDTO matricula = matriculaService.update( requestDTO, id);
        return ResponseEntity.ok(matricula);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        matriculaService.findById(id);
        return ResponseEntity.noContent().build();
    }

}
