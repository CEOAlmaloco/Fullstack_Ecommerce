# Solución: Microservicios que se Detienen

## Problemas Identificados

### 1. msvc-productos
- **Error**: Columna `imagen` es `VARCHAR(255)` pero Base64 tiene 4822 caracteres
- **Solución**: Cambiar columna a `TEXT` en el modelo `Producto.java` ✅

### 2. msvc-usuario
- **Error**: PasswordEncoder no se inyectaba correctamente
- **Solución**: Inyectar PasswordEncoder como bean ✅

## Pasos para Solucionar

### Paso 1: Solucionar msvc-productos

1. **Eliminar la base de datos** (para recrear con el tipo correcto):
   ```powershell
   cd Backend_Java_Spring\Fullstack_Ecommerce\msvc-productos
   Remove-Item -Recurse -Force data\
   ```

2. **Reiniciar el microservicio**:
   ```bash
   mvn spring-boot:run
   ```

   La tabla se creará con `imagen` como `TEXT` ✅

### Paso 2: Solucionar msvc-usuario

1. **El PasswordEncoder ya está corregido** - se inyecta como bean ✅

2. **Reiniciar el microservicio**:
   ```bash
   cd Backend_Java_Spring\Fullstack_Ecommerce/msvc-usuario
   mvn spring-boot:run
   ```

## Verificación

### Verificar msvc-productos

1. **Revisar logs** - Deberías ver:
   ```
   Imagen principal convertida a Base64: ./img/consolas/4.png -> Base64
   Producto actualizado con imágenes Base64: CO001 - PlayStation 5
   Conversión de imágenes completada. Convertidos: 19, Ya convertidos: 0
   ```

2. **Probar en Postman**:
   ```
   GET http://localhost:8003/api/v1/productos
   Headers:
     X-API-Key: levelup-2024-secret-api-key-change-in-production
   ```

3. **Verificar que las imágenes sean Base64**:
   ```json
   {
       "imagen": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...",
       "imagenes": "[\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...\", ...]"
   }
   ```

### Verificar msvc-usuario

1. **Revisar logs** - Deberías ver:
   ```
   Iniciando carga de datos por defecto para usuarios
   Usuario administrador creado: admin@levelup.com
   Usuario cliente creado: cliente1@levelup.com
   Usuario de prueba completo creado: aa / aaaa - ID: 5
   Carga de datos por defecto de usuarios completada
   ```

2. **Probar en Postman**:
   ```
   GET http://localhost:8095/api/v1/usuarios
   Headers:
     X-API-Key: levelup-2024-secret-api-key-change-in-production
   ```

## Correcciones Aplicadas

### msvc-productos
- ✅ `Producto.java` - Cambiado `@Column` a `@Column(columnDefinition = "TEXT")` para `imagen`
- ✅ `ProductoDataInitializerFromSQL.java` - Usa `ImagePathMapper` para mapear rutas
- ✅ `ImagePathMapper.java` - Servicio para mapear rutas incorrectas a correctas

### msvc-usuario
- ✅ `UsuarioDataInitializer.java` - Inyecta `PasswordEncoder` como bean
- ✅ Removidos imports innecesarios

## Troubleshooting

### Si msvc-productos sigue fallando

1. **Verificar que la base de datos se haya eliminado**:
   ```powershell
   Get-ChildItem -Path data\ -ErrorAction SilentlyContinue
   ```

2. **Si no se eliminó, eliminarla manualmente**:
   ```powershell
   Remove-Item -Path data\msvc_productos_dev.mv.db -Force
   Remove-Item -Path data\msvc_productos_dev.trace.db -Force -ErrorAction SilentlyContinue
   ```

3. **Reiniciar el microservicio**

### Si msvc-usuario sigue fallando

1. **Verificar que el PasswordEncoder esté configurado**:
   ```bash
   # Verificar que SecurityConfig.java tenga el bean PasswordEncoder
   ```

2. **Revisar los logs completos** con `-e`:
   ```bash
   mvn spring-boot:run -e
   ```

3. **Verificar que no haya conflictos de puertos**:
   - msvc-productos: puerto 8003
   - msvc-usuario: puerto 8095

## Puertos de los Microservicios

- **msvc-productos**: `http://localhost:8003/api/v1`
- **msvc-usuario**: `http://localhost:8095/api/v1`

## Notas Importantes

1. **Base de datos H2**: Los archivos `.mv.db` se guardan en `./data/` de cada microservicio
2. **Reiniciar después de cambios**: Siempre reinicia los microservicios después de cambios en entidades
3. **Logs**: Usa `-e` o `-X` para ver errores completos si hay problemas

