package com.consultafacil.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consultafacil.api.model.Agendamento;
import com.consultafacil.api.model.Medico;
import com.consultafacil.api.repository.AgendamentoRepository;
import com.consultafacil.api.repository.MedicoRepository;
import com.consultafacil.api.service.exception.MedicoInexistenteOuInativoException;

@Service
public class AgendamentoService {

	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private AgendamentoRepository agendamentoRepository;
	
	public Agendamento salvar(Agendamento agen) {

		Medico medico = medicoRepository.findOne(agen.getMedico().getCodigo());
		if(medico == null || medico.getUsuario().isInativo()) {
			throw new MedicoInexistenteOuInativoException();
		}
				
		return agendamentoRepository.save(agen);
	}
	
}
