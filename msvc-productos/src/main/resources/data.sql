-- Datos iniciales para productos de Level Up Gaming
INSERT INTO productos (titulo, categoria_id, subcategoria_id, imagen, imagenes, precio, disponible, rating, descripcion, stock, created_at, updated_at) VALUES 
('PlayStation 5', 'CO', 'HA', '/img/consolas/4.png', '["./img/consolas/4.png", "./img/consolas/2.png", "./img/consolas/3.png"]', 549990.0, true, 5.0, 'La consola de última generación de Sony, que ofrece gráficos impresionantes y tiempos de carga ultrarrápidos para una experiencia de juego inmersiva.', 15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('PlayStation 4 Slim', 'CO', 'HA', '/img/consolas/2.png', '["./img/consolas/2.png"]', 179990.0, true, 5.0, 'Una consola versátil y compacta que sigue siendo ideal para disfrutar de un extenso catálogo de juegos con gran rendimiento y entretenimiento garantizado.', 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('DualShock 4', 'CO', 'MA', '/img/consolas/3.png', '["./img/consolas/3.png"]', 60000.0, true, 4.0, 'Control oficial de PlayStation 4 con diseño ergonómico y funciones avanzadas que ofrecen precisión y comodidad durante tus sesiones de juego.', 20, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('Dualsense Azul', 'CO', 'MA', '/img/consolas/5.png', '["./img/consolas/5.png"]', 69990.0, true, 4.0, 'Control de PlayStation 5 en elegante color azul, con retroalimentación háptica y gatillos adaptativos que llevan la experiencia de juego a otro nivel.', 12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('Auriculares PS4', 'CO', 'AC', '/img/consolas/6.png', '["./img/consolas/6.png"]', 150000.0, false, 3.0, 'Auriculares diseñados para PlayStation 4, con sonido envolvente y micrófono integrado para comunicación clara en partidas multijugador.', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('Auriculares Logitech', 'PE', 'AU', '/img/perifericos/1.png', '["./img/perifericos/1.png"]', 100000.0, true, 4.0, 'Auriculares de alto rendimiento con gran calidad de sonido y micrófono ajustable, ideales para juegos, streaming y comunicación online.', 25, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('Teclado Redragon RGB', 'PE', 'TE', '/img/perifericos/2.png', '["./img/perifericos/2.png"]', 145990.0, true, 4.0, 'Teclado mecánico con iluminación RGB y switches de alto rendimiento, ideal para juegos y escritura intensiva.', 12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('Mouse Cougar', 'PE', 'MO', '/img/perifericos/3.png', '["./img/perifericos/3.png"]', 85990.0, true, 4.0, 'Mouse gamer ergonómico con alta precisión y diseño personalizable, perfecto para sesiones intensas y competitivas.', 18, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('Monitor ASUS', 'PE', 'MT', '/img/perifericos/4.png', '["./img/perifericos/4.png"]', 175990.0, true, 4.0, 'Monitor de alto rendimiento con gran calidad de imagen y refresco rápido, ideal para disfrutar de gráficos nítidos en juegos y multimedia.', 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('Webcam Logitech', 'PE', 'CW', '/img/perifericos/5.png', '["./img/perifericos/5.png"]', 67990.0, true, 4.0, 'Webcam de alta definición con gran calidad de imagen y audio, perfecta para streaming, videollamadas y creación de contenido.', 14, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('Micrófono Logitech', 'PE', 'MI', '/img/perifericos/6.png', '["./img/perifericos/6.png"]', 86990.0, true, 4.0, 'Micrófono de alta fidelidad con captación clara y profesional, ideal para streaming, grabaciones y comunicación en equipo.', 9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('Polerón StarCraft', 'RO', 'PG', '/img/polerones/1.png', '["./img/polerones/1.png"]', 40000.0, true, 4.0, 'Polerón inspirado en StarCraft con diseño gamer único y tela cómoda, perfecto para mostrar tu pasión por el universo de Blizzard.', 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('Polerón Super Papá Gamer', 'RO', 'PG', '/img/polerones/2.png', '["./img/polerones/2.png"]', 40000.0, true, 4.0, 'Polerón divertido y cómodo diseñado para los papás que disfrutan tanto del gaming como de la familia.', 25, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('Polerón Hollow Knight', 'RO', 'PG', '/img/polerones/3.png', '["./img/polerones/3.png"]', 40000.0, true, 5.0, 'Polerón con diseño inspirado en Hollow Knight, ideal para fanáticos del juego indie y su mundo misterioso.', 20, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('Polerón PlayStation Retro Negro', 'RO', 'PG', '/img/polerones/4.png', '["./img/polerones/4.png"]', 40000.0, true, 4.0, 'Polerón retro en color negro con el clásico logo de PlayStation, perfecto para los nostálgicos del gaming.', 22, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('Polerón Stumble Guys', 'RO', 'PG', '/img/polerones/5.png', '["./img/polerones/5.png"]', 40000.0, true, 4.0, 'Polerón colorido y divertido inspirado en Stumble Guys, ideal para gamers que disfrutan de las partidas llenas de caos y risas.', 28, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('Polerón S.T.A.R.S', 'RO', 'PG', '/img/polerones/6.png', '["./img/polerones/6.png"]', 40000.0, true, 5.0, 'Polerón con diseño de la unidad S.T.A.R.S de Resident Evil, pensado para los fanáticos del survival horror.', 15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('Catan', 'EN', 'JM', '/img/entretenimiento/catan.png', '["./img/entretenimiento/catan.png"]', 29990.0, true, 4.0, 'Un clásico juego de estrategia donde los jugadores compiten por colonizar y expandirse en la isla de Catan. Ideal para 3-4 jugadores y perfecto para noches de juego en familia o con amigos.', 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('Carcassonne', 'EN', 'JM', '/img/entretenimiento/carcassone.png', '["./img/entretenimiento/carcassone.png"]', 24990.0, true, 4.0, 'Un juego de colocación de fichas donde los jugadores construyen el paisaje alrededor de la fortaleza medieval de Carcassonne. Ideal para 2-5 jugadores y fácil de aprender.', 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
