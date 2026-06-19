package com.raizesdonordeste.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raizesdonordeste.domain.entity.Pedido;
import java.util.List;
import java.util.Optional;

public interface PedidoRepository
        extends JpaRepository<Pedido, Long> {
	
	List<Pedido> findByUsuarioId(Long usuarioId);
	Optional<Pedido> findById(Long id);
}
