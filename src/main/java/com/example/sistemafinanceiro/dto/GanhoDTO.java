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
@Schema(description = "Dados de um ganho financeiro")
public class GanhoDTO {

    @Schema(description = "Identificador único do ganho", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Descrição é obrigatória")
    @Schema(description = "Descrição resumida do ganho", example = "Salário mensal", requiredMode = Schema.RequiredMode.REQUIRED)
    private String descricao;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    @Schema(description = "Valor do ganho (deve ser positivo)", example = "3500.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal valor;

    @NotNull(message = "Data é obrigatória")
    @Schema(description = "Data em que o ganho foi recebido (yyyy-MM-dd)", example = "2026-08-05", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate dataGanho;

    @Schema(description = "Tipo/fonte do ganho", example = "SALARIO",
            allowableValues = {"SALARIO", "FREELANCE", "INVESTIMENTO", "ALUGUEL", "OUTROS"})
    private String tipo;

    @Schema(description = "Descrição detalhada ou observações adicionais", example = "Pagamento referente ao mês de agosto")
    private String descricaoDetalhada;

    @Schema(description = "Indica se o ganho é recorrente", example = "true")
    private Boolean recorrente;

    @Schema(description = "Periodicidade da recorrência", example = "MENSAL",
            allowableValues = {"DIARIO", "SEMANAL", "MENSAL", "ANUAL"})
    private String tipoRecorrencia;

    @Schema(description = "Data e hora de criação do registro", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dataCriacao;

    @Schema(description = "Data e hora da última atualização do registro", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dataAtualizacao;

    @Schema(description = "ID do usuário ao qual este ganho pertence", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long usuarioId;
}
