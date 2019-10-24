package com.consultafacil.api.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.consultafacil.api.event.RecursoCriadoEvent;
import com.consultafacil.api.exceptionhandler.ConsultaFacilExceptionHandler.Erro;
import com.consultafacil.api.model.Agendamento;
import com.consultafacil.api.repository.AgendamentoRepository;
import com.consultafacil.api.repository.filter.AgendamentoFilter;
import com.consultafacil.api.service.AgendamentoService;
import com.consultafacil.api.service.exception.MedicoInexistenteOuInativoException;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoResource {

	@Autowired
	private AgendamentoRepository agendamentoRepository;
	
	@Autowired
	private AgendamentoService agendamentoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public Page<Agendamento> listar(Pageable pageable) {
		return agendamentoRepository.findAllPage(pageable);
	}
	
	@PostMapping
	public ResponseEntity<Agendamento> criar(@Valid @RequestBody Agendamento agendamento, HttpServletResponse response){
		
		Agendamento agendamentoSalvo = agendamentoService.salvar(agendamento);
		
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
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		agendamentoRepository.delete(codigo);
	}
	
	@ExceptionHandler({MedicoInexistenteOuInativoException.class})
	public ResponseEntity<?> handleMedicoInexistenteOuInativoException(MedicoInexistenteOuInativoException ex, WebRequest request){
		String mensagemUsuario = messageSource.getMessage("msg.medico.indexistente", null, LocaleContextHolder.getLocale());
		//pegando a excecao
		String msgDev = ExceptionUtils.getRootCauseMessage(ex);
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, msgDev));
		
		return ResponseEntity.badRequest().body(erros);
	}
	
//	@GetMapping("/{codigo}")
//	public List<Agendamento> pesquisar(AgendamentoFilter agendamentoFilter){
//		return agendamentoRepository.filtrar(agendamentoFilter);
//	}
}
