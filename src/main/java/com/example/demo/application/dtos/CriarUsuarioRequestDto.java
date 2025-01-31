package com.example.demo.application.dtos;

import lombok.Data;

@Data
public class CriarUsuarioRequestDto {

	private String nome;
	private String telefone;
	private String email;
	private String senha;
}
