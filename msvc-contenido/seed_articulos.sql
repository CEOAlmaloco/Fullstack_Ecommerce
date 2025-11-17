-- Script para insertar datos de ejemplo en la tabla articulos (blogs)
-- Ejecutar en la base de datos: levelup_contenido
-- Este script inserta los blogs que se usaban en ContenidoDataInitializer

-- Nota: Las imágenes ahora usan URLs de S3 según la categoría:
-- TECNOLOGIA o NOTICIAS → img/ralidadv.jfif
-- LANZAMIENTOS → img/juegos_esperados.jpg
-- Otros → img/evento.jpg

-- Blog 1: PlayStation 5 Pro (NOTICIAS)
INSERT INTO articulos (
    titulo_articulo, resumen_articulo, contenido_articulo, categoria_articulo,
    autor_articulo, fecha_publicacion, fecha_actualizacion, estado_articulo,
    tiempo_lectura, vistas_articulo, likes_articulo, compartidos_articulo,
    es_destacado, es_premium, activo, imagen_articulo, etiquetas_articulo
)
SELECT 
    'PlayStation 5 Pro: Todo lo que necesitas saber',
    'Sony anuncia oficialmente la PlayStation 5 Pro con mejoras significativas en rendimiento y gráficos. Descubre todas las características y fecha de lanzamiento.',
    'Sony ha anunciado oficialmente la PlayStation 5 Pro, una versión mejorada de su consola de última generación. Con mejoras significativas en rendimiento y gráficos, esta nueva consola promete llevar la experiencia gaming a otro nivel.',
    'NOTICIAS',
    'Miguel Torres',
    '2024-12-15 10:00:00',
    '2024-12-15 10:00:00',
    'PUBLICADO',
    5,
    0,
    0,
    0,
    true,
    false,
    true,
    'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/ralidadv.jfif',
    'PlayStation, Consolas, Noticias'
WHERE NOT EXISTS (
    SELECT 1 FROM articulos WHERE titulo_articulo = 'PlayStation 5 Pro: Todo lo que necesitas saber'
);

-- Blog 2: Gaming Setup 2025 (GUIAS)
INSERT INTO articulos (
    titulo_articulo, resumen_articulo, contenido_articulo, categoria_articulo,
    autor_articulo, fecha_publicacion, fecha_actualizacion, estado_articulo,
    tiempo_lectura, vistas_articulo, likes_articulo, compartidos_articulo,
    es_destacado, es_premium, activo, imagen_articulo, etiquetas_articulo
)
SELECT 
    'Guía completa para armar tu setup gaming perfecto en 2025',
    '¿Quieres crear el setup gaming definitivo? Te mostramos los componentes esenciales, periféricos recomendados y tips de configuración para que tengas la mejor experiencia de juego posible.',
    'Desde monitores hasta sillas gaming, aquí encontrarás todo lo que necesitas para crear el setup gaming perfecto.',
    'GUIAS',
    'Ana López',
    '2024-12-22 14:00:00',
    '2024-12-22 14:00:00',
    'PUBLICADO',
    10,
    0,
    0,
    0,
    true,
    false,
    true,
    'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/evento.jpg',
    'Setup, Guías, Periféricos'
WHERE NOT EXISTS (
    SELECT 1 FROM articulos WHERE titulo_articulo = 'Guía completa para armar tu setup gaming perfecto en 2025'
);

-- Blog 3: eSports Chile (ESPORTS)
INSERT INTO articulos (
    titulo_articulo, resumen_articulo, contenido_articulo, categoria_articulo,
    autor_articulo, fecha_publicacion, fecha_actualizacion, estado_articulo,
    tiempo_lectura, vistas_articulo, likes_articulo, compartidos_articulo,
    es_destacado, es_premium, activo, imagen_articulo, etiquetas_articulo
)
SELECT 
    'El crecimiento de los esports en Chile: Una industria en expansión',
    'Los deportes electrónicos han experimentado un crecimiento exponencial en Chile durante los últimos años. Desde torneos locales hasta competencias internacionales, el país se posiciona como un referente en la región.',
    'Conoce los equipos más destacados y las oportunidades que ofrece esta industria.',
    'ESPORTS',
    'Carlos Mendoza',
    '2024-12-28 16:00:00',
    '2024-12-28 16:00:00',
    'PUBLICADO',
    9,
    0,
    0,
    0,
    true,
    false,
    true,
    'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/evento.jpg',
    'eSports, Chile, Competencias'
