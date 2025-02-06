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
import com.example.demo.application.dtos.ObterDadosUsuarioResponse;
import com.example.demo.domain.exceptions.AccessDeniedException;
import com.example.demo.domain.exceptions.BusinessException;
import com.example.demo.domain.models.entities.Usuario;
import com.example.demo.domain.models.enums.Role;
import com.example.demo.domain.services.interfaces.UsuarioDomainService;
import com.example.demo.infrastructure.components.JwtTokenComponent;
import com.example.demo.infrastructure.components.MessageProducerComponent;
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
	
	@Autowired
	private JwtTokenComponent jwtTokenComponent;
	
	@Autowired
	private MessageProducerComponent messageProducerComponent;

	@Override
	public CriarUsuarioResponseDto criarUsuario(CriarUsuarioRequestDto request) throws Exception {
		
		if (usuarioRepository.existsByEmail(request.getEmail())) 
			throw new BusinessException("Já existe um usuário cadastrado com o e-mail: " + request.getEmail());
		
		if (usuarioRepository.existsByTelefone(request.getTelefone()))
			throw new BusinessException("Já existe um usuário cadastrado com o telefone: " + request.getTelefone());
		
		var usuario = new Usuario();
		usuario.setId(UUID.randomUUID());
		usuario.setNome(request.getNome());
		usuario.setTelefone(request.getTelefone());
		usuario.setEmail(request.getEmail());
		usuario.setRole(Role.USER);
		usuario.setSenha(sha256Component.hash(request.getSenha()));
		
		usuarioRepository.save(usuario);
		
		try {
			messageProducerComponent.send(usuario);	
		} catch (Exception e) {
            e.printStackTrace();
        }
		
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
		response.setToken(jwtTokenComponent.generateToken(usuario));
		response.setDataHoraAcesso(new Date());
		response.setDataHoraExpiracao(jwtTokenComponent.getExpirationDate());
		
		return response;
	}
	
	@Override
	public ObterDadosUsuarioResponse obterDadosUsuario(String token) throws Exception {

		String email = jwtTokenComponent.getEmailFromToken(token);
		
		var usuario = usuarioRepository.findByEmail(email);
		
		if (usuario == null)
			throw new AccessDeniedException();
		
		var response = new ObterDadosUsuarioResponse();
		response.setId(usuario.getId());
		response.setNome(usuario.getNome());
		response.setTelefone(usuario.getTelefone());
		response.setEmail(usuario.getEmail());
		
		return response;
	}
}
