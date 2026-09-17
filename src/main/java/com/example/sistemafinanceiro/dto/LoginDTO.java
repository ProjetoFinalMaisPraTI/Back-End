package com.example.sistemafinanceiro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Credenciais de autenticação do usuário")
public class LoginDTO {

    @NotBlank(message = "Email é obrigatório")
    @Schema(description = "E-mail cadastrado no sistema", example = "joao@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Schema(description = "Senha do usuário", example = "minhaS3nha!", requiredMode = Schema.RequiredMode.REQUIRED)
    private String senha;
}
