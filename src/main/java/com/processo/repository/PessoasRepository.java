package com.processo.repository;

import com.processo.model.Pessoa;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PessoasRepository extends MongoRepository<Pessoa, String> {

    public Pessoa findByCpf(String cpf);
    
    public Pessoa findByIdNotAndCpf(String id, String cpf);
}
