package com.consultafacil.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.consultafacil.api.model.Agendamento;
import com.consultafacil.api.repository.agendamento.AgendamentoRepositoryQuery;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long>, AgendamentoRepositoryQuery{

	@Query("SELECT a FROM Agendamento a INNER JOIN a.usuario u WHERE u IS NOT NULL")
    Page<Agendamento> findAllPage(Pageable pageable);
	
}
