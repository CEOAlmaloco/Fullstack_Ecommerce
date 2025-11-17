-- Datos iniciales para productos de Level Up Gaming
-- Base de datos normalizada con tablas separadas para categorías y subcategorías
-- Rutas de imágenes como URLs completas de S3
-- Estructura similar a verificar_y_actualizar_rutas.sql usando UPDATE para actualizar datos existentes

-- PASO 1: Insertar CATEGORÍAS (con ON CONFLICT para evitar duplicados)
INSERT INTO categorias (id, nombre) VALUES
  ('CO', 'Consola'),
  ('PE', 'Perifericos'),
  ('RO', 'Ropa'),
  ('EN', 'Entretenimiento')
ON CONFLICT (id) DO UPDATE SET nombre = EXCLUDED.nombre;

-- PASO 2: Insertar SUBCATEGORÍAS (con relación a categorías, evitar duplicados)
INSERT INTO subcategorias (id, nombre, categoria_id) VALUES
  ('HA', 'Hardware', 'CO'),
  ('MA', 'Mandos', 'CO'),
  ('AC', 'Accesorios', 'CO'),
  ('TE', 'Teclados', 'PE'),
  ('MO', 'Mouses', 'PE'),
  ('AU', 'Auriculares', 'PE'),
  ('MT', 'Monitores', 'PE'),
  ('MI', 'Microfonos', 'PE'),
  ('CW', 'Camaras web', 'PE'),
  ('MP', 'Mousepad', 'PE'),
  ('SI', 'Sillas Gamers', 'PE'),
  ('PG', 'Polerones Gamers Personalizados', 'RO'),
  ('PR', 'Poleras Personalizadas', 'RO'),
  ('JM', 'Juegos de Mesa', 'EN')
ON CONFLICT (id) DO UPDATE SET
  nombre = EXCLUDED.nombre,
  categoria_id = EXCLUDED.categoria_id;

-- PASO 3: Insertar o actualizar PRODUCTOS usando estructura similar a verificar_y_actualizar_rutas.sql
-- Primero insertamos si no existe, luego actualizamos con UPDATE

-- PlayStation 5
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'PlayStation 5', 'CO', 'HA', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play5white.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play5white.png", "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play5.webp"]', 549990.0, true, 1.7, 'La consola de última generación de Sony, que ofrece gráficos impresionantes y tiempos de carga ultrarrápidos para una experiencia de juego inmersiva.', 50, 'CO001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'CO001');

UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play5white.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play5white.png", "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play5.webp"]',
    titulo = 'PlayStation 5',
    categoria_id = 'CO',
    subcategoria_id = 'HA',
    precio = 549990.0,
    disponible = true,
    rating = 1.7,
    descripcion = 'La consola de última generación de Sony, que ofrece gráficos impresionantes y tiempos de carga ultrarrápidos para una experiencia de juego inmersiva.',
    stock = 50,
    updated_at = CURRENT_TIMESTAMP
WHERE codigo_producto = 'CO001' 
   OR imagen LIKE '%consolas/4.png%' 
   OR imagen LIKE '%consolas/1.png%'
   OR imagen LIKE '%img/consolas/4.png%'
   OR imagen LIKE '%img/consolas/1.png%';

-- PlayStation 4 Slim
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'PlayStation 4 Slim', 'CO', 'HA', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play4.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play4.png"]', 179990.0, true, 5.0, 'Una consola versátil y compacta que sigue siendo ideal para disfrutar de un extenso catálogo de juegos con gran rendimiento y entretenimiento garantizado.', 30, 'CO002', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'CO002');

UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play4.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play4.png"]',
    titulo = 'PlayStation 4 Slim',
    categoria_id = 'CO',
    subcategoria_id = 'HA',
    precio = 179990.0,
    disponible = true,
    rating = 5.0,
    descripcion = 'Una consola versátil y compacta que sigue siendo ideal para disfrutar de un extenso catálogo de juegos con gran rendimiento y entretenimiento garantizado.',
    stock = 30,
    updated_at = CURRENT_TIMESTAMP
WHERE codigo_producto = 'CO002' 
   OR imagen LIKE '%consolas/2.png%'
   OR imagen LIKE '%img/consolas/2.png%';

