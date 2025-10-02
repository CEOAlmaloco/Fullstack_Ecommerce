# Microservicio de Promociones - Level-Up Gamer

## Descripción

Microservicio especializado en la gestión de promociones, descuentos, cupones y ofertas especiales para Level-Up Gamer.
Maneja campañas de marketing, códigos promocionales y la aplicación automática de descuentos según reglas de negocio.

## Funcionalidad Principal

- Gestión de cupones y códigos promocionales
- Ofertas por temporada (Black Friday, Cyber Monday)
- Descuentos por categorías de productos
- Promociones por nivel de usuario LevelUp
- Campañas de marketing automatizadas
- Validación de elegibilidad para promociones
- Historial de uso de cupones

## Endpoints API

### Gestión de Promociones

- **POST** `/promociones` - Crear nueva promoción (admin)
- **GET** `/promociones` - Listar promociones activas
- **GET** `/promociones/{id}` - Obtener detalle de promoción específica
- **PUT** `/promociones/{id}` - Actualizar promoción existente
- **DELETE** `/promociones/{id}` - Desactivar promoción

### Cupones y Códigos

- **POST** `/promociones/cupones` - Generar nuevos cupones
- **GET** `/promociones/cupones/{codigo}/validar` - Validar código de cupón
- **POST** `/promociones/cupones/{codigo}/usar` - Aplicar cupón a pedido
- **GET** `/promociones/cupones/usuario/{userId}` - Cupones disponibles para usuario
- **GET** `/promociones/cupones/{codigo}/usos` - Historial de uso del cupón

### Ofertas Especiales

- **GET** `/promociones/ofertas-del-dia` - Ofertas diarias activas
- **GET** `/promociones/flash-sales` - Ofertas flash limitadas
- **GET** `/promociones/categoria/{categoria}` - Promociones por categoría
- **GET** `/promociones/usuario/{userId}/personalizadas` - Ofertas personalizadas

### Campañas de Marketing

- **POST** `/promociones/campanas` - Crear campaña de marketing
- **GET** `/promociones/campanas/activas` - Campañas en curso
- **PUT** `/promociones/campanas/{id}/activar` - Activar campaña
- **PUT** `/promociones/campanas/{id}/pausar` - Pausar campaña
- **GET** `/promociones/campanas/{id}/estadisticas` - Métricas de campaña

## Comunicación con otros Microservicios

### Envía datos a:

- **msvc-carrito**: Descuentos aplicables al carrito actual
- **msvc-pedido**: Promociones aplicadas en el pedido final
- **msvc-notificaciones**: Alertas de nuevas promociones a usuarios
- **msvc-usuario**: Ofertas personalizadas según perfil

### Recibe datos de:

- **msvc-usuario**: Nivel LevelUp y historial para personalización
- **msvc-productos**: Información de productos para ofertas específicas
- **msvc-pedido**: Historial de compras para targeting
- **msvc-auth**: Validación de permisos administrativos

## Tipos de Promociones

### Descuentos Porcentuales

- **Descuento Fijo**: 10%, 20%, 30% sobre precio original
- **Descuento Escalonado**: Mayor descuento por mayor compra
- **Descuento por Categoría**: Específico para gaming, hardware, etc.

### Descuentos en Monto

- **Monto Fijo**: $5.000, $10.000 de descuento
- **Envío Gratis**: Eliminación del costo de despacho
- **2x1 o 3x2**: Ofertas de cantidad

### Promociones Especiales

- **Primera Compra**: Descuento para nuevos usuarios
- **Cumpleaños**: Oferta especial en mes de cumpleaños
- **Referidos**: Descuento por referir amigos
- **Nivel LevelUp**: Descuentos según nivel de gamificación

### Ofertas Temporales

- **Flash Sales**: Ofertas de pocas horas
- **Ofertas Diarias**: Cambio diario de productos
- **Fin de Semana**: Promociones de viernes a domingo
- **Temporada**: Black Friday, Cyber Monday, Navidad

## Estados de Promoción

1. **BORRADOR** - En creación, no visible
2. **PROGRAMADA** - Programada para activarse
3. **ACTIVA** - Disponible para usuarios
4. **PAUSADA** - Temporalmente deshabilitada
5. **EXPIRADA** - Fecha de vencimiento alcanzada
6. **AGOTADA** - Límite de usos alcanzado
7. **CANCELADA** - Cancelada manualmente

## Reglas de Validación

### Elegibilidad de Usuario

- **Nivel Mínimo**: Algunos descuentos requieren nivel LevelUp
- **Primera Compra**: Solo para usuarios nuevos
- **Historial**: Basado en compras anteriores
- **Ubicación**: Promociones por región

### Restricciones de Producto

- **Categorías Incluidas**: Solo ciertos tipos de productos
- **Productos Excluidos**: Lista negra de productos
- **Stock Mínimo**: Promoción solo si hay stock suficiente
- **Precio Mínimo**: Descuento solo sobre cierto monto

### Límites de Uso

- **Usos por Usuario**: Máximo 1 vez por usuario
- **Usos Totales**: Límite global de la promoción
- **Tiempo de Vigencia**: Fecha de inicio y fin
- **Combinabilidad**: Si se puede combinar con otros descuentos

## Funcionalidades Especiales

- **Auto-aplicación**: Descuentos automáticos según reglas
- **Códigos Únicos**: Generación de códigos únicos por usuario
- **A/B Testing**: Diferentes versiones de promociones
- **Targeting**: Ofertas personalizadas por comportamiento
- **Notificaciones Push**: Alertas de ofertas limitadas

## Algoritmo de Aplicación de Descuentos

1. **Validar Elegibilidad**: Usuario cumple requisitos
2. **Verificar Restricciones**: Productos y fechas válidas
3. **Calcular Descuento**: Aplicar fórmula correspondiente
4. **Verificar Límites**: No exceder usos máximos
5. **Aplicar Descuento**: Modificar precio final
6. **Registrar Uso**: Guardar en historial

## Métricas y Analytics

- **Tasa de Conversión**: % de usuarios que usan promoción
- **ROI de Campaña**: Retorno de inversión
- **Productos Más Promocionados**: Análisis de efectividad
- **Usuarios Más Activos**: Quiénes usan más promociones
- **Horarios Peak**: Cuándo se usan más las ofertas

## Reglas de Negocio

- **Descuento Máximo**: No más del 70% del precio original
- **Combinación**: Máximo 2 promociones por pedido
- **Duración Mínima**: Promociones mínimo 24 horas
- **Notificación**: Avisar 24h antes de expirar oferta
- **Stock**: Promociones se pausan si no hay stock
- **Fraude**: Detectar uso fraudulento de códigos

## Integración con Gamificación

- **Puntos LevelUp**: Descuentos canjeables por puntos
- **Niveles**: Ofertas exclusivas por nivel
- **Logros**: Descuentos por completar desafíos
- **Referidos**: Promociones por invitar amigos

## Tecnologías

- Spring Boot 3.4.5
- Spring Data JPA
- H2 Database
- Redis (cache de promociones activas)
- OpenFeign
- Swagger/OpenAPI
- Scheduled Tasks (activación/expiración automática)
- Event-driven architecture
- Template engine (códigos únicos)

## Puerto

- Desarrollo: 8093
- Producción: Configurable via environment