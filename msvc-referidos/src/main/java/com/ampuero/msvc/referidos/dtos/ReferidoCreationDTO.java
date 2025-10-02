package com.ampuero.msvc.referidos.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReferidoCreationDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    private String nombreReferido;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 100, message = "Los apellidos no pueden exceder 100 caracteres")
    private String apellidosReferido;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    private String emailReferido;

    @NotBlank(message = "El RUN es obligatorio")
    @Size(min = 7, max = 9, message = "El RUN debe tener entre 7 y 9 caracteres")
    private String runReferido;

    private String codigoReferido; // Código del usuario que refiere (opcional)
}
