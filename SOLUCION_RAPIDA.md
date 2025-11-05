# Solución Rápida: Microservicios que se Detienen

## Problemas Identificados

### ✅ msvc-productos
- **Error**: Columna `imagen` es `VARCHAR(255)` pero Base64 tiene 4822 caracteres
- **Solución aplicada**: Modelo corregido a `TEXT`
- **Acción necesaria**: Eliminar base de datos para recrearla

### ✅ msvc-usuario  
- **Error**: PasswordEncoder no se inyectaba correctamente
- **Solución aplicada**: Ahora se inyecta como bean
- **Acción necesaria**: Solo reiniciar

## Pasos Rápidos de Solución

### 1. Solucionar msvc-productos

```powershell
# Ir al directorio del microservicio
cd Backend_Java_Spring\Fullstack_Ecommerce\msvc-productos

# Eliminar la base de datos (para recrearla con el tipo correcto)
Remove-Item -Recurse -Force data\ -ErrorAction SilentlyContinue

# Reiniciar el microservicio
mvn spring-boot:run
```

### 2. Solucionar msvc-usuario

```powershell
# Ir al directorio del microservicio
cd Backend_Java_Spring\Fullstack_Ecommerce\msvc-usuario

# Solo reiniciar (ya está corregido)
mvn spring-boot:run
```

## Verificación Rápida

### Verificar msvc-productos

1. **Esperar a que inicie** - Deberías ver:
   ```
   Imagen principal convertida a Base64: ./img/consolas/4.png -> Base64
   Producto actualizado con imágenes Base64: CO001 - PlayStation 5
   ```

2. **Probar en Postman**:
   ```
   GET http://localhost:8003/api/v1/productos
   Headers: X-API-Key: levelup-2024-secret-api-key-change-in-production
   ```

3. **Verificar que las imágenes sean Base64**:
   ```json
   {
       "imagen": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...",
       ...
   }
   ```

### Verificar msvc-usuario

1. **Esperar a que inicie** - Deberías ver:
   ```
   Iniciando carga de datos por defecto para usuarios
   Usuario administrador creado: admin@levelup.com
   Usuario de prueba completo creado: aa / aaaa - ID: 5
   ```

2. **Probar en Postman**:
   ```
   GET http://localhost:8095/api/v1/usuarios
   Headers: X-API-Key: levelup-2024-secret-api-key-change-in-production
   ```

## Si Sigue Fallando

### msvc-productos

Si después de eliminar `data\` sigue fallando:

1. **Verificar que no haya procesos ejecutándose**:
   ```powershell
   # Ver procesos Java
   Get-Process java -ErrorAction SilentlyContinue
   ```

2. **Eliminar manualmente los archivos**:
   ```powershell
   Remove-Item -Path data\msvc_productos_dev.mv.db -Force
   Remove-Item -Path data\msvc_productos_dev.trace.db -Force -ErrorAction SilentlyContinue
   ```

3. **Reiniciar el microservicio**

### msvc-usuario

Si sigue fallando:

1. **Ver logs completos**:
   ```bash
   mvn spring-boot:run -e
   ```

2. **Verificar que SecurityConfig tenga el bean PasswordEncoder**

3. **Verificar que no haya conflictos de puertos** (puerto 8095)

## Puertos

- **msvc-productos**: `http://localhost:8003/api/v1`
- **msvc-usuario**: `http://localhost:8095/api/v1`

## Correcciones Aplicadas

### msvc-productos
- ✅ `Producto.java` - Campo `imagen` ahora es `TEXT` (no `VARCHAR(255)`)
- ✅ `ImagePathMapper.java` - Mapea rutas incorrectas a correctas
- ✅ `ProductoDataInitializerFromSQL.java` - Usa el mapeo antes de convertir

### msvc-usuario
- ✅ `UsuarioDataInitializer.java` - Inyecta `PasswordEncoder` como bean
- ✅ Removidos imports innecesarios

