package com.controller;

import com.dto.request.DisciplinaRequestDTO;
import com.dto.response.DisciplinaResponseDTO;
import com.service.DisciplinaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @GetMapping
    public ResponseEntity<List<DisciplinaResponseDTO>> findAll(){
        List<DisciplinaResponseDTO> list = disciplinaService.findAll();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaResponseDTO> findById(@PathVariable String id){
        DisciplinaResponseDTO disciplina = disciplinaService.findById(id);
        return ResponseEntity.ok(disciplina);
    }

    @PostMapping
    public ResponseEntity<DisciplinaResponseDTO> create(@RequestBody DisciplinaRequestDTO requestDTO){
        DisciplinaResponseDTO disciplina = disciplinaService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(disciplina);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisciplinaResponseDTO> update(@RequestBody DisciplinaRequestDTO requestDTO, @PathVariable String id){
        DisciplinaResponseDTO disciplina = disciplinaService.update(requestDTO, id);
        return ResponseEntity.ok(disciplina);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        disciplinaService.findById(id);
        return ResponseEntity.noContent().build();
    }

}
