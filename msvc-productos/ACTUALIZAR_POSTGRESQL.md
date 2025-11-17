# Actualizar Rutas de Imágenes en PostgreSQL

El backend está usando **PostgreSQL**. Necesitas ejecutar el script SQL en la base de datos correcta.

## Paso 1: Verificar qué base de datos está usando

El backend probablemente está usando una base de datos específica, no `postgres`. Verifica en los logs o variables de entorno:

```bash
# Ver las variables de entorno del proceso
ps e -p 1983 | grep -i "DB_URL\|DB_NAME"

# O buscar en los logs la URL de conexión
grep -i "jdbc:postgresql" msvc-productos/productos.log | head -5
```

## Paso 2: Conectarte a la base de datos correcta

Probablemente sea `levelup_productos` o similar. Conéctate así:

```bash
psql -h levelup-db.cvcqjuf3hjcs.us-east-1.rds.amazonaws.com \
     -p 5432 \
     -U levelup_admin \
     -d levelup_productos  # <-- Cambia esto por el nombre correcto
```

Si no sabes el nombre, lista las bases de datos:

```bash
psql -h levelup-db.cvcqjuf3hjcs.us-east-1.rds.amazonaws.com \
     -p 5432 \
     -U levelup_admin \
     -d postgres \
     -c "\l" | grep levelup
```

## Paso 3: Ejecutar el script SQL

Una vez conectado a la base de datos correcta:

```sql
-- Verificar rutas actuales
SELECT codigo_producto, titulo, imagen 
FROM productos 
ORDER BY codigo_producto;

-- Ejecutar el script verificar_y_actualizar_rutas.sql
-- (copia y pega todo el contenido del archivo)
```

## Paso 4: Verificar que se actualizaron

```sql
-- Debe devolver 0 filas si todo está actualizado
SELECT codigo_producto, titulo, imagen 
FROM productos 
WHERE imagen LIKE '%consolas/%' OR imagen LIKE '%perifericos/%';
```

## Paso 5: Probar la app

Los cambios se reflejan inmediatamente. Prueba la app Android de nuevo.

