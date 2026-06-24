package com.raizesdonordeste.api.dto.fidelidade;

public class ResgatePontosRequest {

    private Long usuarioId;
    private Integer pontos;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }
}