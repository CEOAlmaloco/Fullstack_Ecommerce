# Comandos Específicos para Actualizar Carrusel y Productos en AWS

## Problema
- **Carrusel:** Devuelve URLs incorrectas (`carrusel.png`, `play5white.png`, `monitorasus.png`)
- **Productos:** Tienen rutas antiguas en RDS (`img/consolas/2.png`, `img/perifericos/4.png`, etc.)

## Solución Paso a Paso

### PASO 1: Actualizar Código del Carrusel en AWS

**Conectarse a la instancia EC2:**
```bash
ssh -i tu-clave.pem ec2-user@ec2-44-209-152-110.compute-1.amazonaws.com
```

**Navegar al directorio del proyecto:**
```bash
cd /ruta/completa/a/Backend_Java_Spring/Fullstack_Ecommerce/msvc-productos
```

**Hacer pull de los cambios:**
```bash
git pull origin Rama-principal
```

**Recompilar el microservicio:**
```bash
mvn clean package -DskipTests
```

**Detener el servicio actual:**
```bash
# Buscar el proceso
ps aux | grep msvc-productos

# Detener el proceso (reemplaza PID con el número que encuentres)
kill -9 PID

# O detener todos los procesos Java de productos
pkill -f "msvc-productos"
```

**Esperar unos segundos:**
```bash
sleep 5
```

**Reiniciar el servicio:**
```bash
export SPRING_PROFILES_ACTIVE=prod
nohup java -Xms256m -Xmx512m -jar target/msvc-productos-0.0.1-SNAPSHOT.jar > /tmp/msvc-productos.log 2>&1 &
```

**Verificar que está corriendo:**
```bash
ps aux | grep msvc-productos
tail -f /tmp/msvc-productos.log
```

**Probar el endpoint del carrusel:**
```bash
curl http://localhost:8003/productos/carrusel | jq
```

Debería devolver:
```json
[
  {
    "id": "1",
    "url": "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/carruselproductos.png",
    "titulo": "¡Bienvenido a Level-Up Gamer!"
  },
  {
    "id": "2",
    "url": "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/carruselnoticias.png",
    "titulo": "¡Explora nuestros productos gamer de alta calidad!"
  },
  {
    "id": "3",
    "url": "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/blog.png",
    "titulo": "¡Lee desde noticias a guias del mundo gaming!"
  }
]
```

---

### PASO 2: Actualizar Rutas de Productos en RDS

**Conectarse a RDS PostgreSQL:**
```bash
psql -h levelup-db.cvcqjuf3hjcs.us-east-1.rds.amazonaws.com \
     -p 5432 \
     -U levelup_admin \
     -d levelup_productos
```

**Cuando te pida la contraseña, ingrésala.**

**Verificar rutas actuales (opcional):**
```sql
SELECT codigo_producto, titulo, imagen 
FROM productos 
ORDER BY codigo_producto 
LIMIT 10;
```

