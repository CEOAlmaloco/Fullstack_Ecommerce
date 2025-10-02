package com.ampuero.msvc.notificaciones.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlantillaCreationDTO {
    @NotBlank(message = "El código de plantilla no puede estar vacío")
    private String codigoPlantilla;
    
    @NotBlank(message = "El nombre de plantilla no puede estar vacío")
    private String nombrePlantilla;
    
    private String descripcionPlantilla;
    
    @NotBlank(message = "El tipo de plantilla no puede estar vacío")
    private String tipoPlantilla;
    
    private String asuntoPlantilla;
    private String contenidoPlantilla;
    private String variablesPlantilla;
    private String versionPlantilla;
}
