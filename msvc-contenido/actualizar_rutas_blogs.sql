-- Script para actualizar rutas de imágenes de BLOGS/ARTÍCULOS
-- Ejecutar en la base de datos: levelup_contenido
-- Tabla: articulos
-- Campo: imagen_articulo

-- NOTA: Las imágenes se asignan según la categoría del artículo:
-- - TECNOLOGIA o NOTICIAS → ralidadv.jfif
-- - LANZAMIENTOS → juegos_esperados.jpg
-- - Otras categorías (GUIAS, ESPORTS, INDUSTRIA, etc.) → evento.jpg

-- PASO 1: Verificar qué rutas tienen actualmente los artículos
SELECT id_articulo, titulo_articulo, categoria_articulo,
       CASE 
           WHEN imagen_articulo LIKE 'data:image%' THEN 'Base64'
           WHEN imagen_articulo LIKE 'https://%' THEN 'URL S3'
           WHEN imagen_articulo IS NULL OR imagen_articulo = '' THEN 'Sin imagen'
           ELSE 'Otro formato'
       END as tipo_imagen,
       LEFT(imagen_articulo, 80) as imagen_preview
FROM articulos 
ORDER BY id_articulo;

-- PASO 2: Ver cuántos artículos tienen Base64 o rutas incorrectas por categoría
SELECT 
    categoria_articulo,
    COUNT(*) as total_articulos,
    SUM(CASE WHEN imagen_articulo LIKE 'data:image%' THEN 1 ELSE 0 END) as base64_images,
    SUM(CASE WHEN imagen_articulo LIKE 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com%' THEN 1 ELSE 0 END) as rutas_correctas,
    SUM(CASE WHEN imagen_articulo IS NULL OR imagen_articulo = '' THEN 1 ELSE 0 END) as sin_imagen
FROM articulos
GROUP BY categoria_articulo
ORDER BY categoria_articulo;

-- PASO 3: Actualizar artículos según su categoría

-- TECNOLOGIA y NOTICIAS → ralidadv.jfif
UPDATE articulos 
SET imagen_articulo = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/ralidadv.jfif'
WHERE categoria_articulo IN ('TECNOLOGIA', 'NOTICIAS')
  AND (imagen_articulo IS NULL 
       OR imagen_articulo = ''
       OR imagen_articulo LIKE 'data:image%'
       OR imagen_articulo NOT LIKE 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/ralidadv.jfif');

-- LANZAMIENTOS → juegos_esperados.jpg
UPDATE articulos 
SET imagen_articulo = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/juegos_esperados.jpg'
WHERE categoria_articulo = 'LANZAMIENTOS'
  AND (imagen_articulo IS NULL 
       OR imagen_articulo = ''
       OR imagen_articulo LIKE 'data:image%'
       OR imagen_articulo NOT LIKE 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/juegos_esperados.jpg');

-- Otras categorías (GUIAS, ESPORTS, INDUSTRIA, etc.) → evento.jpg
UPDATE articulos 
SET imagen_articulo = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/evento.jpg'
WHERE categoria_articulo NOT IN ('TECNOLOGIA', 'NOTICIAS', 'LANZAMIENTOS')
  AND (imagen_articulo IS NULL 
       OR imagen_articulo = ''
       OR imagen_articulo LIKE 'data:image%'
       OR imagen_articulo NOT LIKE 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/evento.jpg');

-- PASO 4: Verificar que se actualizaron correctamente
SELECT id_articulo, titulo_articulo, categoria_articulo, imagen_articulo 
FROM articulos 
ORDER BY categoria_articulo, id_articulo;

