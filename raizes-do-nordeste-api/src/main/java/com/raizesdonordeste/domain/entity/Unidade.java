package com.raizesdonordeste.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "unidades")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private Boolean ativo;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    
    private LocalDateTime dataAtualizacao;

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
        this.ativo = true;
        
    }

     @PreUpdate
      public void preUpdate() {
          this.dataAtualizacao = LocalDateTime.now();
        }
}