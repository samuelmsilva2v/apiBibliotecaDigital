package com.example.demo.domain.services.interfaces;

import java.util.List;
import java.util.UUID;

import com.example.demo.application.dtos.LivroRequestDto;
import com.example.demo.application.dtos.LivroResponseDto;
import com.example.demo.application.dtos.MessageLivroResponseDto;
import com.example.demo.domain.models.enums.StatusLivro;

public interface LivroDomainService {

	LivroResponseDto registrarLivro(LivroRequestDto request) throws Exception;
	
	MessageLivroResponseDto editarLivro(UUID id, LivroRequestDto request) throws Exception;
	
	LivroResponseDto atualizarStatus(UUID id, StatusLivro status);
	
	MessageLivroResponseDto apagarLivro(UUID id) throws Exception;
	
	LivroResponseDto buscarLivroPorId(UUID id) throws Exception;
	
	List<LivroResponseDto> consultarLivros() throws Exception;
	
	List<LivroResponseDto> buscarLivrosPorTitulo(String titulo) throws Exception;
	
	List<LivroResponseDto> buscarLivroPorAutor(String autor) throws Exception;
	
	List<LivroResponseDto> buscarLivroPorEditora(String editora) throws Exception;
	
	boolean verificarDisponibilidade(UUID id) throws Exception;
}
