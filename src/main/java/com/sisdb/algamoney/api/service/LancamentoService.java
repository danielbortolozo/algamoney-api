package com.sisdb.algamoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.sisdb.algamoney.api.model.Lancamento;
import com.sisdb.algamoney.api.model.Pessoa;
import com.sisdb.algamoney.api.repository.LancamentoRepository;
import com.sisdb.algamoney.api.repository.PessoaRepository;
import com.sisdb.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	public Lancamento atualizar(Long codigo, Lancamento lancamento) {
		Lancamento lancamentoSalvo = buscarLancamentoCodigo(codigo);
		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");
		return lancamentoRepository.save(lancamentoSalvo);
	}

//	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
//		Pessoa pessoaSalva = buscarPessoaCodigo(codigo);		
//		pessoaSalva.setAtivo(ativo);
//		pessoaRepository.save(pessoaSalva);
//	}

	private Lancamento buscarLancamentoCodigo(Long codigo) {
		Lancamento lancamentoSalvo = lancamentoRepository.findById(codigo).orElse(null);
		if (lancamentoSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return lancamentoSalvo;
	}

	public Lancamento salvar(Lancamento lancamento) {
		Pessoa pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo()).orElse(null);
		if (pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}

		return lancamentoRepository.save(lancamento);
	}

}
