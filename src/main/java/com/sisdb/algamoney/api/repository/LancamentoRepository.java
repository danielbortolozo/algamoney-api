package com.sisdb.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisdb.algamoney.api.model.Lancamento;



public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

}
