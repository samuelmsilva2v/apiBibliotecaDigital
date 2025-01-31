package com.example.demo.application.dtos;

import lombok.Data;

@Data
public class MessageLivroResponseDto {

	private LivroResponseDto evento;
	private String mensagem;
	
	public MessageLivroResponseDto(LivroResponseDto evento, String mensagem) {
		super();
		this.evento = evento;
		this.mensagem = mensagem;
	}
}
