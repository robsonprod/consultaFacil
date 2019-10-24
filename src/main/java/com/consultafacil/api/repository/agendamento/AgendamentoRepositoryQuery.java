package com.consultafacil.api.repository.agendamento;

import java.util.List;

import com.consultafacil.api.model.Agendamento;
import com.consultafacil.api.repository.filter.AgendamentoFilter;

public interface AgendamentoRepositoryQuery {

	public List<Agendamento> filtrar(AgendamentoFilter agendamentoFilter);
	
}
