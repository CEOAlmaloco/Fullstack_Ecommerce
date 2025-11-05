# Usuarios de Prueba - Credenciales

Este documento contiene las credenciales de los usuarios de prueba creados automáticamente por el inicializador de datos.

## Usuario Principal de Prueba

**Para pruebas rápidas en frontend y backend:**

- **Correo/Email**: `aa`
- **Contraseña**: `aaaa`
- **Tipo**: Cliente
- **Estado**: Activo
- **Nivel**: Novato
- **Código de Referido**: `AA001`

---

## Otros Usuarios de Prueba

### Usuario Administrador

- **Correo/Email**: `admin@levelup.com`
- **Contraseña**: `admin123`
- **Tipo**: Administrador
- **Estado**: Activo
- **Nivel**: Gran Maestro
- **Código de Referido**: `ADMIN001`
- **Puntos LevelUp**: 1000

### Usuario Cliente 1

- **Correo/Email**: `cliente1@levelup.com`
- **Contraseña**: `cliente123`
- **Tipo**: Cliente
- **Estado**: Activo
- **Nivel**: Oro
- **Código de Referido**: `JUAN001`
- **Puntos LevelUp**: 500

### Usuario Cliente 2

- **Correo/Email**: `cliente2@levelup.com`
- **Contraseña**: `cliente123`
- **Tipo**: Cliente
- **Estado**: Activo
- **Nivel**: Plata
- **Código de Referido**: `MARIA001`
- **Puntos LevelUp**: 250
- **Referido por**: `JUAN001`

### Usuario Cliente 3

- **Correo/Email**: `cliente3@levelup.com`
- **Contraseña**: `cliente123`
- **Tipo**: Cliente
- **Estado**: Activo
- **Nivel**: Novato
- **Código de Referido**: `CARLOS001`
- **Puntos LevelUp**: 50

---

## Notas Importantes

1. **Contraseñas Hasheadas**: Todas las contraseñas se almacenan hasheadas con BCrypt en la base de datos.

2. **Usuario de Prueba Simple**: El usuario `aa` con contraseña `aaaa` está diseñado para pruebas rápidas sin necesidad de escribir correos largos.

3. **Inicialización Automática**: Estos usuarios se crean automáticamente al iniciar el microservicio `msvc-usuario` si `default.data.enabled=true` está configurado.

4. **Verificación**: Para verificar que los usuarios se crearon correctamente, usa el endpoint:
   ```
   GET http://localhost:8095/api/v1/usuarios
   ```

5. **Autenticación**: Para iniciar sesión, usa el microservicio de autenticación (`msvc-auth`) con estas credenciales.

---

## Endpoints Relacionados

### Obtener Usuario por Correo
```
GET http://localhost:8095/api/v1/usuarios/correo/{correo}
Ejemplo: GET http://localhost:8095/api/v1/usuarios/correo/aa
```

### Obtener Usuario por Código de Referido
```
GET http://localhost:8095/api/v1/usuarios/referido/{codigoReferido}
Ejemplo: GET http://localhost:8095/api/v1/usuarios/referido/AA001
```

### Verificar Disponibilidad de Correo
```
GET http://localhost:8095/api/v1/usuarios/verificar-correo/{correo}
Ejemplo: GET http://localhost:8095/api/v1/usuarios/verificar-correo/aa
```

---

## Cambiar Contraseña

Si necesitas cambiar la contraseña de un usuario de prueba, puedes:

1. **Actualizar el inicializador**: Modifica `UsuarioDataInitializer.java` y reinicia el microservicio (esto recreará los usuarios).

2. **Usar el endpoint de actualización**: 
   ```
   PUT http://localhost:8095/api/v1/usuarios/{id}
   Body: { "password": "nueva_contraseña" }
   ```

3. **Acceder directamente a la base de datos H2**: 
   - URL: `http://localhost:8095/h2-console`
   - JDBC URL: `jdbc:h2:file:./data/msvc_usuario_dev`
   - Usuario: `sa`
   - Contraseña: (vacío)

---

## Troubleshooting

### El usuario no se crea

1. Verifica que `default.data.enabled=true` esté configurado en `application-dev.properties`
2. Revisa los logs del microservicio al iniciar
3. Verifica que el correo no exista ya en la base de datos

### No puedo iniciar sesión

1. Verifica que la contraseña sea exactamente `aaaa` (sin espacios)
2. Verifica que el correo sea exactamente `aa` (sin espacios)
3. Verifica que el microservicio de autenticación esté funcionando correctamente
4. Verifica que el usuario esté en estado ACTIVO

### La contraseña no funciona

1. Verifica que estés usando BCrypt para comparar contraseñas en el backend
2. Revisa que el hash de la contraseña se haya generado correctamente
3. Si es necesario, regenera el usuario eliminándolo de la base de datos y reiniciando el microservicio

