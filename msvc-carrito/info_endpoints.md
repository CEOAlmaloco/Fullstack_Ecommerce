POST: /carrito -> crear nuevo carrito para usuario con validaciones

GET: /carrito -> obtener todos los carritos

GET: /carrito/usuario/{idUsuario} -> obtener carritos de un usuario específico

GET: /carrito/usuario/{idUsuario}/activo -> obtener carrito activo de un usuario

GET: /carrito/estado/{estado} -> obtener carritos por estado (ACTIVO, CONVERTIDO, ABANDONADO, EXPIRADO, GUARDADO)

GET: /carrito/expirados -> obtener carritos expirados

GET: /carrito/fechas?fechaInicio=...&fechaFin=... -> obtener carritos por rango de fechas

GET: /carrito/promocion/{codigoPromocional} -> obtener carritos por código promocional

GET: /carrito/{id} -> obtener carrito específico por ID

POST: /carrito/{id}/limpiar -> limpiar carrito (eliminar todos los items)

POST: /carrito/{id}/promocion -> aplicar código promocional al carrito

DELETE: /carrito/{id}/promocion -> remover promoción del carrito

POST: /carrito/{id}/calcular-totales -> recalcular totales del carrito

POST: /carrito/{id}/convertir-pedido -> convertir carrito a pedido

POST: /carrito/{id}/duplicar -> duplicar carrito para otro usuario

POST: /carrito/{id}/guardar -> guardar carrito para después

POST: /carrito/{id}/recuperar -> recuperar carrito guardado

PUT: /carrito/{id} -> actualizar datos del carrito

PUT: /carrito/{id}/estado -> cambiar estado del carrito

DELETE: /carrito/{id} -> eliminar carrito

POST: /carrito/items -> agregar item al carrito

GET: /carrito/items -> obtener todos los items

GET: /carrito/items/carrito/{idCarrito} -> obtener items de un carrito específico

GET: /carrito/items/estado/{estado} -> obtener items por estado (ACTIVO, ELIMINADO, NO_DISPONIBLE)

GET: /carrito/items/producto/{idProducto} -> obtener items por producto

GET: /carrito/items/{id} -> obtener item específico por ID

PUT: /carrito/items/{id}/cantidad -> actualizar cantidad de un item

DELETE: /carrito/items/{id} -> remover item del carrito

GET: /carrito/{id}/contar-items -> contar cantidad de items en carrito

GET: /carrito/{id}/sumar-cantidad -> sumar cantidad total de productos en carrito

se comunica con:
productos -> para obtener información de productos (nombre, precio, descripción)
inventario -> para verificar disponibilidad y stock de productos
promociones -> para aplicar descuentos y validar códigos promocionales
pedido -> para convertir carrito en pedido
usuario -> para validar permisos y obtener datos del usuario