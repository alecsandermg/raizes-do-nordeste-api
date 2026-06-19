package com.raizesdonordeste.api.controller;

import org.springframework.web.bind.annotation.*;

import com.raizesdonordeste.api.dto.fidelidade.FidelidadeResponse;
import com.raizesdonordeste.application.service.FidelidadeService;

@RestController
@RequestMapping("/fidelidade")
public class FidelidadeController {

    private final FidelidadeService fidelidadeService;

    public FidelidadeController(
            FidelidadeService fidelidadeService) {

        this.fidelidadeService =
                fidelidadeService;
    }

    @GetMapping("/{usuarioId}")
    public FidelidadeResponse consultar(
            @PathVariable Long usuarioId) {

        return fidelidadeService
                .consultar(usuarioId);
    }
}