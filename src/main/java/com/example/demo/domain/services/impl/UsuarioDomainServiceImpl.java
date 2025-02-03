package com.example.demo.domain.services.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.application.dtos.AutenticarUsuarioRequestDto;
import com.example.demo.application.dtos.CriarUsuarioRequestDto;
import com.example.demo.application.dtos.CriarUsuarioResponseDto;
import com.example.demo.domain.models.entities.Usuario;
import com.example.demo.domain.services.interfaces.UsuarioDomainService;
import com.example.demo.infrastructure.repositories.UsuarioRepository;

@Service
public class UsuarioDomainServiceImpl implements UsuarioDomainService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CriarUsuarioResponseDto criarUsuario(CriarUsuarioRequestDto request) throws Exception {
		
		var usuario = modelMapper.map(request, Usuario.class);
		usuario.setId(UUID.randomUUID());
		
		usuarioRepository.save(usuario);
		
		return modelMapper.map(usuario, CriarUsuarioResponseDto.class);
	}

	@Override
	public String autenticarUsuario(AutenticarUsuarioRequestDto request) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
