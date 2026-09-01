package com.controller;

import com.dto.request.TurmaRequestDTO;
import com.dto.response.TurmaResponseDTO;
import com.service.TurmaService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/turma")
public class TurmaController {

    private final TurmaService turmaService;

    public TurmaController(TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    @GetMapping("/busca")
    public Page<TurmaResponseDTO> buscaAvancada(@RequestParam(defaultValue = "0")int page,
                                                @RequestParam(defaultValue = "10")int size,
                                                @RequestParam(defaultValue = "id")String sortBy,
                                                @RequestParam(defaultValue = "asc") String direction,
                                                @RequestParam(required = false) String nome,
                                                @RequestParam(required = false) LocalDate data){
        return turmaService.buscaAvancada(page, size, sortBy, direction, nome, data);
    }

    @GetMapping
    public ResponseEntity<List<TurmaResponseDTO>> findAll(){
        List<TurmaResponseDTO> list = turmaService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaResponseDTO> findById (@PathVariable long id){
        TurmaResponseDTO turma = turmaService.findById(id);
        return ResponseEntity.ok(turma);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmaResponseDTO> update(@PathVariable long id, @RequestBody TurmaRequestDTO turmaRequestDTO){
        TurmaResponseDTO turma = turmaService.update(id, turmaRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(turma);
    }

    @PostMapping
    public ResponseEntity<TurmaResponseDTO> create(@RequestBody TurmaRequestDTO turmaRequestDTO){
        TurmaResponseDTO turma = turmaService.create(turmaRequestDTO);
        return ResponseEntity.ok(turma);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id){
        turmaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
