package com.raizesdonordeste.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raizesdonordeste.domain.entity.MovimentacaoPontos;

public interface MovimentacaoPontosRepository
        extends JpaRepository<MovimentacaoPontos, Long> {

}