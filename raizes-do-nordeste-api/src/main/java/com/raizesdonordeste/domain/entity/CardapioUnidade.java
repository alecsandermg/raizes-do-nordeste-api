package com.raizesdonordeste.domain.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cardapio_unidade")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardapioUnidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "unidade_id")
    private Unidade unidade;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Column(nullable = false)
    private BigDecimal precoVenda;

    @Column(nullable = false)
    private Boolean disponivel;
}