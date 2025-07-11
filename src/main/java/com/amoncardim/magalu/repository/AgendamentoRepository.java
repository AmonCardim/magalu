package com.amoncardim.magalu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amoncardim.magalu.model.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long>{
}
