package com.sisdb.algamoney.api.repository.lancamento;

import java.util.List;

import com.sisdb.algamoney.api.model.Lancamento;
import com.sisdb.algamoney.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	
	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
	
}
