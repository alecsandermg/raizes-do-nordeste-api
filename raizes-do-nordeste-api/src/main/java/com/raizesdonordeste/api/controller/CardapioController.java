package com.raizesdonordeste.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.raizesdonordeste.api.dto.cardapio.CardapioRequest;
import com.raizesdonordeste.api.dto.cardapio.CardapioResponse;
import com.raizesdonordeste.application.service.CardapioService;

@RestController
@RequestMapping("/cardapios")
public class CardapioController {

    private final CardapioService cardapioService;

    public CardapioController(
            CardapioService cardapioService) {

        this.cardapioService = cardapioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CardapioResponse criar(
            @RequestBody CardapioRequest request) {

        return cardapioService.criar(request);
    }

    @GetMapping("/unidade/{unidadeId}")
    public List<CardapioResponse> listarPorUnidade(
            @PathVariable Long unidadeId) {

        return cardapioService
                .listarPorUnidade(unidadeId);
    }
}