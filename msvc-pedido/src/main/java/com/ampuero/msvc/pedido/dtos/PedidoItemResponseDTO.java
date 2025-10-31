package com.ampuero.msvc.pedido.dtos;

import lombok.Data;

@Data
public class PedidoItemResponseDTO {
    private Long id;
    private Long idPedido;
    private Long idProducto;
    private String nombreProducto;
    private Double precio;
    private Integer cantidad;
    private Double subtotal;
    private String imagenUrl;
}

