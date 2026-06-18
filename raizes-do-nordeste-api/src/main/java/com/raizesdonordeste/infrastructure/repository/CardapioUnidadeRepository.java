package com.raizesdonordeste.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raizesdonordeste.domain.entity.CardapioUnidade;

public interface CardapioUnidadeRepository
        extends JpaRepository<CardapioUnidade, Long> {

    List<CardapioUnidade> findByUnidadeId(Long unidadeId);
}