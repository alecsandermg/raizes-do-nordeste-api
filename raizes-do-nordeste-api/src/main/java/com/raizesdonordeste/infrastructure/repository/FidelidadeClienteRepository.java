package com.raizesdonordeste.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raizesdonordeste.domain.entity.FidelidadeCliente;

public interface FidelidadeClienteRepository
        extends JpaRepository<FidelidadeCliente, Long> {

    Optional<FidelidadeCliente>
        findByUsuarioId(Long usuarioId);
}