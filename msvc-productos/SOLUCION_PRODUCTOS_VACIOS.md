# Solución: Backend devuelve 0 productos

## Problema
El endpoint `/api/v1/productos` devuelve una lista vacía (0 productos) aunque el carrusel funciona correctamente.

## Diagnóstico

### 1. Verificar logs del backend en AWS

```bash
# Ver los logs del microservicio
tail -f /tmp/msvc-productos.log

# O si usas systemd
sudo journalctl -u msvc-productos -f
```

Busca estos mensajes:
- `GET /productos - Iniciando obtención de todos los productos`
- `Productos obtenidos de BD: X`
- `GET /productos - ADVERTENCIA: No se encontraron productos`

### 2. Verificar que la BD tiene productos

```bash
# Conectarse a la BD (ajustar según tu configuración)
# Si usas H2:
# Acceder a http://44.209.152.110:8003/h2-console
# JDBC URL: jdbc:h2:file:./data/msvc_productos_dev
# Usuario: sa
# Password: (vacío)

# O si usas PostgreSQL/MySQL, conectarse directamente
```

### 3. Verificar inicialización de datos

El archivo `data.sql` debería ejecutarse automáticamente al iniciar. Verificar en los logs:
- `Executing SQL script from URL [file:.../data.sql]`
- `INSERT INTO productos`

## Soluciones

### Solución 1: Reinicializar la BD

```bash
# En AWS, detener el microservicio
kill -9 PID

# Eliminar la BD (si usas H2 file-based)
rm -f ./data/msvc_productos_dev.mv.db
rm -f ./data/msvc_productos_dev.trace.db

# Reiniciar el microservicio (se recreará la BD con data.sql)
cd msvc-productos
mvn spring-boot:run
```

### Solución 2: Ejecutar data.sql manualmente

```bash
# Si la BD ya existe pero está vacía, ejecutar data.sql manualmente
# Opción A: Desde H2 Console
# 1. Acceder a http://44.209.152.110:8003/h2-console
# 2. Copiar y pegar el contenido de src/main/resources/data.sql
# 3. Ejecutar

# Opción B: Desde línea de comandos (si tienes acceso a la BD)
psql -h localhost -U usuario -d msvc_productos -f src/main/resources/data.sql
```

### Solución 3: Verificar configuración de inicialización

Verificar en `application.properties`:
```properties
spring.sql.init.mode=always
spring.sql.init.data-locations=classpath:data.sql
spring.jpa.defer-datasource-initialization=true
```

### Solución 4: Insertar productos manualmente (temporal)

Si necesitas productos rápidamente para probar:

```sql
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
VALUES 
('PlayStation 5', 'CO', 'HA', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play5white.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play5white.png"]', 549990.0, true, 4.5, 'La consola de última generación de Sony', 50, 'CO001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Teclado Redragon RGB', 'PE', 'TE', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/teclado_rd_rgb.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/teclado_rd_rgb.png"]', 145990.0, true, 4.0, 'Teclado mecánico con iluminación RGB', 40, 'PE002', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
```

## Verificación

Después de aplicar la solución:

```bash
# Probar el endpoint
curl http://localhost:8003/api/v1/productos

# Debería devolver un JSON con productos, no una lista vacía []
```

## Logs esperados

Si todo funciona correctamente, deberías ver en los logs:

```
GET /productos - Iniciando obtención de todos los productos
Iniciando carga de productos desde BD...
Productos obtenidos de BD: 19
GET /productos - Productos obtenidos del servicio: 19
GET /productos - Productos mapeados a DTO: 19
```

## Comandos útiles

```bash
# Ver cuántos productos hay en la BD (desde H2 Console)
SELECT COUNT(*) FROM productos;

# Ver todos los productos
SELECT id, titulo, disponible FROM productos;

# Verificar que data.sql se ejecutó
SELECT * FROM productos WHERE codigo_producto = 'CO001';
```

