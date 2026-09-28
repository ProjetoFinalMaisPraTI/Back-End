package com.example.sistemafinanceiro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Relatório financeiro consolidado de um mês específico")
public class RelatorioMensalDTO {

    @Schema(description = "Ano de referência do relatório", example = "2026")
    private int ano;

    @Schema(description = "Mês de referência do relatório (1–12)", example = "8")
    private int mes;

    @Schema(description = "Soma total das despesas do período", example = "1250.00")
    private BigDecimal totalDespesas;

    @Schema(description = "Soma total dos ganhos do período", example = "3500.00")
    private BigDecimal totalGanhos;

    @Schema(description = "Saldo do período (totalGanhos - totalDespesas)", example = "2250.00")
    private BigDecimal saldo;

    @Schema(description = "Lista detalhada das despesas do período")
    private List<DespesaDTO> despesas;

    @Schema(description = "Lista detalhada dos ganhos do período")
    private List<GanhoDTO> ganhos;
}
