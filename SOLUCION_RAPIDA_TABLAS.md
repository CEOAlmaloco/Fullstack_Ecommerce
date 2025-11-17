# Solución Rápida: Crear Tablas de Blogs y Eventos

## Problema
Las tablas `articulos` y `eventos` no se están creando automáticamente con JPA/Hibernate.

## Solución Rápida: Crear Tablas Manualmente

### Paso 1: Crear tabla de artículos (blogs)

```bash
psql -h levelup-db.cvcqjuf3hjcs.us-east-1.rds.amazonaws.com \
     -p 5432 \
     -U levelup_admin \
     -d levelup_contenido
```

Luego copiar y pegar el contenido de `msvc-contenido/crear_tabla_articulos.sql`:

```sql
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

CREATE INDEX IF NOT EXISTS idx_articulos_categoria ON articulos(categoria_articulo);
CREATE INDEX IF NOT EXISTS idx_articulos_estado ON articulos(estado_articulo);
CREATE INDEX IF NOT EXISTS idx_articulos_activo ON articulos(activo);
CREATE INDEX IF NOT EXISTS idx_articulos_fecha_publicacion ON articulos(fecha_publicacion);
```

### Paso 2: Crear tabla de eventos

```bash
psql -h levelup-db.cvcqjuf3hjcs.us-east-1.rds.amazonaws.com \
     -p 5432 \
     -U levelup_admin \
     -d levelup_eventos
```

Luego copiar y pegar el contenido de `msvc-eventos/crear_tabla_eventos.sql`:

```sql
CREATE TABLE IF NOT EXISTS eventos (
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

CREATE INDEX IF NOT EXISTS idx_eventos_tipo ON eventos(tipo_evento);
CREATE INDEX IF NOT EXISTS idx_eventos_activo ON eventos(activo);
CREATE INDEX IF NOT EXISTS idx_eventos_fecha_inicio ON eventos(fecha_inicio);
CREATE INDEX IF NOT EXISTS idx_eventos_fecha_fin ON eventos(fecha_fin);
```

### Paso 3: Verificar que las tablas se crearon

```bash
# Verificar tabla articulos
psql -h levelup-db.cvcqjuf3hjcs.us-east-1.rds.amazonaws.com \
     -p 5432 \
     -U levelup_admin \
     -d levelup_contenido \
     -c "\dt articulos"

# Verificar tabla eventos
psql -h levelup-db.cvcqjuf3hjcs.us-east-1.rds.amazonaws.com \
     -p 5432 \
     -U levelup_admin \
     -d levelup_eventos \
     -c "\dt eventos"
```

### Paso 4: Actualizar rutas de imágenes (si hay datos)

Una vez que las tablas existan, ejecutar los scripts de actualización:

```bash
# Para blogs
psql -h levelup-db.cvcqjuf3hjcs.us-east-1.rds.amazonaws.com \
     -p 5432 \
     -U levelup_admin \
     -d levelup_contenido \
     -f msvc-contenido/actualizar_rutas_blogs.sql

# Para eventos
psql -h levelup-db.cvcqjuf3hjcs.us-east-1.rds.amazonaws.com \
     -p 5432 \
     -U levelup_admin \
     -d levelup_eventos \
     -f msvc-eventos/actualizar_rutas_eventos.sql
```

## Nota Importante

Después de crear las tablas manualmente, los microservicios deberían poder usarlas normalmente. Si hay datos existentes en Base64, los scripts de actualización los convertirán a URLs de S3.

