package com.example.demo.domain.exceptions;

public class AccessDeniedException extends Exception {
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Acesso negado. Usuário não encontrado.";
	}
}
