POST: /promociones -> crear nueva promoción con validaciones de negocio

GET: /promociones -> obtener todas las promociones

GET: /promociones/activas -> obtener promociones activas actualmente

GET: /promociones/duoc -> obtener promociones aplicables para usuarios Duoc (20% descuento)

GET: /promociones/categoria/{categoria} -> obtener promociones por categoría de producto

GET: /promociones/{id} -> obtener promoción específica por ID

GET: /promociones/codigo/{codigo} -> obtener promoción por código

POST: /promociones/validar -> validar si una promoción es aplicable

POST: /promociones/aplicar -> aplicar promoción a un pedido

PUT: /promociones/{id} -> actualizar datos de promoción

PUT: /promociones/{id}/estado -> activar/desactivar promoción

DELETE: /promociones/{id} -> eliminar promoción

se comunica con:
usuario -> para validar si el usuario es de Duoc y aplicar descuentos especiales
carrito -> para aplicar descuentos al carrito de compras
pedido -> para aplicar promociones en el proceso de checkout