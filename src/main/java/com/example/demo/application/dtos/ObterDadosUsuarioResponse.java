package com.example.demo.application.dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class ObterDadosUsuarioResponse {

	private UUID id;
	private String nome;
	private String telefone;
	private String email;
}
