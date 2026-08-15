package com.example.sistemafinanceiro.repository;

import com.example.sistemafinanceiro.entity.DespesaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<DespesaEntity, Long> {
    List<DespesaEntity> findByUsuarioId(Long usuarioId);
    
    @Query("SELECT d FROM DespesaEntity d WHERE d.usuario.id = :usuarioId AND YEAR(d.dataDespesa) = :ano AND MONTH(d.dataDespesa) = :mes")
    List<DespesaEntity> findByUsuarioIdAndMes(@Param("usuarioId") Long usuarioId, @Param("ano") int ano, @Param("mes") int mes);
    
    List<DespesaEntity> findByUsuarioIdAndDataDespesaBetween(Long usuarioId, LocalDate dataInicio, LocalDate dataFim);
}