**Ejecutar los UPDATEs (copiar y pegar todo el bloque):**
```sql
-- Actualizar PlayStation 5
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play5white.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play5white.png", "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play5.webp"]'
WHERE codigo_producto = 'CO001' 
   OR imagen LIKE '%consolas/4.png%' 
   OR imagen LIKE '%consolas/1.png%'
   OR imagen LIKE '%img/consolas/4.png%'
   OR imagen LIKE '%img/consolas/1.png%';

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

-- Actualizar Dualsense Azul
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mandoplayazul.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mandoplayazul.png"]'
WHERE codigo_producto = 'CO005' 
   OR imagen LIKE '%consolas/5.png%'
   OR imagen LIKE '%img/consolas/5.png%';

-- Actualizar Auriculares PS4
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/audifonoazul.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/audifonoazul.png"]'
WHERE codigo_producto = 'CO006' 
   OR imagen LIKE '%consolas/6.png%'
   OR imagen LIKE '%img/consolas/6.png%';

-- Actualizar Auriculares Logitech
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/audilogitech.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/audilogitech.png"]'
WHERE codigo_producto = 'PE001' 
   OR imagen LIKE '%perifericos/1.png%'
   OR imagen LIKE '%img/perifericos/1.png%';

-- Actualizar Teclado Redragon RGB
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/teclado_rd_rgb.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/teclado_rd_rgb.png"]'
WHERE codigo_producto = 'PE002' 
   OR imagen LIKE '%perifericos/2.png%'
   OR imagen LIKE '%img/perifericos/2.png%';

-- Actualizar Mouse Cougar
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mousecougar.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mousecougar.png"]'
WHERE codigo_producto = 'PE003' 
   OR imagen LIKE '%perifericos/3.png%'
   OR imagen LIKE '%img/perifericos/3.png%';

-- Actualizar Monitor ASUS
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/monitorasus.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/monitorasus.png"]'
WHERE codigo_producto = 'PE004' 
   OR imagen LIKE '%perifericos/4.png%'
   OR imagen LIKE '%img/perifericos/4.png%';

-- Actualizar Webcam Logitech
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/webcamlogitech.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/webcamlogitech.png"]'
WHERE codigo_producto = 'PE005' 
   OR imagen LIKE '%perifericos/5.png%'
   OR imagen LIKE '%img/perifericos/5.png%';

-- Actualizar Micrófono Logitech
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/micrologitech.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/micrologitech.png"]'
WHERE codigo_producto = 'PE006' 
   OR imagen LIKE '%perifericos/6.png%'
   OR imagen LIKE '%img/perifericos/6.png%';

-- Actualizar Polerón StarCraft
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronstarcraf.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronstarcraf.png"]'
WHERE codigo_producto = 'RO001' 
   OR imagen LIKE '%polerones/1.png%'
   OR imagen LIKE '%img/polerones/1.png%';

-- Actualizar Polerón Super Papá Gamer
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronpapa.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronpapa.png"]'
WHERE codigo_producto = 'RO002' 
   OR imagen LIKE '%polerones/2.png%'
   OR imagen LIKE '%img/polerones/2.png%';

-- Actualizar Polerón Hollow Knight
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronthekin.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronthekin.png"]'
WHERE codigo_producto = 'RO003' 
   OR imagen LIKE '%polerones/3.png%'
   OR imagen LIKE '%img/polerones/3.png%';

-- Actualizar Polerón PlayStation Retro Negro
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronplay.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronplay.png"]'
WHERE codigo_producto = 'RO004' 
   OR imagen LIKE '%polerones/4.png%'
   OR imagen LIKE '%img/polerones/4.png%';

-- Actualizar Polerón Stumble Guys
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/stumblepoleron.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/stumblepoleron.png"]'
WHERE codigo_producto = 'RO005' 
   OR imagen LIKE '%polerones/5.png%'
   OR imagen LIKE '%img/polerones/5.png%';

-- Actualizar Polerón S.T.A.R.S
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronstars.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronstars.png"]'
WHERE codigo_producto = 'RO006' 
   OR imagen LIKE '%polerones/6.png%'
   OR imagen LIKE '%img/polerones/6.png%';

-- Actualizar Catan
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/catan.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/catan.png"]'
WHERE codigo_producto = 'EN001' 
   OR imagen LIKE '%entretenimiento/catan%'
   OR imagen LIKE '%img/entretenimiento/catan%';

-- Actualizar Carcassonne
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/carcassone.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/carcassone.png"]'
WHERE codigo_producto = 'EN002' 
   OR imagen LIKE '%entretenimiento/carcassone%'
   OR imagen LIKE '%img/entretenimiento/carcassone%';
```

**Verificar que se actualizaron:**
```sql
SELECT codigo_producto, titulo, imagen 
FROM productos 
WHERE imagen LIKE '%consolas/%' 
   OR imagen LIKE '%perifericos/%' 
   OR imagen LIKE '%polerones/%' 
   OR imagen LIKE '%entretenimiento/%';
```

Si no devuelve filas, significa que todas las rutas se actualizaron correctamente.

**Salir de psql:**
```sql
\q
```

---

### PASO 3: Verificar que Todo Funciona

**Probar el carrusel desde tu máquina local:**
```bash
curl http://ec2-44-209-152-110.compute-1.amazonaws.com:8094/productos/carrusel
```

**Probar un producto específico:**
```bash
curl http://ec2-44-209-152-110.compute-1.amazonaws.com:8094/productos | jq '.[] | select(.codigoProducto == "CO001") | .imagenUrl'
```

Debería devolver: `https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play5white.png`

---

## Notas Importantes

1. **El carrusel NO viene de la BD**, viene del código Java. Por eso necesitas reiniciar el servicio.
2. **Los productos SÍ vienen de la BD**, por eso necesitas ejecutar los UPDATEs en RDS.
3. **NO necesitas borrar las tablas**, solo actualizar las rutas.
4. Los cambios en RDS se reflejan inmediatamente (no necesitas reiniciar el servicio).

