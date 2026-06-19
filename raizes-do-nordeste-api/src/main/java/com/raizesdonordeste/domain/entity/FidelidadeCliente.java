package com.raizesdonordeste.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fidelidade_clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FidelidadeCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private Integer saldoPontos;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @PrePersist
    public void prePersist() {

        this.dataCriacao = LocalDateTime.now();

        if (this.saldoPontos == null) {
            this.saldoPontos = 0;
        }
    }
}