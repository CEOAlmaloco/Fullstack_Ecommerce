# Verificar qué Base de Datos está usando el Backend

El backend puede estar usando **H2 local** en lugar de **PostgreSQL**. Esto explica por qué las imágenes siguen siendo las rutas antiguas después de actualizar PostgreSQL.

## Problema

Los logs de Kotlin muestran que el backend devuelve:
- `img/consolas/2.png` (ruta antigua)
- `img/perifericos/1.png` (ruta antigua)

Pero actualizamos PostgreSQL con:
- `img/play4.png` (ruta nueva)
- `img/audilogitech.png` (ruta nueva)

## Solución: Verificar y Actualizar

### Paso 1: Verificar qué BD está usando el backend

En AWS, verifica las variables de entorno del microservicio:

```bash
# Ver las variables de entorno del proceso Java
ps aux | grep java | grep msvc-productos

# O verificar los logs del microservicio
tail -f msvc-productos/productos.log | grep -i "datasource\|database\|h2\|postgres"
```

### Paso 2: Si está usando H2 local

Si el backend está usando H2 (archivo local), necesitas actualizar esa base de datos también:

#### Opción A: Acceder a H2 Console

1. Abre en el navegador: `http://44.209.152.110:8003/h2-console`
2. Credenciales:
   - **JDBC URL**: `jdbc:h2:file:./data/msvc_productos_dev`
   - **Usuario**: `sa`
   - **Password**: (vacío)
3. Ejecuta el script `actualizar_rutas_imagenes.sql`

#### Opción B: Si está usando PostgreSQL (con variables de entorno)

Si el backend está configurado con variables de entorno para PostgreSQL, verifica que esté conectado a la base de datos correcta:

```bash
# Verificar variables de entorno
env | grep -i "DB_URL\|DB_DRIVER\|DB_USERNAME"

# Si usa PostgreSQL, debería mostrar algo como:
# DB_URL=jdbc:postgresql://levelup-db.cvcqjuf3hjcs.us-east-1.rds.amazonaws.com:5432/levelup_productos
# DB_DRIVER=org.postgresql.Driver
```

### Paso 3: Reiniciar el microservicio

Después de actualizar la base de datos (H2 o PostgreSQL), reinicia el microservicio:

```bash
# Encontrar el PID del microservicio
ps aux | grep "msvc-productos" | grep -v grep

# Matar el proceso (reemplaza PID con el número real)
kill -9 PID

# Reiniciar el microservicio
cd msvc-productos
nohup java -Xms256m -Xmx512m -jar target/msvc-productos-0.0.1-SNAPSHOT.jar > productos.log 2>&1 &
```

### Paso 4: Verificar que los cambios se aplicaron

1. Espera unos segundos a que el microservicio inicie
2. Prueba la app Android de nuevo
3. Verifica los logs de Kotlin - deberían mostrar las rutas nuevas:
   - `img/play4.png` ✅
   - `img/audilogitech.png` ✅
   - NO `img/consolas/2.png` ❌

## Nota sobre Descuentos y Ratings

- **Descuentos**: Se muestran correctamente si `producto.descuento > 0`
- **Ratings**: Ahora usa `rating` si `ratingPromedio` es 0 (corregido en Kotlin)

## Si el problema persiste

1. Verifica que el backend esté usando la base de datos correcta
2. Verifica que el script SQL se ejecutó correctamente
3. Verifica que las imágenes existan en S3 con los nombres correctos
4. Revisa los logs del backend para ver errores de conexión a la BD

