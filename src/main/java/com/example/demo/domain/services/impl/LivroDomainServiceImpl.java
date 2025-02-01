package com.example.demo.domain.services.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.application.dtos.LivroRequestDto;
import com.example.demo.application.dtos.LivroResponseDto;
import com.example.demo.application.dtos.MessageLivroResponseDto;
import com.example.demo.domain.models.entities.Livro;
import com.example.demo.domain.models.enums.StatusLivro;
import com.example.demo.domain.services.interfaces.LivroDomainService;
import com.example.demo.infrastructure.repositories.LivroRepository;

@Service
public class LivroDomainServiceImpl implements LivroDomainService {

	@Autowired
	private LivroRepository livroRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public LivroResponseDto registrarLivro(LivroRequestDto request) throws Exception {

		var livro = modelMapper.map(request, Livro.class);
		livro.setId(UUID.randomUUID());

		livroRepository.save(livro);

		return modelMapper.map(livro, LivroResponseDto.class);
	}

	@Override
	public MessageLivroResponseDto editarLivro(UUID id, LivroRequestDto request) throws Exception {

		var livro = livroRepository.findById(id).get();

		modelMapper.map(request, livro);

		livroRepository.save(livro);

		var livroResponseDto = modelMapper.map(livro, LivroResponseDto.class);
		var mensagem = "O livro " + id + " foi atualizado com sucesso.";

		return new MessageLivroResponseDto(livroResponseDto, mensagem);
	}

	@Override
	public LivroResponseDto atualizarStatus(UUID id, StatusLivro status) {

		var livro = livroRepository.findById(id).get();
		livro.setStatus(status);

		livroRepository.save(livro);

		return modelMapper.map(livro, LivroResponseDto.class);
	}

	@Override
	public MessageLivroResponseDto apagarLivro(UUID id) throws Exception {

		var livro = livroRepository.findById(id).get();

		livroRepository.delete(livro);

		var livroResponseDto = modelMapper.map(livro, LivroResponseDto.class);
		var mensagem = "Livro " + id + " excluído com sucesso!";

		return new MessageLivroResponseDto(livroResponseDto, mensagem);
	}

	@Override
	public LivroResponseDto buscarLivroPorId(UUID id) throws Exception {

		var livro = livroRepository.findById(id).get();

		return modelMapper.map(livro, LivroResponseDto.class);
	}

	@Override
	public List<LivroResponseDto> consultarLivros() throws Exception {
		return livroRepository.findAll().stream().map(livro -> modelMapper.map(livro, LivroResponseDto.class)).toList();
	}

	@Override
	public List<LivroResponseDto> buscarLivrosPorTitulo(String titulo) throws Exception {
		return livroRepository.findByTitulo(titulo).stream().map(livro -> modelMapper.map(livro, LivroResponseDto.class)).toList();
	}

	@Override
	public List<LivroResponseDto> buscarLivroPorAutor(String autor) throws Exception {
		return livroRepository.findByAutor(autor).stream().map(livro -> modelMapper.map(livro, LivroResponseDto.class)).toList();
	}

	@Override
	public List<LivroResponseDto> buscarLivroPorEditora(String editora) throws Exception {
		return livroRepository.findByEditora(editora).stream().map(livro -> modelMapper.map(livro, LivroResponseDto.class)).toList();
	}

	@Override
	public boolean verificarDisponibilidade(UUID id) throws Exception {
		
		var livro = livroRepository.findById(id).get();
		
		return livro.getStatus() == StatusLivro.DISPONIVEL && livro.getEmprestadoPara() == null;
	}
}
