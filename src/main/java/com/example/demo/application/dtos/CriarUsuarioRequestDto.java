package com.example.demo.application.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CriarUsuarioRequestDto { 

	@NotBlank(message = "Por favor, informe o nome do usuário.")
	@Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
	private String nome;

	@NotBlank(message = "Por favor, informe o telefone do usuário.")
	@Pattern(regexp = "^\\d{2} \\d{5}-\\d{4}$", message = "Formato de telefone inválido. Use o padrão XX XXXXX-XXXX.")
	private String telefone;

	@NotBlank(message = "Por favor, informe o e-mail do usuário.")
	@Email(message = "Formato de e-mail inválido.")
	private String email;

	@NotBlank(message = "Por favor, informe a senha do usuário.")
	@Pattern(regexp = "^(?=.*[!@#$%^&*()\\-_=+{};:,<.>/?])(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*$", message = "Informe a senha com letras minúsculas, maiúsculas, números e símbolos e pelo menos 8 carateres.")
	private String senha;
}
