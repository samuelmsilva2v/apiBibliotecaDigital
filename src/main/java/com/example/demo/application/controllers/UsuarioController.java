package com.example.demo.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.application.dtos.AutenticarUsuarioRequestDto;
import com.example.demo.application.dtos.CriarUsuarioRequestDto;
import com.example.demo.application.dtos.CriarUsuarioResponseDto;
import com.example.demo.domain.services.interfaces.UsuarioDomainService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioDomainService usuarioDomainService;

    @PostMapping
    public CriarUsuarioResponseDto criarUsuario(@RequestBody CriarUsuarioRequestDto request) throws Exception {
        return usuarioDomainService.criarUsuario(request);
    }

    @PostMapping("/autenticar")
    public String autenticarUsuario(@RequestBody AutenticarUsuarioRequestDto request) throws Exception {
        return usuarioDomainService.autenticarUsuario(request);
    }
}
