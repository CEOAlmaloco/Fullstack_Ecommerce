# Microservicio de Inventario - Level-Up Gamer

## Descripción

Microservicio especializado en el control y gestión de inventario de productos. Maneja el stock disponible, alertas de
stock crítico, reservas temporales y la sincronización con el catálogo de productos.

## Funcionalidad Principal

- Control de stock en tiempo real
- Gestión de inventario por producto
- Alertas de stock crítico
- Reservas temporales para carrito
- Historial de movimientos de stock
- Reposición automática de productos
- Integración con proveedores

## Endpoints API

### Gestión de Stock

- **GET** `/inventario/{productoId}` - Consultar stock de producto específico
- **PUT** `/inventario/{productoId}/stock` - Actualizar cantidad de stock
- **POST** `/inventario/{productoId}/entrada` - Registrar entrada de mercancía
- **POST** `/inventario/{productoId}/salida` - Registrar salida de productos
- **GET** `/inventario/productos` - Stock de todos los productos

### Reservas Temporales

- **POST** `/inventario/{productoId}/reservar` - Reservar stock para carrito
- **DELETE** `/inventario/{productoId}/liberar` - Liberar reserva de stock
- **GET** `/inventario/{productoId}/disponible` - Stock disponible (sin reservas)
- **PUT** `/inventario/reservas/expirar` - Expirar reservas vencidas

### Alertas y Monitoreo

- **GET** `/inventario/stock-critico` - Productos con stock bajo
- **GET** `/inventario/agotados` - Productos sin stock
- **POST** `/inventario/{productoId}/stock-critico` - Configurar alerta crítica
- **GET** `/inventario/movimientos/{productoId}` - Historial de movimientos

### Reportes

- **GET** `/inventario/reporte-stock` - Reporte general de inventario
- **GET** `/inventario/productos-populares` - Productos con mayor rotación
- **GET** `/inventario/reposicion-sugerida` - Sugerencias de reposición

## Comunicación con otros Microservicios

### Envía datos a:

- **msvc-productos**: Estado de disponibilidad para mostrar en catálogo
- **msvc-carrito**: Validación de stock disponible
- **msvc-pedido**: Confirmación de stock para procesar pedido
- **Sistema de Alertas**: Notificaciones de stock crítico

### Recibe datos de:

- **msvc-productos**: Información de nuevos productos para crear inventario
- **msvc-carrito**: Solicitudes de reserva temporal de stock
- **msvc-pedido**: Reducción de stock por pedidos confirmados
- **Sistema de Proveedores**: Entradas de mercancía

## Tipos de Movimientos de Stock

1. **ENTRADA_COMPRA** - Mercancía recibida de proveedores
2. **SALIDA_VENTA** - Productos vendidos a clientes
3. **AJUSTE_INVENTARIO** - Correcciones manuales
4. **RESERVA_CARRITO** - Stock reservado temporalmente
5. **LIBERACION_RESERVA** - Stock liberado de reserva
6. **DEVOLUCION_CLIENTE** - Productos devueltos
7. **MERMA** - Productos dañados o perdidos

## Reglas de Negocio Stock Crítico

- **Juegos de Mesa**: Stock crítico < 5 unidades
- **Consolas**: Stock crítico < 2 unidades
- **Computadores**: Stock crítico < 1 unidad
- **Accesorios**: Stock crítico < 10 unidades
- **Ropa Gamer**: Stock crítico < 15 unidades
- **Periféricos**: Stock crítico < 8 unidades

## Funcionalidades Especiales

- **Reservas Inteligentes**: Reserva automática al agregar al carrito
- **Expiración de Reservas**: Liberación automática después de 30 minutos
- **Alertas Proactivas**: Notificaciones antes de agotarse
- **Reposición Automática**: Sugerencias basadas en histórico
- **Sincronización**: Actualización en tiempo real con otros servicios

## Validaciones de Inventario

- Stock no puede ser negativo
- Reservas no pueden exceder stock disponible
- Movimientos deben tener justificación
- Stock crítico debe estar configurado
- Entradas requieren documentación de respaldo

## Tecnologías

- Spring Boot 3.4.5
- Spring Data JPA
- H2 Database
- Redis (cache de reservas)
- OpenFeign
- Swagger/OpenAPI
- Scheduled Tasks (limpieza de reservas)
- Event-driven architecture

## Puerto

- Desarrollo: 8086
- Producción: Configurable via environment
