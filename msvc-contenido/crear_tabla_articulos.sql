-- Script para crear la tabla articulos manualmente
-- Ejecutar en la base de datos: levelup_contenido
-- Solo usar si JPA/Hibernate no crea la tabla automáticamente

CREATE TABLE IF NOT EXISTS articulos (
    id_articulo BIGSERIAL PRIMARY KEY,
    titulo_articulo VARCHAR(255) NOT NULL,
    contenido_articulo TEXT,
    resumen_articulo VARCHAR(500),
    imagen_articulo TEXT,
    categoria_articulo VARCHAR(50),
    etiquetas_articulo VARCHAR(255),
    autor_articulo VARCHAR(100),
    fecha_publicacion TIMESTAMP,
    fecha_actualizacion TIMESTAMP,
    estado_articulo VARCHAR(50),
    vistas_articulo INTEGER DEFAULT 0,
    likes_articulo INTEGER DEFAULT 0,
    compartidos_articulo INTEGER DEFAULT 0,
    tiempo_lectura INTEGER,
    es_destacado BOOLEAN DEFAULT FALSE,
    es_premium BOOLEAN DEFAULT FALSE,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

-- Crear índices para mejorar el rendimiento
CREATE INDEX IF NOT EXISTS idx_articulos_categoria ON articulos(categoria_articulo);
CREATE INDEX IF NOT EXISTS idx_articulos_estado ON articulos(estado_articulo);
CREATE INDEX IF NOT EXISTS idx_articulos_activo ON articulos(activo);
CREATE INDEX IF NOT EXISTS idx_articulos_fecha_publicacion ON articulos(fecha_publicacion);

-- Verificar que la tabla se creó
SELECT * FROM articulos LIMIT 0;

