package com.example.demo.application.dtos;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class AutenticarUsuarioResponseDto {

	private UUID id;
	private String nome;
	private String email;
	private String telefone;
	private String token;
	private Date dataHoraAcesso;
	private Date dataHoraExpiracao;
}
