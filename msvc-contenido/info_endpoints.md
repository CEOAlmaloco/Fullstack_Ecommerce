POST: /contenido/articulos -> crear nuevo artículo con validaciones de contenido y categoría

GET: /contenido/articulos -> obtener todos los artículos

GET: /contenido/articulos/publicados -> obtener artículos publicados

GET: /contenido/articulos/destacados -> obtener artículos destacados en portada

GET: /contenido/articulos/populares -> obtener artículos más leídos

GET: /contenido/articulos/recientes -> obtener artículos más recientes

GET: /contenido/articulos/categoria/{categoria} -> obtener artículos por categoría (NOTICIAS, REVIEWS, GUIAS, HARDWARE, SOFTWARE, ESPORTS, STREAMING, RETRO)

GET: /contenido/articulos/buscar?q={busqueda} -> buscar artículos por texto

GET: /contenido/articulos/gratuitos -> obtener artículos gratuitos

GET: /contenido/articulos/premium -> obtener artículos premium

GET: /contenido/articulos/autor/{autor} -> obtener artículos por autor

GET: /contenido/articulos/{id} -> obtener artículo específico por ID

GET: /contenido/articulos/titulo/{titulo} -> obtener artículo por título

POST: /contenido/articulos/{id}/vista -> incrementar contador de vistas

POST: /contenido/articulos/{id}/like -> dar like a artículo

POST: /contenido/articulos/{id}/compartir -> compartir artículo en redes sociales

PUT: /contenido/articulos/{id} -> actualizar datos de artículo

PUT: /contenido/articulos/{id}/estado -> cambiar estado (BORRADOR, REVISION, PROGRAMADO, PUBLICADO, ARCHIVADO)

DELETE: /contenido/articulos/{id} -> eliminar artículo

POST: /contenido/comentarios -> crear nuevo comentario en artículo

GET: /contenido/comentarios/articulo/{idArticulo} -> obtener comentarios de un artículo

GET: /contenido/comentarios/{idComentario}/respuestas -> obtener respuestas de un comentario

GET: /contenido/comentarios/pendientes -> obtener comentarios pendientes de moderación

GET: /contenido/comentarios/usuario/{idUsuario} -> obtener comentarios de un usuario

GET: /contenido/comentarios/{id} -> obtener comentario específico por ID

PUT: /contenido/comentarios/{id}/aprobar -> aprobar comentario para publicación

PUT: /contenido/comentarios/{id}/rechazar -> rechazar comentario

POST: /contenido/comentarios/{id}/like -> dar like a comentario

PUT: /contenido/comentarios/{id} -> actualizar comentario

DELETE: /contenido/comentarios/{id} -> eliminar comentario

se comunica con:
usuario -> para validar permisos de contenido premium y obtener información del autor
referidos -> para generar puntos LevelUp por crear contenido o comentarios destacados
notificaciones -> para enviar alertas de nuevo contenido a suscriptores