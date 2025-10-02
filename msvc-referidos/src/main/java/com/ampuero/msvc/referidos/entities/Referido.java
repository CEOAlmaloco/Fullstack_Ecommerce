package com.ampuero.msvc.referidos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "referidos")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Referido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_referido")
    private Long idReferido;

    @Column(nullable = false, name = "nombre_referido")
    private String nombreReferido;

    @Column(nullable = false, name = "apellidos_referido")
    private String apellidosReferido;

    @Column(nullable = false, unique = true, name = "email_referido")
    private String emailReferido;

    @Column(nullable = false, unique = true, name = "run_referido")
    private String runReferido;

    @Column(nullable = false, unique = true, name = "codigo_referido")
    private String codigoReferido;

    @Column(nullable = false, name = "puntos_levelup")
    private Integer puntosLevelup = 0;

    @Column(nullable = false, name = "nivel_usuario")
    private String nivelUsuario = "BRONZE";

    @Column(nullable = false, name = "fecha_registro")
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @Column(name = "id_referidor")
    private Long idReferidor;

    @Column(nullable = false)
    private Boolean activo = true;

    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
        if (puntosLevelup == null) {
            puntosLevelup = 0;
        }
        if (nivelUsuario == null) {
            nivelUsuario = "BRONZE";
        }
    }
}
