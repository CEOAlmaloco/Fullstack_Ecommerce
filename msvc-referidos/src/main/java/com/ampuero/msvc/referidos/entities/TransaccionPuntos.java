package com.ampuero.msvc.referidos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transacciones_puntos")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TransaccionPuntos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaccion")
    private Long id;

    @Column(nullable = false, name = "id_usuario")
    private Long idUsuario;

    @Column(nullable = false, name = "id_evento")
    private Long idEvento;

    @Column(nullable = false, name = "tipo_transaccion")
    @Enumerated(EnumType.STRING)
    private TipoTransaccion tipoTransaccion;

    @Column(nullable = false, name = "puntos")
    private Integer puntos;

    @Column(nullable = false, name = "puntos_anteriores")
    private Integer puntosAnteriores;

    @Column(nullable = false, name = "puntos_nuevos")
    private Integer puntosNuevos;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "codigo_referencia")
    private String codigoReferencia; // Para referencias a pedidos, cupones, etc.

    @Column(name = "fecha_transaccion")
    private LocalDateTime fechaTransaccion;

    @Column(nullable = false)
    private Boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evento", insertable = false, updatable = false)
    private EventoPuntos evento;

    @PrePersist
    protected void onCreate() {
        fechaTransaccion = LocalDateTime.now();
        if (activo == null) activo = true;
    }

    public enum TipoTransaccion {
        CREDITO, // Puntos ganados
        DEBITO   // Puntos usados
    }
}

