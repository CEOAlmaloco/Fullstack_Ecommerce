package com.ampuero.msvc.referidos.dtos;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {
    private String mensaje;
    private String detalles;
    private String timestamp;
    private String path;
}
