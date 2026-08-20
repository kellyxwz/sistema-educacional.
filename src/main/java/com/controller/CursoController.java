package com.controller;

import com.dto.request.CursoRequestDTO;

import com.dto.response.CursoResponseDTO;

import com.service.CursoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/curso")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> findAll(){
        List<CursoResponseDTO> list = cursoService.findAll();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> findById(@PathVariable String id){
        CursoResponseDTO curso = cursoService.findById(id);
        return ResponseEntity.ok(curso);
    }

    @PostMapping
    public ResponseEntity<CursoResponseDTO> create(@RequestBody CursoRequestDTO requestDTO){
        CursoResponseDTO curso = cursoService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(curso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> update(@RequestBody CursoRequestDTO requestDTO, @PathVariable String id){
        CursoResponseDTO curso = cursoService.update( requestDTO, id);
        return ResponseEntity.ok(curso);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        cursoService.findById(id);
        return ResponseEntity.noContent().build();
    }

}
