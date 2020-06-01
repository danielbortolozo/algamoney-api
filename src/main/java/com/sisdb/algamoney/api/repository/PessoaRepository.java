package com.sisdb.algamoney.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisdb.algamoney.api.model.Pessoa;
import com.sisdb.algamoney.api.repository.pessoa.PessoaRepositoryQuery;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQuery  {
	
	
    List<Pessoa> findByNome(String nome);
    
}
