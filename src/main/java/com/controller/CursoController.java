package com.controller;

import com.dto.request.CursoRequestDTO;

import com.dto.response.CursoResponseDTO;

import com.service.CursoService;

import org.springframework.data.domain.Page;
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

    @GetMapping("/busca")
    public Page<CursoResponseDTO> buscaAvancada(@RequestParam(defaultValue = "0")int page,
                                                @RequestParam(defaultValue = "5")int size,
                                                @RequestParam(defaultValue = "id")String sortBy,
                                                @RequestParam(defaultValue = "asc")String direction,
                                                @RequestParam(required = false)String nome,
                                                @RequestParam(required = false)String descricao,
                                                @RequestParam(required = false)Integer cargaHoraria){
        return cursoService.buscaAvancada(page, size, sortBy, direction, nome, descricao, cargaHoraria);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> findById(@PathVariable long id){
        CursoResponseDTO curso = cursoService.findById(id);
        return ResponseEntity.ok(curso);
    }

    @PostMapping
    public ResponseEntity<CursoResponseDTO> create(@RequestBody CursoRequestDTO requestDTO){
        CursoResponseDTO curso = cursoService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(curso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> update(@RequestBody CursoRequestDTO requestDTO, @PathVariable long id){
        CursoResponseDTO curso = cursoService.update( requestDTO, id);
        return ResponseEntity.ok(curso);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        cursoService.findById(id);
        return ResponseEntity.noContent().build();
    }

}
