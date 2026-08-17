package com.example.sistemafinanceiro.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDTO {
    
    @NotBlank(message = "Email é obrigatório")
    private String email;
    
    @NotBlank(message = "Senha é obrigatória")
    private String senha;
}
