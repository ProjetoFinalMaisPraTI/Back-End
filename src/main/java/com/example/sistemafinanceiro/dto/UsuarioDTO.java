package com.example.sistemafinanceiro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Dados de um usuário do sistema")
public class UsuarioDTO {

    @Schema(description = "Identificador único do usuário", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Schema(description = "Nome completo do usuário", example = "João da Silva", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Schema(description = "Endereço de e-mail (deve ser único)", example = "joao@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "Número de telefone", example = "(51) 99999-0000")
    private String telefone;

    @Schema(description = "Data de nascimento no formato yyyy-MM-dd", example = "1990-05-15")
    private String dataNascimento;

    @Schema(description = "CPF do usuário (somente dígitos ou formatado)", example = "123.456.789-00")
    private String cpf;

    @Schema(description = "Indica se o usuário está ativo no sistema", example = "true")
    private Boolean ativo;

    @Schema(description = "Data e hora de criação do registro", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dataCriacao;

    @Schema(description = "Data e hora da última atualização do registro", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dataAtualizacao;
}
