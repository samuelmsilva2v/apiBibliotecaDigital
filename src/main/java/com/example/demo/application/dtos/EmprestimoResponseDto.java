package com.example.demo.application.dtos;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class EmprestimoResponseDto {

	private UUID id;
	private LivroResponseDto livro;
	
	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "UTC")
	private Date dataEmprestimo;
	
	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "UTC")
	private Date dataDevolucaoPrevista;
	private boolean devolvido;
}
