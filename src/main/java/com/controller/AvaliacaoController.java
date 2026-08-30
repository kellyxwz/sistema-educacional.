package com.controller;

import com.dto.request.AvaliacaoRequestDTO;
import com.dto.response.AvaliacaoResponseDTO;
import com.service.AvaliacaoService;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @GetMapping
    public ResponseEntity<List<AvaliacaoResponseDTO>> findAll(){
        List<AvaliacaoResponseDTO> list = avaliacaoService.findAll();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> findById(@PathVariable long id){
        AvaliacaoResponseDTO avaliacao = avaliacaoService.findById(id);
        return ResponseEntity.ok(avaliacao);
    }

    @PostMapping
    public ResponseEntity<AvaliacaoResponseDTO> create(@RequestBody AvaliacaoRequestDTO requestDTO){
        AvaliacaoResponseDTO avaliacao = avaliacaoService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(avaliacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> update(@RequestBody AvaliacaoRequestDTO requestDTO, @PathVariable long id){
        AvaliacaoResponseDTO avaliacao = avaliacaoService.update( requestDTO, id);
        return ResponseEntity.ok(avaliacao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        avaliacaoService.findById(id);
        return ResponseEntity.noContent().build();
    }

}
