package com.example.sistemafinanceiro.controller;

import com.example.sistemafinanceiro.dto.UsuarioDTO;
import com.example.sistemafinanceiro.entity.UsuarioEntity;
import com.example.sistemafinanceiro.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Usuários", description = "Cadastro e gerenciamento de usuários do sistema")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(
            summary = "Criar usuário",
            description = "Cadastra um novo usuário no sistema. O e-mail deve ser único.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou e-mail já cadastrado",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioEntity usuario = usuarioService.criar(usuarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(converterParaDTO(usuario));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(
            summary = "Listar todos os usuários",
            description = "Retorna a lista completa de usuários cadastrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioDTO.class))))
    })
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        List<UsuarioEntity> usuarios = usuarioService.listar();
        List<UsuarioDTO> dtos = usuarios.stream().map(this::converterParaDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna os dados de um usuário específico pelo seu identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                    content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obterPorId(
            @Parameter(description = "ID do usuário", example = "1") @PathVariable Long id) {
        Optional<UsuarioEntity> usuario = usuarioService.obterPorId(id);
        return usuario.map(u -> ResponseEntity.ok(converterParaDTO(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Buscar usuário por e-mail",
            description = "Retorna os dados de um usuário pelo seu endereço de e-mail.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                    content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content)
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioDTO> obterPorEmail(
            @Parameter(description = "E-mail do usuário", example = "joao@email.com") @PathVariable String email) {
        Optional<UsuarioEntity> usuario = usuarioService.obterPorEmail(email);
        return usuario.map(u -> ResponseEntity.ok(converterParaDTO(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza os dados de um usuário existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(
            @Parameter(description = "ID do usuário", example = "1") @PathVariable Long id,
            @Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioEntity usuario = usuarioService.atualizar(id, usuarioDTO);
            return ResponseEntity.ok(converterParaDTO(usuario));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Deletar usuário",
            description = "Remove um usuário do sistema pelo seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do usuário", example = "1") @PathVariable Long id) {
        try {
            usuarioService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Verificar disponibilidade de e-mail",
            description = "Informa se um endereço de e-mail já está em uso. Retorna `true` se existir.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Verificação realizada",
                    content = @Content(schema = @Schema(implementation = Boolean.class)))
    })
    @GetMapping("/verificar-email/{email}")
    public ResponseEntity<Boolean> verificarEmail(
            @Parameter(description = "E-mail a verificar", example = "joao@email.com") @PathVariable String email) {
        boolean existe = usuarioService.existeEmail(email);
        return ResponseEntity.ok(existe);
    }

    private UsuarioDTO converterParaDTO(UsuarioEntity usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .telefone(usuario.getTelefone())
                .dataNascimento(usuario.getDataNascimento())
                .cpf(usuario.getCpf())
                .ativo(usuario.getAtivo())
                .dataCriacao(usuario.getDataCriacao())
                .dataAtualizacao(usuario.getDataAtualizacao())
                .build();
    }
}
