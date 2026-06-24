package com.raizesdonordeste.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.raizesdonordeste.api.dto.pedido.PedidoRequest;
import com.raizesdonordeste.api.dto.pedido.PedidoResponse;
import com.raizesdonordeste.application.service.PedidoService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.raizesdonordeste.api.dto.pedido.PedidoStatusRequest;

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
    @PatchMapping("/{id}/status")
    public PedidoResponse alterarStatus(
            @PathVariable Long id,
            @RequestBody PedidoStatusRequest request) {

        return pedidoService.alterarStatus(
                id,
                request);
    }
    @PatchMapping("/{id}/cancelar")
    public PedidoResponse cancelar(
            @PathVariable Long id) {

        return pedidoService.cancelar(id);
    }
}