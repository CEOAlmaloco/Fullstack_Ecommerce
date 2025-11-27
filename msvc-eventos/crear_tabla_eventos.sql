-- Script para crear la tabla eventos manualmente
-- Ejecutar en la base de datos: levelup_web_eventos
-- Solo usar si JPA/Hibernate no crea la tabla automáticamente

-- Eliminar tabla si existe (cuidado en producción)
DROP TABLE IF EXISTS eventos CASCADE;

CREATE TABLE eventos (
    id_evento BIGSERIAL PRIMARY KEY,
    nombre_evento VARCHAR(255) NOT NULL,
    descripcion_evento TEXT,
    fecha_inicio TIMESTAMP NOT NULL,
    fecha_fin TIMESTAMP NOT NULL,
    ubicacion_evento VARCHAR(255),
    ciudad VARCHAR(100),
    coordenadas_latitud DOUBLE PRECISION,
    coordenadas_longitud DOUBLE PRECISION,
    imagen TEXT,
    imagenes TEXT,
    tipo_evento VARCHAR(50),
    cupos_maximos INTEGER,
    cupos_disponibles INTEGER,
    costo_entrada DOUBLE PRECISION,
    puntos_levelup INTEGER,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    requisitos_edad INTEGER,
    equipos_requeridos VARCHAR(255)
);

-- Crear índices para mejorar el rendimiento
CREATE INDEX idx_eventos_tipo ON eventos(tipo_evento);
CREATE INDEX idx_eventos_activo ON eventos(activo);
CREATE INDEX idx_eventos_fecha_inicio ON eventos(fecha_inicio);
CREATE INDEX idx_eventos_fecha_fin ON eventos(fecha_fin);

-- Verificar que la tabla se creó
SELECT * FROM eventos LIMIT 0;

