package com.raizesdonordeste.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.raizesdonordeste.api.dto.unidade.UnidadeRequest;
import com.raizesdonordeste.api.dto.unidade.UnidadeResponse;
import com.raizesdonordeste.application.service.UnidadeService;

@RestController
@RequestMapping("/unidades")
public class UnidadeController {

    private final UnidadeService unidadeService;

    public UnidadeController(UnidadeService unidadeService) {
        this.unidadeService = unidadeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UnidadeResponse criar(
            @RequestBody UnidadeRequest request) {

        return unidadeService.criar(request);
    }

    @GetMapping
    public List<UnidadeResponse> listar() {

        return unidadeService.listar();
    }

    @GetMapping("/{id}")
    public UnidadeResponse buscarPorId(
            @PathVariable Long id) {

        return unidadeService.buscarPorId(id);
    }
}