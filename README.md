## Endpoints
#### Livros
| Método | Endpoint             | Descrição                       |
|--------|----------------------|---------------------------------|
| POST   | `/api/livros`        | Registra um novo livro          |
| GET    | `/api/livros`        | Consulta todas os livros        |
| GET    | `/api/livros/{id}`   | Consulta um livro através do ID |
| PUT    | `/api/livros/{id}`   | Edita um livro                  |
| DELETE | `/api/livros/{id}`   | Exclui um livro                 |
| PUT    | `/api/livros/{id}/status`   | Atualiza o status de um livro |
| GET    | `/api/livros/{id}/disponibilidade`   | Consulta a disponibilidade de um livro |
| GET    | `/api/livros/titulo`   | Consulta um grupo através do título |
| GET    | `/api/livros/editora`   | Consulta um grupo através da editora |
| GET    | `/api/livros/autor`   | Consulta um grupo através do autor |
