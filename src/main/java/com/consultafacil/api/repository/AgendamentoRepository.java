package com.consultafacil.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.consultafacil.api.model.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long>{

}
