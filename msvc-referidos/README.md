# MSVC-REFERIDOS - Sistema de Gamificación Level-Up Gamer

## Descripción

Microservicio encargado del sistema de referidos y gamificación de Level-Up Gamer. Permite a los usuarios referir amigos
y ganar puntos LevelUp que pueden canjear por productos y descuentos.

## Funcionalidades Principales

### Sistema de Referidos

- **Registro con código de referido**: Los usuarios pueden agregar un código de referido al registrarse
- **Generación de códigos únicos**: Cada usuario recibe un código de referido único
- **Trazabilidad de referencias**: Seguimiento completo de quién refirió a quién
- **Validación de códigos**: Verificación de códigos de referido válidos

### Sistema de Gamificación

- **Puntos LevelUp**: Acumulación de puntos por cada referido exitoso
- **Sistema de niveles**: Progresión basada en puntos acumulados
- **Recompensas**: Canje de puntos por productos y descuentos
- **Historial de puntos**: Seguimiento de ganancias y canjes

### Gestión de Usuarios Referidos

- **Creación de referidos**: Registro de nuevos usuarios a través de códigos
- **Actualización de perfiles**: Modificación de información de usuarios referidos
- **Consulta de referidos**: Listado y búsqueda de usuarios referidos por usuario
- **Estadísticas**: Métricas de referidos por usuario

## Tecnologías Utilizadas

- **Java 21**: Lenguaje de programación
- **Spring Boot**: Framework principal
- **Spring Data JPA**: Persistencia de datos
- **Spring Web**: API REST
- **Spring Validation**: Validación de datos
- **H2 Database**: Base de datos en memoria para desarrollo
- **Lombok**: Reducción de código boilerplate
- **OpenAPI/Swagger**: Documentación de API

## Estructura del Proyecto

```
src/main/java/com/ampuero/msvc/referidos/
├── controllers/          # Controladores REST
├── dtos/                # Data Transfer Objects
├── entities/            # Entidades JPA
├── repositories/        # Repositorios de datos
├── services/           # Lógica de negocio
├── exceptions/         # Manejo de excepciones
└── ReferidosApplication.java
```

## Base de Datos

### Tabla: referidos

- `id`: Identificador único
- `nombre`: Nombre del usuario referido
- `apellidos`: Apellidos del usuario
- `email`: Correo electrónico único
- `run`: RUN chileno único
- `codigo_referido`: Código único para referir otros usuarios
- `puntos_levelup`: Puntos acumulados por referidos
- `nivel`: Nivel actual del usuario
- `fecha_registro`: Fecha de creación del registro
- `activo`: Estado del usuario (activo/inactivo)

## Endpoints de la API

### Referidos

- `POST /api/referidos` - Crear nuevo referido
- `GET /api/referidos` - Listar todos los referidos
- `GET /api/referidos/{id}` - Obtener referido por ID
- `PUT /api/referidos/{id}` - Actualizar referido
- `DELETE /api/referidos/{id}` - Eliminar referido

### Códigos de Referido

- `GET /api/referidos/codigo/{codigo}` - Validar código de referido
- `GET /api/referidos/usuario/{usuarioId}/codigo` - Obtener código de usuario

### Puntos LevelUp

- `GET /api/referidos/{id}/puntos` - Obtener puntos de usuario
- `POST /api/referidos/{id}/puntos/sumar` - Sumar puntos por referido
- `POST /api/referidos/{id}/puntos/canjear` - Canjear puntos por recompensa

## Reglas de Negocio

### Sistema de Referidos

- Cada usuario puede referir múltiples personas
- Un usuario solo puede ser referido por una persona
- Los códigos de referido son únicos y no reutilizables
- Validación de RUN chileno con dígito verificador

### Sistema de Puntos

- Por cada referido exitoso: +10 puntos LevelUp
- Los puntos no expiran
- Mínimo de puntos para canje: 50 puntos
- Los canjes se registran en el historial

### Niveles de Usuario

- **Bronze** (0-99 puntos): Usuario básico
- **Silver** (100-299 puntos): Usuario frecuente
- **Gold** (300-599 puntos): Usuario premium
- **Platinum** (600+ puntos): Usuario VIP

## Configuración

### Variables de Entorno

```properties
# Base de datos
spring.datasource.url=jdbc:h2:file:./data/msvc_referidos_dev
spring.datasource.username=sa
spring.datasource.password=sa

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

## Ejecución

### Desarrollo

```bash
./mvnw spring-boot:run
```

### Compilación

```bash
./mvnw clean compile
```

### Tests

```bash
./mvnw test
```

## Documentación API

Una vez iniciada la aplicación, la documentación Swagger estará disponible en:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## Integración con Otros Microservicios

- **msvc-auth**: Autenticación y autorización de usuarios
- **msvc-usuario**: Gestión de perfiles de usuario
- **msvc-productos**: Catálogo de productos para canjes
- **msvc-promociones**: Aplicación de descuentos por puntos

## Casos de Uso

### Registro con Referido

1. Usuario ingresa código de referido válido
2. Sistema valida el código
3. Se crea el nuevo usuario
4. Se otorgan puntos al usuario que refirió
5. Se actualiza el nivel del referidor

### Canje de Puntos

1. Usuario consulta sus puntos disponibles
2. Selecciona producto/recompensa
3. Sistema valida puntos suficientes
4. Se descuentan los puntos
5. Se registra el canje en historial

## Consideraciones de Seguridad

- Validación de entrada en todos los endpoints
- Sanitización de datos de usuario
- Validación de permisos por rol
- Logging de operaciones críticas
- Rate limiting para prevenir abuso

## Monitoreo y Logs

- Logs estructurados con niveles configurable
- Métricas de performance de endpoints
- Alertas por errores de validación
- Seguimiento de operaciones de puntos