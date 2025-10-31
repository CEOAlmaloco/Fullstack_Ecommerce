package com.ampuero.msvc.pedido.repositories;

import com.ampuero.msvc.pedido.models.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {
    List<PedidoItem> findByIdPedido(Long idPedido);
}

