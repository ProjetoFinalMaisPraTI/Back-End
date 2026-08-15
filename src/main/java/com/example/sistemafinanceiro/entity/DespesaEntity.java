package com.example.sistemafinanceiro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "despesas")
public class DespesaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Descrição é obrigatória")
    @Column(nullable = false, length = 255)
    private String descricao;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @NotNull(message = "Data é obrigatória")
    @Column(nullable = false, name = "data_despesa")
    private LocalDate dataDespesa;

    @Column(length = 100)
    private String categoria;

    @Column(length = 500)
    private String descricaoDetalhada;

    @Column(name = "recorrente", nullable = false)
    private Boolean recorrente = false;

    @Column(name = "tipo_recorrencia", length = 50)
    private String tipoRecorrencia; // DIARIA, SEMANAL, MENSAL, ANUAL

    @Builder.Default
    @Column(nullable = false)
    private Boolean paga = false;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;
}
