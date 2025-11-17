# Solución: Carrusel y Productos con URLs Incorrectas

## Problema Identificado

### 1. Carrusel (NO es problema de RDS)
El carrusel **NO viene de la base de datos**, viene directamente del código Java en `ProductoController.obtenerImagenesCarrusel()`.

**Logs muestran URLs incorrectas:**
- `carrusel.png` (debería ser `carruselproductos.png`)
- `play5white.png` (debería ser `carruselnoticias.png`)
- `monitorasus.png` (debería ser `blog.png`)

**Causa:** El código en AWS no está actualizado. El código local tiene las URLs correctas, pero el servicio en AWS está ejecutando una versión antigua.

### 2. Productos (SÍ es problema de RDS)
Los productos **SÍ vienen de la base de datos RDS** y tienen rutas antiguas:
- `img/consolas/2.png` (debería ser `img/play4.png`)
- `img/perifericos/4.png` (debería ser `img/monitorasus.png`)
- `img/polerones/5.png` (debería ser `img/stumblepoleron.png`)
- etc.

**Causa:** La base de datos RDS tiene las rutas antiguas y necesita actualizarse con el script SQL.

## Solución

### Paso 1: Actualizar el código del carrusel en AWS

El código local ya está correcto. Necesitas hacer pull y reiniciar el servicio en AWS:

```bash
# En la instancia EC2
cd /ruta/del/proyecto/Backend_Java_Spring/Fullstack_Ecommerce/msvc-productos

# Hacer pull de los cambios
git pull origin Rama-principal

# Recompilar el microservicio
mvn clean package -DskipTests

# Detener el servicio actual
pkill -f "msvc-productos"

# Esperar unos segundos
sleep 5

# Reiniciar con el perfil de producción
export SPRING_PROFILES_ACTIVE=prod
nohup java -Xms256m -Xmx512m -jar target/msvc-productos-0.0.1-SNAPSHOT.jar > /tmp/msvc-productos.log 2>&1 &

# Verificar los logs
tail -f /tmp/msvc-productos.log
```

### Paso 2: Actualizar las rutas de productos en RDS

Las rutas de productos están en la base de datos RDS y necesitan actualizarse con el script SQL.

**Opción A: Usar el script `verificar_y_actualizar_rutas.sql`**

```bash
# Conectarse a RDS
psql -h levelup-db.cvcqjuf3hjcs.us-east-1.rds.amazonaws.com \
     -p 5432 \
     -U levelup_admin \
     -d levelup_productos

# Ejecutar el script (copiar y pegar el contenido del archivo)
\i /ruta/al/verificar_y_actualizar_rutas.sql
```

**Opción B: Ejecutar UPDATEs directos**

```sql
-- Conectarse a la base de datos
\c levelup_productos

-- Actualizar PlayStation 5
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play5white.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play5white.png", "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play5.webp"]'
WHERE codigo_producto = 'CO001' 
   OR imagen LIKE '%consolas/4.png%'
   OR imagen LIKE '%img/consolas/4.png%';

-- Actualizar PlayStation 4 Slim
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play4.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play4.png"]'
WHERE codigo_producto = 'CO002' 
   OR imagen LIKE '%consolas/2.png%'
   OR imagen LIKE '%img/consolas/2.png%';

-- Actualizar DualShock 4
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mandoplay.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mandoplay.png"]'
WHERE codigo_producto = 'CO003' 
   OR imagen LIKE '%consolas/3.png%'
   OR imagen LIKE '%img/consolas/3.png%';

-- Actualizar Monitor ASUS
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/monitorasus.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/monitorasus.png"]'
WHERE codigo_producto = 'PE004' 
   OR imagen LIKE '%perifericos/4.png%'
   OR imagen LIKE '%img/perifericos/4.png%';

-- Actualizar Polerón Stumble Guys
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/stumblepoleron.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/stumblepoleron.png"]'
WHERE codigo_producto = 'RO005' 
   OR imagen LIKE '%polerones/5.png%'
   OR imagen LIKE '%img/polerones/5.png%';

-- Y así para todos los productos...
```

### Paso 3: Verificar que los cambios funcionan

1. **Verificar el carrusel:**
   ```bash
   curl http://ec2-44-209-152-110.compute-1.amazonaws.com:8094/productos/carrusel
   ```
   
   Debería devolver:
   ```json
   [
     {
       "id": "1",
       "url": "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/carruselproductos.png",
       ...
     },
     {
       "id": "2",
       "url": "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/carruselnoticias.png",
       ...
     },
     {
       "id": "3",
       "url": "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/blog.png",
       ...
     }
   ]
   ```

2. **Verificar productos:**
   ```bash
   curl http://ec2-44-209-152-110.compute-1.amazonaws.com:8094/productos | jq '.[0].imagenUrl'
   ```
   
   Debería devolver una URL con el formato correcto (ej: `img/play5white.png` o la URL completa de S3).

## Resumen

- **Carrusel:** NO es problema de RDS. Es código Java que necesita actualizarse en AWS.
- **Productos:** SÍ es problema de RDS. Las rutas en la BD necesitan actualizarse con SQL.

**NO necesitas borrar las tablas.** Solo necesitas:
1. Actualizar el código en AWS (pull + recompilar + reiniciar)
2. Actualizar las rutas en RDS con el script SQL

