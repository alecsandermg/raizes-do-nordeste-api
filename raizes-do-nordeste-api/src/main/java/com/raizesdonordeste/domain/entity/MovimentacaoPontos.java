package com.raizesdonordeste.domain.entity;

import java.time.LocalDateTime;

import com.raizesdonordeste.domain.enums.TipoMovimentacaoPontos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movimentacao_pontos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimentacaoPontos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
        name = "fidelidade_cliente_id",
        nullable = false
    )
    private FidelidadeCliente fidelidadeCliente;

    @Column(nullable = false)
    private Integer quantidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimentacaoPontos tipo;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
    }
}