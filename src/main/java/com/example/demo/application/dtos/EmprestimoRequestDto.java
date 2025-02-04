package com.example.demo.application.dtos;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "UTC")
    private Date dataEmprestimo;

    @NotNull(message = "A data de devolução prevista é obrigatória.")
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "UTC")
    @FutureOrPresent(message = "A data de devolução prevista deve ser no futuro ou no presente.")
    private Date dataDevolucaoPrevista;

    private boolean devolvido;
}
