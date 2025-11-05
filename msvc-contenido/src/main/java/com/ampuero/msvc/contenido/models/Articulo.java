package com.ampuero.msvc.contenido.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "articulos")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Articulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_articulo")
    private Long idArticulo;

    @Column(nullable = false, name = "titulo_articulo")
    private String tituloArticulo;

    @Column(name = "contenido_articulo", columnDefinition = "TEXT")
    private String contenidoArticulo;

    @Column(name = "resumen_articulo")
    private String resumenArticulo;

    @Column(name = "imagen_articulo", columnDefinition = "TEXT")
    private String imagenArticulo;

    @Column(name = "categoria_articulo")
    private String categoriaArticulo; // NOTICIAS, REVIEWS, GUIAS, HARDWARE, SOFTWARE, ESPORTS, STREAMING, RETRO

    @Column(name = "etiquetas_articulo")
    private String etiquetasArticulo;

    @Column(name = "autor_articulo")
    private String autorArticulo;

    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "estado_articulo")
    private String estadoArticulo; // BORRADOR, REVISION, PROGRAMADO, PUBLICADO, ARCHIVADO

    @Column(name = "vistas_articulo")
    private Integer vistasArticulo = 0;

    @Column(name = "likes_articulo")
    private Integer likesArticulo = 0;

    @Column(name = "compartidos_articulo")
    private Integer compartidosArticulo = 0;

    @Column(name = "tiempo_lectura")
    private Integer tiempoLectura; // en minutos

    @Column(name = "es_destacado")
    private Boolean esDestacado = false;

    @Column(name = "es_premium")
    private Boolean esPremium = false;

    @Column(nullable = false)
    private Boolean activo = true;
}
