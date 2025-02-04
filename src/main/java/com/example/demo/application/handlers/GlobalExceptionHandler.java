package com.example.demo.application.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.domain.exceptions.BusinessException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Captura exceções genéricas (não tratadas)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Ocorreu um erro inesperado: " + ex.getMessage());
	}

	// Captura exceções de negócio personalizadas (se houver)
	@ExceptionHandler(BusinessException.class) // Substitua por sua exceção personalizada
	public ResponseEntity<String> handleBusinessException(BusinessException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro de negócio: " + ex.getMessage());
	}
}