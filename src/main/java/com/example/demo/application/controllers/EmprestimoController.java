package com.example.demo.application.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.application.dtos.EmprestimoRequestDto;
import com.example.demo.application.dtos.EmprestimoResponseDto;
import com.example.demo.domain.services.interfaces.EmprestimoDomainService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoDomainService emprestimoDomainService;

    @PostMapping
    public EmprestimoResponseDto emprestarLivro(@RequestBody @Valid EmprestimoRequestDto request) throws Exception {
        return emprestimoDomainService.emprestarLivro(request);
    }

    @PutMapping("/{id}/devolver")
    public EmprestimoResponseDto devolverLivro(@PathVariable UUID id) throws Exception {
        return emprestimoDomainService.devolverLivro(id);
    }
}
