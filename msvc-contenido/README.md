# Microservicio de Contenido - Level-Up Gamer

## Descripción

Microservicio encargado de la gestión de contenido educativo y de entretenimiento para la comunidad gamer. Maneja blogs,
noticias, guías, reviews y todo el contenido editorial que enriquece la experiencia de los usuarios en Level-Up Gamer.

## Funcionalidad Principal

- Gestión de blogs y artículos gaming
- Noticias del mundo gamer
- Guías y tutoriales para mejorar experiencia de juego
- Reviews de productos y juegos
- Contenido educativo sobre gaming
- Sistema de categorización y etiquetas
- Comentarios y interacción de usuarios

## Endpoints API

### Gestión de Contenido

- **POST** `/contenido/articulos` - Crear nuevo artículo (admin/editor)
- **GET** `/contenido/articulos` - Listar todos los artículos publicados
- **GET** `/contenido/articulos/{id}` - Obtener artículo específico
- **PUT** `/contenido/articulos/{id}` - Actualizar artículo existente
- **DELETE** `/contenido/articulos/{id}` - Eliminar artículo

### Blogs y Noticias

- **GET** `/contenido/blogs` - Listar entradas de blog
- **GET** `/contenido/noticias` - Últimas noticias gaming
- **GET** `/contenido/destacados` - Contenido destacado en portada
- **GET** `/contenido/populares` - Artículos más leídos
- **GET** `/contenido/recientes` - Contenido más reciente

### Guías y Tutoriales

- **GET** `/contenido/guias` - Guías disponibles
- **GET** `/contenido/tutoriales` - Tutoriales paso a paso
- **GET** `/contenido/tips` - Tips y consejos gaming
- **GET** `/contenido/reviews` - Reviews de productos y juegos

### Categorización y Búsqueda

- **GET** `/contenido/categorias` - Listar categorías disponibles
- **GET** `/contenido/categoria/{categoria}` - Contenido por categoría
- **GET** `/contenido/etiquetas` - Listar etiquetas (tags)
- **GET** `/contenido/buscar` - Búsqueda de contenido
- **GET** `/contenido/filtrar` - Filtros avanzados

### Interacción de Usuarios

- **POST** `/contenido/{articuloId}/comentarios` - Agregar comentario
- **GET** `/contenido/{articuloId}/comentarios` - Listar comentarios
- **PUT** `/contenido/comentarios/{id}` - Editar comentario propio
- **DELETE** `/contenido/comentarios/{id}` - Eliminar comentario
- **POST** `/contenido/{articuloId}/like` - Dar like a artículo
- **POST** `/contenido/{articuloId}/compartir` - Compartir artículo

## Comunicación con otros Microservicios

### Envía datos a:

- **msvc-notificaciones**: Alertas de nuevo contenido para suscriptores
- **msvc-referidos**: Puntos por crear contenido o comentarios destacados
- **msvc-usuario**: Historial de lectura y preferencias de contenido
- **Frontend**: Todo el contenido para mostrar en la web

### Recibe datos de:

- **msvc-usuario**: Información del autor y preferencias de lectura
- **msvc-auth**: Validación de permisos para crear/editar contenido
- **msvc-productos**: Información de productos para reviews
- **Sistema de Moderación**: Validación de comentarios

## Tipos de Contenido

### Blogs Gaming

- **Noticias de la Industria** - Últimas novedades
- **Análisis de Juegos** - Reviews detallados
- **Tendencias Gaming** - Qué está de moda
- **Eventos y Lanzamientos** - Cobertura de eventos

### Guías y Tutoriales

- **Setup Gaming** - Cómo armar tu setup perfecto
- **Mejores Prácticas** - Tips para mejorar en juegos
- **Hardware Reviews** - Análisis de componentes
- **Software Gaming** - Programas útiles para gamers

### Contenido Educativo

- **Carreras en Gaming** - Oportunidades profesionales
- **Tecnología Gaming** - Explicaciones técnicas
- **Historia del Gaming** - Evolución de los videojuegos
- **Cultura Gamer** - Aspectos sociales del gaming

### Reviews y Comparativas

- **Reviews de Productos** - Análisis detallados
- **Comparativas** - Producto A vs Producto B
- **Unboxing** - Primeras impresiones
- **Long-term Reviews** - Después de uso prolongado

## Categorías de Contenido

1. **Noticias** - Actualidad gaming
2. **Reviews** - Análisis de productos
3. **Guías** - Tutoriales y consejos
4. **Hardware** - Componentes y equipos
5. **Software** - Juegos y aplicaciones
6. **Esports** - Competencias y torneos
7. **Streaming** - Contenido para streamers
8. **Retro Gaming** - Juegos clásicos

## Estados de Contenido

1. **BORRADOR** - En creación
2. **REVISION** - Esperando aprobación
3. **PROGRAMADO** - Programado para publicar
4. **PUBLICADO** - Visible para usuarios
5. **ARCHIVADO** - No visible pero conservado
6. **ELIMINADO** - Removido del sistema

## Funcionalidades Especiales

- **Editor WYSIWYG**: Editor visual para crear contenido
- **Programación de Publicación**: Contenido programado
- **SEO Optimization**: Meta tags y optimización
- **Responsive Images**: Imágenes adaptativas
- **Social Sharing**: Botones de redes sociales
- **Reading Time**: Tiempo estimado de lectura
- **Related Content**: Contenido relacionado
- **Newsletter**: Suscripción a contenido nuevo

## Sistema de Comentarios

- **Moderación**: Aprobación manual/automática
- **Respuestas Anidadas**: Hilos de conversación
- **Votación**: Upvote/downvote en comentarios
- **Reportes**: Sistema de reportes de spam
- **Gamificación**: Puntos por comentarios útiles

## Métricas y Analytics

- **Vistas por Artículo**: Contador de visualizaciones
- **Tiempo de Lectura**: Engagement del usuario
- **Shares**: Veces compartido en redes sociales
- **Comentarios**: Nivel de interacción
- **Rating**: Calificación promedio del artículo

## Reglas de Negocio

- **Contenido Original**: Solo contenido propio o con permisos
- **Moderación**: Todos los comentarios pasan por moderación
- **Puntos LevelUp**: 25 puntos por comentario aprobado
- **Contenido Premium**: Algunos artículos solo para usuarios registrados
- **Frecuencia**: Mínimo 3 artículos nuevos por semana
- **Calidad**: Mínimo 500 palabras por artículo

## SEO y Optimización

- **Meta Tags**: Título, descripción, keywords
- **URLs Amigables**: Slug optimizado para SEO
- **Sitemap**: Generación automática
- **Schema Markup**: Datos estructurados
- **Open Graph**: Para redes sociales
- **AMP**: Páginas móviles aceleradas

## Tecnologías

- Spring Boot 3.4.5
- Spring Data JPA
- H2 Database
- Elasticsearch (búsqueda de contenido)
- Redis (cache de artículos populares)
- OpenFeign
- Swagger/OpenAPI
- Thymeleaf (renderizado de contenido)
- Image processing (thumbnails)
- Scheduled Tasks (publicación automática)

## Integraciones Externas

- **Google Analytics** - Métricas de contenido
- **Social Media APIs** - Compartir automático
- **CDN** - Distribución de imágenes
- **SEO Tools** - Optimización automática

## Puerto

- Desarrollo: 8092
- Producción: Configurable via environment