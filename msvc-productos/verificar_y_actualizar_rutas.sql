-- Script para verificar y actualizar rutas de imágenes de PRODUCTOS
-- Ejecutar en la base de datos que está usando el backend
-- 
-- NOTA: Este script solo actualiza rutas de imágenes de PRODUCTOS.
-- Las imágenes del carrusel y logo se manejan directamente en el backend Java:
-- - Carrusel: ProductoController.obtenerImagenesCarrusel() (carruselnoticias.png, carruselproductos.png)
-- - Logo: ProductoController.obtenerLogo() (logo.png con fallback a levelup_logo.png)
--
-- Este script es útil cuando la BD ya tiene datos con rutas antiguas.
-- Si la BD se inicializa desde cero, data.sql ya tiene las rutas correctas.

-- PASO 1: Verificar qué rutas tiene actualmente
SELECT codigo_producto, titulo, imagen 
FROM productos 
ORDER BY codigo_producto;

-- PASO 2: Ver cuántos productos tienen rutas antiguas
SELECT 
    COUNT(*) as total_productos,
    SUM(CASE WHEN imagen LIKE '%consolas/%' OR imagen LIKE '%perifericos/%' OR imagen LIKE '%polerones/%' THEN 1 ELSE 0 END) as rutas_antiguas,
    SUM(CASE WHEN imagen LIKE '%play4.png%' OR imagen LIKE '%play5white.png%' OR imagen LIKE '%mandoplay.png%' THEN 1 ELSE 0 END) as rutas_nuevas
FROM productos;

-- PASO 3: Actualizar todas las rutas antiguas a las nuevas

-- Actualizar PlayStation 5
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play5white.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play5white.png", "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play5.webp"]'
WHERE codigo_producto = 'CO001' 
   OR imagen LIKE '%consolas/4.png%' 
   OR imagen LIKE '%consolas/1.png%';

-- Actualizar PlayStation 4 Slim
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play4.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play4.png"]'
WHERE codigo_producto = 'CO002' 
   OR imagen LIKE '%consolas/2.png%';

-- Actualizar DualShock 4
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mandoplay.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mandoplay.png"]'
WHERE codigo_producto = 'CO003' 
   OR imagen LIKE '%consolas/3.png%';

-- Actualizar Dualsense Azul
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mandoplayazul.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mandoplayazul.png"]'
WHERE codigo_producto = 'CO005' 
   OR imagen LIKE '%consolas/5.png%';

-- Actualizar Auriculares PS4
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/audifonoazul.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/audifonoazul.png"]'
WHERE codigo_producto = 'CO006' 
   OR imagen LIKE '%consolas/6.png%';

-- Actualizar Auriculares Logitech
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/audilogitech.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/audilogitech.png"]'
WHERE codigo_producto = 'PE001' 
   OR imagen LIKE '%perifericos/1.png%';

-- Actualizar Teclado Redragon RGB
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/teclado_rd_rgb.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/teclado_rd_rgb.png"]'
WHERE codigo_producto = 'PE002' 
   OR imagen LIKE '%perifericos/2.png%';

-- Actualizar Mouse Cougar
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mousecougar.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mousecougar.png"]'
WHERE codigo_producto = 'PE003' 
   OR imagen LIKE '%perifericos/3.png%';

-- Actualizar Monitor ASUS
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/monitorasus.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/monitorasus.png"]'
WHERE codigo_producto = 'PE004' 
   OR imagen LIKE '%perifericos/4.png%';

-- Actualizar Webcam Logitech
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/webcamlogitech.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/webcamlogitech.png"]'
WHERE codigo_producto = 'PE005' 
   OR imagen LIKE '%perifericos/5.png%';

-- Actualizar Micrófono Logitech
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/micrologitech.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/micrologitech.png"]'
WHERE codigo_producto = 'PE006' 
   OR imagen LIKE '%perifericos/6.png%';

-- Actualizar Polerón StarCraft
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronstarcraf.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronstarcraf.png"]'
WHERE codigo_producto = 'RO001' 
   OR imagen LIKE '%polerones/1.png%';

-- Actualizar Polerón Super Papá Gamer
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronpapa.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronpapa.png"]'
WHERE codigo_producto = 'RO002' 
   OR imagen LIKE '%polerones/2.png%';

-- Actualizar Polerón Hollow Knight
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronthekin.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronthekin.png"]'
WHERE codigo_producto = 'RO003' 
   OR imagen LIKE '%polerones/3.png%';

-- Actualizar Polerón PlayStation Retro Negro
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronplay.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronplay.png"]'
WHERE codigo_producto = 'RO004' 
   OR imagen LIKE '%polerones/4.png%';

-- Actualizar Polerón Stumble Guys
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/stumblepoleron.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/stumblepoleron.png"]'
WHERE codigo_producto = 'RO005' 
   OR imagen LIKE '%polerones/5.png%';

-- Actualizar Polerón S.T.A.R.S
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronstars.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronstars.png"]'
WHERE codigo_producto = 'RO006' 
   OR imagen LIKE '%polerones/6.png%';

-- Actualizar Catan
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/catan.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/catan.png"]'
WHERE codigo_producto = 'EN001' 
   OR imagen LIKE '%entretenimiento/catan%';

-- Actualizar Carcassonne
UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/carcassone.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/carcassone.png"]'
WHERE codigo_producto = 'EN002' 
   OR imagen LIKE '%entretenimiento/carcassone%';

-- PASO 4: Verificar que se actualizaron correctamente
SELECT codigo_producto, titulo, imagen 
FROM productos 
ORDER BY codigo_producto;

