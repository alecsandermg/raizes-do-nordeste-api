package com.raizesdonordeste.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.raizesdonordeste.api.dto.pedido.PedidoRequest;
import com.raizesdonordeste.api.dto.pedido.PedidoResponse;
import com.raizesdonordeste.application.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(
            PedidoService pedidoService) {

        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse criar(
            @RequestBody PedidoRequest request) {

        return pedidoService.criar(request);
    }
}