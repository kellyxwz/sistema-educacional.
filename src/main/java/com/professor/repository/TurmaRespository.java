package com.professor.repository;

import com.professor.model.Turma;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TurmaRespository extends MongoRepository<Turma, String> {


}
