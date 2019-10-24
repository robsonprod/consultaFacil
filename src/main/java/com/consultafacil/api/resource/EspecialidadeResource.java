package com.consultafacil.api.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.consultafacil.api.model.Especialidade;
import com.consultafacil.api.repository.EspecialidadeRepository;
import com.consultafacil.api.service.EspecialidadeService;

@RestController
@RequestMapping("/categorias")
public class EspecialidadeResource {

	@Autowired
	private EspecialidadeRepository categoriaRepository;
	
	@Autowired
	private EspecialidadeService categoriaService;

	@GetMapping
	public List<Especialidade> listar() {
		return categoriaRepository.findAll();
	}

	/*
	 * @GetMapping public ResponseEntity<?> listar(){
	 * List<Categoria> categorias = categoriaRepository.findAll(); 
	 * 		return !categorias.isEmpty() ? ResponseEntity.ok(categorias) : ResponseEntity.noContent().build();
	 * noContet() 204 
	 * }
	 */
	
//	recuperando uri
	@PostMapping
//	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Especialidade> criar(@Valid @RequestBody Especialidade categoria, HttpServletResponse response) {
		Especialidade categoriaSalva = categoriaRepository.save(categoria);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{codigo}").buildAndExpand(categoriaSalva.getCodigo()).toUri();
		
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(categoriaSalva);
	}

	@GetMapping("/{cod}")
	public ResponseEntity<? extends Object> buscarPeloCodigo(@PathVariable Long cod) {
		Especialidade categoria = categoriaRepository.findOne(cod);
		return categoria != null 
				? ResponseEntity.ok(categoria) 
						: ResponseEntity.notFound().build();
		
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		categoriaRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Especialidade> atualizar(@PathVariable Long codigo, @Valid @RequestBody Especialidade categoria){
		Especialidade categoriaSalva = categoriaService.atualizar(codigo, categoria);
		return ResponseEntity.ok(categoriaSalva);
	}
	
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		categoriaService.atualizarPropriedadeAtivo(codigo, ativo);
	}
	
}
