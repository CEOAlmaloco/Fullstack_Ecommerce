package com.ampuero.msvc.contenido.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comentarios")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comentario")
    private Long idComentario;

    @Column(name = "id_articulo")
    private Long idArticulo;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    @Column(name = "contenido_comentario", columnDefinition = "TEXT")
    private String contenidoComentario;

    @Column(name = "fecha_comentario")
    private LocalDateTime fechaComentario;

    @Column(name = "estado_comentario")
    private String estadoComentario; // PENDIENTE, APROBADO, RECHAZADO

    @Column(name = "likes_comentario")
    private Integer likesComentario = 0;

    @Column(name = "id_comentario_padre")
    private Long idComentarioPadre; // Para respuestas anidadas

    @Column(nullable = false)
    private Boolean activo = true;
}
