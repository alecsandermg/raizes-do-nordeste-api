package com.raizesdonordeste.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.raizesdonordeste.api.dto.pagamento.PagamentoRequest;
import com.raizesdonordeste.api.dto.pagamento.PagamentoResponse;
import com.raizesdonordeste.application.service.PagamentoService;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(
            PagamentoService pagamentoService) {

        this.pagamentoService = pagamentoService;
    }

    @PostMapping("/{pedidoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public PagamentoResponse processar(
            @PathVariable Long pedidoId,
            @RequestBody PagamentoRequest request) {
    	
    	System.out.println("ENTROU NO PAGAMENTO");

        return pagamentoService
                .processar(pedidoId, request);
    }
}