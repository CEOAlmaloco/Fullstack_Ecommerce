POST: /eventos -> crear nuevo evento gaming con validaciones de fechas y cupos

GET: /eventos -> obtener todos los eventos

GET: /eventos/futuros -> obtener eventos futuros

GET: /eventos/en-curso -> obtener eventos en curso actualmente

GET: /eventos/tipo/{tipo} -> obtener eventos por tipo (TORNEO, LANZAMIENTO, MEETUP, WORKSHOP)

GET: /eventos/con-cupos -> obtener eventos con cupos disponibles

GET: /eventos/mapa -> obtener eventos con coordenadas para mapa interactivo

GET: /eventos/{id} -> obtener evento específico por ID

GET: /eventos/nombre/{nombre} -> obtener evento por nombre

GET: /eventos/{id}/disponibilidad -> validar disponibilidad de cupos

POST: /eventos/{id}/participar -> registrar participación de usuario en evento

DELETE: /eventos/{id}/cancelar-participacion -> cancelar participación de usuario

PUT: /eventos/{id} -> actualizar datos de evento

PUT: /eventos/{id}/estado -> activar/desactivar evento

DELETE: /eventos/{id} -> eliminar evento

se comunica con:
usuario -> para validar edad y registrar participaciones
referidos -> para generar puntos LevelUp por participación en eventos
notificaciones -> para enviar recordatorios de eventos próximos