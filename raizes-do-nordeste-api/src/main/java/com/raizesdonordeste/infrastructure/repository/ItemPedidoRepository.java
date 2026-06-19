package com.raizesdonordeste.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raizesdonordeste.domain.entity.ItemPedido;

public interface ItemPedidoRepository
        extends JpaRepository<ItemPedido, Long> {

}