package com.controller;

import com.dto.request.PessoaRequestDTO;
import com.dto.response.PessoaResponseDTO;
import com.service.PessoaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping
    public ResponseEntity<List<PessoaResponseDTO>> findAll(){
        List<PessoaResponseDTO> list = pessoaService.findAll();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> findById(@PathVariable String id){
        PessoaResponseDTO pessoa = pessoaService.findById(id);
        return ResponseEntity.ok(pessoa);
    }

    @PostMapping
    public ResponseEntity<PessoaResponseDTO> create(@RequestBody PessoaRequestDTO requestDTO){
        PessoaResponseDTO pessoa = pessoaService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> update(@RequestBody PessoaRequestDTO requestDTO, @PathVariable String id){
        PessoaResponseDTO pessoa = pessoaService.update( requestDTO, id);
        return ResponseEntity.ok(pessoa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        pessoaService.findById(id);
        return ResponseEntity.noContent().build();
    }

}
