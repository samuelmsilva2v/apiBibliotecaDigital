package com.example.demo.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.application.dtos.AutenticarUsuarioRequestDto;
import com.example.demo.application.dtos.AutenticarUsuarioResponseDto;
import com.example.demo.application.dtos.CriarUsuarioRequestDto;
import com.example.demo.application.dtos.CriarUsuarioResponseDto;
import com.example.demo.application.dtos.ObterDadosUsuarioResponse;
import com.example.demo.domain.services.interfaces.UsuarioDomainService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioDomainService usuarioDomainService;

	@PostMapping
	public CriarUsuarioResponseDto criarUsuario(@RequestBody @Valid CriarUsuarioRequestDto request) throws Exception {
		return usuarioDomainService.criarUsuario(request);
	}

	@PostMapping("/autenticar")
	public AutenticarUsuarioResponseDto autenticarUsuario(@RequestBody @Valid AutenticarUsuarioRequestDto request)
			throws Exception {
		return usuarioDomainService.autenticarUsuario(request);
	}

	@GetMapping("/obter-dados")
	public ObterDadosUsuarioResponse obterDados(HttpServletRequest request) throws Exception {

		String token = request.getHeader("Authorization").replace("Bearer ", "").trim();

		return usuarioDomainService.obterDadosUsuario(token);
	}
}
