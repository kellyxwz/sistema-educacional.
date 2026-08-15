package com.professor.controller;

import com.professor.dto.request.TurmaRequestDTO;
import com.professor.dto.response.TurmaResponseDTO;
import com.professor.service.TurmaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turma")
public class TurmaController {

    private final TurmaService turmaService;

    public TurmaController(TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    @GetMapping
    public ResponseEntity<List<TurmaResponseDTO>> findAll(){
        List<TurmaResponseDTO> list = turmaService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaResponseDTO> findById (@PathVariable String id){
        TurmaResponseDTO turma = turmaService.findById(id);
        return ResponseEntity.ok(turma);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmaResponseDTO> update(@PathVariable String id, @RequestBody TurmaRequestDTO turmaRequestDTO){
        TurmaResponseDTO turma = turmaService.update(id, turmaRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(turma);
    }

    @PostMapping
    public ResponseEntity<TurmaResponseDTO> create(@RequestBody TurmaRequestDTO turmaRequestDTO){
        TurmaResponseDTO turma = turmaService.create(turmaRequestDTO);
        return ResponseEntity.ok(turma);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id){
        turmaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
