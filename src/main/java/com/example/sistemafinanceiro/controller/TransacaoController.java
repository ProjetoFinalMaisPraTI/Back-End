package com.example.sistemafinanceiro.controller;

import com.example.sistemafinanceiro.entity.TransacaoEntity;
import com.example.sistemafinanceiro.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @GetMapping
    public List<TransacaoEntity> listar() {
        return transacaoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<TransacaoEntity> criar(@RequestBody TransacaoEntity transacao) {
        TransacaoEntity novaTransacao = transacaoRepository.save(transacao);
        return ResponseEntity.ok(novaTransacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransacaoEntity> atualizar(@PathVariable Long id, @RequestBody TransacaoEntity transacaoDetalhes) {
        return transacaoRepository.findById(id)
                .map(transacao -> {
                    transacao.setTitulo(transacaoDetalhes.getTitulo());
                    transacao.setValor(transacaoDetalhes.getValor());
                    transacao.setData(transacaoDetalhes.getData());
                    transacao.setParcelado(transacaoDetalhes.getParcelado());
                    transacao.setQtdParcelas(transacaoDetalhes.getQtdParcelas());
                    transacao.setDescricao(transacaoDetalhes.getDescricao());
                    transacao.setRecorrente(transacaoDetalhes.getRecorrente());
                    transacao.setRepetirDias(transacaoDetalhes.getRepetirDias());
                    transacao.setTipo(transacaoDetalhes.getTipo());
                    
                    TransacaoEntity atualizada = transacaoRepository.save(transacao);
                    return ResponseEntity.ok(atualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (transacaoRepository.existsById(id)) {
            transacaoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}