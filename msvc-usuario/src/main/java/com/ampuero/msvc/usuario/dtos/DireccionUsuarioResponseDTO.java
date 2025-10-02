package com.ampuero.msvc.usuario.dtos;

import com.ampuero.msvc.usuario.entities.DireccionUsuario;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para respuesta de direcciones de usuario
 * Contiene los datos de una dirección para mostrar en respuestas de API
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DireccionUsuarioResponseDTO {

    private Long idDireccion;
    private Long idUsuario;
    private String direccion;
    private String direccion2;
    private String ciudad;
    private String estadoProvincia;
    private String codigoPostal;
    private String pais;
    private DireccionUsuario.TipoDireccion tipoDireccion;
    private Boolean esPrincipal;
    private Boolean activa;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    
    /**
     * Constructor que mapea desde la entidad
     */
    public DireccionUsuarioResponseDTO(DireccionUsuario direccion) {
        this.idDireccion = direccion.getIdDireccion();
        this.idUsuario = direccion.getUsuario().getIdUsuario();
        this.direccion = direccion.getDireccion();
        this.direccion2 = direccion.getDireccion2();
        this.ciudad = direccion.getCiudad();
        this.estadoProvincia = direccion.getEstadoProvincia();
        this.codigoPostal = direccion.getCodigoPostal();
        this.pais = direccion.getPais();
        this.tipoDireccion = direccion.getTipoDireccion();
        this.esPrincipal = direccion.getEsPrincipal();
        this.activa = direccion.getActiva();
        this.fechaCreacion = direccion.getFechaCreacion();
        this.fechaModificacion = direccion.getFechaModificacion();
    }
}
