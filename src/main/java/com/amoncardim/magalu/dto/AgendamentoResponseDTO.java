package com.amoncardim.magalu.dto;

import java.time.LocalDateTime;

import com.amoncardim.magalu.enums.StatusAgendamento;
import com.amoncardim.magalu.enums.TipoComunicacao;

public record AgendamentoResponseDTO(
    Long id,
    String destinatario,
    String mensagem,
    TipoComunicacao tipoComunicacao,
    LocalDateTime dataHoraEnvio,
    StatusAgendamento status,
    LocalDateTime criadoEm
) {
}
