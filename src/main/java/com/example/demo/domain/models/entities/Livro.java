package com.example.demo.domain.models.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "livros")
@Data
public class Livro {

	@Id
	private UUID id;
	
	@Column(nullable = false, unique = true)
	private String titulo;
	
	@Column(nullable = false)
	private String autor;
	
	@Column(nullable = false)
	private String editora;
	
	@Column(nullable = false)
	private Integer anoPublicacao;
	// TODO private StatusLivro status;
	
	@ManyToOne
	private Usuario emprestadoPara;
}
