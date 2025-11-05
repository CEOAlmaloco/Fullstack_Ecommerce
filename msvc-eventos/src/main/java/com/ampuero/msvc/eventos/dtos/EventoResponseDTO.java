package com.ampuero.msvc.eventos.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventoResponseDTO {
    // Campos originales (compatibilidad con TypeScript)
    private Long idEvento;
    private String nombreEvento;
    private String descripcionEvento;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String ubicacionEvento;
    private Double coordenadasLatitud;
    private Double coordenadasLongitud;
    private String tipoEvento;
    private Integer cuposMaximos;
    private Integer cuposDisponibles;
    private Double costoEntrada;
    private Integer puntosLevelUp;
    private Boolean activo;
    private Integer requisitosEdad;
    private String equiposRequeridos;
    private String ciudad;
    private String imagen;
    private String imagenes;
    
    // Campos adicionales para compatibilidad con Kotlin
    // id: String (alias de idEvento)
    public String getId() {
        return idEvento != null ? idEvento.toString() : null;
    }
    
    // titulo: String (alias de nombreEvento)
    public String getTitulo() {
        return nombreEvento;
    }
    
    // descripcion: String (alias de descripcionEvento)
    public String getDescripcion() {
        return descripcionEvento;
    }
    
    // ubicacion: String (alias de ubicacionEvento)
    public String getUbicacion() {
        return ubicacionEvento;
    }
    
    // latitud: Double (alias de coordenadasLatitud)
    public Double getLatitud() {
        return coordenadasLatitud;
    }
    
    // longitud: Double (alias de coordenadasLongitud)
    public Double getLongitud() {
        return coordenadasLongitud;
    }
    
    // categoria: String (alias de tipoEvento)
    public String getCategoria() {
        return tipoEvento;
    }
    
    // capacidadMaxima: Int (alias de cuposMaximos)
    public Integer getCapacidadMaxima() {
        return cuposMaximos;
    }
    
    // participantesActuales: Int (calculado)
    public Integer getParticipantesActuales() {
        return cuposMaximos != null && cuposDisponibles != null ? 
            cuposMaximos - cuposDisponibles : 0;
    }
    
    // puntosRecompensa: Int (alias de puntosLevelUp)
    public Integer getPuntosRecompensa() {
        return puntosLevelUp;
    }
    
    // edadMinima: Int (alias de requisitosEdad)
    public Integer getEdadMinima() {
        return requisitosEdad;
    }
    
    // precio: Double (alias de costoEntrada)
    public Double getPrecio() {
        return costoEntrada;
    }
    
    // estado: String (calculado de activo)
    public String getEstado() {
        return activo != null && activo ? "ACTIVO" : "INACTIVO";
    }
    
    // fechaInicio: String (formato ISO)
    public String getFechaInicioString() {
        return fechaInicio != null ? fechaInicio.toString() : null;
    }
    
    // fechaFin: String (formato ISO)
    public String getFechaFinString() {
        return fechaFin != null ? fechaFin.toString() : null;
    }
    
    // createdAt: String (formato ISO)
    public String getCreatedAt() {
        return fechaInicio != null ? fechaInicio.toString() : null;
    }
}
