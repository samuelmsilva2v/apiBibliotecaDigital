package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import com.example.demo.application.dtos.CriarUsuarioResponseDto;
import com.example.demo.application.dtos.EmprestimoRequestDto;
import com.example.demo.application.dtos.EmprestimoResponseDto;
import com.example.demo.application.dtos.LivroRequestDto;
import com.example.demo.application.dtos.LivroResponseDto;
import com.example.demo.application.dtos.MessageLivroResponseDto;
import com.example.demo.domain.models.enums.StatusLivro;
import com.fasterxml.jackson.core.type.TypeReference;
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
	private static String livroTitulo;
	private static String livroAutor;
	private static String livroEditora;
	private static UUID usuarioId;
	private static UUID livroId;
	private static UUID emprestimoId;

	@Test
	@Order(1)
	public void createUserSuccessfullyTest() throws Exception {

		var request = new CriarUsuarioRequestDto();
		var faker = new Faker();

		request.setNome(faker.name().fullName());
		request.setTelefone(faker.numerify("## #####-####"));
		request.setEmail(faker.internet().emailAddress());
		String senha = faker.regexify("[A-Z]") + faker.regexify("[a-z]") + faker.regexify("\\d")
				+ faker.regexify("[@$!%*?&]") + faker.regexify("[A-Za-z\\d@$!%*?&]{4,}");
		request.setSenha(senha);

		System.out.println("Request: " + objectMapper.writeValueAsString(request));

		MvcResult result = mockMvc.perform(post("/api/usuarios/criar").contentType("application/json")
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk()).andReturn();

		var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		var response = objectMapper.readValue(content, CriarUsuarioResponseDto.class);

		emailUsuario = request.getEmail();
		telefoneUsuario = request.getTelefone();
		usuarioId = response.getId();
	}

	@Test
	@Order(2)
	public void emailAlreadyRegisteredTest() throws Exception {

		var request = new CriarUsuarioRequestDto();
		var faker = new Faker();

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
	public void phoneAlreadyRegisteredTest() throws Exception {

		var request = new CriarUsuarioRequestDto();
		var faker = new Faker();

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
	public void requiredFieldsValidationTest() throws Exception {

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
	public void strongPasswordValidationTest() throws Exception {

		var request = new CriarUsuarioRequestDto();
		var faker = new Faker();

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

	@Test
	@Order(6)
	public void registerBookTest() throws Exception {

		var request = new LivroRequestDto();
		var faker = new Faker();

		request.setTitulo(faker.book().title());
		request.setAutor(faker.book().author());
		request.setEditora(faker.book().publisher());
		request.setAnoPublicacao(faker.number().numberBetween(1000, 2024));
		request.setStatus(StatusLivro.DISPONIVEL);
		request.setEmprestadoPara(null);

		MvcResult result = mockMvc.perform(
				post("/api/livros").contentType("application/json").content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn();

		var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		var response = objectMapper.readValue(content, LivroResponseDto.class);

		assertNotNull(response.getId());
		assertEquals(response.getTitulo(), request.getTitulo());
		assertEquals(response.getAutor(), request.getAutor());
		assertEquals(response.getEditora(), request.getEditora());
		assertEquals(response.getAnoPublicacao(), request.getAnoPublicacao());
		assertEquals(response.getStatus(), request.getStatus());

		livroId = response.getId();
	}

	@Test
	@Order(7)
	public void editBookTest() throws Exception {

		var request = new LivroRequestDto();
		var faker = new Faker();

		request.setTitulo(faker.book().title());
		request.setAutor(faker.book().author());
		request.setEditora(faker.book().publisher());
		request.setAnoPublicacao(faker.number().numberBetween(1000, 2024));
		request.setStatus(StatusLivro.DISPONIVEL);
		request.setEmprestadoPara(null);

		var result = mockMvc.perform(put("/api/livros/" + livroId).contentType("application/json")
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk()).andReturn();

		var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		var response = objectMapper.readValue(content, MessageLivroResponseDto.class);

		assertEquals(response.getLivro().getId(), livroId);
		assertEquals(response.getLivro().getTitulo(), request.getTitulo());
		assertEquals(response.getLivro().getAutor(), request.getAutor());
		assertEquals(response.getLivro().getEditora(), request.getEditora());
		assertEquals(response.getLivro().getAnoPublicacao(), request.getAnoPublicacao());
		assertEquals(response.getLivro().getStatus(), request.getStatus());
		assertNotNull(response.getMensagem());

		livroTitulo = response.getLivro().getTitulo();
		livroAutor = response.getLivro().getAutor();
		livroEditora = response.getLivro().getEditora();
	}

	@Test
	@Order(8)
	public void getBookByIdTest() throws Exception {

		var result = mockMvc.perform(get("/api/livros/" + livroId)).andExpect(status().isOk()).andReturn();

		var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		var response = objectMapper.readValue(content, LivroResponseDto.class);

		assertEquals(response.getId(), livroId);
		assertNotNull(response.getTitulo());
		assertNotNull(response.getAutor());
		assertNotNull(response.getEditora());
		assertNotNull(response.getAnoPublicacao());
		assertNotNull(response.getStatus());
	}

	@Test
	@Order(9)
	public void getBookByTitleTest() throws Exception {

		var result = mockMvc.perform(get("/api/livros/titulo?titulo=" + livroTitulo)).andExpect(status().isOk())
				.andReturn();

		var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		var response = objectMapper.readValue(content, new TypeReference<List<LivroResponseDto>>() {
		});

		response.stream().filter(livro -> livro.getTitulo().equals(livroTitulo)).findFirst()
				.orElseThrow(() -> new AssertionError("Livro com título " + livroTitulo + " não encontrado na lista"));
	}

	@Test
	@Order(10)
	public void getBookByAuthorTest() throws Exception {

		var result = mockMvc.perform(get("/api/livros/autor?autor=" + livroAutor)).andExpect(status().isOk())
				.andReturn();

		var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		var response = objectMapper.readValue(content, new TypeReference<List<LivroResponseDto>>() {
		});

		response.stream().filter(livro -> livro.getAutor().equals(livroAutor)).findFirst()
				.orElseThrow(() -> new AssertionError("Livro com autor " + livroAutor + " não encontrado na lista"));
	}

	@Test
	@Order(11)
	public void getBookByPublisherTest() throws Exception {

		var result = mockMvc.perform(get("/api/livros/editora?editora=" + livroEditora)).andExpect(status().isOk())
				.andReturn();

		var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		var response = objectMapper.readValue(content, new TypeReference<List<LivroResponseDto>>() {
		});

		response.stream().filter(livro -> livro.getEditora().equals(livroEditora)).findFirst().orElseThrow(
				() -> new AssertionError("Livro com editora " + livroEditora + " não encontrado na lista"));
	}

	@Test
	@Order(12)
	public void getBookByAvailabilityTest() throws Exception {

		var result = mockMvc.perform(get("/api/livros/" + livroId + "/disponibilidade")).andExpect(status().isOk())
				.andReturn();

		var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		assertEquals("true", content);
	}

	@Test
	@Order(13)
	public void getAllBooksTest() throws Exception {

		var result = mockMvc.perform(get("/api/livros")).andExpect(status().isOk()).andReturn();

		var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		var response = objectMapper.readValue(content, new TypeReference<List<LivroResponseDto>>() {
		});

		response.stream().filter(livro -> livro.getId().equals(livroId)).findFirst()
				.orElseThrow(() -> new AssertionError("Livro com ID" + livroId + " não encontrado na lista"));
	}

	@Test
	@Order(14)
	public void registerLoanTest() throws Exception {

		var request = new EmprestimoRequestDto();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dataEmprestimo = dateFormat.parse(dateFormat.format(new Date()));
		Date dataDevolucaoPrevista = dateFormat
				.parse(dateFormat.format(new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000)));

		request.setUsuarioId(usuarioId);
		request.setLivroId(livroId);
		request.setDataEmprestimo(dataEmprestimo);
		request.setDataDevolucaoPrevista(dataDevolucaoPrevista);
		request.setDevolvido(false);

		var result = mockMvc.perform(post("/api/emprestimos").contentType("application/json")
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk()).andReturn();

		var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		var response = objectMapper.readValue(content, EmprestimoResponseDto.class);

		assertNotNull(response.getId());
		assertEquals(response.getLivro().getId(), livroId);

		emprestimoId = response.getId();
	}

	@Test
	@Order(15)
	public void updateBookStatusTest() throws Exception {

		var result = mockMvc
				.perform(put("/api/livros/" + livroId + "/status").contentType("application/json")
						.content(objectMapper.writeValueAsString(StatusLivro.EMPRESTADO)))
				.andExpect(status().isOk()).andReturn();

		var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		var response = objectMapper.readValue(content, LivroResponseDto.class);

		assertEquals(response.getId(), livroId);
		assertEquals(response.getStatus(), StatusLivro.EMPRESTADO);
	}

	@Test
	@Order(16)
	public void returnLoanTest() throws Exception {

		var result = mockMvc
				.perform(put("/api/emprestimos/" + emprestimoId + "/devolver").contentType("application/json"))
				.andExpect(status().isOk()).andReturn();

		var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		var response = objectMapper.readValue(content, EmprestimoResponseDto.class);

		assertTrue(response.isDevolvido());
	}

	@Test
	@Order(17)
	public void getAllLoans() throws Exception {

		var result = mockMvc.perform(get("/api/emprestimos")).andExpect(status().isOk()).andReturn();

		var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		var response = objectMapper.readValue(content, new TypeReference<List<EmprestimoResponseDto>>() {
		});

		response.stream().filter(emprestimo -> emprestimo.getId().equals(emprestimoId)).findFirst()
				.orElseThrow(() -> new AssertionError("Empréstimo com ID" + emprestimoId + " não encontrado na lista"));
	}

}
