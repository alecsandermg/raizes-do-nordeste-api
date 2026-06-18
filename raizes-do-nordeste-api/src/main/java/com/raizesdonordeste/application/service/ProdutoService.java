package com.raizesdonordeste.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.raizesdonordeste.api.dto.produto.ProdutoRequest;
import com.raizesdonordeste.api.dto.produto.ProdutoResponse;
import com.raizesdonordeste.domain.entity.Produto;
import com.raizesdonordeste.infrastructure.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoResponse criar(ProdutoRequest request) {

        Produto produto = Produto.builder()
                .nome(request.getNome())
                .descricao(request.getDescricao())
                .precoBase(request.getPrecoBase())
                .categoria(request.getCategoria())
                .build();

        produto = produtoRepository.save(produto);

        return converterParaResponse(produto);
    }

    public List<ProdutoResponse> listar() {

        return produtoRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

    public ProdutoResponse buscarPorId(Long id) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Produto não encontrado"));

        return converterParaResponse(produto);
    }

    private ProdutoResponse converterParaResponse(
            Produto produto) {

        ProdutoResponse response =
                new ProdutoResponse();

        response.setId(produto.getId());
        response.setNome(produto.getNome());
        response.setDescricao(produto.getDescricao());
        response.setPrecoBase(produto.getPrecoBase());
        response.setCategoria(produto.getCategoria());
        response.setAtivo(produto.getAtivo());

        return response;
    }
}