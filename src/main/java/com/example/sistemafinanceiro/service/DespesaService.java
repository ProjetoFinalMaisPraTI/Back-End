package com.example.sistemafinanceiro.service;

import com.example.sistemafinanceiro.dto.DespesaDTO;
import com.example.sistemafinanceiro.entity.DespesaEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DespesaService {
    DespesaEntity criar(DespesaDTO despesaDTO);
    Optional<DespesaEntity> obterPorId(Long id);
    List<DespesaEntity> listarPorUsuario(Long usuarioId);
    List<DespesaEntity> listarPorMes(Long usuarioId, int ano, int mes);
    List<DespesaEntity> listarPorPeriodo(Long usuarioId, LocalDate dataInicio, LocalDate dataFim);
    DespesaEntity atualizar(Long id, DespesaDTO despesaDTO);
    void deletar(Long id);
}
