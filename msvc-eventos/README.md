# Microservicio de Eventos - Level-Up Gamer

## Descripción

Microservicio dedicado a la gestión de eventos gaming a nivel nacional. Permite a los usuarios descubrir, participar y
ganar puntos LevelUp en eventos relacionados con videojuegos, torneos, lanzamientos y actividades de la comunidad gamer
chilena.

## Funcionalidad Principal

- Gestión de eventos gaming nacionales
- Mapa interactivo de ubicaciones de eventos
- Registro de participación en eventos
- Generación de puntos LevelUp por participación
- Calendario de eventos próximos
- Integración con comunidades gaming
- Notificaciones de eventos cercanos

## Endpoints API

### Gestión de Eventos

- **POST** `/eventos` - Crear nuevo evento (admin)
- **GET** `/eventos` - Listar todos los eventos activos
- **GET** `/eventos/{id}` - Obtener detalle específico del evento
- **PUT** `/eventos/{id}` - Actualizar información del evento
- **DELETE** `/eventos/{id}` - Cancelar/eliminar evento

### Mapa y Ubicaciones

- **GET** `/eventos/mapa` - Eventos con coordenadas para mapa
- **GET** `/eventos/region/{regionId}` - Eventos por región
- **GET** `/eventos/cercanos` - Eventos cerca del usuario
- **GET** `/eventos/ciudad/{ciudad}` - Eventos por ciudad específica

### Participación de Usuarios

- **POST** `/eventos/{eventoId}/participar` - Registrarse en evento
- **DELETE** `/eventos/{eventoId}/cancelar-participacion` - Cancelar participación
- **GET** `/eventos/{eventoId}/participantes` - Lista de participantes
- **GET** `/eventos/usuario/{userId}/participaciones` - Eventos del usuario
- **PUT** `/eventos/{eventoId}/confirmar-asistencia` - Confirmar asistencia real

### Calendario y Programación

- **GET** `/eventos/calendario` - Vista de calendario mensual
- **GET** `/eventos/proximos` - Próximos eventos (7 días)
- **GET** `/eventos/este-mes` - Eventos del mes actual
- **GET** `/eventos/filtrar` - Filtrar por fecha, tipo, región

### Puntos y Recompensas

- **POST** `/eventos/{eventoId}/generar-puntos` - Asignar puntos por participación
- **GET** `/eventos/{eventoId}/puntos-disponibles` - Puntos que otorga el evento
- **GET** `/eventos/ranking-participacion` - Usuarios más activos en eventos

## Comunicación con otros Microservicios

### Envía datos a:

- **msvc-referidos**: Puntos LevelUp ganados por participación en eventos
- **msvc-notificaciones**: Recordatorios y alertas de eventos próximos
- **msvc-usuario**: Historial de participación en eventos del usuario
- **Frontend**: Datos de mapa y eventos para visualización

### Recibe datos de:

- **msvc-usuario**: Información de ubicación y preferencias del usuario
- **msvc-auth**: Validación de usuarios para registrarse en eventos
- **msvc-referidos**: Consulta de nivel de usuario para eventos exclusivos
- **Sistema Externo**: APIs de mapas y geolocalización

## Tipos de Eventos

### Torneos Gaming

- **Esports Tournaments** - Competencias oficiales
- **Local Gaming Contests** - Torneos locales
- **Online Championships** - Competencias virtuales
- **Retro Gaming** - Eventos de juegos clásicos

### Lanzamientos y Demos

- **Game Launches** - Lanzamientos de juegos
- **Beta Testing** - Pruebas de juegos nuevos
- **Hardware Demos** - Demostraciones de equipos
- **VR Experiences** - Experiencias de realidad virtual

### Comunidad y Networking

- **Gaming Meetups** - Encuentros de gamers
- **Cosplay Events** - Eventos de cosplay
- **Streaming Sessions** - Sesiones de streaming en vivo
- **Gaming Talks** - Charlas y conferencias

### Educativos

- **Gaming Workshops** - Talleres de gaming
- **Career in Gaming** - Charlas de carreras
- **Tech Tutorials** - Tutoriales técnicos
- **Game Development** - Desarrollo de juegos

## Estados de Evento

1. **PROGRAMADO** - Evento creado y programado
2. **ABIERTO** - Inscripciones abiertas
3. **LLENO** - Cupos agotados
4. **EN_CURSO** - Evento en desarrollo
5. **FINALIZADO** - Evento terminado
6. **CANCELADO** - Evento cancelado
7. **POSPUESTO** - Evento pospuesto

## Información de Eventos

### Datos Básicos

- **Título**: Nombre del evento
- **Descripción**: Detalles completos
- **Fecha y Hora**: Programación completa
- **Duración**: Tiempo estimado
- **Ubicación**: Dirección física o virtual
- **Coordenadas**: Para mapa interactivo

### Participación

- **Cupos**: Máximo de participantes
- **Requisitos**: Edad, nivel, equipos necesarios
- **Costo**: Gratuito o con entrada
- **Registro**: Fecha límite de inscripción

### Recompensas

- **Puntos LevelUp**: Por participación
- **Premios**: Para ganadores
- **Certificados**: De participación
- **Descuentos**: En productos relacionados

## Funcionalidades Especiales

- **Mapa Interactivo**: Visualización geográfica de eventos
- **Notificaciones Inteligentes**: Eventos cerca del usuario
- **Check-in QR**: Confirmación de asistencia con código QR
- **Live Updates**: Actualizaciones en tiempo real
- **Social Sharing**: Compartir eventos en redes sociales
- **Favoritos**: Marcar eventos de interés

## Reglas de Negocio

- **Puntos por Participación**: 200-500 puntos según tipo de evento
- **Bonus por Asistencia**: +100 puntos por confirmar asistencia real
- **Límite de Inscripciones**: Máximo 5 eventos simultáneos por usuario
- **Cancelación**: Hasta 24 horas antes del evento
- **Eventos Exclusivos**: Solo para usuarios nivel Pro Gamer o superior
- **Historial**: Registro permanente de participaciones

## Integración con Mapa

- **Google Maps API**: Para ubicaciones y direcciones
- **Geolocalización**: Eventos cercanos al usuario
- **Filtros Geográficos**: Por región, ciudad, distancia
- **Rutas**: Cómo llegar al evento
- **Street View**: Vista previa del lugar

## Notificaciones de Eventos

- **Recordatorio 24h**: Un día antes del evento
- **Recordatorio 2h**: Dos horas antes
- **Evento Iniciado**: Cuando comienza el evento
- **Nuevos Eventos**: En área de interés del usuario
- **Cambios**: Modificaciones de horario o lugar

## Tecnologías

- Spring Boot 3.4.5
- Spring Data JPA
- H2 Database
- Redis (cache de eventos activos)
- OpenFeign
- Swagger/OpenAPI
- Google Maps API
- QR Code generation
- Scheduled Tasks (limpieza de eventos pasados)
- WebSocket (actualizaciones en tiempo real)

## Puerto

- Desarrollo: 8091
- Producción: Configurable via environment