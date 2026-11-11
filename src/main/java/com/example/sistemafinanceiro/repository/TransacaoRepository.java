package com.example.sistemafinanceiro.repository;

import com.example.sistemafinanceiro.entity.TransacaoEntity;
import com.example.sistemafinanceiro.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<TransacaoEntity, Long> {
    List<TransacaoEntity> findByUsuario(UsuarioEntity usuario);
}