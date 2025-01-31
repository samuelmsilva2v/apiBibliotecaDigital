package com.example.demo.application.dtos;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class EmprestimoRequestDto {

	private UUID livroId;
	private UUID usuarioId;
	private Date dataEmprestimo;
	private Date dataDevolucaoPrevista;
	private boolean devolvido;
}
