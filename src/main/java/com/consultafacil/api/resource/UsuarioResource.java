package com.consultafacil.api.resource;

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

import com.consultafacil.api.event.RecursoCriadoEvent;
import com.consultafacil.api.model.Usuario;
import com.consultafacil.api.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Usuario> listar() {
		return usuarioRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario usuario, HttpServletResponse response){
		
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, usuarioSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<? extends Object> buscarPeloCodigo(@PathVariable Long codigo){
		Usuario usuario = usuarioRepository.findOne(codigo);
		
		return usuario != null 
				? ResponseEntity.ok(usuario)
						: ResponseEntity.notFound().build();
	}
}
