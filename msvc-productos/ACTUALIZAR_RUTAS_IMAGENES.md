# Actualizar Rutas de Imágenes en la Base de Datos

## Problema

Las URLs de las imágenes en la BD apuntan a rutas que no existen en S3:

**Rutas actuales en BD (incorrectas):**
- `img/consolas/2.png` → No existe en S3
- `img/consolas/3.png` → No existe en S3
- `img/consolas/4.png` → No existe en S3
- `img/perifericos/2.png` → No existe en S3
- `img/polerones/1.png` → No existe en S3

**Rutas correctas según data.sql:**
- `img/play4.png`
- `img/mandoplay.png`
- `img/play5white.png`
- `img/teclado_rd_rgb.png`
- `img/poleronstarcraf.png`

## Solución: Script SQL para Actualizar Rutas

Ejecuta este script SQL en la BD para actualizar las rutas:

```sql
-- Actualizar PlayStation 4 Slim
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play4.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play4.png"]'
WHERE codigo_producto = 'CO002';

-- Actualizar PlayStation 5
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play5white.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play5white.png", "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play5.webp"]'
WHERE codigo_producto = 'CO001';

-- Actualizar DualShock 4
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mandoplay.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mandoplay.png"]'
WHERE codigo_producto = 'CO003';

-- Actualizar Dualsense Azul
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mandoplayazul.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mandoplayazul.png"]'
WHERE codigo_producto = 'CO005';

-- Actualizar Auriculares PS4
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/audifonoazul.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/audifonoazul.png"]'
WHERE codigo_producto = 'CO006';

-- Actualizar Auriculares Logitech
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/audilogitech.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/audilogitech.png"]'
WHERE codigo_producto = 'PE001';

-- Actualizar Teclado Redragon RGB
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/teclado_rd_rgb.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/teclado_rd_rgb.png"]'
WHERE codigo_producto = 'PE002';

-- Actualizar Mouse Cougar
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mousecougar.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mousecougar.png"]'
WHERE codigo_producto = 'PE003';

-- Actualizar Monitor ASUS
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/monitorasus.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/monitorasus.png"]'
WHERE codigo_producto = 'PE004';

-- Actualizar Webcam Logitech
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/webcamlogitech.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/webcamlogitech.png"]'
WHERE codigo_producto = 'PE005';

-- Actualizar Micrófono Logitech
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/micrologitech.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/micrologitech.png"]'
WHERE codigo_producto = 'PE006';

-- Actualizar Polerón StarCraft
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronstarcraf.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronstarcraf.png"]'
WHERE codigo_producto = 'RO001';

-- Actualizar Polerón Super Papá Gamer
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronpapa.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronpapa.png"]'
WHERE codigo_producto = 'RO002';

-- Actualizar Polerón Hollow Knight
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronthekin.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronthekin.png"]'
WHERE codigo_producto = 'RO003';

-- Actualizar Polerón PlayStation Retro Negro
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronplay.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronplay.png"]'
WHERE codigo_producto = 'RO004';

-- Actualizar Polerón Stumble Guys
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/stumblepoleron.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/stumblepoleron.png"]'
WHERE codigo_producto = 'RO005';

-- Actualizar Polerón S.T.A.R.S
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronstars.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronstars.png"]'
WHERE codigo_producto = 'RO006';

-- Actualizar Catan
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/catan.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/catan.png"]'
WHERE codigo_producto = 'EN001';

-- Actualizar Carcassonne
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/carcassone.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/carcassone.png"]'
WHERE codigo_producto = 'EN002';
```

## Cómo Ejecutar el Script

### Opción 1: Desde H2 Console (si usas H2)

1. Acceder a: `http://44.209.152.110:8003/h2-console`
2. JDBC URL: `jdbc:h2:file:./data/msvc_productos_dev`
3. Usuario: `sa`
4. Password: (vacío)
5. Copiar y pegar el script SQL completo
6. Ejecutar

### Opción 2: Desde línea de comandos (si usas PostgreSQL/MySQL)

```bash
# Conectarse a la BD
psql -h localhost -U usuario -d msvc_productos

# O para MySQL
mysql -u usuario -p msvc_productos

# Ejecutar el script
# (copiar y pegar el SQL de arriba)
```

### Opción 3: Crear un archivo SQL y ejecutarlo

```bash
# Crear archivo
nano actualizar_imagenes.sql
# (pegar el script SQL)

# Ejecutar
psql -h localhost -U usuario -d msvc_productos -f actualizar_imagenes.sql
```

## Verificación

Después de ejecutar el script:

```sql
-- Verificar que las rutas se actualizaron
SELECT codigo_producto, titulo, imagen FROM productos;

-- Probar una URL directamente
-- Debería devolver 200 OK
curl -I https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play4.png
```

## Nota Importante

**Antes de ejecutar el script, verifica qué imágenes realmente existen en S3:**

```bash
# Si tienes AWS CLI configurado
aws s3 ls s3://levelup-gamer-products/img/ --recursive
```

Si las imágenes en S3 tienen nombres diferentes, ajusta el script SQL según corresponda.

