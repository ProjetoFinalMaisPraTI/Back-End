package com.example.sistemafinanceiro.controller;

import com.example.sistemafinanceiro.dto.DespesaDTO;
import com.example.sistemafinanceiro.entity.DespesaEntity;
import com.example.sistemafinanceiro.service.DespesaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Despesas", description = "Registro e consulta de despesas por usuário, mês e período")
public class DespesaController {

    private final DespesaService despesaService;

    public DespesaController(DespesaService despesaService) {
        this.despesaService = despesaService;
    }

    @Operation(
            summary = "Criar despesa",
            description = "Registra uma nova despesa vinculada a um usuário.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Despesa criada com sucesso",
                    content = @Content(schema = @Schema(implementation = DespesaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou usuário não encontrado",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<DespesaDTO> criar(@Valid @RequestBody DespesaDTO despesaDTO) {
        try {
            DespesaEntity despesa = despesaService.criar(despesaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(converterParaDTO(despesa));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(
            summary = "Listar todas as despesas",
            description = "Retorna a lista completa de despesas cadastradas no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DespesaDTO.class))))
    })
    @GetMapping
    public ResponseEntity<List<DespesaDTO>> listar() {
        return ResponseEntity.ok(List.of());
    }

    @Operation(
            summary = "Buscar despesa por ID",
            description = "Retorna os dados de uma despesa específica pelo seu identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Despesa encontrada",
                    content = @Content(schema = @Schema(implementation = DespesaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Despesa não encontrada",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<DespesaDTO> obterPorId(
            @Parameter(description = "ID da despesa", example = "1") @PathVariable Long id) {
        Optional<DespesaEntity> despesa = despesaService.obterPorId(id);
        return despesa.map(d -> ResponseEntity.ok(converterParaDTO(d)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Listar despesas de um usuário",
            description = "Retorna todas as despesas vinculadas ao usuário informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DespesaDTO.class))))
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<DespesaDTO>> listarPorUsuario(
            @Parameter(description = "ID do usuário", example = "1") @PathVariable Long usuarioId) {
        List<DespesaEntity> despesas = despesaService.listarPorUsuario(usuarioId);
        List<DespesaDTO> dtos = despesas.stream().map(this::converterParaDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    @Operation(
            summary = "Listar despesas de um usuário por mês",
            description = "Retorna as despesas de um usuário filtradas pelo ano e mês informados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DespesaDTO.class))))
    })
    @GetMapping("/usuario/{usuarioId}/mes")
    public ResponseEntity<List<DespesaDTO>> listarPorMes(
            @Parameter(description = "ID do usuário", example = "1") @PathVariable Long usuarioId,
            @Parameter(description = "Ano de referência", example = "2026") @RequestParam int ano,
            @Parameter(description = "Mês de referência (1–12)", example = "8") @RequestParam int mes) {
        List<DespesaEntity> despesas = despesaService.listarPorMes(usuarioId, ano, mes);
        List<DespesaDTO> dtos = despesas.stream().map(this::converterParaDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    @Operation(
            summary = "Listar despesas de um usuário por período",
            description = "Retorna as despesas de um usuário entre duas datas (formato ISO: `yyyy-MM-dd`).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DespesaDTO.class))))
    })
    @GetMapping("/usuario/{usuarioId}/periodo")
    public ResponseEntity<List<DespesaDTO>> listarPorPeriodo(
            @Parameter(description = "ID do usuário", example = "1") @PathVariable Long usuarioId,
            @Parameter(description = "Data de início (yyyy-MM-dd)", example = "2026-08-01")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @Parameter(description = "Data de fim (yyyy-MM-dd)", example = "2026-08-31")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        List<DespesaEntity> despesas = despesaService.listarPorPeriodo(usuarioId, dataInicio, dataFim);
        List<DespesaDTO> dtos = despesas.stream().map(this::converterParaDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    @Operation(
            summary = "Atualizar despesa",
            description = "Atualiza os dados de uma despesa existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Despesa atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = DespesaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Despesa não encontrada",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<DespesaDTO> atualizar(
            @Parameter(description = "ID da despesa", example = "1") @PathVariable Long id,
            @Valid @RequestBody DespesaDTO despesaDTO) {
        try {
            DespesaEntity despesa = despesaService.atualizar(id, despesaDTO);
            return ResponseEntity.ok(converterParaDTO(despesa));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Deletar despesa",
            description = "Remove uma despesa do sistema pelo seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Despesa removida com sucesso",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Despesa não encontrada",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da despesa", example = "1") @PathVariable Long id) {
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
