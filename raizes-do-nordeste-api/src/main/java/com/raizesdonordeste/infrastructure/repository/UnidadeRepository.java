package com.raizesdonordeste.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raizesdonordeste.domain.entity.Unidade;

public interface UnidadeRepository
        extends JpaRepository<Unidade, Long> {

}