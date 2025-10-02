package com.ampuero.msvc.pedido.dtos;

import lombok.Data;

@Data
public class ClienteResponseDTO {
    private Long idUsuario;
    private String nombreCliente;
    private String correoCliente;
    // ... otros campos ...
}