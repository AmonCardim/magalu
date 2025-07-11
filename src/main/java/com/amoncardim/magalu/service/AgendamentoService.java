package com.amoncardim.magalu.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.amoncardim.magalu.dto.AgendamentoRequestDTO;
import com.amoncardim.magalu.dto.AgendamentoResponseDTO;
import com.amoncardim.magalu.enums.StatusAgendamento;
import com.amoncardim.magalu.model.Agendamento;
import com.amoncardim.magalu.repository.AgendamentoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    public AgendamentoResponseDTO agendar(AgendamentoRequestDTO agendamentoRequestDTO) {
        Agendamento agendamento = Agendamento.builder()
            .destinatario(agendamentoRequestDTO.destinatario())
            .mensagem(agendamentoRequestDTO.mensagem())
            .tipoComunicacao(agendamentoRequestDTO.tipoComunicacao())
            .dataHoraEnvio(agendamentoRequestDTO.dataHoraEnvio())
            .status(StatusAgendamento.AGENDADO)
            .criadoEm(LocalDateTime.now())
            .build();
        return toResponse(agendamentoRepository.save(agendamento));
    }

    public AgendamentoResponseDTO buscarPorId(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado."));
        return toResponse(agendamento);
    }

    public void remover(Long id) {
        if (!agendamentoRepository.existsById(id)) {
            throw new EntityNotFoundException("Agendaemnto não encontrado.");
        }
    }

public AgendamentoResponseDTO toResponse(Agendamento agendamento) {
    return new AgendamentoResponseDTO(
        agendamento.getId(),
        agendamento.getDestinatario(),
        agendamento.getMensagem(),
        agendamento.getTipoComunicacao(),
        agendamento.getDataHoraEnvio(),
        agendamento.getStatus(),
        agendamento.getCriadoEm()
        );
    }
}