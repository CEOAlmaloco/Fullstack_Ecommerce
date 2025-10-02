# Microservicio de Pedidos - Level-Up Gamer

## Descripción

Microservicio encargado de la gestión completa del proceso de pedidos y órdenes de compra. Maneja desde la creación del
pedido hasta el seguimiento del despacho, integrando todos los aspectos del proceso de venta.

## Funcionalidad Principal

- Creación y gestión de pedidos
- Procesamiento de órdenes de compra
- Seguimiento de estados de pedido
- Gestión de despachos a todo Chile
- Cálculo de totales con descuentos
- Historial de compras del usuario
- Integración con sistema de puntos LevelUp

## Endpoints API

### Gestión de Pedidos

- **POST** `/pedidos` - Crear nuevo pedido desde carrito
- **GET** `/pedidos/{id}` - Obtener detalle específico del pedido
- **GET** `/pedidos/usuario/{userId}` - Historial de pedidos del usuario
- **PUT** `/pedidos/{id}/estado` - Actualizar estado del pedido
- **GET** `/pedidos` - Listar todos los pedidos (admin/vendedor)

### Estados y Seguimiento

- **GET** `/pedidos/{id}/seguimiento` - Tracking del pedido
- **PUT** `/pedidos/{id}/confirmar` - Confirmar pedido
- **PUT** `/pedidos/{id}/procesar` - Procesar pago
- **PUT** `/pedidos/{id}/despachar` - Marcar como despachado
- **PUT** `/pedidos/{id}/entregar` - Confirmar entrega

### Reportes y Estadísticas

- **GET** `/pedidos/estadisticas` - Estadísticas de ventas
- **GET** `/pedidos/ventas-diarias` - Reporte de ventas por día
- **GET** `/pedidos/productos-populares` - Productos más vendidos

## Comunicación con otros Microservicios

### Envía datos a:

- **msvc-inventario**: Reducción de stock al confirmar pedido
- **msvc-usuario**: Asociación de pedido con usuario
- **msvc-referidos**: Generación de puntos LevelUp por compra
- **msvc-resenia**: Habilitación de reseñas post-compra

### Recibe datos de:

- **msvc-carrito**: Información completa del carrito para crear pedido
- **msvc-productos**: Detalles y precios de productos
- **msvc-usuario**: Datos del usuario y dirección de despacho
- **msvc-auth**: Validación de usuario autenticado

## Estados del Pedido

1. **CREADO** - Pedido generado desde carrito
2. **CONFIRMADO** - Usuario confirmó la compra
3. **PAGADO** - Pago procesado exitosamente
4. **PREPARANDO** - Productos siendo preparados
5. **DESPACHADO** - Pedido enviado al cliente
6. **EN_TRANSITO** - En camino al destino
7. **ENTREGADO** - Recibido por el cliente
8. **CANCELADO** - Pedido cancelado
9. **DEVUELTO** - Producto devuelto

## Funcionalidades Especiales

- **Despacho Nacional**: Envíos a todo Chile
- **Descuentos Automáticos**: Aplicación de descuentos Duoc
- **Puntos LevelUp**: Generación automática por compra
- **Seguimiento Real**: Tracking detallado del pedido
- **Notificaciones**: Alertas por cambios de estado

## Reglas de Negocio

- Pedido mínimo: Sin monto mínimo
- Despacho gratuito: Compras sobre $50.000
- Puntos LevelUp: 1 punto por cada $1.000 gastado
- Tiempo de entrega: 3-7 días hábiles
- Devoluciones: Hasta 30 días
- Garantía: Según fabricante de cada producto

## Cálculos de Pedido

- **Subtotal**: Suma de productos
- **Descuento Usuario**: 20% para @duoc.cl
- **Descuento Referidos**: Según puntos LevelUp
- **Costo Despacho**: Gratis sobre $50.000
- **Total Final**: Subtotal - descuentos + despacho

## Tecnologías

- Spring Boot 3.4.5
- Spring Data JPA
- H2 Database
- OpenFeign
- Swagger/OpenAPI
- Scheduled Tasks (seguimiento)
- Email notifications

## Puerto

- Desarrollo: 8085
- Producción: Configurable via environment
