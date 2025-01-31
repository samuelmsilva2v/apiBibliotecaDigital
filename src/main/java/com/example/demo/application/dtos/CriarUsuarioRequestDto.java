package com.example.demo.application.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CriarUsuarioRequestDto {

	@NotBlank(message = "O nome do usuário é obrigatório.")
	@Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
	private String nome;

	@NotBlank(message = "O telefone é obrigatório.")
	@Pattern(regexp = "^\\d{2} \\d{5}-\\d{4}$", message = "Formato de telefone inválido. Use o padrão XX XXXXX-XXXX.")
	private String telefone;

	@NotBlank(message = "O e-mail é obrigatório.")
	@Email(message = "Formato de e-mail inválido.")
	private String email;

	@NotBlank(message = "A senha é obrigatória.")
	@Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
	@Pattern(regexp = "^(?=.*[!@#$%^&*()\\-_=+{};:,<.>/?])(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*$", message = "A senha deve conter pelo menos um símbolo, um número, uma letra maiúscula e uma minúscula.")
	private String senha;
}
