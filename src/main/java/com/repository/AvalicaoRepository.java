package com.repository;

import com.model.Avalicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AvalicaoRepository extends JpaRepository<Avalicao, Long>, JpaSpecificationExecutor<Avalicao> {
}
