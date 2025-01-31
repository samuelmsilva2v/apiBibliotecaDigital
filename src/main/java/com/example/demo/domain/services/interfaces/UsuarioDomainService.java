package com.example.demo.domain.services.interfaces;

import com.example.demo.application.dtos.AutenticarUsuarioRequestDto;
import com.example.demo.application.dtos.CriarUsuarioRequestDto;
import com.example.demo.application.dtos.CriarUsuarioResponseDto;

public interface UsuarioDomainService {

	CriarUsuarioResponseDto criarUsuario(CriarUsuarioRequestDto request) throws Exception;
	
	String autenticarUsuario(AutenticarUsuarioRequestDto request) throws Exception;
}
