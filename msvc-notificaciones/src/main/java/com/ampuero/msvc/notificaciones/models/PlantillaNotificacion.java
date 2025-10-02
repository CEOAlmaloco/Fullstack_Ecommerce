package com.ampuero.msvc.notificaciones.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "plantillas_notificacion")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlantillaNotificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plantilla")
    private Long idPlantilla;

    @Column(nullable = false, name = "codigo_plantilla")
    private String codigoPlantilla;

    @Column(nullable = false, name = "nombre_plantilla")
    private String nombrePlantilla;

    @Column(name = "descripcion_plantilla")
    private String descripcionPlantilla;

    @Column(nullable = false, name = "tipo_plantilla")
    private String tipoPlantilla; // EMAIL, WHATSAPP, SMS, PUSH

    @Column(name = "asunto_plantilla")
    private String asuntoPlantilla;

    @Column(name = "contenido_plantilla", columnDefinition = "TEXT")
    private String contenidoPlantilla;

    @Column(name = "variables_plantilla")
    private String variablesPlantilla; // JSON con variables disponibles

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "version_plantilla")
    private String versionPlantilla;

    @Column(nullable = false)
    private Boolean activo = true;
}
