package com.example.sistemafinanceiro.service;

import com.example.sistemafinanceiro.dto.GanhoDTO;
import com.example.sistemafinanceiro.entity.GanhoEntity;
import com.example.sistemafinanceiro.entity.UsuarioEntity;
import com.example.sistemafinanceiro.repository.GanhoRepository;
import com.example.sistemafinanceiro.repository.UsuarioRepository;
import com.example.sistemafinanceiro.service.impl.GanhoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("GanhoService Tests")
class GanhoServiceTest {

    @Mock
    private GanhoRepository ganhoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private GanhoServiceImpl ganhoService;

    private GanhoDTO ganhoDTO;
    private GanhoEntity ganhoEntity;
    private UsuarioEntity usuarioEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuarioEntity = UsuarioEntity.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@example.com")
                .ativo(true)
                .build();

        ganhoDTO = GanhoDTO.builder()
                .descricao("Salário")
                .valor(new BigDecimal("3000.00"))
                .dataGanho(LocalDate.of(2026, 8, 15))
                .tipo("SALARIO")
                .descricaoDetalhada("Salário mensal")
                .recorrente(true)
                .tipoRecorrencia("MENSAL")
                .usuarioId(1L)
                .build();

        ganhoEntity = GanhoEntity.builder()
                .id(1L)
                .descricao("Salário")
                .valor(new BigDecimal("3000.00"))
                .dataGanho(LocalDate.of(2026, 8, 15))
                .tipo("SALARIO")
                .descricaoDetalhada("Salário mensal")
                .recorrente(true)
                .tipoRecorrencia("MENSAL")
                .usuario(usuarioEntity)
                .build();
    }

    @Test
    @DisplayName("Deve criar um ganho com sucesso")
    void testCriarGanhoComSucesso() {
        // Arrange
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioEntity));
        when(ganhoRepository.save(any(GanhoEntity.class))).thenReturn(ganhoEntity);

        // Act
        GanhoEntity resultado = ganhoService.criar(ganhoDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals("Salário", resultado.getDescricao());
        assertEquals(new BigDecimal("3000.00"), resultado.getValor());
        verify(ganhoRepository, times(1)).save(any(GanhoEntity.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não encontrado")
    void testCriarGanhoUsuarioNaoEncontrado() {
        // Arrange
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> ganhoService.criar(ganhoDTO));
        verify(ganhoRepository, never()).save(any(GanhoEntity.class));
    }

    @Test
    @DisplayName("Deve obter ganho por ID com sucesso")
    void testObterPorIdComSucesso() {
        // Arrange
        when(ganhoRepository.findById(1L)).thenReturn(Optional.of(ganhoEntity));

        // Act
        Optional<GanhoEntity> resultado = ganhoService.obterPorId(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Salário", resultado.get().getDescricao());
        verify(ganhoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve listar ganhos por usuário")
    void testListarPorUsuario() {
        // Arrange
        List<GanhoEntity> ganhos = Arrays.asList(ganhoEntity);
        when(ganhoRepository.findByUsuarioId(1L)).thenReturn(ganhos);

        // Act
        List<GanhoEntity> resultado = ganhoService.listarPorUsuario(1L);

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(ganhoRepository, times(1)).findByUsuarioId(1L);
    }

    @Test
    @DisplayName("Deve listar ganhos por mês")
    void testListarPorMes() {
        // Arrange
        List<GanhoEntity> ganhos = Arrays.asList(ganhoEntity);
        when(ganhoRepository.findByUsuarioIdAndMes(1L, 2026, 8)).thenReturn(ganhos);

        // Act
        List<GanhoEntity> resultado = ganhoService.listarPorMes(1L, 2026, 8);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(ganhoRepository, times(1)).findByUsuarioIdAndMes(1L, 2026, 8);
    }

    @Test
    @DisplayName("Deve atualizar ganho com sucesso")
    void testAtualizarComSucesso() {
        // Arrange
        GanhoDTO atualizacao = GanhoDTO.builder()
                .descricao("Salário Atualizado")
                .valor(new BigDecimal("3500.00"))
                .dataGanho(LocalDate.of(2026, 8, 20))
                .build();

        when(ganhoRepository.findById(1L)).thenReturn(Optional.of(ganhoEntity));
        when(ganhoRepository.save(any(GanhoEntity.class))).thenReturn(ganhoEntity);

        // Act
        GanhoEntity resultado = ganhoService.atualizar(1L, atualizacao);

        // Assert
        assertNotNull(resultado);
        verify(ganhoRepository, times(1)).save(any(GanhoEntity.class));
    }

    @Test
    @DisplayName("Deve deletar ganho com sucesso")
    void testDeletarComSucesso() {
        // Arrange
        when(ganhoRepository.findById(1L)).thenReturn(Optional.of(ganhoEntity));
        doNothing().when(ganhoRepository).delete(ganhoEntity);

        // Act
        ganhoService.deletar(1L);

        // Assert
        verify(ganhoRepository, times(1)).delete(ganhoEntity);
    }
}
