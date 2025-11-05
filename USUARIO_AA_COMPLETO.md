# Usuario de Prueba Completo - "aa"

Este documento contiene toda la información del usuario de prueba "aa" que tiene todas las relaciones y datos configurados para probar todos los endpoints GET.

## Información Básica

- **Correo/Email**: `aa`
- **Contraseña**: `aaaa`
- **ID del Usuario**: **5** (después de admin, cliente1, cliente2, cliente3)
- **Nombre**: Usuario Prueba Completo
- **Apellido**: Prueba Completo
- **Teléfono**: +56911111111
- **Fecha de Nacimiento**: 1995-06-15
- **Género**: MASCULINO
- **Dirección**: Av. Prueba Completa 123
- **Región**: Región Metropolitana
- **Comuna**: Santiago
- **Ciudad**: Santiago
- **País**: Chile
- **Código Postal**: 8320000

## Credenciales de Acceso

- **Email**: `aa`
- **Contraseña**: `aaaa`
- **Tipo**: CLIENTE
- **Estado**: ACTIVO
- **Nivel**: ORO
- **Email Verificado**: Sí
- **Teléfono Verificado**: Sí
- **Acepta Términos**: Sí
- **Acepta Marketing**: Sí

## Código de Referido

- **Código de Referido**: `AA001`
- **Puntos LevelUp**: 750
- **Referido Por**: `null` (es usuario raíz, no fue referido por nadie)

## Usuarios Referidos por "aa"

El usuario "aa" tiene 2 usuarios referidos:

### Referido 1
- **Correo**: `referido1@levelup.com`
- **Contraseña**: `referido123`
- **Nombre**: Referido Uno
- **Código de Referido**: `REF001`
- **Referido Por**: `AA001` (usuario "aa")
- **Nivel**: BRONCE
- **Puntos LevelUp**: 150

### Referido 2
- **Correo**: `referido2@levelup.com`
- **Contraseña**: `referido123`
- **Nombre**: Referida Dos
- **Código de Referido**: `REF002`
- **Referido Por**: `AA001` (usuario "aa")
- **Nivel**: BRONCE
- **Puntos LevelUp**: 100

## Carrito de Compras

El usuario "aa" tiene un carrito activo con 3 items:

### Carrito Activo
- **Estado**: ACTIVO
- **Moneda**: CLP
- **Fecha de Creación**: Actual (se crea al iniciar el microservicio)
- **Fecha de Expiración**: 7 días después de la creación

### Items en el Carrito

#### Item 1: PlayStation 5
- **ID Producto**: 1
- **Nombre**: PlayStation 5
- **Precio Unitario**: $549,990
- **Cantidad**: 1
- **Subtotal**: $549,990
- **Total Item**: $549,990

#### Item 2: Teclado Redragon RGB
- **ID Producto**: 7
- **Nombre**: Teclado Redragon RGB
- **Precio Unitario**: $145,990
- **Cantidad**: 1
- **Subtotal**: $145,990
- **Total Item**: $145,990

#### Item 3: Mouse Cougar
- **ID Producto**: 8
- **Nombre**: Mouse Cougar
- **Precio Unitario**: $85,990
- **Cantidad**: 2
- **Subtotal**: $171,980
- **Total Item**: $171,980

### Total del Carrito
- **Total Carrito**: $867,960
- **Total Descuentos**: $0
- **Total Impuestos**: $0
- **Total Final**: $867,960

## Endpoints GET para Probar

### 1. Obtener Usuario por ID
```
GET http://localhost:8095/api/v1/usuarios/5
Headers:
  X-API-Key: levelup-2024-secret-api-key-change-in-production
```

### 2. Obtener Usuario por Correo
```
GET http://localhost:8095/api/v1/usuarios/correo/aa
Headers:
  X-API-Key: levelup-2024-secret-api-key-change-in-production
```

### 3. Obtener Usuario por Código de Referido
```
GET http://localhost:8095/api/v1/usuarios/referido/AA001
Headers:
  X-API-Key: levelup-2024-secret-api-key-change-in-production
```

### 4. Usuarios Referidos por Código
```
GET http://localhost:8095/api/v1/usuarios/referidos/AA001?page=0&size=20
Headers:
  X-API-Key: levelup-2024-secret-api-key-change-in-production
```

**Respuesta esperada**: Debe incluir 2 usuarios (referido1@levelup.com y referido2@levelup.com)

### 5. Obtener Carrito Activo del Usuario
```
GET http://localhost:8008/carrito/usuario/5/activo
Headers:
  X-API-Key: levelup-2024-secret-api-key-change-in-production
```

### 6. Obtener Carrito Activo (con header X-User-Id)
```
GET http://localhost:8008/carrito/activo
Headers:
  X-API-Key: levelup-2024-secret-api-key-change-in-production
  X-User-Id: 5
```

### 7. Obtener Items del Carrito
```
GET http://localhost:8008/carrito/items/carrito/{idCarrito}
Headers:
  X-API-Key: levelup-2024-secret-api-key-change-in-production
```

### 8. Obtener Referidos del Usuario
```
GET http://localhost:8089/referidos/usuario/{idUsuario}/referidos
Headers:
  X-API-Key: levelup-2024-secret-api-key-change-in-production
```

### 9. Obtener Referido por Código
```
GET http://localhost:8089/referidos/codigo/AA001
Headers:
  X-API-Key: levelup-2024-secret-api-key-change-in-production
```

### 10. Obtener Puntos del Usuario
```
GET http://localhost:8089/referidos/{id}/puntos
Headers:
  X-API-Key: levelup-2024-secret-api-key-change-in-production
```

## Notas Importantes

1. **ID del Usuario**: El ID es **5** porque se crea después de:
   - ID 1: admin@levelup.com
   - ID 2: cliente1@levelup.com
   - ID 3: cliente2@levelup.com
   - ID 4: cliente3@levelup.com
   - ID 5: aa (usuario de prueba completo)

2. **Orden de Inicialización**: 
   - Primero se crea el usuario en `msvc-usuario`
   - Luego se crea el carrito con items en `msvc-carrito`
   - Finalmente se crean los referidos en `msvc-referidos`

3. **Sincronización**: Los microservicios deben iniciarse en este orden:
   - `msvc-usuario` (primero)
   - `msvc-carrito` (segundo)
   - `msvc-referidos` (tercero)

4. **Verificación**: Para verificar que todo está correcto, ejecuta los endpoints GET listados arriba.

## Troubleshooting

### El usuario no tiene carrito
- Verifica que `msvc-carrito` se haya iniciado después de `msvc-usuario`
- Verifica que `default.data.enabled=true` en `application-dev.properties` de `msvc-carrito`
- Revisa los logs de `msvc-carrito` al iniciar

### No hay usuarios referidos
- Verifica que `msvc-referidos` se haya iniciado después de `msvc-usuario`
- Verifica que `default.data.enabled=true` en `application-dev.properties` de `msvc-referidos`
- Revisa los logs de `msvc-referidos` al iniciar

### El ID del usuario no es 5
- Si el ID es diferente, actualiza el valor en `CarritoDataInitializer.java`
- El ID se muestra en los logs al iniciar `msvc-usuario`

