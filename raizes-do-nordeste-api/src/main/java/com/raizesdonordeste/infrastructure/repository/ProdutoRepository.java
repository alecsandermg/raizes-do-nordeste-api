package com.raizesdonordeste.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raizesdonordeste.domain.entity.Produto;

public interface ProdutoRepository
        extends JpaRepository<Produto, Long> {

}