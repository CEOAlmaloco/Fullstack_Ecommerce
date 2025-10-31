package com.ampuero.msvc.pedido.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class PedidoCreationDTO {
    @NotNull(message = "El ID de usuario es requerido")
    private Long idUsuario;

    @NotBlank(message = "El nombre es requerido")
    private String nombreEnvio;

    @NotBlank(message = "El apellido es requerido")
    private String apellidoEnvio;

    @NotBlank(message = "El email es requerido")
    private String emailEnvio;

    @NotBlank(message = "El teléfono es requerido")
    private String telefonoEnvio;

    @NotBlank(message = "La dirección es requerida")
    private String direccionEnvio;

    private String departamentoEnvio;

    @NotBlank(message = "La región es requerida")
    private String regionEnvio;

    @NotBlank(message = "La comuna es requerida")
    private String comunaEnvio;

    private String indicadoresEntrega;

    @NotNull(message = "El subtotal es requerido")
    private Double subtotal;

    @NotNull(message = "El descuento es requerido")
    private Double descuento;

    @NotNull(message = "El IVA es requerido")
    private Double iva;

    @NotNull(message = "El total es requerido")
    private Double total;

    private Long idCarrito;

    private List<PedidoItemCreationDTO> items;
}

