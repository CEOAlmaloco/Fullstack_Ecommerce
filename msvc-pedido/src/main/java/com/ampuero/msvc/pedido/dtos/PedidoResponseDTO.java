package com.ampuero.msvc.pedido.dtos;

import com.ampuero.msvc.pedido.models.Pedido;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PedidoResponseDTO {
    private Long id;
    private String codigo;
    private Long idUsuario;
    private String nombreEnvio;
    private String apellidoEnvio;
    private String emailEnvio;
    private String telefonoEnvio;
    private String direccionEnvio;
    private String departamentoEnvio;
    private String regionEnvio;
    private String comunaEnvio;
    private String indicadoresEntrega;
    private Double subtotal;
    private Double descuento;
    private Double iva;
    private Double total;
    private String estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private Long idCarrito;
    private Long idPago;
    private List<PedidoItemResponseDTO> items;
}

