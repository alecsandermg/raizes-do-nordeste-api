package com.raizesdonordeste.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.raizesdonordeste.api.dto.unidade.UnidadeRequest;
import com.raizesdonordeste.api.dto.unidade.UnidadeResponse;
import com.raizesdonordeste.domain.entity.Unidade;
import com.raizesdonordeste.infrastructure.repository.UnidadeRepository;

@Service
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;

    public UnidadeService(UnidadeRepository unidadeRepository) {
        this.unidadeRepository = unidadeRepository;
    }

    public UnidadeResponse criar(UnidadeRequest request) {

        Unidade unidade = Unidade.builder()
                .nome(request.getNome())
                .cidade(request.getCidade())
                .estado(request.getEstado())
                .endereco(request.getEndereco())
                .build();

        unidade = unidadeRepository.save(unidade);

        return converterParaResponse(unidade);
    }

    public List<UnidadeResponse> listar() {

        return unidadeRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

    public UnidadeResponse buscarPorId(Long id) {

        Unidade unidade = unidadeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Unidade não encontrada"));

        return converterParaResponse(unidade);
    }

    private UnidadeResponse converterParaResponse(
            Unidade unidade) {

        UnidadeResponse response =
                new UnidadeResponse();

        response.setId(unidade.getId());
        response.setNome(unidade.getNome());
        response.setCidade(unidade.getCidade());
        response.setEstado(unidade.getEstado());
        response.setEndereco(unidade.getEndereco());
        response.setAtivo(unidade.getAtivo());
        response.setDataCriacao(unidade.getDataCriacao());
        response.setDataAtualizacao(unidade.getDataAtualizacao());

        return response;
    }
}