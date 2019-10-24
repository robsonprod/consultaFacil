package com.consultafacil.api.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.consultafacil.api.event.RecursoCriadoEvent;
import com.consultafacil.api.model.Agendamento;
import com.consultafacil.api.model.Especialidade;
import com.consultafacil.api.model.Medico;
import com.consultafacil.api.repository.MedicoRepository;

import sun.security.provider.certpath.ResponderId;

@RestController
@RequestMapping("/medicos")
public class MedicoResource {

	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Medico> listar() {
		return medicoRepository.findAll();
	}
//	@GetMapping
//	public Page<Medico> listar(Pageable pageable) {
//		return medicoRepository.findAllPage(pageable);
//	}
	
	@PostMapping
	public ResponseEntity<Medico> criar(@Valid @RequestBody Medico medico, HttpServletResponse response){
		
		Medico medicoSalvo = medicoRepository.save(medico);
		
		/*
		 * URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
		 * .path("/{codigo}").buildAndExpand(medicoSalvo.getCodigo()).toUri();
		 * 
		 * response.setHeader("Location", uri.toASCIIString());
		 */
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, medicoSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(medicoSalvo);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<? extends Object> buscarPeloCodigo(@PathVariable Long codigo){
		Medico medico = medicoRepository.findOne(codigo);
		
		return medico != null 
				? ResponseEntity.ok(medico)
						: ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		medicoRepository.delete(codigo);
	}
	
}
