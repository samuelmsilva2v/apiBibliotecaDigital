package com.example.demo.application.dtos;

import lombok.Data;

@Data
public class MessageLivroResponseDto {

	private LivroResponseDto livro;
	private String mensagem;
	
	public MessageLivroResponseDto() {
	}

	public MessageLivroResponseDto(LivroResponseDto livro, String mensagem) {
		super();
		this.livro = livro;
		this.mensagem = mensagem;
	}
}