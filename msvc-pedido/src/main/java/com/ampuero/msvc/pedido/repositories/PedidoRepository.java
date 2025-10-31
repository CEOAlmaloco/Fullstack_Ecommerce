package com.ampuero.msvc.pedido.repositories;

import com.ampuero.msvc.pedido.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Optional<Pedido> findByCodigo(String codigo);
    List<Pedido> findByIdUsuario(Long idUsuario);
    List<Pedido> findByEstado(Pedido.EstadoPedido estado);
    List<Pedido> findByIdUsuarioAndEstado(Long idUsuario, Pedido.EstadoPedido estado);
}

