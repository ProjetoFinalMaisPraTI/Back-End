package com.example.sistemafinanceiro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Dados de uma despesa financeira")
public class DespesaDTO {

    @Schema(description = "Identificador único da despesa", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Descrição é obrigatória")
    @Schema(description = "Descrição resumida da despesa", example = "Conta de luz", requiredMode = Schema.RequiredMode.REQUIRED)
    private String descricao;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    @Schema(description = "Valor da despesa (deve ser positivo)", example = "150.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal valor;

    @NotNull(message = "Data é obrigatória")
    @Schema(description = "Data em que a despesa ocorreu (yyyy-MM-dd)", example = "2026-08-10", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate dataDespesa;

    @Schema(description = "Categoria da despesa", example = "Moradia",
            allowableValues = {"Moradia", "Alimentação", "Transporte", "Saúde", "Educação", "Lazer", "Outros"})
    private String categoria;

    @Schema(description = "Descrição detalhada ou observações adicionais", example = "Fatura do mês de agosto")
    private String descricaoDetalhada;

    @Schema(description = "Indica se a despesa é recorrente", example = "true")
    private Boolean recorrente;

    @Schema(description = "Periodicidade da recorrência", example = "MENSAL",
            allowableValues = {"DIARIO", "SEMANAL", "MENSAL", "ANUAL"})
    private String tipoRecorrencia;

    @Schema(description = "Indica se a despesa já foi paga", example = "false")
    private Boolean paga;

    @Schema(description = "Data e hora de criação do registro", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dataCriacao;

    @Schema(description = "Data e hora da última atualização do registro", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dataAtualizacao;

    @Schema(description = "ID do usuário ao qual esta despesa pertence", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long usuarioId;
}