-- DualShock 4
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'DualShock 4', 'CO', 'MA', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mandoplay.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mandoplay.png"]', 60000.0, true, 4.0, 'Control oficial de PlayStation 4 con diseño ergonómico y funciones avanzadas que ofrecen precisión y comodidad durante tus sesiones de juego.', 100, 'CO003', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'CO003');

UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mandoplay.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mandoplay.png"]',
    titulo = 'DualShock 4',
    categoria_id = 'CO',
    subcategoria_id = 'MA',
    precio = 60000.0,
    disponible = true,
    rating = 4.0,
    descripcion = 'Control oficial de PlayStation 4 con diseño ergonómico y funciones avanzadas que ofrecen precisión y comodidad durante tus sesiones de juego.',
    stock = 100,
    updated_at = CURRENT_TIMESTAMP
WHERE codigo_producto = 'CO003' 
   OR imagen LIKE '%consolas/3.png%'
   OR imagen LIKE '%img/consolas/3.png%';

-- Dualsense Azul
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'Dualsense Azul', 'CO', 'MA', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mandoplayazul.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mandoplayazul.png"]', 69990.0, true, 4.0, 'Control de PlayStation 5 en elegante color azul, con retroalimentación háptica y gatillos adaptativos que llevan la experiencia de juego a otro nivel.', 80, 'CO005', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'CO005');

UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mandoplayazul.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mandoplayazul.png"]',
    titulo = 'Dualsense Azul',
    categoria_id = 'CO',
    subcategoria_id = 'MA',
    precio = 69990.0,
    disponible = true,
    rating = 4.0,
    descripcion = 'Control de PlayStation 5 en elegante color azul, con retroalimentación háptica y gatillos adaptativos que llevan la experiencia de juego a otro nivel.',
    stock = 80,
    updated_at = CURRENT_TIMESTAMP
WHERE codigo_producto = 'CO005' 
   OR imagen LIKE '%consolas/5.png%'
   OR imagen LIKE '%img/consolas/5.png%';

-- Auriculares PS4
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'Auriculares PS4', 'CO', 'AC', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/audifonoazul.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/audifonoazul.png"]', 150000.0, false, 3.0, 'Auriculares diseñados para PlayStation 4, con sonido envolvente y micrófono integrado para comunicación clara en partidas multijugador.', 0, 'CO006', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'CO006');

UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/audifonoazul.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/audifonoazul.png"]',
    titulo = 'Auriculares PS4',
    categoria_id = 'CO',
    subcategoria_id = 'AC',
    precio = 150000.0,
    disponible = false,
    rating = 3.0,
    descripcion = 'Auriculares diseñados para PlayStation 4, con sonido envolvente y micrófono integrado para comunicación clara en partidas multijugador.',
    stock = 0,
    updated_at = CURRENT_TIMESTAMP
WHERE codigo_producto = 'CO006' 
   OR imagen LIKE '%consolas/6.png%'
   OR imagen LIKE '%img/consolas/6.png%';

-- Auriculares Logitech
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'Auriculares Logitech', 'PE', 'AU', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/audilogitech.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/audilogitech.png"]', 100000.0, true, 4.0, 'Auriculares de alto rendimiento con gran calidad de sonido y micrófono ajustable, ideales para juegos, streaming y comunicación online.', 60, 'PE001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'PE001');

UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/audilogitech.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/audilogitech.png"]',
    titulo = 'Auriculares Logitech',
    categoria_id = 'PE',
    subcategoria_id = 'AU',
    precio = 100000.0,
    disponible = true,
    rating = 4.0,
    descripcion = 'Auriculares de alto rendimiento con gran calidad de sonido y micrófono ajustable, ideales para juegos, streaming y comunicación online.',
    stock = 60,
    updated_at = CURRENT_TIMESTAMP
WHERE codigo_producto = 'PE001' 
   OR imagen LIKE '%perifericos/1.png%'
   OR imagen LIKE '%img/perifericos/1.png%';

-- Teclado Redragon RGB
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'Teclado Redragon RGB', 'PE', 'TE', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/teclado_rd_rgb.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/teclado_rd_rgb.png"]', 145990.0, true, 4.0, 'Teclado mecánico con iluminación RGB y switches de alto rendimiento, ideal para juegos y escritura intensiva.', 40, 'PE002', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'PE002');

UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/teclado_rd_rgb.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/teclado_rd_rgb.png"]',
    titulo = 'Teclado Redragon RGB',
    categoria_id = 'PE',
    subcategoria_id = 'TE',
    precio = 145990.0,
    disponible = true,
    rating = 4.0,
    descripcion = 'Teclado mecánico con iluminación RGB y switches de alto rendimiento, ideal para juegos y escritura intensiva.',
    stock = 40,
    updated_at = CURRENT_TIMESTAMP
WHERE codigo_producto = 'PE002' 
   OR imagen LIKE '%perifericos/2.png%'
   OR imagen LIKE '%img/perifericos/2.png%';

-- Mouse Cougar
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'Mouse Cougar', 'PE', 'MO', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mousecougar.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mousecougar.png"]', 85990.0, true, 4.0, 'Mouse gamer ergonómico con alta precisión y diseño personalizable, perfecto para sesiones intensas y competitivas.', 70, 'PE003', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'PE003');

UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mousecougar.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mousecougar.png"]',
    titulo = 'Mouse Cougar',
    categoria_id = 'PE',
    subcategoria_id = 'MO',
    precio = 85990.0,
    disponible = true,
    rating = 4.0,
    descripcion = 'Mouse gamer ergonómico con alta precisión y diseño personalizable, perfecto para sesiones intensas y competitivas.',
    stock = 70,
    updated_at = CURRENT_TIMESTAMP
WHERE codigo_producto = 'PE003' 
   OR imagen LIKE '%perifericos/3.png%'
   OR imagen LIKE '%img/perifericos/3.png%';

-- Monitor ASUS
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'Monitor ASUS', 'PE', 'MT', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/monitorasus.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/monitorasus.png"]', 175990.0, true, 4.0, 'Monitor de alto rendimiento con gran calidad de imagen y refresco rápido, ideal para disfrutar de gráficos nítidos en juegos y multimedia.', 25, 'PE004', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'PE004');

UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/monitorasus.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/monitorasus.png"]',
    titulo = 'Monitor ASUS',
    categoria_id = 'PE',
    subcategoria_id = 'MT',
    precio = 175990.0,
    disponible = true,
    rating = 4.0,
    descripcion = 'Monitor de alto rendimiento con gran calidad de imagen y refresco rápido, ideal para disfrutar de gráficos nítidos en juegos y multimedia.',
    stock = 25,
    updated_at = CURRENT_TIMESTAMP
WHERE codigo_producto = 'PE004' 
   OR imagen LIKE '%perifericos/4.png%'
   OR imagen LIKE '%img/perifericos/4.png%';

-- Webcam Logitech
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'Webcam Logitech', 'PE', 'CW', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/webcamlogitech.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/webcamlogitech.png"]', 67990.0, true, 4.0, 'Webcam de alta definición con gran calidad de imagen y audio, perfecta para streaming, videollamadas y creación de contenido.', 45, 'PE005', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'PE005');

UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/webcamlogitech.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/webcamlogitech.png"]',
    titulo = 'Webcam Logitech',
    categoria_id = 'PE',
    subcategoria_id = 'CW',
    precio = 67990.0,
    disponible = true,
    rating = 4.0,
    descripcion = 'Webcam de alta definición con gran calidad de imagen y audio, perfecta para streaming, videollamadas y creación de contenido.',
    stock = 45,
    updated_at = CURRENT_TIMESTAMP
WHERE codigo_producto = 'PE005' 
   OR imagen LIKE '%perifericos/5.png%'
   OR imagen LIKE '%img/perifericos/5.png%';

-- Microfono Logitech
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'Microfono Logitech', 'PE', 'MI', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/micrologitech.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/micrologitech.png"]', 86990.0, true, 4.0, 'Micrófono de alta fidelidad con captación clara y profesional, ideal para streaming, grabaciones y comunicación en equipo.', 35, 'PE006', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'PE006');

UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/micrologitech.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/micrologitech.png"]',
    titulo = 'Microfono Logitech',
    categoria_id = 'PE',
    subcategoria_id = 'MI',
    precio = 86990.0,
    disponible = true,
    rating = 4.0,
    descripcion = 'Micrófono de alta fidelidad con captación clara y profesional, ideal para streaming, grabaciones y comunicación en equipo.',
    stock = 35,
    updated_at = CURRENT_TIMESTAMP
WHERE codigo_producto = 'PE006' 
   OR imagen LIKE '%perifericos/6.png%'
   OR imagen LIKE '%img/perifericos/6.png%';

-- Poleron StarCraft
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'Poleron StarCraft', 'RO', 'PG', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronstarcraf.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronstarcraf.png"]', 40000.0, true, 4.5, 'Polerón inspirado en StarCraft con diseño gamer único y tela cómoda, perfecto para mostrar tu pasión por el universo de Blizzard.', 200, 'RO001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'RO001');

UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronstarcraf.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronstarcraf.png"]',
    titulo = 'Poleron StarCraft',
    categoria_id = 'RO',
    subcategoria_id = 'PG',
    precio = 40000.0,
    disponible = true,
    rating = 4.5,
    descripcion = 'Polerón inspirado en StarCraft con diseño gamer único y tela cómoda, perfecto para mostrar tu pasión por el universo de Blizzard.',
    stock = 200,
    updated_at = CURRENT_TIMESTAMP
WHERE codigo_producto = 'RO001' 
   OR imagen LIKE '%polerones/1.png%'
   OR imagen LIKE '%img/polerones/1.png%';

-- Poleron Super Papá Gamer
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'Poleron Super Papá Gamer', 'RO', 'PG', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronpapa.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronpapa.png"]', 40000.0, true, 4.5, 'Polerón divertido y cómodo diseñado para los papás que disfrutan tanto del gaming como de la familia.', 200, 'RO002', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'RO002');

UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronpapa.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronpapa.png"]',
    titulo = 'Poleron Super Papá Gamer',
    categoria_id = 'RO',
    subcategoria_id = 'PG',
    precio = 40000.0,
    disponible = true,
    rating = 4.5,
    descripcion = 'Polerón divertido y cómodo diseñado para los papás que disfrutan tanto del gaming como de la familia.',
    stock = 200,
    updated_at = CURRENT_TIMESTAMP
WHERE codigo_producto = 'RO002' 
   OR imagen LIKE '%polerones/2.png%'
   OR imagen LIKE '%img/polerones/2.png%';

-- Poleron Hollow Knight
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'Poleron Hollow Knight', 'RO', 'PG', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronthekin.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronthekin.png"]', 40000.0, true, 4.5, 'Polerón con diseño inspirado en Hollow Knight, ideal para fanáticos del juego indie y su mundo misterioso.', 200, 'RO003', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'RO003');

UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronthekin.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronthekin.png"]',
    titulo = 'Poleron Hollow Knight',
    categoria_id = 'RO',
    subcategoria_id = 'PG',
    precio = 40000.0,
    disponible = true,
    rating = 4.5,
    descripcion = 'Polerón con diseño inspirado en Hollow Knight, ideal para fanáticos del juego indie y su mundo misterioso.',
    stock = 200,
    updated_at = CURRENT_TIMESTAMP
WHERE codigo_producto = 'RO003' 
   OR imagen LIKE '%polerones/3.png%'
   OR imagen LIKE '%img/polerones/3.png%';

-- Poleron PlayStation Retro Negro
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'Poleron PlayStation Retro Negro', 'RO', 'PG', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronplay.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronplay.png"]', 40000.0, true, 4.5, 'Polerón retro en color negro con el clásico logo de PlayStation, perfecto para los nostálgicos del gaming.', 200, 'RO004', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'RO004');

UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronplay.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronplay.png"]',
    titulo = 'Poleron PlayStation Retro Negro',
    categoria_id = 'RO',
    subcategoria_id = 'PG',
    precio = 40000.0,
    disponible = true,
    rating = 4.5,
    descripcion = 'Polerón retro en color negro con el clásico logo de PlayStation, perfecto para los nostálgicos del gaming.',
    stock = 200,
    updated_at = CURRENT_TIMESTAMP
WHERE codigo_producto = 'RO004' 
   OR imagen LIKE '%polerones/4.png%'
   OR imagen LIKE '%img/polerones/4.png%';

