package com.ampuero.msvc.referidos.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReferidoResponseDTO {
    private Long idReferido;
    private String nombreReferido;
    private String apellidosReferido;
    private String emailReferido;
    private String runReferido;
    private String codigoReferido;
    private Integer puntosLevelup;
    private String nivelUsuario;
    private LocalDateTime fechaRegistro;
    private Long idReferidor;
    private Boolean activo;
}
