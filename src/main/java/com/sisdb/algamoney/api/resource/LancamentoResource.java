package com.sisdb.algamoney.api.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sisdb.algamoney.api.event.RecursoCriadoEvent;
import com.sisdb.algamoney.api.exceptionhandler.AlgamoneyExceptionHandler.Erro;
import com.sisdb.algamoney.api.model.Lancamento;
import com.sisdb.algamoney.api.model.Pessoa;
import com.sisdb.algamoney.api.repository.LancamentoRepository;
import com.sisdb.algamoney.api.repository.filter.LancamentoFilter;
import com.sisdb.algamoney.api.service.LancamentoService;
import com.sisdb.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

@RestController
@RequestMapping("/lancamentos")
@CrossOrigin(maxAge = 10, origins = { "http://localhost:4200"} )
public class LancamentoResource {

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ApplicationEventPublisher publisher;

	@PostMapping
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		System.out.println("cheguei no back-end"+lancamento.toString());
		Lancamento lancamentoSalvo = lancamentoService.salvar(lancamento);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Lancamento> atualizar(@PathVariable Long codigo, @Valid @RequestBody Lancamento lanc) {
		System.out.println("passei no atualizar????");
		Lancamento lancSalva = lancamentoService.atualizar(codigo, lanc);
		return ResponseEntity.ok(lancSalva);
	}
	
	@GetMapping
	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoRepository.filtrar(lancamentoFilter, pageable);
	}

//	@GetMapping
//	public List<Lancamento> listar() {
//		return lancamentoRepository.findAll();
//	}
//	
	@GetMapping(path = {"/{codigo}"})
	public ResponseEntity<?> findById(@PathVariable Long codigo){
	   return lancamentoRepository.findById(codigo)
	           .map(record -> ResponseEntity.ok().body(record))
	           .orElse(ResponseEntity.notFound().build());
	}

	@ExceptionHandler({ PessoaInexistenteOuInativaException.class })
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {
		String mensagemUsuario = messageSource.getMessage("pessoa-inexistente-ou-inativa", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return ResponseEntity.badRequest().body(erros);
	}
	
	@DeleteMapping(path = { "/{codigo}" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		System.out.println("cheguei no excluir...>>>>");
		lancamentoRepository.deleteById(codigo);
	}
	
	
	

}
