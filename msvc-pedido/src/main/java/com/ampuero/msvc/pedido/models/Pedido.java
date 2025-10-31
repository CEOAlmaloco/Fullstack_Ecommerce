package com.ampuero.msvc.pedido.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long id;

    @Column(nullable = false, unique = true, name = "codigo_pedido")
    private String codigo;

    @Column(nullable = false, name = "id_usuario")
    private Long idUsuario;

    @Column(nullable = false, name = "nombre_envio")
    private String nombreEnvio;

    @Column(nullable = false, name = "apellido_envio")
    private String apellidoEnvio;

    @Column(nullable = false, name = "email_envio")
    private String emailEnvio;

    @Column(nullable = false, name = "telefono_envio")
    private String telefonoEnvio;

    @Column(nullable = false, name = "direccion_envio", columnDefinition = "TEXT")
    private String direccionEnvio;

    @Column(name = "departamento_envio")
    private String departamentoEnvio;

    @Column(nullable = false, name = "region_envio")
    private String regionEnvio;

    @Column(nullable = false, name = "comuna_envio")
    private String comunaEnvio;

    @Column(name = "indicadores_entrega", columnDefinition = "TEXT")
    private String indicadoresEntrega;

    @Column(nullable = false)
    private Double subtotal;

    @Column(nullable = false)
    private Double descuento;

    @Column(nullable = false)
    private Double iva;

    @Column(nullable = false)
    private Double total;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "id_carrito")
    private Long idCarrito;

    @Column(name = "id_pago")
    private Long idPago;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoPedido.PENDIENTE;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }

    public enum EstadoPedido {
        PENDIENTE,
        PROCESANDO,
        COMPLETADA,
        FALLIDA,
        CANCELADA
    }
}

