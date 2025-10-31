package com.ampuero.msvc.referidos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "eventos_puntos")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EventoPuntos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Long id;

    @Column(nullable = false, name = "tipo_evento")
    @Enumerated(EnumType.STRING)
    private TipoEvento tipoEvento;

    @Column(nullable = false, name = "nombre_evento")
    private String nombreEvento;

    @Column(name = "descripcion_evento", columnDefinition = "TEXT")
    private String descripcionEvento;

    @Column(nullable = false, name = "puntos_otorgados")
    private Integer puntosOtorgados;

    @Column(name = "codigo_evento")
    private String codigoEvento;

    @Column(name = "fecha_evento")
    private LocalDateTime fechaEvento;

    @Column(name = "lugar_evento")
    private String lugarEvento;

    @Column(name = "direccion_evento")
    private String direccionEvento;

    @Column(name = "id_usuario")
    private Long idUsuario; // Para eventos específicos de usuario

    @Column(name = "id_producto")
    private Long idProducto; // Para eventos relacionados con productos

    @Column(name = "id_pedido")
    private Long idPedido; // Para eventos relacionados con pedidos

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private Boolean activo = true;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (activo == null) activo = true;
    }

    public enum TipoEvento {
        INICIO_SESION,
        REGISTRO,
        REFERIDO,
        COMPRA,
        ASISTENCIA_EVENTO,
        CANJE_PUNTOS,
        REVIEW_PRODUCTO,
        LOGRO_DESBLOQUEADO
    }
}

