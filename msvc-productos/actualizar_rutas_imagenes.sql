-- Script SQL para actualizar rutas de imágenes en la BD
-- Ejecutar este script en la BD para corregir las URLs de las imágenes

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

-- Verificar que se actualizaron correctamente
SELECT codigo_producto, titulo, imagen FROM productos ORDER BY codigo_producto;