-- Poleron Stumble Guys
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'Poleron Stumble Guys', 'RO', 'PG', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/stumblepoleron.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/stumblepoleron.png"]', 40000.0, true, 4.5, 'Polerón colorido y divertido inspirado en Stumble Guys, ideal para gamers que disfrutan de las partidas llenas de caos y risas.', 200, 'RO005', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'RO005');

UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/stumblepoleron.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/stumblepoleron.png"]',
    titulo = 'Poleron Stumble Guys',
    categoria_id = 'RO',
    subcategoria_id = 'PG',
    precio = 40000.0,
    disponible = true,
    rating = 4.5,
    descripcion = 'Polerón colorido y divertido inspirado en Stumble Guys, ideal para gamers que disfrutan de las partidas llenas de caos y risas.',
    stock = 200,
    updated_at = CURRENT_TIMESTAMP
WHERE codigo_producto = 'RO005' 
   OR imagen LIKE '%polerones/5.png%'
   OR imagen LIKE '%img/polerones/5.png%';

-- Poleron S.T.A.R.S
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'Poleron S.T.A.R.S', 'RO', 'PG', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronstars.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronstars.png"]', 40000.0, true, 4.5, 'Polerón con diseño de la unidad S.T.A.R.S de Resident Evil, pensado para los fanáticos del survival horror.', 200, 'RO006', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'RO006');

UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronstars.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronstars.png"]',
    titulo = 'Poleron S.T.A.R.S',
    categoria_id = 'RO',
    subcategoria_id = 'PG',
    precio = 40000.0,
    disponible = true,
    rating = 4.5,
    descripcion = 'Polerón con diseño de la unidad S.T.A.R.S de Resident Evil, pensado para los fanáticos del survival horror.',
    stock = 200,
    updated_at = CURRENT_TIMESTAMP
WHERE codigo_producto = 'RO006' 
   OR imagen LIKE '%polerones/6.png%'
   OR imagen LIKE '%img/polerones/6.png%';

-- Catan
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'Catan', 'EN', 'JM', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/catan.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/catan.png"]', 29990.0, true, 4.0, 'Un clásico juego de estrategia donde los jugadores compiten por colonizar y expandirse en la isla de Catan. Ideal para 3-4 jugadores y perfecto para noches de juego en familia o con amigos.', 80, 'EN001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'EN001');

UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/catan.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/catan.png"]',
    titulo = 'Catan',
    categoria_id = 'EN',
    subcategoria_id = 'JM',
    precio = 29990.0,
    disponible = true,
    rating = 4.0,
    descripcion = 'Un clásico juego de estrategia donde los jugadores compiten por colonizar y expandirse en la isla de Catan. Ideal para 3-4 jugadores y perfecto para noches de juego en familia o con amigos.',
    stock = 80,
    updated_at = CURRENT_TIMESTAMP
WHERE codigo_producto = 'EN001' 
   OR imagen LIKE '%entretenimiento/catan%'
   OR imagen LIKE '%img/entretenimiento/catan%';

-- Carcassonne
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'Carcassonne', 'EN', 'JM', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/carcassone.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/carcassone.png"]', 24990.0, true, 4.0, 'Un juego de colocación de fichas donde los jugadores construyen el paisaje alrededor de la fortaleza medieval de Carcassonne. Ideal para 2-5 jugadores y fácil de aprender.', 70, 'EN002', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'EN002');

UPDATE productos 
SET imagen = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/carcassone.png',
    imagenes = '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/carcassone.png"]',
    titulo = 'Carcassonne',
    categoria_id = 'EN',
    subcategoria_id = 'JM',
    precio = 24990.0,
    disponible = true,
    rating = 4.0,
    descripcion = 'Un juego de colocación de fichas donde los jugadores construyen el paisaje alrededor de la fortaleza medieval de Carcassonne. Ideal para 2-5 jugadores y fácil de aprender.',
    stock = 70,
    updated_at = CURRENT_TIMESTAMP
WHERE codigo_producto = 'EN002' 
   OR imagen LIKE '%entretenimiento/carcassone%'
   OR imagen LIKE '%img/entretenimiento/carcassone%';
