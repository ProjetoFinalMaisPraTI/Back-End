package com.example.sistemafinanceiro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponseDTO {
    private String token;
    private String nome;
    private String email;
}