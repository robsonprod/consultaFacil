package com.consultafacil.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.consultafacil.api.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long>{

}
