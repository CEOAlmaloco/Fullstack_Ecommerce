# Microservicio de Notificaciones - Level-Up Gamer

## Descripción

Microservicio centralizado para el envío de notificaciones multicanal. Gestiona comunicaciones por email, WhatsApp, SMS
y push notifications para mantener informados a los usuarios sobre el estado de sus pedidos, promociones y actividades
en Level-Up Gamer.

## Funcionalidad Principal

- Envío de emails transaccionales y promocionales
- Integración con WhatsApp Business API
- Notificaciones push para aplicaciones móviles
- SMS para confirmaciones críticas
- Gestión de plantillas de mensajes
- Programación de envíos diferidos
- Seguimiento de entrega y apertura

## Endpoints API

### Envío de Notificaciones

- **POST** `/notificaciones/email` - Enviar email individual
- **POST** `/notificaciones/whatsapp` - Enviar mensaje WhatsApp
- **POST** `/notificaciones/sms` - Enviar SMS
- **POST** `/notificaciones/push` - Enviar notificación push
- **POST** `/notificaciones/masivo` - Envío masivo multicanal

### Gestión de Plantillas

- **GET** `/notificaciones/plantillas` - Listar plantillas disponibles
- **GET** `/notificaciones/plantillas/{tipo}` - Plantillas por tipo
- **POST** `/notificaciones/plantillas` - Crear nueva plantilla
- **PUT** `/notificaciones/plantillas/{id}` - Actualizar plantilla
- **DELETE** `/notificaciones/plantillas/{id}` - Eliminar plantilla

### Programación y Cola

- **POST** `/notificaciones/programar` - Programar envío diferido
- **GET** `/notificaciones/cola` - Ver cola de envíos pendientes
- **PUT** `/notificaciones/{id}/cancelar` - Cancelar envío programado
- **GET** `/notificaciones/programadas` - Listar envíos programados

### Seguimiento y Estadísticas

- **GET** `/notificaciones/{id}/estado` - Estado de notificación específica
- **GET** `/notificaciones/usuario/{userId}/historial` - Historial por usuario
- **GET** `/notificaciones/estadisticas` - Métricas de entrega
- **GET** `/notificaciones/reportes` - Reportes de efectividad

## Comunicación con otros Microservicios

### Recibe datos de:

- **msvc-auth**: Notificaciones de registro y login
- **msvc-usuario**: Cambios de perfil y preferencias
- **msvc-pedido**: Estados de pedidos y tracking
- **msvc-pagos**: Confirmaciones y fallos de pago
- **msvc-inventario**: Alertas de stock crítico
- **msvc-referidos**: Nuevos puntos y niveles
- **msvc-eventos**: Recordatorios de eventos
- **msvc-soporte**: Respuestas de tickets

### Envía datos a:

- **Servicios Externos**: APIs de email, WhatsApp, SMS
- **msvc-usuario**: Confirmación de preferencias de notificación
- **Analytics**: Métricas de engagement y apertura

## Tipos de Notificaciones

### Transaccionales (Críticas)

- **Bienvenida**: Registro exitoso
- **Confirmación Pedido**: Pedido creado
- **Pago Exitoso**: Transacción confirmada
- **Despacho**: Producto enviado
- **Entrega**: Producto recibido
- **Soporte**: Respuestas de tickets

### Promocionales (Marketing)

- **Ofertas Especiales**: Descuentos y promociones
- **Productos Nuevos**: Lanzamientos
- **Eventos Gaming**: Invitaciones
- **Puntos LevelUp**: Recordatorios de canje
- **Cumpleaños**: Felicitaciones y bonos
- **Carrito Abandonado**: Recordatorios

### Operacionales (Sistema)

- **Stock Crítico**: Alertas para admin
- **Reseñas Pendientes**: Moderación
- **Reembolsos**: Procesamiento
- **Mantenimiento**: Avisos de sistema

## Canales de Comunicación

### Email

- **SMTP Configurado**: SendGrid, Amazon SES
- **Plantillas HTML**: Responsive design
- **Tracking**: Apertura y clicks
- **Listas**: Segmentación por tipo de usuario

### WhatsApp Business

- **API Oficial**: Meta Business API
- **Mensajes de Plantilla**: Pre-aprobados
- **Chat Soporte**: Redirección automática
- **Multimedia**: Imágenes y documentos

### Push Notifications

- **Firebase FCM**: Para aplicaciones móviles
- **Web Push**: Para navegadores
- **Segmentación**: Por dispositivo y usuario
- **Rich Notifications**: Con imágenes y acciones

### SMS

- **Confirmaciones**: Códigos de verificación
- **Urgentes**: Alertas críticas
- **Backup**: Cuando otros canales fallan

## Preferencias de Usuario

- **Opt-in/Opt-out**: Por tipo de notificación
- **Horarios**: Configuración de envío
- **Canales**: Preferencias por tipo de mensaje
- **Frecuencia**: Límites de envío
- **Idioma**: Español por defecto

## Plantillas Predefinidas

### Pedidos

- `PEDIDO_CONFIRMADO`: "Tu pedido #{numero} ha sido confirmado"
- `PEDIDO_DESPACHADO`: "Tu pedido está en camino"
- `PEDIDO_ENTREGADO`: "Tu pedido ha sido entregado"

### Pagos

- `PAGO_EXITOSO`: "Pago procesado exitosamente"
- `PAGO_FALLIDO`: "Error en el procesamiento del pago"

### Marketing

- `OFERTA_ESPECIAL`: "¡Oferta exclusiva para ti!"
- `PRODUCTO_NUEVO`: "Nuevo producto disponible"

## Reglas de Negocio

- Máximo 3 intentos de reenvío por notificación
- Emails promocionales: Máximo 2 por semana
- WhatsApp: Solo mensajes transaccionales
- Horario de envío: 8:00 - 22:00 (Chile)
- Opt-out respetado inmediatamente
- Logs de auditoría por 1 año

## Estados de Notificación

1. **CREADA** - Notificación generada
2. **EN_COLA** - En cola de envío
3. **ENVIANDO** - Procesando envío
4. **ENVIADA** - Enviada exitosamente
5. **ENTREGADA** - Confirmada por proveedor
6. **ABIERTA** - Usuario abrió el mensaje
7. **FALLIDA** - Error en envío
8. **CANCELADA** - Cancelada antes del envío

## Tecnologías

- Spring Boot 3.4.5
- Spring Integration (Message queues)
- RabbitMQ (Cola de mensajes)
- Redis (Cache de plantillas)
- H2 Database
- OpenFeign
- Swagger/OpenAPI
- Thymeleaf (Plantillas HTML)
- Scheduled Tasks

## Integraciones Externas

- **SendGrid** - Servicio de email
- **WhatsApp Business API** - Meta
- **Firebase FCM** - Push notifications
- **Twilio** - SMS backup

## Puerto

- Desarrollo: 8090
- Producción: Configurable via environment