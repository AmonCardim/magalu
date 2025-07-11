package com.amoncardim.magalu;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.amoncardim.magalu.controller.AgendamentoController;
import com.amoncardim.magalu.dto.AgendamentoRequestDTO;
import com.amoncardim.magalu.dto.AgendamentoResponseDTO;
import com.amoncardim.magalu.enums.StatusAgendamento;
import com.amoncardim.magalu.enums.TipoComunicacao;
import com.amoncardim.magalu.service.AgendamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AgendamentoController.class)
public class AgendamentoControllerTest {

    //Permite simular requisições HTTP(POST, GET, DELETE)
    @Autowired
    private MockMvc mockMvc;

    // Object Mapper -> converte objetos em JSON e vice-versa
    @Autowired
    private ObjectMapper objectMapper;

    // Cria um mock do service usado dentro do controller
    // Evitando a necessidade de acessar o banco de dados real durante os testes
    @MockitoBean
    private AgendamentoService service;

    @Test
    void deveAgendarComunicacao() throws Exception {
        AgendamentoRequestDTO requestDTO = new AgendamentoRequestDTO(
            "cliente@gmail.com",
            "mensagem de teste",
            TipoComunicacao.EMAIL,
            LocalDateTime.now().plusMinutes(1)
        );     
        
        AgendamentoResponseDTO response = new AgendamentoResponseDTO(
            1L, requestDTO.destinatario(), 
            requestDTO.mensagem(), 
            requestDTO.tipoComunicacao(), 
            requestDTO.dataHoraEnvio(), 
            StatusAgendamento.AGENDADO, 
            LocalDateTime.now()
        );
            
        when(service.agendar(any())).thenReturn(response);

        mockMvc.perform(post("/api/agendamentos")
        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.destinatario").value("cliente@gmail.com"));
    }

    @Test
    void deveBuscarAgendamentoPorId() throws Exception {
        Long id = 1L;

        AgendamentoResponseDTO response = new AgendamentoResponseDTO(
            1L, 
            "cliente@gmail.com",
            "mensagem de teste", 
            TipoComunicacao.SMS,
            LocalDateTime.now().plusMinutes(2),
            StatusAgendamento.AGENDADO, 
            LocalDateTime.now()
        );

        when(service.buscarPorId(id)).thenReturn(response);

        mockMvc.perform(get("/api/agendamentos/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.tipoComunicacao").value("SMS"));
    }

    @Test
    void deveRemoverAgendamento() throws Exception {

        Long id = 1L;

        doNothing().when(service).remover(id);

        mockMvc.perform(delete("api/agendamentos/{id}", id))
        .andExpect(status().isNoContent());

    }
}
