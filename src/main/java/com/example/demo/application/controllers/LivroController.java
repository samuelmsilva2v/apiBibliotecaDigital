package com.example.demo.application.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.application.dtos.LivroRequestDto;
import com.example.demo.application.dtos.LivroResponseDto;
import com.example.demo.application.dtos.MessageLivroResponseDto;
import com.example.demo.domain.models.enums.StatusLivro;
import com.example.demo.domain.services.interfaces.LivroDomainService;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

	@Autowired
	private LivroDomainService livroDomainService;

	@PostMapping
	public LivroResponseDto post(@RequestBody LivroRequestDto request) throws Exception {
		return livroDomainService.registrarLivro(request);
	}

	@PutMapping("/{id}")
	public MessageLivroResponseDto put(@PathVariable UUID id, @RequestBody LivroRequestDto request) throws Exception {
		return livroDomainService.editarLivro(id, request);
	}

	@PutMapping("/{id}/status")
	public LivroResponseDto putStatus(@PathVariable UUID id, @RequestBody StatusLivro status) {
		return livroDomainService.atualizarStatus(id, status);
	}

	@DeleteMapping("/{id}")
	public MessageLivroResponseDto delete(@PathVariable UUID id) throws Exception {
		return livroDomainService.apagarLivro(id);

	}

	@GetMapping("/{id}")
	public LivroResponseDto getById(@PathVariable UUID id) throws Exception {
		return livroDomainService.buscarLivroPorId(id);
	}

	@GetMapping
	public List<LivroResponseDto> get() throws Exception {
		return livroDomainService.consultarLivros();

	}

	@GetMapping("/titulo")
	public List<LivroResponseDto> getByTitulo(@RequestParam String titulo) throws Exception {
		return livroDomainService.buscarLivrosPorTitulo(titulo);
	}

	@GetMapping("/autor")
	public List<LivroResponseDto> getByAutor(@RequestParam String autor) throws Exception {
		return livroDomainService.buscarLivroPorAutor(autor);		
	}

	@GetMapping("/editora")
	public List<LivroResponseDto> getByEditora(@RequestParam String editora) throws Exception {
		return livroDomainService.buscarLivroPorEditora(editora);
	}

	@GetMapping("/{id}/disponibilidade")
	public Boolean getAvailability(@PathVariable UUID id) throws Exception {
		return livroDomainService.verificarDisponibilidade(id); 
	}
}
