package com.example.demo.application.dtos;

import java.util.UUID;

import com.example.demo.domain.models.enums.StatusLivro;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LivroRequestDto {

	@NotBlank(message = "O título é obrigatório.")
    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres")
	private String titulo;
	
	@NotBlank(message = "O autor é obrigatório.")
    @Size(max = 100, message = "O autor deve ter no máximo 100 caracteres")
	private String autor;
	
	@NotBlank(message = "A editora é obrigatória.")
    @Size(max = 100, message = "A editora deve ter no máximo 100 caracteres")
	private String editora;
	
	@NotNull(message = "O ano de publicação é obrigatório")
    @Min(value = 1000, message = "O ano de publicação deve ser válido")
    @Max(value = 2024, message = "O ano de publicação não pode ser no futuro")
	private Integer anoPublicacao;
	
	@NotNull(message = "O status do livro é obrigatório.")
	private StatusLivro status;
	private UUID emprestadoPara;
}
