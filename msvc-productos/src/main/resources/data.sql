-- Datos iniciales para productos de Level Up Gaming
-- Base de datos normalizada con tablas separadas para categorías y subcategorías
-- Rutas de imágenes sin ./ inicial para que el backend las sirva correctamente

-- PASO 1: Insertar CATEGORÍAS
INSERT INTO categorias (id, nombre) VALUES
('CO', 'Consola'),
('PE', 'Perifericos'),
('RO', 'Ropa'),
('EN', 'Entretenimiento');

-- PASO 2: Insertar SUBCATEGORÍAS (con relación a categorías)
-- CONSOLAS (CO)
INSERT INTO subcategorias (id, nombre, categoria_id) VALUES
('HA', 'Hardware', 'CO'),
('MA', 'Mandos', 'CO'),
('AC', 'Accesorios', 'CO');

-- PERIFERICOS (PE)
INSERT INTO subcategorias (id, nombre, categoria_id) VALUES
('TE', 'Teclados', 'PE'),
('MO', 'Mouses', 'PE'),
('AU', 'Auriculares', 'PE'),
('MT', 'Monitores', 'PE'),
('MI', 'Microfonos', 'PE'),
('CW', 'Camaras web', 'PE'),
('MP', 'Mousepad', 'PE'),
('SI', 'Sillas Gamers', 'PE');

-- ROPA (RO)
INSERT INTO subcategorias (id, nombre, categoria_id) VALUES
('PG', 'Polerones Gamers Personalizados', 'RO'),
('PR', 'Poleras Personalizadas', 'RO');

-- ENTRETENIMIENTO (EN)
INSERT INTO subcategorias (id, nombre, categoria_id) VALUES
('JM', 'Juegos de Mesa', 'EN');

