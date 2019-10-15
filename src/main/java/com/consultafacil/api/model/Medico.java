package com.consultafacil.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "medico")
public class Medico extends Usuario{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@Column
	@NotNull
	private String crm;
	
	@Column
	private String clinica;
	
	public String getCrm() {
		return crm;
	}
	public void setCrm(String crm) {
		this.crm = crm;
	}
	public String getClinica() {
		return clinica;
	}
	public void setClinica(String clinica) {
		this.clinica = clinica;
	}
	
}
