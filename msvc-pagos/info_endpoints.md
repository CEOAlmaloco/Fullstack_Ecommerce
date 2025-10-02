POST: /pagos -> crear nuevo pago con validaciones de método y monto

GET: /pagos -> obtener todos los pagos

GET: /pagos/pedido/{idPedido} -> obtener pagos de un pedido específico

GET: /pagos/usuario/{idUsuario} -> obtener pagos de un usuario específico

GET: /pagos/estado/{estado} -> obtener pagos por estado (PENDIENTE, PROCESANDO, APROBADO, RECHAZADO, CANCELADO, REEMBOLSADO)

GET: /pagos/metodo/{metodo} -> obtener pagos por método (TARJETA_CREDITO, TARJETA_DEBITO, TRANSFERENCIA, PAYPAL, WEBPAY)

GET: /pagos/vencidos -> obtener pagos vencidos

GET: /pagos/reintentar -> obtener pagos para reintentar

GET: /pagos/fechas?fechaInicio=...&fechaFin=... -> obtener pagos por rango de fechas

GET: /pagos/{id} -> obtener pago específico por ID

GET: /pagos/transaccion/{numeroTransaccion} -> obtener pago por número de transacción

GET: /pagos/autorizacion/{codigoAutorizacion} -> obtener pago por código de autorización

POST: /pagos/{id}/procesar -> procesar pago específico

POST: /pagos/{id}/aprobar -> aprobar pago con código de autorización

POST: /pagos/{id}/rechazar -> rechazar pago con motivo

POST: /pagos/{id}/cancelar -> cancelar pago

POST: /pagos/{id}/reembolsar -> reembolsar pago con monto específico

POST: /pagos/{id}/reintentar -> reintentar pago fallido

PUT: /pagos/{id} -> actualizar datos de pago

PUT: /pagos/{id}/estado -> cambiar estado de pago

DELETE: /pagos/{id} -> eliminar pago

POST: /pagos/transacciones -> crear nueva transacción

GET: /pagos/transacciones -> obtener todas las transacciones

GET: /pagos/transacciones/pago/{idPago} -> obtener transacciones de un pago

GET: /pagos/transacciones/tipo/{tipo} -> obtener transacciones por tipo (PAGO, REEMBOLSO, REVERSO, CONSULTA)

GET: /pagos/transacciones/estado/{estado} -> obtener transacciones por estado (INICIADA, PROCESANDO, COMPLETADA, FALLIDA, CANCELADA)

GET: /pagos/transacciones/proveedor/{proveedor} -> obtener transacciones por proveedor

GET: /pagos/transacciones/fechas?fechaInicio=...&fechaFin=... -> obtener transacciones por rango de fechas

GET: /pagos/transacciones/{id} -> obtener transacción específica por ID

GET: /pagos/transacciones/externa/{numeroTransaccionExterna} -> obtener transacción por número externo

POST: /pagos/transacciones/{id}/procesar -> procesar transacción

POST: /pagos/transacciones/{id}/completar -> completar transacción con respuesta

POST: /pagos/transacciones/{id}/fallar -> marcar transacción como fallida

PUT: /pagos/transacciones/{id} -> actualizar transacción

DELETE: /pagos/transacciones/{id} -> eliminar transacción

POST: /pagos/webpay/{idPago} -> procesar pago con Webpay Plus

POST: /pagos/paypal/{idPago} -> procesar pago con PayPal

POST: /pagos/transferencia/{idPago} -> procesar pago por transferencia bancaria

GET: /pagos/reportes/total?fechaInicio=...&fechaFin=... -> obtener total de pagos por período

GET: /pagos/reportes/cantidad/{estado} -> obtener cantidad de pagos por estado

GET: /pagos/reportes/comisiones?fechaInicio=...&fechaFin=... -> obtener comisiones por período

se comunica con:
usuario -> para validar datos del usuario y límites de pago
pedido -> para actualizar estado del pedido cuando se confirma el pago
inventario -> para liberar stock cuando se confirma el pago
notificaciones -> para enviar confirmaciones de pago y fallos
referidos -> para generar puntos LevelUp por compras exitosas
promociones -> para aplicar descuentos y validar cupones
