package com.raizesdonordeste.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.raizesdonordeste.api.dto.cardapio.CardapioRequest;
import com.raizesdonordeste.api.dto.cardapio.CardapioResponse;
import com.raizesdonordeste.domain.entity.CardapioUnidade;
import com.raizesdonordeste.domain.entity.Produto;
import com.raizesdonordeste.domain.entity.Unidade;
import com.raizesdonordeste.infrastructure.repository.CardapioUnidadeRepository;
import com.raizesdonordeste.infrastructure.repository.ProdutoRepository;
import com.raizesdonordeste.infrastructure.repository.UnidadeRepository;

@Service
public class CardapioService {

    private final CardapioUnidadeRepository cardapioRepository;
    private final UnidadeRepository unidadeRepository;
    private final ProdutoRepository produtoRepository;

    public CardapioService(
            CardapioUnidadeRepository cardapioRepository,
            UnidadeRepository unidadeRepository,
            ProdutoRepository produtoRepository) {

        this.cardapioRepository = cardapioRepository;
        this.unidadeRepository = unidadeRepository;
        this.produtoRepository = produtoRepository;
    }

    public CardapioResponse criar(CardapioRequest request) {

        Unidade unidade = unidadeRepository.findById(
                request.getUnidadeId())
                .orElseThrow(() ->
                        new RuntimeException("Unidade não encontrada"));

        Produto produto = produtoRepository.findById(
                request.getProdutoId())
                .orElseThrow(() ->
                        new RuntimeException("Produto não encontrado"));

        CardapioUnidade cardapio = CardapioUnidade.builder()
                .unidade(unidade)
                .produto(produto)
                .precoVenda(request.getPrecoVenda())
                .disponivel(request.getDisponivel())
                .build();

        cardapio = cardapioRepository.save(cardapio);

        return converterParaResponse(cardapio);
    }

    public List<CardapioResponse> listarPorUnidade(Long unidadeId) {

        return cardapioRepository.findByUnidadeId(unidadeId)
                .stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

    private CardapioResponse converterParaResponse(
            CardapioUnidade cardapio) {

        CardapioResponse response =
                new CardapioResponse();

        response.setId(cardapio.getId());
        response.setUnidadeId(
                cardapio.getUnidade().getId());
        response.setProdutoId(
                cardapio.getProduto().getId());
        response.setProdutoNome(
                cardapio.getProduto().getNome());
        response.setPrecoVenda(
                cardapio.getPrecoVenda());
        response.setDisponivel(
                cardapio.getDisponivel());

        return response;
    }
}