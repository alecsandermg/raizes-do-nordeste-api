package com.raizesdonordeste.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.raizesdonordeste.api.dto.estoque.EstoqueRequest;
import com.raizesdonordeste.api.dto.estoque.EstoqueResponse;
import com.raizesdonordeste.application.service.EstoqueService;

@RestController
@RequestMapping("/estoques")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(
            EstoqueService estoqueService) {

        this.estoqueService = estoqueService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstoqueResponse criar(
            @RequestBody EstoqueRequest request) {

        return estoqueService.criar(request);
    }

    @GetMapping("/unidade/{unidadeId}")
    public List<EstoqueResponse> listarPorUnidade(
            @PathVariable Long unidadeId) {

        return estoqueService
                .listarPorUnidade(unidadeId);
    }
}