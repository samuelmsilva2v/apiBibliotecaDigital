package com.example.demo.domain.services.interfaces;

import java.util.List;
import java.util.UUID;

import com.example.demo.application.dtos.EmprestimoRequestDto;
import com.example.demo.application.dtos.EmprestimoResponseDto;

public interface EmprestimoDomainService {

	EmprestimoResponseDto emprestarLivro(EmprestimoRequestDto request) throws Exception;
	
	EmprestimoResponseDto devolverLivro(UUID id) throws Exception;
	
	List<EmprestimoResponseDto> consultarEmprestimos() throws Exception;
}
