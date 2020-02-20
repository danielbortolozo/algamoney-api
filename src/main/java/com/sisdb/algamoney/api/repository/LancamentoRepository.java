package com.sisdb.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisdb.algamoney.api.model.Lancamento;
import com.sisdb.algamoney.api.repository.lancamento.LancamentoRepositoryQuery;



public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {
	
	

}
