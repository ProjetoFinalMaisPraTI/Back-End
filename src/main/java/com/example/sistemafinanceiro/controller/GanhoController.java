package com.example.sistemafinanceiro.controller;

import com.example.sistemafinanceiro.dto.GanhoDTO;
import com.example.sistemafinanceiro.entity.GanhoEntity;
import com.example.sistemafinanceiro.service.GanhoService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ganhos")
@CrossOrigin(origins = "*", maxAge = 3600)
public class GanhoController {

    private final GanhoService ganhoService;

    public GanhoController(GanhoService ganhoService) {
        this.ganhoService = ganhoService;
    }

    @PostMapping
    public ResponseEntity<GanhoDTO> criar(@Valid @RequestBody GanhoDTO ganhoDTO) {
        try {
            GanhoEntity ganho = ganhoService.criar(ganhoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(converterParaDTO(ganho));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<GanhoDTO>> listar() {
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GanhoDTO> obterPorId(@PathVariable Long id) {
        Optional<GanhoEntity> ganho = ganhoService.obterPorId(id);
        return ganho.map(g -> ResponseEntity.ok(converterParaDTO(g)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<GanhoDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<GanhoEntity> ganhos = ganhoService.listarPorUsuario(usuarioId);
        List<GanhoDTO> dtos = ganhos.stream().map(this::converterParaDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/usuario/{usuarioId}/mes")
    public ResponseEntity<List<GanhoDTO>> listarPorMes(
            @PathVariable Long usuarioId,
            @RequestParam int ano,
            @RequestParam int mes) {
        List<GanhoEntity> ganhos = ganhoService.listarPorMes(usuarioId, ano, mes);
        List<GanhoDTO> dtos = ganhos.stream().map(this::converterParaDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/usuario/{usuarioId}/periodo")
    public ResponseEntity<List<GanhoDTO>> listarPorPeriodo(
            @PathVariable Long usuarioId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        List<GanhoEntity> ganhos = ganhoService.listarPorPeriodo(usuarioId, dataInicio, dataFim);
        List<GanhoDTO> dtos = ganhos.stream().map(this::converterParaDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GanhoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody GanhoDTO ganhoDTO) {
        try {
            GanhoEntity ganho = ganhoService.atualizar(id, ganhoDTO);
            return ResponseEntity.ok(converterParaDTO(ganho));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            ganhoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private GanhoDTO converterParaDTO(GanhoEntity ganho) {
        return GanhoDTO.builder()
                .id(ganho.getId())
                .descricao(ganho.getDescricao())
                .valor(ganho.getValor())
                .dataGanho(ganho.getDataGanho())
                .tipo(ganho.getTipo())
                .descricaoDetalhada(ganho.getDescricaoDetalhada())
                .recorrente(ganho.getRecorrente())
                .tipoRecorrencia(ganho.getTipoRecorrencia())
                .dataCriacao(ganho.getDataCriacao())
                .dataAtualizacao(ganho.getDataAtualizacao())
                .usuarioId(ganho.getUsuario().getId())
                .build();
    }
}
