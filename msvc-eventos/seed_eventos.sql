-- Script para insertar datos de ejemplo en la tabla eventos
-- Ejecutar en la base de datos: levelup_eventos
-- Este script inserta los eventos que se usaban en EventoDataInitializer

-- Nota: Los eventos NO tienen imágenes, solo coordenadas para el mapa
-- Las imágenes se dejan como NULL o vacío

-- Evento 1: Santiago Gaming Fest
INSERT INTO eventos (
    nombre_evento, descripcion_evento, fecha_inicio, fecha_fin,
    ubicacion_evento, ciudad, coordenadas_latitud, coordenadas_longitud,
    imagen, imagenes, tipo_evento, cupos_maximos, cupos_disponibles,
    costo_entrada, puntos_levelup, activo, requisitos_edad, equipos_requeridos
)
SELECT 
    'Santiago Gaming Fest',
    'El evento gaming más grande de Santiago. Compite, juega y gana premios increíbles.',
    '2025-07-12 11:00:00',
    '2025-07-12 20:00:00',
    'Centro Cultural Estación Mapocho',
    'Santiago',
    -33.4275,
    -70.6785,
    NULL,
    '[]',
    'TORNEO',
    500,
    450,
    5000.0,
    100,
    true,
    13,
    'Consola propia opcional'
WHERE NOT EXISTS (
    SELECT 1 FROM eventos WHERE nombre_evento = 'Santiago Gaming Fest'
);

-- Evento 2: Viña eSports Meetup
INSERT INTO eventos (
    nombre_evento, descripcion_evento, fecha_inicio, fecha_fin,
    ubicacion_evento, ciudad, coordenadas_latitud, coordenadas_longitud,
    imagen, imagenes, tipo_evento, cupos_maximos, cupos_disponibles,
    costo_entrada, puntos_levelup, activo, requisitos_edad, equipos_requeridos
)
SELECT 
    'Viña eSports Meetup',
    'Encuentro de esports en Viña del Mar. Competencias, networking y diversión.',
    '2025-07-26 16:00:00',
    '2025-07-26 22:00:00',
    'Quinta Vergara',
    'Viña del Mar',
    -33.0246,
    -71.5518,
    NULL,
    '[]',
    'MEETUP',
    300,
    280,
    3000.0,
    80,
    true,
    13,
    'Ninguno'
WHERE NOT EXISTS (
    SELECT 1 FROM eventos WHERE nombre_evento = 'Viña eSports Meetup'
);

-- Evento 3: Concepción Retro Game Day
INSERT INTO eventos (
    nombre_evento, descripcion_evento, fecha_inicio, fecha_fin,
    ubicacion_evento, ciudad, coordenadas_latitud, coordenadas_longitud,
    imagen, imagenes, tipo_evento, cupos_maximos, cupos_disponibles,
    costo_entrada, puntos_levelup, activo, requisitos_edad, equipos_requeridos
)
SELECT 
    'Concepción Retro Game Day',
    'Día dedicado a los juegos retro. Disfruta de clásicos y nostalgia gaming.',
    '2025-08-09 12:00:00',
    '2025-08-09 20:00:00',
    'Plaza de la Independencia',
    'Concepción',
    -36.8201,
    -73.0444,
    NULL,
    '[]',
    'MEETUP',
    200,
    180,
    2000.0,
    70,
    true,
    10,
    'Ninguno'
WHERE NOT EXISTS (
    SELECT 1 FROM eventos WHERE nombre_evento = 'Concepción Retro Game Day'
);

-- Evento 4: La Serena LAN Party
INSERT INTO eventos (
    nombre_evento, descripcion_evento, fecha_inicio, fecha_fin,
    ubicacion_evento, ciudad, coordenadas_latitud, coordenadas_longitud,
    imagen, imagenes, tipo_evento, cupos_maximos, cupos_disponibles,
    costo_entrada, puntos_levelup, activo, requisitos_edad, equipos_requeridos
)
SELECT 
    'La Serena LAN Party',
    'LAN Party en La Serena. Trae tu PC y juega con otros gamers.',
    '2025-08-23 14:00:00',
    '2025-08-23 22:00:00',
    'Mall Plaza La Serena',
    'La Serena',
    -29.9027,
    -71.2519,
    NULL,
    '[]',
    'LANZAMIENTO',
    150,
    140,
    4000.0,
    60,
    true,
    16,
    'PC propia'
