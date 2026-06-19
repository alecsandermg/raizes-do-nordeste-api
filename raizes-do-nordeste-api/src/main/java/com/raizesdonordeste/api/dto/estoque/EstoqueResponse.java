package com.raizesdonordeste.api.dto.estoque;

public class EstoqueResponse {

    private Long id;
    private Long unidadeId;
    private Long produtoId;
    private String produtoNome;
    private Integer quantidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUnidadeId() {
        return unidadeId;
    }

    public void setUnidadeId(Long unidadeId) {
        this.unidadeId = unidadeId;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}