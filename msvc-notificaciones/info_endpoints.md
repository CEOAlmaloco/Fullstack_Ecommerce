POST: /notificaciones -> crear nueva notificación con validaciones de tipo y canal

GET: /notificaciones -> obtener todas las notificaciones

GET: /notificaciones/usuario/{idUsuario} -> obtener notificaciones de un usuario específico

GET: /notificaciones/estado/{estado} -> obtener notificaciones por estado (CREADA, EN_COLA, ENVIANDO, ENVIADA, ENTREGADA, ABIERTA, FALLIDA, CANCELADA)

GET: /notificaciones/tipo/{tipo} -> obtener notificaciones por tipo (EMAIL, WHATSAPP, SMS, PUSH)

GET: /notificaciones/canal/{canal} -> obtener notificaciones por canal (TRANSACCIONAL, PROMOCIONAL, OPERACIONAL)

GET: /notificaciones/destinatario/{destinatario} -> obtener notificaciones por destinatario

GET: /notificaciones/{id} -> obtener notificación específica por ID

POST: /notificaciones/{id}/enviar -> enviar notificación específica

PUT: /notificaciones/{id}/entregada -> marcar notificación como entregada

PUT: /notificaciones/{id}/abierta -> marcar notificación como abierta

POST: /notificaciones/{id}/reintentar -> reintentar envío de notificación fallida

PUT: /notificaciones/{id} -> actualizar datos de notificación

PUT: /notificaciones/{id}/estado -> cambiar estado de notificación

DELETE: /notificaciones/{id} -> eliminar notificación

POST: /notificaciones/plantillas -> crear nueva plantilla de notificación

GET: /notificaciones/plantillas -> obtener todas las plantillas

GET: /notificaciones/plantillas/tipo/{tipo} -> obtener plantillas por tipo

GET: /notificaciones/plantillas/activas -> obtener plantillas activas

GET: /notificaciones/plantillas/{id} -> obtener plantilla por ID

GET: /notificaciones/plantillas/codigo/{codigo} -> obtener plantilla por código

PUT: /notificaciones/plantillas/{id} -> actualizar plantilla

DELETE: /notificaciones/plantillas/{id} -> eliminar plantilla

POST: /notificaciones/masivo -> enviar notificación masiva a múltiples usuarios

POST: /notificaciones/masivo/plantilla -> enviar notificación masiva usando plantilla

se comunica con:
usuario -> para obtener preferencias de notificación y datos de contacto
auth -> para notificaciones de registro y login
pedido -> para notificaciones de estados de pedidos
pagos -> para confirmaciones y fallos de pago
inventario -> para alertas de stock crítico
referidos -> para notificaciones de nuevos puntos y niveles
eventos -> para recordatorios de eventos próximos
contenido -> para alertas de nuevo contenido