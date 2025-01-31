package com.example.demo.domain.models.entities;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "emprestimos")
@Data
public class Emprestimo {

	@Id
	private UUID id;

	@ManyToOne
	private Livro livro;

	@ManyToOne
	private Usuario usuario;

	@Column(nullable = false)
	private Date dataEmprestimo;

	@Column(nullable = false)
	private Date dataDevolucaoPrevista;
	private boolean devolvido;
}
