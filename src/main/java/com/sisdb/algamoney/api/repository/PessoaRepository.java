package com.sisdb.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisdb.algamoney.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
	

}
