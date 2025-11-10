-- Datos iniciales para productos de Level Up Gaming
-- Base de datos normalizada con tablas separadas para categorías y subcategorías
-- Rutas de imágenes sin ./ inicial para que el backend las sirva correctamente

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
  ('AC', 'Accesorios', 'CO')
ON CONFLICT (id) DO UPDATE SET
  nombre = EXCLUDED.nombre,
  categoria_id = EXCLUDED.categoria_id;

INSERT INTO subcategorias (id, nombre, categoria_id) VALUES
  ('TE', 'Teclados', 'PE'),
  ('MO', 'Mouses', 'PE'),
  ('AU', 'Auriculares', 'PE'),
  ('MT', 'Monitores', 'PE'),
  ('MI', 'Microfonos', 'PE'),
  ('CW', 'Camaras web', 'PE'),
  ('MP', 'Mousepad', 'PE'),
  ('SI', 'Sillas Gamers', 'PE')
ON CONFLICT (id) DO UPDATE SET
  nombre = EXCLUDED.nombre,
  categoria_id = EXCLUDED.categoria_id;

INSERT INTO subcategorias (id, nombre, categoria_id) VALUES
  ('PG', 'Polerones Gamers Personalizados', 'RO'),
  ('PR', 'Poleras Personalizadas', 'RO')
ON CONFLICT (id) DO UPDATE SET
  nombre = EXCLUDED.nombre,
  categoria_id = EXCLUDED.categoria_id;

INSERT INTO subcategorias (id, nombre, categoria_id) VALUES
  ('JM', 'Juegos de Mesa', 'EN')
ON CONFLICT (id) DO UPDATE SET
  nombre = EXCLUDED.nombre,
  categoria_id = EXCLUDED.categoria_id;

-- PASO 3: Insertar PRODUCTOS (con relaciones a categorías y subcategorías, evitar duplicados)
-- NOTA: Las imágenes se insertan como URLs públicas (S3). Si necesitas usar Base64, habilita
-- el inicializador estableciendo productos.autoconvert-base64=true.
-- CONSOLAS (CO) - Hardware (HA)
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'PlayStation 5', 'CO', 'HA', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play5white.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play5white.png", "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play5.webp"]', 549990.0, true, 1.7, 'La consola de última generación de Sony, que ofrece gráficos impresionantes y tiempos de carga ultrarrápidos para una experiencia de juego inmersiva.', 50, 'CO001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'CO001')
UNION ALL
SELECT 'PlayStation 4 Slim', 'CO', 'HA', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play4.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play4.png"]', 179990.0, true, 5.0, 'Una consola versátil y compacta que sigue siendo ideal para disfrutar de un extenso catálogo de juegos con gran rendimiento y entretenimiento garantizado.', 30, 'CO002', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'CO002');

-- CONSOLAS (CO) - Mandos (MA)
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'DualShock 4', 'CO', 'MA', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mandoplay.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mandoplay.png"]', 60000.0, true, 4.0, 'Control oficial de PlayStation 4 con diseño ergonómico y funciones avanzadas que ofrecen precisión y comodidad durante tus sesiones de juego.', 100, 'CO003', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'CO003')
UNION ALL
SELECT 'Dualsense Azul', 'CO', 'MA', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mandoplayazul.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mandoplayazul.png"]', 69990.0, true, 4.0, 'Control de PlayStation 5 en elegante color azul, con retroalimentación háptica y gatillos adaptativos que llevan la experiencia de juego a otro nivel.', 80, 'CO005', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'CO005');

-- CONSOLAS (CO) - Accesorios (AC)
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'Auriculares PS4', 'CO', 'AC', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/audifonoazul.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/audifonoazul.png"]', 150000.0, false, 3.0, 'Auriculares diseñados para PlayStation 4, con sonido envolvente y micrófono integrado para comunicación clara en partidas multijugador.', 0, 'CO006', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'CO006');

-- PERIFERICOS (PE) - Auriculares (AU)
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'Auriculares Logitech', 'PE', 'AU', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/audilogitech.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/audilogitech.png"]', 100000.0, true, 4.0, 'Auriculares de alto rendimiento con gran calidad de sonido y micrófono ajustable, ideales para juegos, streaming y comunicación online.', 60, 'PE001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'PE001');

-- PERIFERICOS (PE) - Teclados (TE)
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'Teclado Redragon RGB', 'PE', 'TE', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/teclado_rd_rgb.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/teclado_rd_rgb.png"]', 145990.0, true, 4.0, 'Teclado mecánico con iluminación RGB y switches de alto rendimiento, ideal para juegos y escritura intensiva.', 40, 'PE002', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'PE002');

