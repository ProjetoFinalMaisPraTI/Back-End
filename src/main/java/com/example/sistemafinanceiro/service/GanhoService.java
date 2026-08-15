package com.example.sistemafinanceiro.service;

import com.example.sistemafinanceiro.dto.GanhoDTO;
import com.example.sistemafinanceiro.entity.GanhoEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface GanhoService {
    GanhoEntity criar(GanhoDTO ganhoDTO);
    Optional<GanhoEntity> obterPorId(Long id);
    List<GanhoEntity> listarPorUsuario(Long usuarioId);
    List<GanhoEntity> listarPorMes(Long usuarioId, int ano, int mes);
    List<GanhoEntity> listarPorPeriodo(Long usuarioId, LocalDate dataInicio, LocalDate dataFim);
    GanhoEntity atualizar(Long id, GanhoDTO ganhoDTO);
    void deletar(Long id);
}
