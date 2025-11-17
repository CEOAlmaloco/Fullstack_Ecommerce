-- Script para actualizar rutas de imágenes de EVENTOS
-- Ejecutar en la base de datos: levelup_eventos
-- Tabla: eventos
-- Campo: imagen

-- NOTA: Todos los eventos usan la misma imagen: evento.jpg de S3

-- PASO 1: Verificar qué rutas tienen actualmente los eventos
SELECT id_evento, nombre_evento, 
       CASE 
           WHEN imagen LIKE 'data:image%' THEN 'Base64'
           WHEN imagen LIKE 'https://%' THEN 'URL S3'
           WHEN imagen IS NULL OR imagen = '' THEN 'Sin imagen'
           ELSE 'Otro formato'
       END as tipo_imagen,
       LEFT(imagen, 80) as imagen_preview
FROM eventos 
ORDER BY id_evento;

-- PASO 2: Ver cuántos eventos tienen Base64 o rutas incorrectas
SELECT 
    COUNT(*) as total_eventos,
    SUM(CASE WHEN imagen LIKE 'data:image%' THEN 1 ELSE 0 END) as base64_images,
    SUM(CASE WHEN imagen LIKE 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/evento.jpg' THEN 1 ELSE 0 END) as rutas_correctas,
    SUM(CASE WHEN imagen IS NULL OR imagen = '' THEN 1 ELSE 0 END) as sin_imagen
FROM eventos;

-- PASO 3: Actualizar todos los eventos para usar evento.jpg de S3
-- Reemplazar Base64 y cualquier otra ruta por la URL correcta de S3
UPDATE eventos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/evento.jpg',
    imagenes = '[]'
WHERE imagen IS NULL 
   OR imagen = ''
   OR imagen LIKE 'data:image%'
   OR imagen NOT LIKE 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/evento.jpg';

-- PASO 4: Verificar que se actualizaron correctamente
SELECT id_evento, nombre_evento, imagen 
FROM eventos 
ORDER BY id_evento;

