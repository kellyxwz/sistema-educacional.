package com.controller;

import com.dto.request.MatriculaRequestDTO;
import com.dto.response.CursoResponseDTO;
import com.dto.response.MatriculaResponseDTO;

import com.service.MatriculaService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/matriculas")
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

    @GetMapping("/busca")
    public Page<MatriculaResponseDTO> buscaAvancada(@RequestParam(defaultValue = "0")int page,
                                                @RequestParam(defaultValue = "5")int size,
                                                @RequestParam(defaultValue = "id")String sortBy,
                                                @RequestParam(defaultValue = "asc")String direction,
                                                @RequestParam(required = false) LocalDate dataMatricula){

        return matriculaService.buscaAvancada(page, size, sortBy, direction, dataMatricula);
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
