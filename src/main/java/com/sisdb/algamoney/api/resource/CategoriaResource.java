package com.sisdb.algamoney.api.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sisdb.algamoney.api.model.Categoria;
import com.sisdb.algamoney.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	
//	@GetMapping
//	public ResponseEntity<?>  listar() {
//		List<Categoria> categorias = categoriaRepository.findAll();
//		return !categorias.isEmpty() ? ResponseEntity.ok(categorias) : ResponseEntity.notFound().build();		
//	}
	
	@GetMapping
	public List<Categoria> listar() {
		return categoriaRepository.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Categoria> criar(@RequestBody Categoria entity, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(entity);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
		.buildAndExpand(categoriaSalva.getCodigo()).toUri();		
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(categoriaSalva);
	}
	
//	@GetMapping("/{codigo}")
//	public Categoria buscarPeloCodigo(@PathVariable Long codigo) {
//		return categoriaRepository.findById(codigo).orElse(null);
//	}
	
//	@GetMapping("/{codigo}")
//	public ResponseEntity<Categoria> buscarId(@PathVariable Long codigo) {
//		Categoria categoria = categoriaRepository.findById(codigo);		
//		return categoria.getCodigo() != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
//	}
	
	@GetMapping(path = {"/{codigo}"})
	public ResponseEntity findById(@PathVariable long codigo){
	   return categoriaRepository.findById(codigo)
	           .map(record -> ResponseEntity.ok().body(record))
	           .orElse(ResponseEntity.notFound().build());
	}

}








