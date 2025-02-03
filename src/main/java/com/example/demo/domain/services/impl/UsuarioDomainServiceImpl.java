package com.example.demo.domain.services.impl;

import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.application.dtos.AutenticarUsuarioRequestDto;
import com.example.demo.application.dtos.AutenticarUsuarioResponseDto;
import com.example.demo.application.dtos.CriarUsuarioRequestDto;
import com.example.demo.application.dtos.CriarUsuarioResponseDto;
import com.example.demo.domain.exceptions.AccessDeniedException;
import com.example.demo.domain.models.entities.Usuario;
import com.example.demo.domain.services.interfaces.UsuarioDomainService;
import com.example.demo.infrastructure.components.SHA256Component;
import com.example.demo.infrastructure.repositories.UsuarioRepository;

@Service
public class UsuarioDomainServiceImpl implements UsuarioDomainService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private SHA256Component sha256Component;

	@Override
	public CriarUsuarioResponseDto criarUsuario(CriarUsuarioRequestDto request) throws Exception {
		
		if (usuarioRepository.existsByEmail(request.getEmail())) 
			throw new IllegalArgumentException("Já existe um usuário cadastrado com o e-mail: " + request.getEmail());
		
		if (usuarioRepository.existsByTelefone(request.getTelefone()))
			throw new IllegalArgumentException("Já existe um usuário cadastrado com o telefone: " + request.getTelefone());
		
		var usuario = new Usuario();
		usuario.setId(UUID.randomUUID());
		usuario.setNome(request.getNome());
		usuario.setTelefone(request.getTelefone());
		usuario.setEmail(request.getEmail());
		usuario.setSenha(sha256Component.hash(request.getSenha()));
		
		usuarioRepository.save(usuario);
		
		return modelMapper.map(usuario, CriarUsuarioResponseDto.class);
	}

	@Override
	public AutenticarUsuarioResponseDto autenticarUsuario(AutenticarUsuarioRequestDto request) throws Exception {
		
		var usuario = usuarioRepository.findByEmailAndSenha(request.getEmail(), sha256Component.hash(request.getSenha()));
		
		if (usuario == null)
			throw new AccessDeniedException();
		
		var response = new AutenticarUsuarioResponseDto();
		response.setId(usuario.getId());
		response.setNome(usuario.getNome());
		response.setTelefone(usuario.getTelefone());
		response.setEmail(usuario.getEmail());
		response.setToken(null);
		response.setDataHoraAcesso(new Date());
		response.setDataHoraExpiracao(null);
		
		return response;
	}
}
