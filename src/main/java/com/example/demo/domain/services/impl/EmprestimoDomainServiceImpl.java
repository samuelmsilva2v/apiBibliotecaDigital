package com.example.demo.domain.services.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.application.dtos.EmprestimoRequestDto;
import com.example.demo.application.dtos.EmprestimoResponseDto;
import com.example.demo.application.dtos.LivroResponseDto;
import com.example.demo.domain.models.entities.Emprestimo;
import com.example.demo.domain.models.enums.StatusLivro;
import com.example.demo.domain.services.interfaces.EmprestimoDomainService;
import com.example.demo.infrastructure.repositories.EmprestimoRepository;
import com.example.demo.infrastructure.repositories.LivroRepository;
import com.example.demo.infrastructure.repositories.UsuarioRepository;

@Service
public class EmprestimoDomainServiceImpl implements EmprestimoDomainService {

	@Autowired
	private EmprestimoRepository emprestimoRepository;

	@Autowired
	private LivroRepository livroRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public EmprestimoResponseDto emprestarLivro(EmprestimoRequestDto request) throws Exception {

		var livro = livroRepository.findById(request.getLivroId()).get();

		var usuario = usuarioRepository.findById(request.getUsuarioId()).get();

		var emprestimo = new Emprestimo();
		emprestimo.setId(UUID.randomUUID());
		emprestimo.setLivro(livro);
		emprestimo.setUsuario(usuario);
		emprestimo.setDataEmprestimo(request.getDataEmprestimo());
		emprestimo.setDataDevolucaoPrevista(request.getDataDevolucaoPrevista());
		emprestimo.setDevolvido(request.isDevolvido());

		livro.setStatus(StatusLivro.EMPRESTADO);
		livro.setEmprestadoPara(usuario);

		emprestimoRepository.save(emprestimo);
		livroRepository.save(livro);

		var response = new EmprestimoResponseDto();
		response.setId(emprestimo.getId());
		response.setLivro(modelMapper.map(livro, LivroResponseDto.class));
		response.setDataEmprestimo(emprestimo.getDataEmprestimo());
		response.setDataDevolucaoPrevista(emprestimo.getDataDevolucaoPrevista());
		response.setDevolvido(emprestimo.isDevolvido());

		return response;

	}

	@Override
	public EmprestimoResponseDto devolverLivro(UUID id) throws Exception {

		var emprestimo = emprestimoRepository.findById(id).get();
		var livro = emprestimo.getLivro();

		emprestimo.setDevolvido(true);

		emprestimoRepository.save(emprestimo);

		var response = new EmprestimoResponseDto();
		response.setId(emprestimo.getId());
		response.setLivro(modelMapper.map(livro, LivroResponseDto.class));
		response.setDataEmprestimo(emprestimo.getDataEmprestimo());
		response.setDataDevolucaoPrevista(emprestimo.getDataDevolucaoPrevista());
		response.setDevolvido(emprestimo.isDevolvido());

		return response;
	}

	@Override
	public List<EmprestimoResponseDto> consultarEmprestimos() throws Exception {
		return emprestimoRepository.findAll().stream().map(emprestimo -> {
			var response = new EmprestimoResponseDto();
			response.setId(emprestimo.getId());
			response.setLivro(modelMapper.map(emprestimo.getLivro(), LivroResponseDto.class));
			response.setDataEmprestimo(emprestimo.getDataEmprestimo());
			response.setDataDevolucaoPrevista(emprestimo.getDataDevolucaoPrevista());
			response.setDevolvido(emprestimo.isDevolvido());
			return response;
		}).toList();
	}
}
