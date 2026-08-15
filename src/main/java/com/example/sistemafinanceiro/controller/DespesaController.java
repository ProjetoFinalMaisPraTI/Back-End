package com.example.sistemafinanceiro.controller;

import com.example.sistemafinanceiro.dto.DespesaDTO;
import com.example.sistemafinanceiro.entity.DespesaEntity;
import com.example.sistemafinanceiro.service.DespesaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/despesas")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DespesaController {

    private final DespesaService despesaService;

    public DespesaController(DespesaService despesaService) {
        this.despesaService = despesaService;
    }

    @PostMapping
    public ResponseEntity<DespesaDTO> criar(@Valid @RequestBody DespesaDTO despesaDTO) {
        try {
            DespesaEntity despesa = despesaService.criar(despesaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(converterParaDTO(despesa));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<DespesaDTO>> listar() {
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespesaDTO> obterPorId(@PathVariable Long id) {
        Optional<DespesaEntity> despesa = despesaService.obterPorId(id);
        return despesa.map(d -> ResponseEntity.ok(converterParaDTO(d)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<DespesaDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<DespesaEntity> despesas = despesaService.listarPorUsuario(usuarioId);
        List<DespesaDTO> dtos = despesas.stream().map(this::converterParaDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/usuario/{usuarioId}/mes")
    public ResponseEntity<List<DespesaDTO>> listarPorMes(
            @PathVariable Long usuarioId,
            @RequestParam int ano,
            @RequestParam int mes) {
        List<DespesaEntity> despesas = despesaService.listarPorMes(usuarioId, ano, mes);
        List<DespesaDTO> dtos = despesas.stream().map(this::converterParaDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/usuario/{usuarioId}/periodo")
    public ResponseEntity<List<DespesaDTO>> listarPorPeriodo(
            @PathVariable Long usuarioId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        List<DespesaEntity> despesas = despesaService.listarPorPeriodo(usuarioId, dataInicio, dataFim);
        List<DespesaDTO> dtos = despesas.stream().map(this::converterParaDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DespesaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody DespesaDTO despesaDTO) {
        try {
            DespesaEntity despesa = despesaService.atualizar(id, despesaDTO);
            return ResponseEntity.ok(converterParaDTO(despesa));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            despesaService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private DespesaDTO converterParaDTO(DespesaEntity despesa) {
        return DespesaDTO.builder()
                .id(despesa.getId())
                .descricao(despesa.getDescricao())
                .valor(despesa.getValor())
                .dataDespesa(despesa.getDataDespesa())
                .categoria(despesa.getCategoria())
                .descricaoDetalhada(despesa.getDescricaoDetalhada())
                .recorrente(despesa.getRecorrente())
                .tipoRecorrencia(despesa.getTipoRecorrencia())
                .paga(despesa.getPaga())
                .dataCriacao(despesa.getDataCriacao())
                .dataAtualizacao(despesa.getDataAtualizacao())
                .usuarioId(despesa.getUsuario().getId())
                .build();
    }
}
