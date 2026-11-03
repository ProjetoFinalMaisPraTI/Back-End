package com.example.sistemafinanceiro.controller;

import com.example.sistemafinanceiro.dto.LoginDTO;
import com.example.sistemafinanceiro.dto.TokenResponseDTO;
import com.example.sistemafinanceiro.entity.UsuarioEntity;
import com.example.sistemafinanceiro.repository.UsuarioRepository;
import com.example.sistemafinanceiro.security.TokenService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth") // o prefixo /api já vem do context-path no application.properties
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // DTO para receber os dados de cadastro (só o que o cadastro precisa)
    public record RegistroDto(
            @NotBlank(message = "Nome é obrigatório") String nome,
            @NotBlank(message = "Email é obrigatório") @Email(message = "Email deve ser válido") String email,
            @NotBlank(message = "Senha é obrigatória") @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres") String senha) {}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO dados) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(dados.getEmail(), dados.getSenha());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            UsuarioEntity usuario = (UsuarioEntity) auth.getPrincipal();
            String token = tokenService.gerarToken(usuario);

            return ResponseEntity.ok(new TokenResponseDTO(token, usuario.getNome(), usuario.getEmail()));
        } catch (AuthenticationException e) {
            // senha errada, usuário inexistente ou conta desativada
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "E-mail ou senha inválidos."));
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody @Valid RegistroDto dados) {
        if (usuarioRepository.findByEmail(dados.email()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "E-mail já está em uso."));
        }

        UsuarioEntity novoUsuario = usuarioRepository.save(
                UsuarioEntity.builder()
                        .nome(dados.nome())
                        .email(dados.email())
                        .senha(passwordEncoder.encode(dados.senha())) // nunca salvar a senha em texto puro
                        .ativo(true) // a coluna ativo é NOT NULL
                        .build());

        // gera o token logo após o registro para já deixar o usuário logado
        String token = tokenService.gerarToken(novoUsuario);

        return ResponseEntity.ok(new TokenResponseDTO(token, novoUsuario.getNome(), novoUsuario.getEmail()));
    }
}