package com.raizesdonordeste.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raizesdonordeste.domain.entity.Estoque;

public interface EstoqueRepository
        extends JpaRepository<Estoque, Long> {

    Optional<Estoque> findByUnidadeIdAndProdutoId(
            Long unidadeId,
            Long produtoId);
}