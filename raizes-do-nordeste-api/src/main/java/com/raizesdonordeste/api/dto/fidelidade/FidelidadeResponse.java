package com.raizesdonordeste.api.dto.fidelidade;

public class FidelidadeResponse {

    private Long usuarioId;
    private Integer saldoPontos;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getSaldoPontos() {
        return saldoPontos;
    }

    public void setSaldoPontos(Integer saldoPontos) {
        this.saldoPontos = saldoPontos;
    }
}