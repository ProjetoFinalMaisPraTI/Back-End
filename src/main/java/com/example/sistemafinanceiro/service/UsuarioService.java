package com.example.sistemafinanceiro.service;

import com.example.sistemafinanceiro.dto.UsuarioDTO;
import com.example.sistemafinanceiro.entity.UsuarioEntity;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    UsuarioEntity criar(UsuarioDTO usuarioDTO);
    Optional<UsuarioEntity> obterPorId(Long id);
    Optional<UsuarioEntity> obterPorEmail(String email);
    List<UsuarioEntity> listar();
    UsuarioEntity atualizar(Long id, UsuarioDTO usuarioDTO);
    void deletar(Long id);
    boolean existeEmail(String email);
}
