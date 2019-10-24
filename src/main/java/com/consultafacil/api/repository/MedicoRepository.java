package com.consultafacil.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.consultafacil.api.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long>{

	/*
	 * @Query("SELECT m FROM Medico m INNER JOIN m.usuario u WHERE u IS NOT NULL")
	 * Page<Medico> findAllPage(Pageable pageable);
	 */
	
}
