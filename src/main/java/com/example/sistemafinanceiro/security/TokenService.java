package com.example.sistemafinanceiro.security;

import com.example.sistemafinanceiro.entity.UsuarioEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    // Chave secreta robusta (em produção, deves guardar isto nas variáveis de ambiente / application.properties)
    private final Key chaveAssinatura = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long EXPIRACAO = 86400000; // 1 dia em milissegundos

    // Gerar o Token JWT
    public String gerarToken(UsuarioEntity usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail()) // Identificador principal (ex: email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRACAO))
                .signWith(chaveAssinatura)
                .compact();
    }

    // Validar e extrair o email de dentro do Token
    public String validarToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(chaveAssinatura)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            return null; // Token inválido ou expirado
        }
    }
}