-- PERIFERICOS (PE) - Mouses (MO)
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'Mouse Cougar', 'PE', 'MO', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mousecougar.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/mousecougar.png"]', 85990.0, true, 4.0, 'Mouse gamer ergonómico con alta precisión y diseño personalizable, perfecto para sesiones intensas y competitivas.', 70, 'PE003', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'PE003');

-- PERIFERICOS (PE) - Monitores (MT)
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'Monitor ASUS', 'PE', 'MT', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/monitorasus.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/monitorasus.png"]', 175990.0, true, 4.0, 'Monitor de alto rendimiento con gran calidad de imagen y refresco rápido, ideal para disfrutar de gráficos nítidos en juegos y multimedia.', 25, 'PE004', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'PE004');

-- PERIFERICOS (PE) - Camaras web (CW)
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'Webcam Logitech', 'PE', 'CW', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/webcamlogitech.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/webcamlogitech.png"]', 67990.0, true, 4.0, 'Webcam de alta definición con gran calidad de imagen y audio, perfecta para streaming, videollamadas y creación de contenido.', 45, 'PE005', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'PE005');

-- PERIFERICOS (PE) - Microfonos (MI)
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'Microfono Logitech', 'PE', 'MI', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/micrologitech.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/micrologitech.png"]', 86990.0, true, 4.0, 'Micrófono de alta fidelidad con captación clara y profesional, ideal para streaming, grabaciones y comunicación en equipo.', 35, 'PE006', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'PE006');

-- ROPA (RO) - Polerones Gamers Personalizados (PG)
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'Poleron StarCraft', 'RO', 'PG', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronstarcraf.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronstarcraf.png"]', 40000.0, true, 4.5, 'Polerón inspirado en StarCraft con diseño gamer único y tela cómoda, perfecto para mostrar tu pasión por el universo de Blizzard.', 200, 'RO001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'RO001')
UNION ALL
SELECT 'Poleron Super Papá Gamer', 'RO', 'PG', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronpapa.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronpapa.png"]', 40000.0, true, 4.5, 'Polerón divertido y cómodo diseñado para los papás que disfrutan tanto del gaming como de la familia.', 200, 'RO002', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'RO002')
UNION ALL
SELECT 'Poleron Hollow Knight', 'RO', 'PG', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronthekin.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronthekin.png"]', 40000.0, true, 4.5, 'Polerón con diseño inspirado en Hollow Knight, ideal para fanáticos del juego indie y su mundo misterioso.', 200, 'RO003', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'RO003')
UNION ALL
SELECT 'Poleron PlayStation Retro Negro', 'RO', 'PG', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronplay.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronplay.png"]', 40000.0, true, 4.5, 'Polerón retro en color negro con el clásico logo de PlayStation, perfecto para los nostálgicos del gaming.', 200, 'RO004', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'RO004')
UNION ALL
SELECT 'Poleron Stumble Guys', 'RO', 'PG', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/stumblepoleron.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/stumblepoleron.png"]', 40000.0, true, 4.5, 'Polerón colorido y divertido inspirado en Stumble Guys, ideal para gamers que disfrutan de las partidas llenas de caos y risas.', 200, 'RO005', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'RO005')
UNION ALL
SELECT 'Poleron S.T.A.R.S', 'RO', 'PG', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronstars.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/poleronstars.png"]', 40000.0, true, 4.5, 'Polerón con diseño de la unidad S.T.A.R.S de Resident Evil, pensado para los fanáticos del survival horror.', 200, 'RO006', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'RO006');

-- ENTRETENIMIENTO (EN) - Juegos de Mesa (JM)
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at)
SELECT 'Catan', 'EN', 'JM', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/catan.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/catan.png"]', 29990.0, true, 4.0, 'Un clásico juego de estrategia donde los jugadores compiten por colonizar y expandirse en la isla de Catan. Ideal para 3-4 jugadores y perfecto para noches de juego en familia o con amigos.', 80, 'EN001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'EN001')
UNION ALL
SELECT 'Carcassonne', 'EN', 'JM', 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/carcassone.png', '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/carcassone.png"]', 24990.0, true, 4.0, 'Un juego de colocación de fichas donde los jugadores construyen el paisaje alrededor de la fortaleza medieval de Carcassonne. Ideal para 2-5 jugadores y fácil de aprender.', 70, 'EN002', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE codigo_producto = 'EN002');
