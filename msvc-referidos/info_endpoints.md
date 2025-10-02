# MSVC-REFERIDOS - Endpoints del Sistema de Gamificación

## Endpoints Principales

### Gestión de Referidos

POST: /api/v1/referidos -> crear nuevo referido con código de referido opcional
GET: /api/v1/referidos -> obtener todos los referidos
GET: /api/v1/referidos/{id} -> obtener referido por ID
PUT: /api/v1/referidos/{id} -> actualizar datos del referido
DELETE: /api/v1/referidos/{id} -> eliminar referido

### Validación de Códigos

GET: /api/v1/referidos/codigo/{codigo} -> validar código de referido
GET: /api/v1/referidos/usuario/{usuarioId}/referidos -> obtener referidos por usuario

### Sistema de Puntos LevelUp

GET: /api/v1/referidos/{id}/puntos -> obtener puntos del usuario
POST: /api/v1/referidos/{id}/puntos/sumar -> sumar puntos por referido exitoso

### Gestión de Estado

PUT: /api/v1/referidos/estado/{id} -> activar/desactivar referido

### Canje de Productos

POST: /api/v1/referidos/{id}/canje -> canjear producto por puntos LevelUp
GET: /api/v1/referidos/productos/canjeables -> obtener productos canjeables por puntos
GET: /api/v1/referidos/{id}/descuentos -> obtener descuentos disponibles por usuario

## Comunicación con Otros Microservicios

### msvc-auth (Puerto 8001)

- Validación de usuarios existentes
- Creación de usuarios en sistema de autenticación
- Verificación de emails y RUNs

### msvc-usuario (Puerto 8002)

- Sincronización de perfiles de usuario
- Actualización de niveles de usuario
- Obtención de información de perfil

### msvc-productos (Puerto 8003)

- Obtención de productos canjeables
- Procesamiento de canjes por puntos
- Descuentos por nivel de usuario

### msvc-inventario (Puerto 8004)

- Verificación de stock de productos
- Reserva de productos para canjes
- Disponibilidad de inventario

### msvc-promociones (Puerto 8007)

- Promociones activas por nivel
- Descuentos disponibles por puntos
- Aplicación de promociones

### msvc-notificaciones (Puerto 8006)

- Notificaciones de referidos exitosos
- Notificaciones de puntos otorgados
- Notificaciones de ascenso de nivel
- Notificaciones de canjes realizados
