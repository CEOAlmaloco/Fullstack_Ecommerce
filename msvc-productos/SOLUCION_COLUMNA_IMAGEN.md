# Solución: Error de Columna IMAGEN Demasiado Larga

## Problema

El error indica que la columna `imagen` es `VARCHAR(255)` pero el Base64 tiene 4822 caracteres:

```
Value too long for column "IMAGEN CHARACTER VARYING(255)": "'data:image/png;base64,... (4822)"
```

## Solución Aplicada

1. **Corregido el modelo `Producto.java`** - Cambiado `@Column` a `@Column(columnDefinition = "TEXT")` para permitir cadenas más largas.

## Pasos para Aplicar la Solución

### Opción 1: Eliminar y Recrear la Base de Datos (Recomendado para Desarrollo)

1. **Detener el microservicio** (Ctrl+C)

2. **Eliminar la base de datos**:
   ```bash
   cd Backend_Java_Spring/Fullstack_Ecommerce/msvc-productos
   rm -rf data/
   ```
   O en Windows:
   ```powershell
   cd Backend_Java_Spring\Fullstack_Ecommerce\msvc-productos
   Remove-Item -Recurse -Force data\
   ```

3. **Reiniciar el microservicio**:
   ```bash
   mvn spring-boot:run
   ```

4. **Hibernate creará automáticamente** la tabla con el tipo correcto (`TEXT`)

### Opción 2: Actualizar Manualmente la Columna (Si quieres mantener los datos)

1. **Abrir la consola H2**:
   ```
   http://localhost:8003/api/v1/h2-console
   ```
   - JDBC URL: `jdbc:h2:file:./data/msvc_productos_dev`
   - Username: `sa`
   - Password: `sa`

2. **Ejecutar el siguiente SQL**:
   ```sql
   ALTER TABLE productos ALTER COLUMN imagen VARCHAR(2147483647);
   ```

3. **Reiniciar el microservicio**

### Opción 3: Usar Migración de Flyway/Liquibase (Producción)

Si estás usando Flyway o Liquibase, crea una migración SQL:

```sql
-- V1__alter_imagen_column.sql
ALTER TABLE productos ALTER COLUMN imagen VARCHAR(2147483647);
```

## Verificación

Después de aplicar la solución:

1. **Reiniciar el microservicio**
2. **Verificar los logs** - Deberías ver:
   ```
   Imagen principal convertida a Base64: ./img/consolas/4.png -> Base64
   Producto actualizado con imágenes Base64: CO001 - PlayStation 5
   Conversión de imágenes completada. Convertidos: 19, Ya convertidos: 0
   ```

3. **Probar en Postman**:
   ```
   GET http://localhost:8003/api/v1/productos
   Headers:
     X-API-Key: levelup-2024-secret-api-key-change-in-production
   ```

4. **Verificar la respuesta** - Las imágenes deberían estar en Base64:
   ```json
   {
       "imagen": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...",
       "imagenes": "[\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...\", ...]"
   }
   ```

## Archivos Modificados

1. **`Producto.java`** - Cambiado `@Column` a `@Column(columnDefinition = "TEXT")` para el campo `imagen`

## Notas

- La columna `imagenes` ya estaba como `TEXT`, así que no necesita cambios
- El tipo `TEXT` en H2 puede almacenar hasta 2,147,483,647 caracteres, suficiente para imágenes Base64
- Para producción, considera usar `BLOB` o almacenar las imágenes en un servicio de almacenamiento (S3, Cloudinary, etc.)

