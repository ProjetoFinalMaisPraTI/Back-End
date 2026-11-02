package com.example.sistemafinanceiro.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "transacoes")
public class TransacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private Double valor;
    private LocalDate data;
    private Boolean parcelado;
    private String qtdParcelas;
    
    @Column(length = 500)
    private String descricao;
    
    private String recorrente;
    private String repetirDias;
    private String tipo; // "despesa" ou "ganho"
}
