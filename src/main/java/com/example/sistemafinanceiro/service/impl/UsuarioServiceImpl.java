package com.example.sistemafinanceiro.service.impl;

import com.example.sistemafinanceiro.dto.UsuarioDTO;
import com.example.sistemafinanceiro.entity.UsuarioEntity;
import com.example.sistemafinanceiro.repository.UsuarioRepository;
import com.example.sistemafinanceiro.service.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsuarioEntity criar(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        UsuarioEntity usuario = UsuarioEntity.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(passwordEncoder.encode(usuarioDTO.getEmail())) // Será substituído por senha real no login
                .telefone(usuarioDTO.getTelefone())
                .dataNascimento(usuarioDTO.getDataNascimento())
                .cpf(usuarioDTO.getCpf())
                .ativo(true)
                .build();

        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioEntity> obterPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioEntity> obterPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioEntity> listar() {
        return usuarioRepository.findAll();
    }

    @Override
    public UsuarioEntity atualizar(Long id, UsuarioDTO usuarioDTO) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setNome(usuarioDTO.getNome());
        usuario.setTelefone(usuarioDTO.getTelefone());
        usuario.setDataNascimento(usuarioDTO.getDataNascimento());
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setAtivo(usuarioDTO.getAtivo());

        return usuarioRepository.save(usuario);
    }

    @Override
    public void deletar(Long id) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuarioRepository.delete(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}
