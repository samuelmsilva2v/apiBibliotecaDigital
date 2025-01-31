package com.example.demo.application.dtos;

import java.util.UUID;

import com.example.demo.domain.models.enums.StatusLivro;

import lombok.Data;

@Data
public class LivroResponseDto {

	private UUID id;
	private String titulo;
	private String autor;
	private String editora;
	private Integer anoPublicacao;
	private StatusLivro status;
	private UUID emprestadoPara;
}
