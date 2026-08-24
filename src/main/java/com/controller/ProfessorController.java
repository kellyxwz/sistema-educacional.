package com.controller;

import com.dto.request.ProfessorRequestDTO;
import com.dto.response.ProfessorResponseDTO;
import com.service.ProfessorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping
    public ResponseEntity<List<ProfessorResponseDTO>> findAll(){
        List<ProfessorResponseDTO> list = professorService.findAll();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> findById(@PathVariable long id){
        ProfessorResponseDTO professorResponse = professorService.findById(id);
        return ResponseEntity.ok(professorResponse);
    }

    @PostMapping
    public ResponseEntity<ProfessorResponseDTO> create(@RequestBody ProfessorRequestDTO requestDTO){
        ProfessorResponseDTO professor = professorService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(professor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> update(@RequestBody ProfessorRequestDTO requestDTO, @PathVariable long id){
        ProfessorResponseDTO professor = professorService.update(id, requestDTO);
        return ResponseEntity.ok(professor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        professorService.findById(id);
        return ResponseEntity.noContent().build();
    }

}
