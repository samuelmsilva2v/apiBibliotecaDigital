package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.demo.application.dtos.CriarUsuarioRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApiBibliotecaDigitalApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private static String emailUsuario;
	private static String telefoneUsuario;

	@Test
	@Order(1)
	public void criarUsuarioComSucessoTest() throws Exception {

		var request = new CriarUsuarioRequestDto();
		Faker faker = new Faker();

		request.setNome(faker.name().fullName());
		request.setTelefone(faker.numerify("## #####-####"));
		request.setEmail(faker.internet().emailAddress());
		String senha = faker.regexify("[A-Z]") + faker.regexify("[a-z]") + faker.regexify("\\d")
				+ faker.regexify("[@$!%*?&]") + faker.regexify("[A-Za-z\\d@$!%*?&]{4,}");
		request.setSenha(senha);

		System.out.println("Request: " + objectMapper.writeValueAsString(request));

		mockMvc.perform(post("/api/usuarios/criar").contentType("application/json")
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk());

		emailUsuario = request.getEmail();
		telefoneUsuario = request.getTelefone();
	}

	@Test
	@Order(2)
	public void emailJaCadastradoTest() throws Exception {

		var request = new CriarUsuarioRequestDto();
		Faker faker = new Faker();

		request.setNome(faker.name().fullName());
		request.setTelefone(faker.numerify("## #####-####"));
		request.setEmail(emailUsuario);
		String senha = faker.regexify("[A-Z]") + faker.regexify("[a-z]") + faker.regexify("\\d")
				+ faker.regexify("[@$!%*?&]") + faker.regexify("[A-Za-z\\d@$!%*?&]{4,}");
		request.setSenha(senha);

		MvcResult result = mockMvc
				.perform(post("/api/usuarios/criar").contentType("application/json")
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains("Já existe um usuário cadastrado com o e-mail: " + request.getEmail()));
	}

	@Test
	@Order(3)
	public void telefoneJaCadastradoTest() throws Exception {

		var request = new CriarUsuarioRequestDto();
		Faker faker = new Faker();

		request.setNome(faker.name().fullName());
		request.setTelefone(telefoneUsuario);
		request.setEmail(faker.internet().emailAddress());
		String senha = faker.regexify("[A-Z]") + faker.regexify("[a-z]") + faker.regexify("\\d")
				+ faker.regexify("[@$!%*?&]") + faker.regexify("[A-Za-z\\d@$!%*?&]{4,}");
		request.setSenha(senha);

		MvcResult result = mockMvc
				.perform(post("/api/usuarios/criar").contentType("application/json")
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains("Já existe um usuário cadastrado com o telefone: " + request.getTelefone()));

	}

	@Test
	@Order(4)
	public void validacaoCamposObrigatóriosTest() throws Exception {

		var request = new CriarUsuarioRequestDto();

		request.setNome(null);
		request.setTelefone(null);
		request.setEmail(null);
		request.setSenha(null);

		MvcResult result = mockMvc
				.perform(post("/api/usuarios/criar").contentType("application/json")
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains("Por favor, informe o nome do usuário."));
		assertTrue(content.contains("Por favor, informe o telefone do usuário."));
		assertTrue(content.contains("Por favor, informe o e-mail do usuário."));
		assertTrue(content.contains("Por favor, informe a senha do usuário."));
	}

	@Test
	@Order(5)
	public void validacaoDeSenhaForteTest() throws Exception {

		var request = new CriarUsuarioRequestDto();
		Faker faker = new Faker();

		request.setNome(faker.name().fullName());
		request.setTelefone(faker.numerify("## #####-####"));
		request.setEmail(faker.internet().emailAddress());
		request.setSenha("testando");

		MvcResult result = mockMvc
				.perform(post("/api/usuarios/criar").contentType("application/json")
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertTrue(content.contains(
				"Informe a senha com letras minúsculas, maiúsculas, números e símbolos e pelo menos 8 carateres."));
	}
}
