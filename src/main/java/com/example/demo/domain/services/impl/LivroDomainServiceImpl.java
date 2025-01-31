package com.example.demo.domain.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.application.dtos.LivroRequestDto;
import com.example.demo.application.dtos.LivroResponseDto;
import com.example.demo.application.dtos.MessageLivroResponseDto;
import com.example.demo.domain.models.enums.StatusLivro;
import com.example.demo.domain.services.interfaces.LivroDomainService;

@Service
public class LivroDomainServiceImpl implements LivroDomainService {

	@Override
	public LivroResponseDto registrarLivro(LivroRequestDto request) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageLivroResponseDto editarLivro(UUID id, LivroRequestDto request) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LivroResponseDto atualizarStatus(UUID id, StatusLivro status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageLivroResponseDto apagarLivro(UUID id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LivroResponseDto buscarLivroPorId(UUID id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LivroResponseDto> consultarLivros() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LivroResponseDto> buscarLivrosPorTitulo(String titulo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LivroResponseDto> buscarLivroPorAutor(String autor) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LivroResponseDto> buscarLivroPorEditora(String editora) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean verificarDisponibilidade(UUID id) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
}