WHERE NOT EXISTS (
    SELECT 1 FROM eventos WHERE nombre_evento = 'La Serena LAN Party'
);

-- Evento 5: Antofagasta Arena Gaming
INSERT INTO eventos (
    nombre_evento, descripcion_evento, fecha_inicio, fecha_fin,
    ubicacion_evento, ciudad, coordenadas_latitud, coordenadas_longitud,
    imagen, imagenes, tipo_evento, cupos_maximos, cupos_disponibles,
    costo_entrada, puntos_levelup, activo, requisitos_edad, equipos_requeridos
)
SELECT 
    'Antofagasta Arena Gaming',
    'Arena gaming en Antofagasta. Competencias profesionales y amateur.',
    '2025-09-06 15:00:00',
    '2025-09-06 23:00:00',
    'Plaza Colón',
    'Antofagasta',
    -23.6509,
    -70.3975,
    NULL,
    '[]',
    'TORNEO',
    250,
    230,
    3500.0,
    80,
    true,
    13,
    'Consola propia opcional'
WHERE NOT EXISTS (
    SELECT 1 FROM eventos WHERE nombre_evento = 'Antofagasta Arena Gaming'
);

-- Evento 6: Temuco Indie Dev Showcase
INSERT INTO eventos (
    nombre_evento, descripcion_evento, fecha_inicio, fecha_fin,
    ubicacion_evento, ciudad, coordenadas_latitud, coordenadas_longitud,
    imagen, imagenes, tipo_evento, cupos_maximos, cupos_disponibles,
    costo_entrada, puntos_levelup, activo, requisitos_edad, equipos_requeridos
)
SELECT 
    'Temuco Indie Dev Showcase',
    'Muestra de juegos independientes desarrollados en Chile.',
    '2025-09-20 10:00:00',
    '2025-09-20 18:00:00',
    'Plaza Aníbal Pinto',
    'Temuco',
    -38.7359,
    -72.5904,
    NULL,
    '[]',
    'WORKSHOP',
    100,
    90,
    2500.0,
    60,
    true,
    13,
    'Ninguno'
WHERE NOT EXISTS (
    SELECT 1 FROM eventos WHERE nombre_evento = 'Temuco Indie Dev Showcase'
);

-- Evento 7: Puerto Montt Game Night
INSERT INTO eventos (
    nombre_evento, descripcion_evento, fecha_inicio, fecha_fin,
    ubicacion_evento, ciudad, coordenadas_latitud, coordenadas_longitud,
    imagen, imagenes, tipo_evento, cupos_maximos, cupos_disponibles,
    costo_entrada, puntos_levelup, activo, requisitos_edad, equipos_requeridos
)
SELECT 
    'Puerto Montt Game Night',
    'Noche gaming en Puerto Montt. Juegos, música y diversión.',
    '2025-10-04 18:00:00',
    '2025-10-04 23:00:00',
    'Arena Puerto Montt',
    'Puerto Montt',
    -41.4718,
    -72.9366,
    NULL,
    '[]',
    'MEETUP',
    180,
    170,
    3000.0,
    90,
    true,
    13,
    'Ninguno'
WHERE NOT EXISTS (
    SELECT 1 FROM eventos WHERE nombre_evento = 'Puerto Montt Game Night'
);

-- Verificar que los eventos se insertaron correctamente
SELECT 
    id_evento,
    nombre_evento,
    ciudad,
    tipo_evento,
    fecha_inicio,
    cupos_disponibles,
    activo
FROM eventos
ORDER BY fecha_inicio;
