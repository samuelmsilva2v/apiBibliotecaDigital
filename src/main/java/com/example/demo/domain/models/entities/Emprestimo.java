package com.example.demo.domain.models.entities;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "emprestimos")
@Data
public class Emprestimo {

	@Id
	private UUID id;
	private Livro livro;
	private Usuario usuario;
	private Date dataEmprestimo;
	private Date dataDevolucaoPrevista;
	private boolean devolvido;
}
