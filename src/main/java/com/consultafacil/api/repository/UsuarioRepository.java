package com.consultafacil.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.consultafacil.api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
