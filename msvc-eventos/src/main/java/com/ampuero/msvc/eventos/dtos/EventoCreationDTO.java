package com.ampuero.msvc.eventos.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EventoCreationDTO {
    @NotBlank(message = "El nombre del evento no puede estar vacío")
    private String nombreEvento;
    
    private String descripcionEvento;
    
    @NotNull(message = "La fecha de inicio no puede estar vacía")
    private LocalDateTime fechaInicio;
    
    @NotNull(message = "La fecha de fin no puede estar vacía")
    private LocalDateTime fechaFin;
    
    private String ubicacionEvento;
    private String ciudad;
    private Double coordenadasLatitud;
    private Double coordenadasLongitud;
    
    @NotBlank(message = "El tipo de evento no puede estar vacío")
    private String tipoEvento;
    
    private Integer cuposMaximos;
    private Double costoEntrada;
    private Integer puntosLevelUp;
    private Integer requisitosEdad;
    private String equiposRequeridos;
}
