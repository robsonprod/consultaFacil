package com.consultafacil.api.repository.agendamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import com.consultafacil.api.model.Agendamento;
import com.consultafacil.api.repository.filter.AgendamentoFilter;

import jdk.nashorn.internal.objects.annotations.Where;

public class AgendamentoRepositoryImpl implements AgendamentoRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	
	public List<Agendamento> filtrar(AgendamentoFilter agendamentoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		CriteriaQuery<Agendamento> criteria = builder.createQuery(Agendamento.class);
		
		Root<Agendamento> root = criteria.from(Agendamento.class);
		
		
		//restricoes
		Predicate[] predicates = criarRestricoes(agendamentoFilter, builder, root);
		criteria.where(predicates);
		
		
		TypedQuery<Agendamento> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}

	private Predicate[] criarRestricoes(AgendamentoFilter agendamentoFilter, CriteriaBuilder builder,
			Root<Agendamento> root) {
		List<Predicate> predicates = new ArrayList<Predicate>();

		//where categoria like 'asdasd'
		/*
		 * if(!StringUtils.isEmpty(agendamentoFilter.getCategoria())) {
		 * predicates.add(builder.like( builder.lower(root.get("categoria.nome")), "%" +
		 * agendamentoFilter.getCategoria().getNome().toLowerCase() + "%")); }
		 */
		
		/*
		 * if(agendamentoFilter.getDataConsulta() != null) {
		 * predicates.add(builder.like(root.get("dataConsulta")), "%" +
		 * agendamentoFilter.getDataConsulta() + "%")) ); }
		 * if(agendamentoFilter.getMedico() != null) { predicates.add(e); }
		 */
		 		 
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
