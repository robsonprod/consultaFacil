package com.consultafacil.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.consultafacil.api.model.Especialidade;
import com.consultafacil.api.repository.EspecialidadeRepository;

@Service
public class EspecialidadeService {
	
	@Autowired
	private EspecialidadeRepository categoriaRepository;

	public Especialidade atualizar(Long codigo, Especialidade categoria) {
		Especialidade categoriaSalva = buscarCategoriaPorCodigo(codigo);
		
		BeanUtils.copyProperties(categoria, categoriaSalva, "codigo");
		return categoriaRepository.save(categoriaSalva);
	}
	
	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Especialidade categoriaSalva = buscarCategoriaPorCodigo(codigo);
		categoriaRepository.save(categoriaSalva);
	}

	public Especialidade buscarCategoriaPorCodigo(Long codigo) {
		Especialidade categoriaSalva = categoriaRepository.findOne(codigo);

		if(categoriaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return categoriaSalva;
	}
	
}
