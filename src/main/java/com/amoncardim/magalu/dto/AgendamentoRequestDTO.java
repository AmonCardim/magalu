package com.amoncardim.magalu.dto;

import java.time.LocalDateTime;

import com.amoncardim.magalu.enums.TipoComunicacao;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AgendamentoRequestDTO(
    @NotBlank(message = "O destinatário é obrigatório.")
    String destinatario,

    @NotBlank(message = "A mensagem é obrigatória.")
    String mensagem,

    @NotBlank(message = "O tipo de comunicação é obrigatório.")
    TipoComunicacao tipoComunicacao,

    @NotNull(message = "A data e hora de envio são obrigatórios.")
    @Future(message = "A data e hora de envio deve ser futura.")
    LocalDateTime dataHoraEnvio
) {
}
