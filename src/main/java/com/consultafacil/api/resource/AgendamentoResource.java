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
import com.consultafacil.api.model.Agendamento;
import com.consultafacil.api.repository.AgendamentoRepository;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoResource {

	@Autowired
	private AgendamentoRepository agendamentoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Agendamento> listar() {
		return agendamentoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Agendamento> criar(@Valid @RequestBody Agendamento agendamento, HttpServletResponse response){
		
		Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, agendamentoSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoSalvo);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<? extends Object> buscarPeloCodigo(@PathVariable Long codigo){
		Agendamento agendamento = agendamentoRepository.findOne(codigo);
		
		return agendamento != null 
				? ResponseEntity.ok(agendamento)
						: ResponseEntity.notFound().build();
	}
	
}
