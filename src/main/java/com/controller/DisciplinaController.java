package com.controller;

import com.dto.request.DisciplinaRequestDTO;
import com.dto.response.CursoResponseDTO;
import com.dto.response.DisciplinaResponseDTO;
import com.service.DisciplinaService;
import org.springframework.data.domain.Page;
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

    @GetMapping("/busca")
    public Page<DisciplinaResponseDTO> buscaAvancada(@RequestParam(defaultValue = "0")int page,
                                                @RequestParam(defaultValue = "5")int size,
                                                @RequestParam(defaultValue = "id")String sortBy,
                                                @RequestParam(defaultValue = "asc")String direction,
                                                @RequestParam(required = false)String nome,
                                                @RequestParam(required = false)Integer cargaHoraria){
        return disciplinaService.buscaAvancada(page, size, sortBy, direction, nome, cargaHoraria);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaResponseDTO> findById(@PathVariable long id){
        DisciplinaResponseDTO disciplina = disciplinaService.findById(id);
        return ResponseEntity.ok(disciplina);
    }

    @PostMapping
    public ResponseEntity<DisciplinaResponseDTO> create(@RequestBody DisciplinaRequestDTO requestDTO){
        DisciplinaResponseDTO disciplina = disciplinaService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(disciplina);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisciplinaResponseDTO> update(@RequestBody DisciplinaRequestDTO requestDTO, @PathVariable long id){
        DisciplinaResponseDTO disciplina = disciplinaService.update(requestDTO, id);
        return ResponseEntity.ok(disciplina);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        disciplinaService.findById(id);
        return ResponseEntity.noContent().build();
    }

}
