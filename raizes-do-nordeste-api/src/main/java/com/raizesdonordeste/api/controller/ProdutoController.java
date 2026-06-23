package com.raizesdonordeste.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.raizesdonordeste.api.dto.produto.ProdutoRequest;
import com.raizesdonordeste.api.dto.produto.ProdutoResponse;
import com.raizesdonordeste.application.service.ProdutoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponse criar(
            @RequestBody ProdutoRequest request) {

        return produtoService.criar(request);
    }

    @GetMapping
    public List<ProdutoResponse> listar() {

        return produtoService.listar();
    }

    @GetMapping("/{id}")
    public ProdutoResponse buscarPorId(
            @PathVariable Long id) {

        return produtoService.buscarPorId(id);
    }
}