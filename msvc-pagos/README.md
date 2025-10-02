# Msvc-Pagos

## Descripción
Microservicio de gestión de pagos para Level-Up Gamer. Maneja las transacciones de pago, procesamiento de órdenes de compra y validación de métodos de pago.

## Funcionalidades
- Procesamiento de pagos
- Validación de métodos de pago
- Gestión de transacciones
- Integración con servicios externos de pago

## Configuración
- Puerto: 8010
- Base de datos: H2 (desarrollo)
- Perfil activo: dev

## Endpoints
- `/api/v1/pagos` - Gestión de pagos
- `/h2-console` - Consola de base de datos

## Tecnologías
- Spring Boot 3.4.5
- Spring Data JPA
- H2 Database
- OpenFeign
- Lombok