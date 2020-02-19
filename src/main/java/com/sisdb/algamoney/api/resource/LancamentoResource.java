package com.sisdb.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisdb.algamoney.api.event.RecursoCriadoEvent;
import com.sisdb.algamoney.api.model.Lancamento;
import com.sisdb.algamoney.api.repository.LancamentoRepository;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	
	@PostMapping	
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento entity, HttpServletResponse response) {
		
		Lancamento lancamentoSalvo = lancamentoRepository.save(entity);
		lancamentoSalvo = lancamentoRepository.getOne(lancamentoSalvo.getCodigo());
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}
	
	@GetMapping
	public List<Lancamento> listar() {
		return lancamentoRepository.findAll();
	}
	
	@GetMapping(path = {"/{codigo}"})
	public ResponseEntity<?> findById(@PathVariable Long codigo){
	   return lancamentoRepository.findById(codigo)
	           .map(record -> ResponseEntity.ok().body(record))
	           .orElse(ResponseEntity.notFound().build());
	}
		

}
