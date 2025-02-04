package com.example.demo.application.dtos;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AutenticarUsuarioResponseDto {

	private UUID id;
	private String nome;
	private String email;
	private String telefone;
	private String token;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataHoraAcesso;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataHoraExpiracao;
}
