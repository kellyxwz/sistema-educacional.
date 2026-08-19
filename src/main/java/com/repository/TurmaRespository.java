package com.repository;

import com.model.Turma;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TurmaRespository extends MongoRepository<Turma, String> {


}
