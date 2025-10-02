# Microservicio de Carrito - Level-Up Gamer

## Descripción

Microservicio responsable de la gestión del carrito de compras en tiempo real. Maneja la adición, eliminación y
modificación de productos, cálculo de totales y preparación para el proceso de checkout.

## Funcionalidad Principal

- Gestión de carrito de compras por usuario
- Adición y eliminación de productos
- Modificación de cantidades
- Cálculo automático de totales
- Aplicación de descuentos
- Validación de stock disponible
- Persistencia temporal del carrito

## Endpoints API

### Gestión del Carrito

- **POST** `/carrito/{userId}/productos` - Agregar producto al carrito
- **GET** `/carrito/{userId}` - Obtener carrito completo del usuario
- **PUT** `/carrito/{userId}/productos/{productoId}` - Modificar cantidad de producto
- **DELETE** `/carrito/{userId}/productos/{productoId}` - Eliminar producto del carrito
- **DELETE** `/carrito/{userId}` - Vaciar carrito completo

### Cálculos y Totales

- **GET** `/carrito/{userId}/total` - Obtener total del carrito
- **GET** `/carrito/{userId}/resumen` - Resumen detallado con precios
- **POST** `/carrito/{userId}/descuento` - Aplicar código de descuento
- **GET** `/carrito/{userId}/items-count` - Cantidad de items en carrito

### Validaciones

- **POST** `/carrito/{userId}/validar` - Validar disponibilidad de productos
- **GET** `/carrito/{userId}/stock-status` - Estado de stock de productos

## Comunicación con otros Microservicios

### Envía datos a:

- **msvc-productos**: Consultas de información y precios de productos
- **msvc-inventario**: Validación de stock disponible
- **msvc-pedido**: Transferencia de carrito para crear pedido
- **msvc-usuario**: Aplicación de descuentos por tipo de usuario

### Recibe datos de:

- **msvc-productos**: Información actualizada de productos
- **msvc-inventario**: Estado actual de stock
- **msvc-auth**: Validación de usuario autenticado
- **Frontend**: Operaciones de carrito desde la interfaz

## Funcionalidades Especiales

- **Descuento Duoc**: 20% automático para usuarios @duoc.cl
- **Validación de Stock**: Verificación en tiempo real
- **Carrito Persistente**: Mantiene items entre sesiones
- **Cálculo Dinámico**: Totales actualizados automáticamente
- **Productos Agotados**: Notificación y manejo de productos sin stock

## Reglas de Negocio

- Máximo 10 unidades por producto
- Validación de stock antes de agregar
- Descuentos acumulables según tipo de usuario
- Carrito expira después de 24 horas de inactividad
- Precios actualizados en tiempo real
- Notificación si producto cambia de precio

## Estados del Carrito

- **Activo**: Carrito con productos válidos
- **Pendiente**: Esperando validación de stock
- **Expirado**: Carrito inactivo por más de 24h
- **Procesando**: En proceso de conversión a pedido

## Tecnologías

- Spring Boot 3.4.5
- Spring Data JPA
- H2 Database
- Redis (cache temporal)
- OpenFeign
- Swagger/OpenAPI
- Scheduled Tasks

## Puerto

- Desarrollo: 8084
- Producción: Configurable via environment
