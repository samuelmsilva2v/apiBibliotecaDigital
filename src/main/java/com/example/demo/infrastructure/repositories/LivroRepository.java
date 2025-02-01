package com.example.demo.infrastructure.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.models.entities.Livro;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

	List<Livro> findByTitulo(String titulo);
	
	List<Livro> findByAutor(String autor);
	
	List<Livro> findByEditora(String editora);
}