WHERE NOT EXISTS (
    SELECT 1 FROM articulos WHERE titulo_articulo = 'El crecimiento de los esports en Chile: Una industria en expansión'
);

-- Blog 4: Gaming Movement (INDUSTRIA)
INSERT INTO articulos (
    titulo_articulo, resumen_articulo, contenido_articulo, categoria_articulo,
    autor_articulo, fecha_publicacion, fecha_actualizacion, estado_articulo,
    tiempo_lectura, vistas_articulo, likes_articulo, compartidos_articulo,
    es_destacado, es_premium, activo, imagen_articulo, etiquetas_articulo
)
SELECT 
    'Gaming Movement: La revolución de los videojuegos independientes',
    'El movimiento de desarrolladores independientes está transformando la industria gaming. Pequeños estudios crean experiencias únicas que compiten con grandes producciones.',
    'Descubre los juegos indie más prometedores del año.',
    'INDUSTRIA',
    'Sofia Ramirez',
    '2025-01-02 12:00:00',
    '2025-01-02 12:00:00',
    'PUBLICADO',
    7,
    0,
    0,
    0,
    true,
    false,
    true,
    'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/evento.jpg',
    'Indie, Desarrollo, Industria'
WHERE NOT EXISTS (
    SELECT 1 FROM articulos WHERE titulo_articulo = 'Gaming Movement: La revolución de los videojuegos independientes'
);

-- Blog 5: Realidad Virtual (TECNOLOGIA)
INSERT INTO articulos (
    titulo_articulo, resumen_articulo, contenido_articulo, categoria_articulo,
    autor_articulo, fecha_publicacion, fecha_actualizacion, estado_articulo,
    tiempo_lectura, vistas_articulo, likes_articulo, compartidos_articulo,
    es_destacado, es_premium, activo, imagen_articulo, etiquetas_articulo
)
SELECT 
    'Realidad Virtual en 2025: Guía para principiantes',
    'La realidad virtual ha llegado para quedarse. Con nuevos dispositivos más accesibles y una biblioteca de juegos en constante crecimiento, nunca ha sido mejor momento para adentrarse en el mundo VR.',
    'Explora las últimas tecnologías VR y cómo están revolucionando la forma en que experimentamos los videojuegos.',
    'TECNOLOGIA',
    'Diego Silva',
    '2025-01-08 10:00:00',
    '2025-01-08 10:00:00',
    'PUBLICADO',
    6,
    0,
    0,
    0,
    true,
    false,
    true,
    'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/ralidadv.jfif',
    'VR, Realidad Virtual, Tecnología'
WHERE NOT EXISTS (
    SELECT 1 FROM articulos WHERE titulo_articulo = 'Realidad Virtual en 2025: Guía para principiantes'
);

-- Blog 6: Juegos 2025 (LANZAMIENTOS)
INSERT INTO articulos (
    titulo_articulo, resumen_articulo, contenido_articulo, categoria_articulo,
    autor_articulo, fecha_publicacion, fecha_actualizacion, estado_articulo,
    tiempo_lectura, vistas_articulo, likes_articulo, compartidos_articulo,
    es_destacado, es_premium, activo, imagen_articulo, etiquetas_articulo
)
SELECT 
    'Los juegos más esperados del resto del 2025',
    'El año gaming está lleno de sorpresas. Desde secuelas muy esperadas hasta nuevas IPs revolucionarias, te mostramos los títulos que marcarán el resto del año.',
    'Descubre los juegos que marcarán el resto del 2025.',
    'LANZAMIENTOS',
    'Maria Gonzalez',
    '2025-01-15 15:00:00',
    '2025-01-15 15:00:00',
    'PUBLICADO',
    8,
    0,
    0,
    0,
    true,
    false,
    true,
    'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/juegos_esperados.jpg',
    'Lanzamientos, Juegos, 2025'
WHERE NOT EXISTS (
    SELECT 1 FROM articulos WHERE titulo_articulo = 'Los juegos más esperados del resto del 2025'
);

-- Verificar que los blogs se insertaron correctamente
SELECT 
    id_articulo,
    titulo_articulo,
    categoria_articulo,
    autor_articulo,
    fecha_publicacion,
    estado_articulo,
    es_destacado,
    activo
FROM articulos
ORDER BY fecha_publicacion DESC;

