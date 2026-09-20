package com.example.sistemafinanceiro.controller;

import com.example.sistemafinanceiro.dto.GanhoDTO;
import com.example.sistemafinanceiro.entity.GanhoEntity;
import com.example.sistemafinanceiro.service.GanhoService;
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
@RequestMapping("/ganhos")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Ganhos", description = "Registro e consulta de ganhos por usuário, mês e período")
public class GanhoController {

    private final GanhoService ganhoService;

    public GanhoController(GanhoService ganhoService) {
        this.ganhoService = ganhoService;
    }

    @Operation(
            summary = "Criar ganho",
            description = "Registra um novo ganho vinculado a um usuário.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ganho criado com sucesso",
                    content = @Content(schema = @Schema(implementation = GanhoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou usuário não encontrado",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<GanhoDTO> criar(@Valid @RequestBody GanhoDTO ganhoDTO) {
        try {
            GanhoEntity ganho = ganhoService.criar(ganhoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(converterParaDTO(ganho));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(
            summary = "Listar todos os ganhos",
            description = "Retorna a lista completa de ganhos cadastrados no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = GanhoDTO.class))))
    })
    @GetMapping
    public ResponseEntity<List<GanhoDTO>> listar() {
        return ResponseEntity.ok(List.of());
    }

    @Operation(
            summary = "Buscar ganho por ID",
            description = "Retorna os dados de um ganho específico pelo seu identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ganho encontrado",
                    content = @Content(schema = @Schema(implementation = GanhoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ganho não encontrado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<GanhoDTO> obterPorId(
            @Parameter(description = "ID do ganho", example = "1") @PathVariable Long id) {
        Optional<GanhoEntity> ganho = ganhoService.obterPorId(id);
        return ganho.map(g -> ResponseEntity.ok(converterParaDTO(g)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Listar ganhos de um usuário",
            description = "Retorna todos os ganhos vinculados ao usuário informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = GanhoDTO.class))))
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<GanhoDTO>> listarPorUsuario(
            @Parameter(description = "ID do usuário", example = "1") @PathVariable Long usuarioId) {
        List<GanhoEntity> ganhos = ganhoService.listarPorUsuario(usuarioId);
        List<GanhoDTO> dtos = ganhos.stream().map(this::converterParaDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    @Operation(
            summary = "Listar ganhos de um usuário por mês",
            description = "Retorna os ganhos de um usuário filtrados pelo ano e mês informados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = GanhoDTO.class))))
    })
    @GetMapping("/usuario/{usuarioId}/mes")
    public ResponseEntity<List<GanhoDTO>> listarPorMes(
            @Parameter(description = "ID do usuário", example = "1") @PathVariable Long usuarioId,
            @Parameter(description = "Ano de referência", example = "2026") @RequestParam int ano,
            @Parameter(description = "Mês de referência (1–12)", example = "8") @RequestParam int mes) {
        List<GanhoEntity> ganhos = ganhoService.listarPorMes(usuarioId, ano, mes);
        List<GanhoDTO> dtos = ganhos.stream().map(this::converterParaDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    @Operation(
            summary = "Listar ganhos de um usuário por período",
            description = "Retorna os ganhos de um usuário entre duas datas (formato ISO: `yyyy-MM-dd`).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = GanhoDTO.class))))
    })
    @GetMapping("/usuario/{usuarioId}/periodo")
    public ResponseEntity<List<GanhoDTO>> listarPorPeriodo(
            @Parameter(description = "ID do usuário", example = "1") @PathVariable Long usuarioId,
            @Parameter(description = "Data de início (yyyy-MM-dd)", example = "2026-08-01")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @Parameter(description = "Data de fim (yyyy-MM-dd)", example = "2026-08-31")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        List<GanhoEntity> ganhos = ganhoService.listarPorPeriodo(usuarioId, dataInicio, dataFim);
        List<GanhoDTO> dtos = ganhos.stream().map(this::converterParaDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    @Operation(
            summary = "Atualizar ganho",
            description = "Atualiza os dados de um ganho existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ganho atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = GanhoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ganho não encontrado",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<GanhoDTO> atualizar(
            @Parameter(description = "ID do ganho", example = "1") @PathVariable Long id,
            @Valid @RequestBody GanhoDTO ganhoDTO) {
        try {
            GanhoEntity ganho = ganhoService.atualizar(id, ganhoDTO);
            return ResponseEntity.ok(converterParaDTO(ganho));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Deletar ganho",
            description = "Remove um ganho do sistema pelo seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ganho removido com sucesso",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Ganho não encontrado",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do ganho", example = "1") @PathVariable Long id) {
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
