package com.service;

import com.dto.request.AvaliacaoRequestDTO;

import com.dto.response.AvaliacaoResponseDTO;
import com.model.Avalicao;
import com.repository.AvalicaoRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class AvaliacaoService {

    private final AvalicaoRepository avalicaoRepository;

    public AvaliacaoService(AvalicaoRepository avalicaoRepository) {
        this.avalicaoRepository = avalicaoRepository;
    }

    public List<AvaliacaoResponseDTO> findAll(){
        return avalicaoRepository.findAll().stream().map(AvaliacaoResponseDTO :: new).toList();
    }

    public AvaliacaoResponseDTO findById(long id){
        Avalicao avaliacao = avalicaoRepository.findById(id).orElseThrow(() -> new RuntimeException("Avaliação não encontrada com o id: " + id));
        return new AvaliacaoResponseDTO(avaliacao);
    }

    public AvaliacaoResponseDTO create(AvaliacaoRequestDTO requestDTO){
        Avalicao avalicao = avalicaoRepository.save(toEntity(requestDTO));

        return new AvaliacaoResponseDTO(avalicao);
    }

    public AvaliacaoResponseDTO update(AvaliacaoRequestDTO requestDTO, long id){
        Avalicao avaliacao = avalicaoRepository.findById(id).orElseThrow(()->new RuntimeException("Avalliação não encontrada"));

        updateData(avaliacao,requestDTO);

        return new AvaliacaoResponseDTO(avaliacao);
    }

    public void deleteById(long id){
        avalicaoRepository.deleteById(id);
    }

    private void updateData(Avalicao avalicao, AvaliacaoRequestDTO dto){
        avalicao.setNota(dto.nota());
        avalicao.setData(dto.data());
    }

        public static Avalicao toEntity(AvaliacaoRequestDTO dto){
        Avalicao avaliacao = new Avalicao();
        avaliacao.setNota(dto.nota());
        avaliacao.setData(dto.data());
        avaliacao.setAtivo(true);
        return avaliacao;
    }


}
