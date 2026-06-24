package com.raizesdonordeste.api.controller;

import org.springframework.web.bind.annotation.*;
import com.raizesdonordeste.api.dto.fidelidade.FidelidadeResponse;
import com.raizesdonordeste.application.service.FidelidadeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.raizesdonordeste.api.dto.fidelidade.ResgatePontosRequest;

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
    @PostMapping("/resgatar")
    public FidelidadeResponse resgatar(
            @RequestBody ResgatePontosRequest request) {

        return fidelidadeService.resgatar(
                request.getUsuarioId(),
                request.getPontos());
    }
}