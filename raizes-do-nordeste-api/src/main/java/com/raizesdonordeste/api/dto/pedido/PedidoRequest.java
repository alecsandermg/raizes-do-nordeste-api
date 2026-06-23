package com.raizesdonordeste.api.dto.pedido;

import java.util.List;

public class PedidoRequest {

    private Long usuarioId;
    private Long unidadeId;
    private List<ItemPedidoRequest> itens;
    private String canalPedido;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getUnidadeId() {
        return unidadeId;
    }

    public void setUnidadeId(Long unidadeId) {
        this.unidadeId = unidadeId;
    }

    public List<ItemPedidoRequest> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoRequest> itens) {
        this.itens = itens;
    }
    public String getCanalPedido() {
        return canalPedido;
    }
}