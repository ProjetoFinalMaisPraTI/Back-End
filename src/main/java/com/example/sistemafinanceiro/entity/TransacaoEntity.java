package com.example.sistemafinanceiro.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "transacoes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private Double valor;
    private LocalDate data;
    private Boolean parcelado;
    private Integer qtdParcelas;
    private String descricao;
    private String recorrente;
    private Integer repetirDias;
    private String tipo;

    // Adiciona esta relação para vincular a transação ao usuário logado
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;
}