# Msvc-Usuario - Endpoints API

## Base URL
- **Desarrollo**: `http://localhost:8002`
- **Producción**: `https://api.levelupgamer.com/msvc-usuario`

## Endpoints Principales

### 1. Gestión de Usuarios

#### Crear Usuario
```http
POST /api/v1/usuarios
Content-Type: application/json

{
  "nombre": "Juan",
  "apellido": "Pérez",
  "correo": "juan.perez@email.com",
  "password": "password123",
  "telefono": "+1234567890",
  "fechaNacimiento": "1990-01-01",
  "genero": "MASCULINO",
  "direccion": "Calle Principal 123",
  "ciudad": "Madrid",
  "pais": "España",
  "codigoPostal": "28001",
  "tipoUsuario": "CLIENTE",
  "referidoPor": "REF123456",
  "aceptaTerminos": true,
  "aceptaMarketing": false
}
```

#### Obtener Usuario por ID
```http
GET /api/v1/usuarios/{id}
```

#### Obtener Usuario por Correo
```http
GET /api/v1/usuarios/correo/{correo}
```

#### Obtener Usuario por Código de Referido
```http
GET /api/v1/usuarios/referido/{codigoReferido}
```

#### Actualizar Usuario
```http
PUT /api/v1/usuarios/{id}
Content-Type: application/json

{
  "nombre": "Juan Carlos",
  "apellido": "Pérez García",
  "telefono": "+1234567891",
  "ciudad": "Barcelona",
  "aceptaMarketing": true
}
```

#### Eliminar Usuario
```http
DELETE /api/v1/usuarios/{id}
```

### 2. Consultas y Filtros

#### Listar Todos los Usuarios (Paginado)
```http
GET /api/v1/usuarios?page=0&size=20&sort=nombre,asc
```

#### Usuarios por Tipo
```http
GET /api/v1/usuarios/tipo/{tipoUsuario}?page=0&size=20
```
- Tipos: `CLIENTE`, `ADMINISTRADOR`, `MODERADOR`, `VENDEDOR`

#### Usuarios por Estado
```http
GET /api/v1/usuarios/estado/{estado}?page=0&size=20
```
- Estados: `ACTIVO`, `INACTIVO`, `SUSPENDIDO`, `BANEADO`, `PENDIENTE_VERIFICACION`

#### Usuarios por Nivel
```http
GET /api/v1/usuarios/nivel/{nivelUsuario}?page=0&size=20
```
- Niveles: `NOVATO`, `BRONCE`, `PLATA`, `ORO`, `PLATINO`, `DIAMANTE`, `MAESTRO`, `GRAN_MAESTRO`

#### Usuarios Referidos
```http
GET /api/v1/usuarios/referidos/{codigoReferido}?page=0&size=20
```

#### Buscar Usuarios
```http
GET /api/v1/usuarios/buscar?nombre=Juan&correo=juan@email.com&ciudad=Madrid&page=0&size=20
```

### 3. Gestión de Estado y Nivel

#### Actualizar Estado de Usuario
```http
PATCH /api/v1/usuarios/{id}/estado
Content-Type: application/json

"SUSPENDIDO"
```

#### Actualizar Nivel de Usuario
```http
PATCH /api/v1/usuarios/{id}/nivel
Content-Type: application/json

"PLATA"
```

#### Agregar Puntos LevelUp
```http
POST /api/v1/usuarios/{id}/puntos
Content-Type: application/json

100
```

#### Reactivar Usuario
```http
PATCH /api/v1/usuarios/{id}/reactivar
```

### 4. Verificaciones

#### Verificar Disponibilidad de Correo
```http
GET /api/v1/usuarios/verificar-correo/{correo}
```

#### Verificar Disponibilidad de Código de Referido
```http
GET /api/v1/usuarios/verificar-codigo-referido/{codigoReferido}
```

### 5. Estadísticas

#### Obtener Estadísticas de Usuarios
```http
GET /api/v1/usuarios/estadisticas
```

**Respuesta:**
```json
{
  "totalUsuarios": 1250,
  "usuariosTipoCLIENTE": 1200,
  "usuariosTipoADMINISTRADOR": 5,
  "usuariosTipoMODERADOR": 25,
  "usuariosTipoVENDEDOR": 20,
  "usuariosEstadoACTIVO": 1100,
  "usuariosEstadoINACTIVO": 100,
  "usuariosEstadoSUSPENDIDO": 30,
  "usuariosEstadoBANEADO": 20,
  "usuariosNivelNOVATO": 800,
  "usuariosNivelBRONCE": 300,
  "usuariosNivelPLATA": 100,
  "usuariosNivelORO": 40,
  "usuariosNivelPLATINO": 10
}
```

## Respuestas de Error

### Error 400 - Datos Inválidos
```json
{
  "status": 400,
  "localDate": "2025-09-29",
  "errors": {
    "correo": "El formato del correo no es válido",
    "password": "La contraseña debe tener al menos 8 caracteres"
  }
}
```

### Error 404 - Usuario No Encontrado
```json
{
  "status": 404,
  "localDate": "2025-09-29",
  "errors": {
    "error": "Usuario no encontrado con ID: 123"
  }
}
```

### Error 409 - Conflicto
```json
{
  "status": 409,
  "localDate": "2025-09-29",
  "errors": {
    "error": "Ya existe un usuario con el correo: juan@email.com"
  }
}
```

## Modelos de Datos

### UsuarioResponseDTO
```json
{
  "idUsuario": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "correo": "juan.perez@email.com",
  "telefono": "+1234567890",
  "fechaNacimiento": "1990-01-01",
  "genero": "MASCULINO",
  "direccion": "Calle Principal 123",
  "ciudad": "Madrid",
  "pais": "España",
  "codigoPostal": "28001",
  "avatarUrl": "https://example.com/avatar.jpg",
  "tipoUsuario": "CLIENTE",
  "estado": "ACTIVO",
  "fechaRegistro": "2025-09-29T10:30:00",
  "ultimoAcceso": "2025-09-29T15:45:00",
  "emailVerificado": true,
  "telefonoVerificado": false,
  "aceptaMarketing": false,
  "codigoReferido": "REF123456",
  "referidoPor": "REF789012",
  "puntosLevelUp": 250,
  "nivelUsuario": "BRONCE",
  "nombreCompleto": "Juan Pérez",
  "edad": 34,
  "nivelDescripcion": "Bronce (100-299 puntos)"
}
```

## Integraciones

### Microservicios Relacionados
- **msvc-auth** (puerto 8001): Autenticación y autorización
- **msvc-referidos** (puerto 8005): Sistema de referidos y gamificación
- **msvc-notificaciones** (puerto 8006): Notificaciones de usuario
- **msvc-inventario** (puerto 8004): Historial de inventario
- **msvc-productos** (puerto 8003): Productos recomendados y favoritos

### CORS
- Orígenes permitidos: `http://localhost:3000`, `http://localhost:5173`
- Métodos: GET, POST, PUT, PATCH, DELETE
- Headers: Content-Type, Authorization

## Autenticación
- Los endpoints requieren token JWT válido
- El token debe ser enviado en el header: `Authorization: Bearer {token}`
- Validación de token se realiza a través de msvc-auth
