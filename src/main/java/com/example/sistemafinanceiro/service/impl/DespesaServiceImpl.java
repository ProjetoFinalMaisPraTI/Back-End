package com.example.sistemafinanceiro.service.impl;

import com.example.sistemafinanceiro.dto.DespesaDTO;
import com.example.sistemafinanceiro.entity.DespesaEntity;
import com.example.sistemafinanceiro.entity.UsuarioEntity;
import com.example.sistemafinanceiro.repository.DespesaRepository;
import com.example.sistemafinanceiro.repository.UsuarioRepository;
import com.example.sistemafinanceiro.service.DespesaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DespesaServiceImpl implements DespesaService {

    private final DespesaRepository despesaRepository;
    private final UsuarioRepository usuarioRepository;

    public DespesaServiceImpl(DespesaRepository despesaRepository, UsuarioRepository usuarioRepository) {
        this.despesaRepository = despesaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public DespesaEntity criar(DespesaDTO despesaDTO) {
        UsuarioEntity usuario = usuarioRepository.findById(despesaDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        DespesaEntity despesa = DespesaEntity.builder()
                .descricao(despesaDTO.getDescricao())
                .valor(despesaDTO.getValor())
                .dataDespesa(despesaDTO.getDataDespesa())
                .categoria(despesaDTO.getCategoria())
                .descricaoDetalhada(despesaDTO.getDescricaoDetalhada())
                .recorrente(despesaDTO.getRecorrente() != null ? despesaDTO.getRecorrente() : false)
                .tipoRecorrencia(despesaDTO.getTipoRecorrencia())
                .paga(despesaDTO.getPaga() != null ? despesaDTO.getPaga() : false)
                .usuario(usuario)
                .build();

        return despesaRepository.save(despesa);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DespesaEntity> obterPorId(Long id) {
        return despesaRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DespesaEntity> listarPorUsuario(Long usuarioId) {
        return despesaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DespesaEntity> listarPorMes(Long usuarioId, int ano, int mes) {
        return despesaRepository.findByUsuarioIdAndMes(usuarioId, ano, mes);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DespesaEntity> listarPorPeriodo(Long usuarioId, LocalDate dataInicio, LocalDate dataFim) {
        return despesaRepository.findByUsuarioIdAndDataDespesaBetween(usuarioId, dataInicio, dataFim);
    }

    @Override
    public DespesaEntity atualizar(Long id, DespesaDTO despesaDTO) {
        DespesaEntity despesa = despesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));

        despesa.setDescricao(despesaDTO.getDescricao());
        despesa.setValor(despesaDTO.getValor());
        despesa.setDataDespesa(despesaDTO.getDataDespesa());
        despesa.setCategoria(despesaDTO.getCategoria());
        despesa.setDescricaoDetalhada(despesaDTO.getDescricaoDetalhada());
        despesa.setRecorrente(despesaDTO.getRecorrente());
        despesa.setTipoRecorrencia(despesaDTO.getTipoRecorrencia());
        despesa.setPaga(despesaDTO.getPaga());

        return despesaRepository.save(despesa);
    }

    @Override
    public void deletar(Long id) {
        DespesaEntity despesa = despesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));
        despesaRepository.delete(despesa);
    }
}
