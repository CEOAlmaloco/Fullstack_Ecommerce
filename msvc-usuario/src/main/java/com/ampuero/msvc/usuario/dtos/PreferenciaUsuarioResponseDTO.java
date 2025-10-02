package com.ampuero.msvc.usuario.dtos;

import com.ampuero.msvc.usuario.entities.PreferenciaUsuario;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para respuesta de preferencias de usuario
 * Contiene los datos de una preferencia para mostrar en respuestas de API
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreferenciaUsuarioResponseDTO {

    private Long idPreferencia;
    private Long idUsuario;
    private String clave;
    private String valor;
    private String descripcion;
    private PreferenciaUsuario.TipoPreferencia tipoPreferencia;
    private String categoria;
    private Boolean activa;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    
    /**
     * Constructor que mapea desde la entidad
     */
    public PreferenciaUsuarioResponseDTO(PreferenciaUsuario preferencia) {
        this.idPreferencia = preferencia.getIdPreferencia();
        this.idUsuario = preferencia.getUsuario().getIdUsuario();
        this.clave = preferencia.getClave();
        this.valor = preferencia.getValor();
        this.descripcion = preferencia.getDescripcion();
        this.tipoPreferencia = preferencia.getTipoPreferencia();
        this.categoria = preferencia.getCategoria();
        this.activa = preferencia.getActiva();
        this.fechaCreacion = preferencia.getFechaCreacion();
        this.fechaModificacion = preferencia.getFechaModificacion();
    }
}
