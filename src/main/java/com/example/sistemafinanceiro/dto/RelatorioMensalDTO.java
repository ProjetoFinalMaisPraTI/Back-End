package com.example.sistemafinanceiro.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelatorioMensalDTO {
    private int ano;
    private int mes;
    private BigDecimal totalDespesas;
    private BigDecimal totalGanhos;
    private BigDecimal saldo;
    private List<DespesaDTO> despesas;
    private List<GanhoDTO> ganhos;
}
