package com.raizesdonordeste.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.raizesdonordeste.api.dto.estoque.EstoqueRequest;
import com.raizesdonordeste.api.dto.estoque.EstoqueResponse;
import com.raizesdonordeste.domain.entity.Estoque;
import com.raizesdonordeste.domain.entity.Produto;
import com.raizesdonordeste.domain.entity.Unidade;
import com.raizesdonordeste.infrastructure.repository.EstoqueRepository;
import com.raizesdonordeste.infrastructure.repository.ProdutoRepository;
import com.raizesdonordeste.infrastructure.repository.UnidadeRepository;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final UnidadeRepository unidadeRepository;
    private final ProdutoRepository produtoRepository;

    public EstoqueService(
            EstoqueRepository estoqueRepository,
            UnidadeRepository unidadeRepository,
            ProdutoRepository produtoRepository) {

        this.estoqueRepository = estoqueRepository;
        this.unidadeRepository = unidadeRepository;
        this.produtoRepository = produtoRepository;
    }

    public EstoqueResponse criar(EstoqueRequest request) {

        Unidade unidade = unidadeRepository.findById(
                request.getUnidadeId())
                .orElseThrow(() ->
                        new RuntimeException("Unidade não encontrada"));

        Produto produto = produtoRepository.findById(
                request.getProdutoId())
                .orElseThrow(() ->
                        new RuntimeException("Produto não encontrado"));

        Estoque estoque = Estoque.builder()
                .unidade(unidade)
                .produto(produto)
                .quantidade(request.getQuantidade())
                .build();

        estoque = estoqueRepository.save(estoque);

        return converterParaResponse(estoque);
    }

    public List<EstoqueResponse> listarPorUnidade(Long unidadeId) {

        return estoqueRepository.findAll()
                .stream()
                .filter(e ->
                        e.getUnidade().getId().equals(unidadeId))
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

    private EstoqueResponse converterParaResponse(
            Estoque estoque) {

        EstoqueResponse response =
                new EstoqueResponse();

        response.setId(estoque.getId());
        response.setUnidadeId(
                estoque.getUnidade().getId());
        response.setProdutoId(
                estoque.getProduto().getId());
        response.setProdutoNome(
                estoque.getProduto().getNome());
        response.setQuantidade(
                estoque.getQuantidade());

        return response;
    }
}