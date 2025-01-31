package com.example.demo.application.dtos;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class EmprestimoResponseDto {

	private UUID id;
	private LivroResponseDto livro;
	private UsuarioResponseDto usuario;
	private Date dataEmprestimo;
	private Date dataDevolucaoPrevista;
	private boolean devolvido;
}
