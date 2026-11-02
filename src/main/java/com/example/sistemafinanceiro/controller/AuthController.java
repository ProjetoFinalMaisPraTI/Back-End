package com.example.sistemafinanceiro.controller;

import com.example.sistemafinanceiro.dto.LoginRequestDTO;
import com.example.sistemafinanceiro.dto.TokenResponseDTO;
import com.example.sistemafinanceiro.entity.UsuarioEntity;
import com.example.sistemafinanceiro.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        // Procura o utilizador pelo e-mail
        UsuarioEntity usuario = usuarioRepository.findByEmail(loginRequest.getEmail())
                .orElse(null);

        if (usuario == null) {
            return ResponseEntity.status(401).body("E-mail ou senha inválidos.");
        }

        // Valida a senha encriptada com o BCrypt
        boolean senhaValida = passwordEncoder.matches(loginRequest.getSenha(), usuario.getSenha());

        if (!senhaValida) {
            return ResponseEntity.status(401).body("E-mail ou senha inválidos.");
        }

        // Se as credenciais estiverem corretas, gera um token de acesso (ou JWT)
        String tokenSimulado = "Bearer_token_exemplo_jwt_valido";
        
        return ResponseEntity.ok(new TokenResponseDTO(tokenSimulado));
    }
}