package com.raizesdonordeste.api.dto.pedido;

import java.math.BigDecimal;
import java.util.List;

public class PedidoResponse {

    private Long id;
    private String status;
    private BigDecimal valorTotal;
    private List<ItemPedidoResponse> itens;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<ItemPedidoResponse> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoResponse> itens) {
        this.itens = itens;
    }
}