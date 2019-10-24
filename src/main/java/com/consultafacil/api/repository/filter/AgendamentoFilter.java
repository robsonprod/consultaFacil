package com.consultafacil.api.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.consultafacil.api.model.Especialidade;
import com.consultafacil.api.model.Medico;

public class AgendamentoFilter {

	private Medico medico;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataConsulta;
	
	private Especialidade categoria;

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public LocalDate getDataConsulta() {
		return dataConsulta;
	}

	public void setDataConsulta(LocalDate dataConsulta) {
		this.dataConsulta = dataConsulta;
	}

	public Especialidade getCategoria() {
		return categoria;
	}

	public void setCategoria(Especialidade categoria) {
		this.categoria = categoria;
	}
	
}
