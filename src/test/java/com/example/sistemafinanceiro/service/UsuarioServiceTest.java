package com.example.sistemafinanceiro.service;

import com.example.sistemafinanceiro.dto.UsuarioDTO;
import com.example.sistemafinanceiro.entity.UsuarioEntity;
import com.example.sistemafinanceiro.repository.UsuarioRepository;
import com.example.sistemafinanceiro.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("UsuarioService Tests")
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private UsuarioDTO usuarioDTO;
    private UsuarioEntity usuarioEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuarioDTO = UsuarioDTO.builder()
                .nome("João Silva")
                .email("joao@example.com")
                .telefone("11999999999")
                .dataNascimento("1990-05-15")
                .cpf("12345678900")
                .build();

        usuarioEntity = UsuarioEntity.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@example.com")
                .senha("encrypted_password")
                .telefone("11999999999")
                .dataNascimento("1990-05-15")
                .cpf("12345678900")
                .ativo(true)
                .build();
    }

    @Test
    @DisplayName("Deve criar um usuário com sucesso")
    void testCriarUsuarioComSucesso() {
        // Arrange
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encrypted_password");
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);

        // Act
        UsuarioEntity resultado = usuarioService.criar(usuarioDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
        assertEquals("joao@example.com", resultado.getEmail());
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando email já existe")
    void testCriarUsuarioComEmailDuplicado() {
        // Arrange
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> usuarioService.criar(usuarioDTO));
        verify(usuarioRepository, never()).save(any(UsuarioEntity.class));
    }

    @Test
    @DisplayName("Deve obter usuário por ID com sucesso")
    void testObterPorIdComSucesso() {
        // Arrange
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioEntity));

        // Act
        Optional<UsuarioEntity> resultado = usuarioService.obterPorId(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("João Silva", resultado.get().getNome());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve retornar vazio quando usuário não encontrado")
    void testObterPorIdNaoEncontrado() {
        // Arrange
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<UsuarioEntity> resultado = usuarioService.obterPorId(999L);

        // Assert
        assertTrue(resultado.isEmpty());
        verify(usuarioRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Deve obter usuário por email com sucesso")
    void testObterPorEmailComSucesso() {
        // Arrange
        when(usuarioRepository.findByEmail("joao@example.com")).thenReturn(Optional.of(usuarioEntity));

        // Act
        Optional<UsuarioEntity> resultado = usuarioService.obterPorEmail("joao@example.com");

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("joao@example.com", resultado.get().getEmail());
        verify(usuarioRepository, times(1)).findByEmail("joao@example.com");
    }

    @Test
    @DisplayName("Deve atualizar usuário com sucesso")
    void testAtualizarComSucesso() {
        // Arrange
        UsuarioDTO atualizacao = UsuarioDTO.builder()
                .nome("João Silva Atualizado")
                .email("joao@example.com")
                .telefone("11988888888")
                .dataNascimento("1990-05-15")
                .cpf("12345678900")
                .ativo(true)
                .build();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioEntity));
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);

        // Act
        UsuarioEntity resultado = usuarioService.atualizar(1L, atualizacao);

        // Assert
        assertNotNull(resultado);
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    @DisplayName("Deve deletar usuário com sucesso")
    void testDeletarComSucesso() {
        // Arrange
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioEntity));
        doNothing().when(usuarioRepository).delete(usuarioEntity);

        // Act
        usuarioService.deletar(1L);

        // Assert
        verify(usuarioRepository, times(1)).delete(usuarioEntity);
    }

    @Test
    @DisplayName("Deve verificar se email existe")
    void testExisteEmail() {
        // Arrange
        when(usuarioRepository.existsByEmail("joao@example.com")).thenReturn(true);

        // Act
        boolean existe = usuarioService.existeEmail("joao@example.com");

        // Assert
        assertTrue(existe);
        verify(usuarioRepository, times(1)).existsByEmail("joao@example.com");
    }
}
