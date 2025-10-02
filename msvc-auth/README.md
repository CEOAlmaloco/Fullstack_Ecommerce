# Microservicio de Autenticación - Level-Up Gamer

## Descripción

Microservicio encargado de la gestión de tokens JWT y autorización de usuarios en la plataforma Level-Up Gamer. **NO
almacena contraseñas** - solo gestiona tokens de acceso y validación de sesiones.

## Funcionalidad Principal

- **Generación y gestión de tokens JWT** (ACCESS y REFRESH)
- **Validación de tokens** para autorización
- **Control de sesiones** activas por usuario
- **Revocación de tokens** (logout, cambio contraseña)
- **Auditoría de accesos** (IP, User-Agent)
- **Comunicación con msvc-usuario** para validar credenciales

## Endpoints API

### 🔐 Autenticación

- **POST** `/auth/login` - Iniciar sesión (valida con msvc-usuario y genera JWT)
- **POST** `/auth/refresh` - Renovar token ACCESS usando REFRESH
- **POST** `/auth/logout` - Cerrar sesión (revoca tokens)
- **POST** `/auth/logout-all` - Cerrar todas las sesiones del usuario

### ✅ Validación de Tokens

- **GET** `/auth/validate` - Validar token JWT actual
- **GET** `/auth/validate/{token}` - Validar token específico
- **GET** `/auth/user-info` - Obtener info del usuario desde token

### 📊 Gestión de Sesiones

- **GET** `/auth/sessions/{userId}` - Listar sesiones activas del usuario
- **DELETE** `/auth/sessions/{tokenId}` - Revocar sesión específica
- **DELETE** `/auth/sessions/user/{userId}` - Revocar todas las sesiones del usuario

## 🔄 Flujo de Autenticación

### 1️⃣ Login (Frontend → Auth → Usuario)

```
POST /auth/login
{
  "correoUsuario": "user@duoc.cl",
  "password": "password123"
}

1. msvc-auth recibe credenciales
2. Llama a msvc-usuario para validar password hasheado
3. Si válido: genera JWT ACCESS + REFRESH
4. Almacena tokens en BD con metadata (IP, User-Agent)
5. Responde con tokens al frontend
```

### 2️⃣ Acceso a Recursos (Frontend → Gateway → Auth)

```
GET /productos (con Authorization: Bearer JWT)

1. msvc-gateway intercepta petición
2. Llama a msvc-auth/validate para verificar token
3. Si válido: permite acceso al recurso
4. Si inválido: retorna 401 Unauthorized
```

### 3️⃣ Renovación de Token

```
POST /auth/refresh
{
  "refreshToken": "eyJ..."
}

1. Valida REFRESH token
2. Genera nuevo ACCESS token
3. Mantiene REFRESH token activo
```

## Comunicación con otros Microservicios

### 📤 Envía peticiones a:

- **msvc-usuario (8082)**:
    - `POST /usuarios/validate-credentials` - Validar email/password
    - `GET /usuarios/{id}` - Obtener datos del usuario para JWT payload

### 📥 Recibe peticiones de:

- **msvc-gateway (8080)**: Validación de tokens en cada request
- **Frontend (React/Angular)**: Login, logout, refresh
- **Todos los microservicios**: Validación de autorización

### 🌐 Comunicación con Frontend

#### Desde Frontend:

```javascript
// Login
POST http://localhost:8080/auth/login
{
  "correoUsuario": "user@duoc.cl", 
  "password": "password123"
}

// Refresh Token
POST http://localhost:8080/auth/refresh
{
  "refreshToken": "eyJ..."
}

// Logout
POST http://localhost:8080/auth/logout
Authorization: Bearer eyJ...

// Validar Token
GET http://localhost:8080/auth/validate
Authorization: Bearer eyJ...
```

#### Hacia Frontend:

```javascript
// Respuesta Login exitoso
{
  "accessToken": "eyJ...",
  "refreshToken": "eyJ...",
  "tokenType": "Bearer",
  "expiresIn": 1800,
  "usuario": {
    "id": 1,
    "nombre": "Juan",
    "correo": "user@duoc.cl",
    "tipoUsuario": "CLIENTE"
  }
}

// Respuesta validación
{
  "valid": true,
  "userId": 1,
  "tipoUsuario": "CLIENTE",
  "expiresAt": "2024-01-01T15:30:00"
}
```

## Reglas de Negocio

- Solo usuarios +18 años pueden registrarse
- Descuento 20% de por vida para correos @duoc.cl
- Validación de correos: @duoc.cl, @profesor.duoc.cl, @gmail.com
- Tokens JWT con expiración configurable
- Roles: Administrador (acceso total), Vendedor (productos y pedidos), Cliente (solo tienda)

## Tecnologías

- Spring Boot 3.4.5
- Spring Security
- JWT (JSON Web Tokens)
- H2 Database
- OpenFeign (comunicación entre microservicios)
- Swagger/OpenAPI

## Puerto

- Desarrollo: 8081
- Producción: Configurable via environment
