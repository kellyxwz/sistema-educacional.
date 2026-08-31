package com.repository;

import com.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TurmaRespository extends JpaRepository<Turma, Long>, JpaSpecificationExecutor<Turma> {


}
