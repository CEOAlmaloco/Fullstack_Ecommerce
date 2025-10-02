package com.ampuero.msvc.notificaciones.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlantillaResponseDTO {
    private Long idPlantilla;
    private String codigoPlantilla;
    private String nombrePlantilla;
    private String descripcionPlantilla;
    private String tipoPlantilla;
    private String asuntoPlantilla;
    private String contenidoPlantilla;
    private String variablesPlantilla;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private String versionPlantilla;
    private Boolean activo;
}
