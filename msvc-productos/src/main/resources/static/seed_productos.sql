-- ==========================================
-- CATEGORÍAS
-- ==========================================
CREATE TABLE IF NOT EXISTS categorias (
    id VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS subcategorias (
    id VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria_id VARCHAR(10) NOT NULL REFERENCES categorias(id)
);

CREATE TABLE IF NOT EXISTS productos (
    id_producto BIGSERIAL PRIMARY KEY,
    titulo TEXT NOT NULL,
    categoria_id VARCHAR(10) NOT NULL REFERENCES categorias(id),
    subcategoria_id VARCHAR(10) NOT NULL REFERENCES subcategorias(id),
    imagen TEXT,
    imagenes TEXT,
    precio DOUBLE PRECISION NOT NULL,
    disponible BOOLEAN,
    rating DOUBLE PRECISION,
    descripcion TEXT,
    stock INTEGER,
    codigo_producto VARCHAR(50) UNIQUE,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

INSERT INTO categorias (id, nombre) VALUES
    ('CO', 'Consola'),
    ('PE', 'Periféricos'),
    ('RO', 'Ropa'),
    ('EN', 'Entretenimiento')
ON CONFLICT (id) DO UPDATE
SET nombre = EXCLUDED.nombre;

-- ==========================================
-- SUBCATEGORÍAS
-- ==========================================
INSERT INTO subcategorias (id, nombre, categoria_id) VALUES
    ('MA', 'Mandos', 'CO'),
    ('AC', 'Accesorios', 'CO'),
    ('HA', 'Hardware', 'CO'),
    ('TE', 'Teclados', 'PE'),
    ('MO', 'Mouses', 'PE'),
    ('AU', 'Auriculares', 'PE'),
    ('MT', 'Monitores', 'PE'),
    ('MI', 'Micrófonos', 'PE'),
    ('CW', 'Cámaras web', 'PE'),
    ('JM', 'Juegos de Mesa', 'EN'),
    ('PG', 'Polerones Gamers Personalizados', 'RO'),
    ('PR', 'Poleras Personalizadas', 'RO'),
    ('MP', 'Mousepad', 'PE'),
    ('SI', 'Sillas Gamers', 'PE')
ON CONFLICT (id) DO UPDATE
SET nombre = EXCLUDED.nombre,
    categoria_id = EXCLUDED.categoria_id;

-- ==========================================
-- PRODUCTOS
-- ==========================================
INSERT INTO productos (
    titulo,
    categoria_id,
    subcategoria_id,
    imagen,
    imagenes,
    precio,
    disponible,
    rating,
    descripcion,
    stock,
    codigo_producto
) VALUES
    ('PlayStation 5', 'CO', 'HA',
        'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/consolas/4.png',
        '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/consolas/4.png","https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/consolas/1.png","https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/consolas/2.png","https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/consolas/3.png"]',
        549990, TRUE, 1.7,
        'La consola de última generación de Sony, que ofrece gráficos impresionantes y tiempos de carga ultrarrápidos para una experiencia de juego inmersiva.',
        15, 'CO001'
    ),
    ('PlayStation 4 Slim', 'CO', 'HA',
        'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/consolas/2.png',
        '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/consolas/1.png","https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/consolas/3.png"]',
        179990, TRUE, 5.0,
        'Una consola versátil y compacta que sigue siendo ideal para disfrutar de un extenso catálogo de juegos con gran rendimiento y entretenimiento garantizado.',
        12, 'CO002'
    ),
    ('DualShock 4', 'CO', 'MA',
        'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/consolas/3.png',
        '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/consolas/3.png"]',
        60000, TRUE, 4.0,
        'Control oficial de PlayStation 4 con diseño ergonómico y funciones avanzadas que ofrecen precisión y comodidad durante tus sesiones de juego.',
        30, 'CO003'
    ),
    ('Dualsense Azul', 'CO', 'MA',
        'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/consolas/5.png',
        '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/consolas/5.png"]',
        69990, TRUE, 4.0,
        'Control de PlayStation 5 en elegante color azul, con retroalimentación háptica y gatillos adaptativos que llevan la experiencia de juego a otro nivel.',
        25, 'CO005'
    ),
    ('Auriculares PS4', 'CO', 'AC',
        'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/consolas/6.png',
        '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/consolas/6.png"]',
        150000, FALSE, 3.0,
        'Auriculares diseñados para PlayStation 4, con sonido envolvente y micrófono integrado para comunicación clara en partidas multijugador.',
        0, 'CO006'
    ),
    ('Auriculares Logitech', 'PE', 'AU',
        'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/perifericos/1.png',
        '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/perifericos/1.png"]',
        100000, TRUE, 4.0,
        'Auriculares de alto rendimiento con gran calidad de sonido y micrófono ajustable, ideales para juegos, streaming y comunicación online.',
        20, 'PE001'
    ),
    ('Teclado Redragon RGB', 'PE', 'TE',
        'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/perifericos/2.png',
        '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/perifericos/2.png"]',
        145990, TRUE, 4.0,
        'Teclado mecánico con iluminación RGB y switches de alto rendimiento, ideal para juegos y escritura intensiva.',
        25, 'PE002'
    ),
    ('Mouse Cougar', 'PE', 'MO',
        'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/perifericos/3.png',
        '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/perifericos/3.png"]',
        85990, TRUE, 4.0,
        'Mouse gamer ergonómico con alta precisión y diseño personalizable, perfecto para sesiones intensas y competitivas.',
        30, 'PE003'
    ),
    ('Monitor ASUS', 'PE', 'MT',
        'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/perifericos/4.png',
        '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/perifericos/4.png"]',
        175990, TRUE, 4.0,
        'Monitor gamer ASUS 144Hz con panel IPS.',
        8, 'PE004'
    ),
    ('Webcam Logitech', 'PE', 'CW',
        'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/perifericos/5.png',
        '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/perifericos/5.png"]',
        67990, TRUE, 4.0,
        'Webcam de alta definición con gran calidad de imagen y audio, perfecta para streaming, videollamadas y creación de contenido.',
        18, 'PE005'
    ),
    ('Micrófono Logitech', 'PE', 'MI',
        'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/perifericos/6.png',
        '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/perifericos/6.png"]',
        86990, TRUE, 4.0,
        'Micrófono de alta fidelidad con captación clara y profesional, ideal para streaming, grabaciones y comunicación en equipo.',
        15, 'PE006'
    ),
    ('Polerón StarCraft', 'RO', 'PG',
        'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/polerones/1.png',
        '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/polerones/1.png"]',
        40000, TRUE, 4.5,
        'Polerón inspirado en StarCraft con diseño gamer único y tela cómoda, perfecto para mostrar tu pasión por el universo de Blizzard.',
        50, 'RO001'
    ),
    ('Polerón Super Papá Gamer', 'RO', 'PG',
        'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/polerones/2.png',
        '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/polerones/2.png"]',
        40000, TRUE, 4.5,
        'Polerón divertido y cómodo diseñado para los papás que disfrutan tanto del gaming como de la familia.',
        50, 'RO002'
    ),
    ('Polerón Hollow Knight', 'RO', 'PG',
        'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/polerones/3.png',
        '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/polerones/3.png"]',
        40000, TRUE, 4.5,
        'Polerón con diseño inspirado en Hollow Knight, ideal para fanáticos del juego indie y su mundo misterioso.',
        50, 'RO003'
    ),
    ('Polerón PlayStation Retro Negro', 'RO', 'PG',
        'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/polerones/4.png',
        '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/polerones/4.png"]',
        40000, TRUE, 4.5,
        'Polerón retro en color negro con el clásico logo de PlayStation, perfecto para los nostálgicos del gaming.',
        50, 'RO004'
    ),
    ('Polerón Stumble Guys', 'RO', 'PG',
        'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/polerones/5.png',
        '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/polerones/5.png"]',
        40000, TRUE, 4.5,
        'Polerón colorido y divertido inspirado en Stumble Guys, ideal para gamers que disfrutan de las partidas llenas de caos y risas.',
        50, 'RO005'
    ),
    ('Polerón S.T.A.R.S', 'RO', 'PG',
        'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/polerones/6.png',
        '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/polerones/6.png"]',
        40000, TRUE, 4.5,
        'Polerón con diseño de la unidad S.T.A.R.S de Resident Evil, pensado para los fanáticos del survival horror.',
        50, 'RO006'
    ),
    ('Catan', 'EN', 'JM',
        'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/entretenimiento/catan.png',
        '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/entretenimiento/catan.png"]',
        29990, TRUE, 4.0,
        'Un clásico juego de estrategia donde los jugadores compiten por colonizar y expandirse en la isla de Catan. Ideal para 3-4 jugadores.',
        30, 'EN001'
    ),
    ('Carcassonne', 'EN', 'JM',
        'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/entretenimiento/carcassone.png',
        '["https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/entretenimiento/carcassone.png"]',
        24990, TRUE, 4.0,
        'Un juego de colocación de fichas donde los jugadores construyen el paisaje alrededor de la fortaleza medieval de Carcassonne.',
        25, 'EN002'
    )
ON CONFLICT (codigo_producto) DO UPDATE
SET titulo         = EXCLUDED.titulo,
    categoria_id   = EXCLUDED.categoria_id,
    subcategoria_id= EXCLUDED.subcategoria_id,
    imagen         = EXCLUDED.imagen,
    imagenes       = EXCLUDED.imagenes,
    precio         = EXCLUDED.precio,
    disponible     = EXCLUDED.disponible,
    rating         = EXCLUDED.rating,
    descripcion    = EXCLUDED.descripcion,
    stock          = EXCLUDED.stock,
    updated_at     = NOW();