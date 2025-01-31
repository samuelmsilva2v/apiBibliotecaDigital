package com.example.demo.application.dtos;

import java.util.Date;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmprestimoRequestDto {

    @NotNull(message = "O ID do livro é obrigatório.")
    private UUID livroId;

    @NotNull(message = "O ID do usuário é obrigatório.")
    private UUID usuarioId;

    @NotNull(message = "A data de empréstimo é obrigatória.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataEmprestimo;

    @NotNull(message = "A data de devolução prevista é obrigatória.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @FutureOrPresent(message = "A data de devolução prevista deve ser no futuro ou no presente.")
    private Date dataDevolucaoPrevista;

    private boolean devolvido;
}
