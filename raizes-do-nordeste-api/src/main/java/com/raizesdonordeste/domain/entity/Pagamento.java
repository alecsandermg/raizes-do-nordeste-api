package com.raizesdonordeste.domain.entity;

import java.time.LocalDateTime;

import com.raizesdonordeste.domain.enums.StatusPagamento;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pagamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPagamento status;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @PrePersist
    public void prePersist() {

        this.dataCriacao = LocalDateTime.now();

        if (this.status == null) {
            this.status = StatusPagamento.PENDENTE;
        }
    }
}