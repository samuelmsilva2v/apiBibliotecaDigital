# 📚 Biblioteca Digital
O **Sistema de Biblioteca Digital** é uma API RESTful desenvolvida em Java com Spring Boot para gerenciar livros, usuários e empréstimos. O projeto inclui autenticação JWT e um sistema de mensageria para notificações.

## Tecnologias Utilizadas:

- Java 21
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA
- Hibernate
- MySQL
- RabbitMQ (Mensageria)
- Lombok
- ModelMapper
- JUnit 5 / Mockito (TDD)
- Swagger/OpenAPI (Documentação)

## Regras de negócio:
- Apenas usuários **administradores** podem cadastrar, editar ou excluir livros.
- Cada usuário pode pegar **até 3 livros emprestados** ao mesmo tempo.
- O status do livro muda para **"EMPRESTADO"** quando um usuário realiza um empréstimo.
- O sistema enviará **notificações** quando um livro for emprestado ou devolvido.

## Endpoints

- #### Livros
| Método | Endpoint                           | Descrição                              |
|--------|------------------------------------|----------------------------------------|
| POST   | `/api/livros`                      | Cadastra um novo livro                 |
| PUT    | `/api/livros/{id}`                 | Edita os dados de um livro             |
| PUT    | `/api/livros/{id}/status`          | Edita apenas o STATUS de um livro      |
| GET    | `/api/livros`                      | Consulta todas os livros               |
| GET    | `/api/livros/{id}`                 | Consulta um livro através do ID        |
| GET    | `/api/livros/{id}/disponibilidade` | Consulta a disponibilidade de um livro |
| GET    | `/api/livros/titulo`               | Consulta livros através do título      |
| GET    | `/api/livros/editora`              | Consulta livros através da editora     |
| GET    | `/api/livros/autor`                | Consulta livros através do autor       |
| DELETE | `/api/livros/{id}`                 | Exclui um livro                        |

- #### Empréstimos
| Método | Endpoint                         | Descrição                                       |
|--------|----------------------------------|-------------------------------------------------|
| POST   | `/api/emprestimos`               | Registra um novo empréstimo                     |
| PUT    | `/api/emprestimos/{id}/devolver` | Altera o STATUS de um empréstimo para devolvido |
| GET    | `/api/emprestimos`               | Consulta todas os empréstimos registrados       |

- #### Usuários
| Método | Endpoint                    | Descrição                                |
|--------|-----------------------------|------------------------------------------|
| POST   | `/api/usuarios/criar`       | Cadastra um novo usuário                 |
| POST   | `/api/usuarios`             | Autentica um novo usuário                |
| GET    | `/api/usuarios/obter-dados` | Consulta os dados do usuário autenticado |

## Como executar o projeto:

#### 1. Clonar o repositório:
```bash
git clone https://github.com/samuelmsilva2v/apiBibliotecaDigital.git
cd apiBibliotecaDigital
```
#### 2. No terminal, navegue até a pasta do projeto.
#### 3. Execute o comando abaixo para criar a imagem Docker:
```bash
docker build -t apiBibliotecaDigital .
```
##### Executando o container
```bash
docker-compose up -d
```
  
