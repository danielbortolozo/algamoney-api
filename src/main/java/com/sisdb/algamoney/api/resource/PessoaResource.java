package com.sisdb.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sisdb.algamoney.api.event.RecursoCriadoEvent;
import com.sisdb.algamoney.api.model.Categoria;
import com.sisdb.algamoney.api.model.Lancamento;
import com.sisdb.algamoney.api.model.Pessoa;
import com.sisdb.algamoney.api.repository.PessoaRepository;
import com.sisdb.algamoney.api.repository.filter.LancamentoFilter;
import com.sisdb.algamoney.api.repository.filter.PessoaFilter;
import com.sisdb.algamoney.api.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
@CrossOrigin(maxAge = 10, origins = { "http://localhost:4200"} )
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private ApplicationEventPublisher publisher;

//	@GetMapping
//	public List<Pessoa> listar() {
//		return pessoaRepository.findAll();
//	}
	
	@GetMapping
	public Page<Pessoa> pesquisar(PessoaFilter pessoaFilter, Pageable pageable) {
		return pessoaRepository.filtrar(pessoaFilter, pageable);
	}
	
	@GetMapping("/listar")
	public List<Pessoa> listar() {
		return pessoaRepository.findAll();
	}
	

	@PostMapping
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa entity, HttpServletResponse response) {

		Pessoa pessoaSalva = pessoaRepository.save(entity);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}

	@GetMapping(path = { "/{codigo}" })
	public ResponseEntity<?> findById(@PathVariable long codigo) {
		return pessoaRepository.findById(codigo).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa) {
		Pessoa pessoaSalva = pessoaService.atualizar(codigo, pessoa);
		return ResponseEntity.ok(pessoaSalva);
	}

	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		pessoaService.atualizarPropriedadeAtivo(codigo, ativo);
	}
	
	@DeleteMapping(path = { "/{codigo}" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		System.out.println("cheguei no excluir...>>>>");
		pessoaRepository.deleteById(codigo);
	}

}
