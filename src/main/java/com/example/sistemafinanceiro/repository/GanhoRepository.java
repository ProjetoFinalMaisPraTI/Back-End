package com.example.sistemafinanceiro.repository;

import com.example.sistemafinanceiro.entity.GanhoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GanhoRepository extends JpaRepository<GanhoEntity, Long> {
    List<GanhoEntity> findByUsuarioId(Long usuarioId);
    
    @Query("SELECT g FROM GanhoEntity g WHERE g.usuario.id = :usuarioId AND YEAR(g.dataGanho) = :ano AND MONTH(g.dataGanho) = :mes")
    List<GanhoEntity> findByUsuarioIdAndMes(@Param("usuarioId") Long usuarioId, @Param("ano") int ano, @Param("mes") int mes);
    
    List<GanhoEntity> findByUsuarioIdAndDataGanhoBetween(Long usuarioId, LocalDate dataInicio, LocalDate dataFim);
}
