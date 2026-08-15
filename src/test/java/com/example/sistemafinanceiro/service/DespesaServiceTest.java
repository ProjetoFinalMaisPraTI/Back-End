package com.example.sistemafinanceiro.service;

import com.example.sistemafinanceiro.dto.DespesaDTO;
import com.example.sistemafinanceiro.entity.DespesaEntity;
import com.example.sistemafinanceiro.entity.UsuarioEntity;
import com.example.sistemafinanceiro.repository.DespesaRepository;
import com.example.sistemafinanceiro.repository.UsuarioRepository;
import com.example.sistemafinanceiro.service.impl.DespesaServiceImpl;
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

@DisplayName("DespesaService Tests")
class DespesaServiceTest {

    @Mock
    private DespesaRepository despesaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private DespesaServiceImpl despesaService;

    private DespesaDTO despesaDTO;
    private DespesaEntity despesaEntity;
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

        despesaDTO = DespesaDTO.builder()
                .descricao("Despesa com Alimentação")
                .valor(new BigDecimal("150.00"))
                .dataDespesa(LocalDate.of(2026, 8, 15))
                .categoria("Alimentação")
                .descricaoDetalhada("Compras no supermercado")
                .recorrente(false)
                .paga(false)
                .usuarioId(1L)
                .build();

        despesaEntity = DespesaEntity.builder()
                .id(1L)
                .descricao("Despesa com Alimentação")
                .valor(new BigDecimal("150.00"))
                .dataDespesa(LocalDate.of(2026, 8, 15))
                .categoria("Alimentação")
                .descricaoDetalhada("Compras no supermercado")
                .recorrente(false)
                .paga(false)
                .usuario(usuarioEntity)
                .build();
    }

    @Test
    @DisplayName("Deve criar uma despesa com sucesso")
    void testCriarDespesaComSucesso() {
        // Arrange
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioEntity));
        when(despesaRepository.save(any(DespesaEntity.class))).thenReturn(despesaEntity);

        // Act
        DespesaEntity resultado = despesaService.criar(despesaDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals("Despesa com Alimentação", resultado.getDescricao());
        assertEquals(new BigDecimal("150.00"), resultado.getValor());
        verify(despesaRepository, times(1)).save(any(DespesaEntity.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não encontrado")
    void testCriarDespesaUsuarioNaoEncontrado() {
        // Arrange
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> despesaService.criar(despesaDTO));
        verify(despesaRepository, never()).save(any(DespesaEntity.class));
    }

    @Test
    @DisplayName("Deve obter despesa por ID com sucesso")
    void testObterPorIdComSucesso() {
        // Arrange
        when(despesaRepository.findById(1L)).thenReturn(Optional.of(despesaEntity));

        // Act
        Optional<DespesaEntity> resultado = despesaService.obterPorId(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Despesa com Alimentação", resultado.get().getDescricao());
        verify(despesaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve listar despesas por usuário")
    void testListarPorUsuario() {
        // Arrange
        List<DespesaEntity> despesas = Arrays.asList(despesaEntity);
        when(despesaRepository.findByUsuarioId(1L)).thenReturn(despesas);

        // Act
        List<DespesaEntity> resultado = despesaService.listarPorUsuario(1L);

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(despesaRepository, times(1)).findByUsuarioId(1L);
    }

    @Test
    @DisplayName("Deve listar despesas por mês")
    void testListarPorMes() {
        // Arrange
        List<DespesaEntity> despesas = Arrays.asList(despesaEntity);
        when(despesaRepository.findByUsuarioIdAndMes(1L, 2026, 8)).thenReturn(despesas);

        // Act
        List<DespesaEntity> resultado = despesaService.listarPorMes(1L, 2026, 8);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(despesaRepository, times(1)).findByUsuarioIdAndMes(1L, 2026, 8);
    }

    @Test
    @DisplayName("Deve atualizar despesa com sucesso")
    void testAtualizarComSucesso() {
        // Arrange
        DespesaDTO atualizacao = DespesaDTO.builder()
                .descricao("Despesa Atualizada")
                .valor(new BigDecimal("200.00"))
                .dataDespesa(LocalDate.of(2026, 8, 16))
                .paga(true)
                .build();

        when(despesaRepository.findById(1L)).thenReturn(Optional.of(despesaEntity));
        when(despesaRepository.save(any(DespesaEntity.class))).thenReturn(despesaEntity);

        // Act
        DespesaEntity resultado = despesaService.atualizar(1L, atualizacao);

        // Assert
        assertNotNull(resultado);
        verify(despesaRepository, times(1)).save(any(DespesaEntity.class));
    }

    @Test
    @DisplayName("Deve deletar despesa com sucesso")
    void testDeletarComSucesso() {
        // Arrange
        when(despesaRepository.findById(1L)).thenReturn(Optional.of(despesaEntity));
        doNothing().when(despesaRepository).delete(despesaEntity);

        // Act
        despesaService.deletar(1L);

        // Assert
        verify(despesaRepository, times(1)).delete(despesaEntity);
    }
}