-- PASO 3: Insertar PRODUCTOS (con relaciones a categorías y subcategorías)
-- CONSOLAS (CO) - Hardware (HA)
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at) VALUES
('PlayStation 5', 'CO', 'HA', 'img/play5white.png', '["img/play5white.png", "img/play5.webp"]', 549990.0, true, 1.7, 'La consola de última generación de Sony, que ofrece gráficos impresionantes y tiempos de carga ultrarrápidos para una experiencia de juego inmersiva.', 15, 'CO001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('PlayStation 4 Slim', 'CO', 'HA', 'img/play4.png', '["img/play4.png"]', 179990.0, true, 5.0, 'Una consola versátil y compacta que sigue siendo ideal para disfrutar de un extenso catálogo de juegos con gran rendimiento y entretenimiento garantizado.', 12, 'CO002', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- CONSOLAS (CO) - Mandos (MA)
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at) VALUES
('DualShock 4', 'CO', 'MA', 'img/mandoplay.png', '["img/mandoplay.png"]', 60000.0, true, 4.0, 'Control oficial de PlayStation 4 con diseño ergonómico y funciones avanzadas que ofrecen precisión y comodidad durante tus sesiones de juego.', 30, 'CO003', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Dualsense Azul', 'CO', 'MA', 'img/mandoplayazul.png', '["img/mandoplayazul.png"]', 69990.0, true, 4.0, 'Control de PlayStation 5 en elegante color azul, con retroalimentación háptica y gatillos adaptativos que llevan la experiencia de juego a otro nivel.', 25, 'CO005', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- CONSOLAS (CO) - Accesorios (AC)
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at) VALUES
('Auriculares PS4', 'CO', 'AC', 'img/audifonoazul.png', '["img/audifonoazul.png"]', 150000.0, false, 3.0, 'Auriculares diseñados para PlayStation 4, con sonido envolvente y micrófono integrado para comunicación clara en partidas multijugador.', 0, 'CO006', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- PERIFERICOS (PE) - Auriculares (AU)
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at) VALUES
('Auriculares Logitech', 'PE', 'AU', 'img/audilogitech.png', '["img/audilogitech.png"]', 100000.0, true, 4.0, 'Auriculares de alto rendimiento con gran calidad de sonido y micrófono ajustable, ideales para juegos, streaming y comunicación online.', 20, 'PE001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- PERIFERICOS (PE) - Teclados (TE)
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at) VALUES
('Teclado Redragon RGB', 'PE', 'TE', 'img/teclado_rd_rgb.png', '["img/teclado_rd_rgb.png"]', 145990.0, true, 4.0, 'Teclado mecánico con iluminación RGB y switches de alto rendimiento, ideal para juegos y escritura intensiva.', 25, 'PE002', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- PERIFERICOS (PE) - Mouses (MO)
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at) VALUES
('Mouse Cougar', 'PE', 'MO', 'img/mousecougar.png', '["img/mousecougar.png"]', 85990.0, true, 4.0, 'Mouse gamer ergonómico con alta precisión y diseño personalizable, perfecto para sesiones intensas y competitivas.', 30, 'PE003', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- PERIFERICOS (PE) - Monitores (MT)
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at) VALUES
('Monitor ASUS', 'PE', 'MT', 'img/monitorasus.png', '["img/monitorasus.png"]', 175990.0, true, 4.0, 'Monitor de alto rendimiento con gran calidad de imagen y refresco rápido, ideal para disfrutar de gráficos nítidos en juegos y multimedia.', 8, 'PE004', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- PERIFERICOS (PE) - Camaras web (CW)
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at) VALUES
('Webcam Logitech', 'PE', 'CW', 'img/webcamlogitech.png', '["img/webcamlogitech.png"]', 67990.0, true, 4.0, 'Webcam de alta definición con gran calidad de imagen y audio, perfecta para streaming, videollamadas y creación de contenido.', 18, 'PE005', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- PERIFERICOS (PE) - Microfonos (MI)
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at) VALUES
('Microfono Logitech', 'PE', 'MI', 'img/micrologitech.png', '["img/micrologitech.png"]', 86990.0, true, 4.0, 'Micrófono de alta fidelidad con captación clara y profesional, ideal para streaming, grabaciones y comunicación en equipo.', 15, 'PE006', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ROPA (RO) - Polerones Gamers Personalizados (PG)
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at) VALUES
('Poleron StarCraft', 'RO', 'PG', 'img/poleronstarcraf.png', '["img/poleronstarcraf.png"]', 40000.0, true, 4.5, 'Polerón inspirado en StarCraft con diseño gamer único y tela cómoda, perfecto para mostrar tu pasión por el universo de Blizzard.', 50, 'RO001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Poleron Super Papá Gamer', 'RO', 'PG', 'img/poleronpapa.png', '["img/poleronpapa.png"]', 40000.0, true, 4.5, 'Polerón divertido y cómodo diseñado para los papás que disfrutan tanto del gaming como de la familia.', 50, 'RO002', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Poleron Hollow Knight', 'RO', 'PG', 'img/poleronthekin.png', '["img/poleronthekin.png"]', 40000.0, true, 4.5, 'Polerón con diseño inspirado en Hollow Knight, ideal para fanáticos del juego indie y su mundo misterioso.', 50, 'RO003', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Poleron PlayStation Retro Negro', 'RO', 'PG', 'img/poleronplay.png', '["img/poleronplay.png"]', 40000.0, true, 4.5, 'Polerón retro en color negro con el clásico logo de PlayStation, perfecto para los nostálgicos del gaming.', 50, 'RO004', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Poleron Stumble Guys', 'RO', 'PG', 'img/stumblepoleron.png', '["img/stumblepoleron.png"]', 40000.0, true, 4.5, 'Polerón colorido y divertido inspirado en Stumble Guys, ideal para gamers que disfrutan de las partidas llenas de caos y risas.', 50, 'RO005', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Poleron S.T.A.R.S', 'RO', 'PG', 'img/poleronstars.png', '["img/poleronstars.png"]', 40000.0, true, 4.5, 'Polerón con diseño de la unidad S.T.A.R.S de Resident Evil, pensado para los fanáticos del survival horror.', 50, 'RO006', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ENTRETENIMIENTO (EN) - Juegos de Mesa (JM)
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, codigo_producto, created_at, updated_at) VALUES
('Catan', 'EN', 'JM', 'img/catan.png', '["img/catan.png"]', 29990.0, true, 4.0, 'Un clásico juego de estrategia donde los jugadores compiten por colonizar y expandirse en la isla de Catan. Ideal para 3-4 jugadores y perfecto para noches de juego en familia o con amigos.', 30, 'EN001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Carcassonne', 'EN', 'JM', 'img/carcassone.png', '["img/carcassone.png"]', 24990.0, true, 4.0, 'Un juego de colocación de fichas donde los jugadores construyen el paisaje alrededor de la fortaleza medieval de Carcassonne. Ideal para 2-5 jugadores y fácil de aprender.', 25, 'EN002', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
