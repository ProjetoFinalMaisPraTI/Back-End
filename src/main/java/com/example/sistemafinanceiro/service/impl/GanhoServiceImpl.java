package com.example.sistemafinanceiro.service.impl;

import com.example.sistemafinanceiro.dto.GanhoDTO;
import com.example.sistemafinanceiro.entity.GanhoEntity;
import com.example.sistemafinanceiro.entity.UsuarioEntity;
import com.example.sistemafinanceiro.repository.GanhoRepository;
import com.example.sistemafinanceiro.repository.UsuarioRepository;
import com.example.sistemafinanceiro.service.GanhoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GanhoServiceImpl implements GanhoService {

    private final GanhoRepository ganhoRepository;
    private final UsuarioRepository usuarioRepository;

    public GanhoServiceImpl(GanhoRepository ganhoRepository, UsuarioRepository usuarioRepository) {
        this.ganhoRepository = ganhoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public GanhoEntity criar(GanhoDTO ganhoDTO) {
        UsuarioEntity usuario = usuarioRepository.findById(ganhoDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        GanhoEntity ganho = GanhoEntity.builder()
                .descricao(ganhoDTO.getDescricao())
                .valor(ganhoDTO.getValor())
                .dataGanho(ganhoDTO.getDataGanho())
                .tipo(ganhoDTO.getTipo())
                .descricaoDetalhada(ganhoDTO.getDescricaoDetalhada())
                .recorrente(ganhoDTO.getRecorrente() != null ? ganhoDTO.getRecorrente() : false)
                .tipoRecorrencia(ganhoDTO.getTipoRecorrencia())
                .usuario(usuario)
                .build();

        return ganhoRepository.save(ganho);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GanhoEntity> obterPorId(Long id) {
        return ganhoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GanhoEntity> listarPorUsuario(Long usuarioId) {
        return ganhoRepository.findByUsuarioId(usuarioId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GanhoEntity> listarPorMes(Long usuarioId, int ano, int mes) {
        return ganhoRepository.findByUsuarioIdAndMes(usuarioId, ano, mes);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GanhoEntity> listarPorPeriodo(Long usuarioId, LocalDate dataInicio, LocalDate dataFim) {
        return ganhoRepository.findByUsuarioIdAndDataGanhoBetween(usuarioId, dataInicio, dataFim);
    }

    @Override
    public GanhoEntity atualizar(Long id, GanhoDTO ganhoDTO) {
        GanhoEntity ganho = ganhoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ganho não encontrado"));

        ganho.setDescricao(ganhoDTO.getDescricao());
        ganho.setValor(ganhoDTO.getValor());
        ganho.setDataGanho(ganhoDTO.getDataGanho());
        ganho.setTipo(ganhoDTO.getTipo());
        ganho.setDescricaoDetalhada(ganhoDTO.getDescricaoDetalhada());
        ganho.setRecorrente(ganhoDTO.getRecorrente());
        ganho.setTipoRecorrencia(ganhoDTO.getTipoRecorrencia());

        return ganhoRepository.save(ganho);
    }

    @Override
    public void deletar(Long id) {
        GanhoEntity ganho = ganhoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ganho não encontrado"));
        ganhoRepository.delete(ganho);
    }
}
