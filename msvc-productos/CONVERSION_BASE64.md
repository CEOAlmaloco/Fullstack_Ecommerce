# Conversión de Imágenes a Base64

## Problema

Las imágenes en la base de datos están almacenadas como rutas (ej: `"./img/consolas/4.png"`) en lugar de Base64 (ej: `"data:image/png;base64,..."`).

## Solución

El inicializador `ProductoDataInitializerFromSQL` convierte automáticamente las rutas a Base64 al iniciar el microservicio.

## Pasos para Forzar la Conversión

### Opción 1: Reiniciar el Microservicio (Recomendado)

1. **Detener el microservicio** si está corriendo
2. **Eliminar la base de datos** (opcional, solo si quieres empezar desde cero):
   ```bash
   cd Backend_Java_Spring/Fullstack_Ecommerce/msvc-productos
   rm -rf data/
   ```
   O en Windows:
   ```powershell
   cd Backend_Java_Spring\Fullstack_Ecommerce\msvc-productos
   Remove-Item -Recurse -Force data\
   ```

3. **Iniciar el microservicio**:
   ```bash
   mvn spring-boot:run
   ```

4. **Verificar los logs** - Deberías ver mensajes como:
   ```
   Iniciando conversión de imágenes a Base64 para productos existentes
   Imagen principal convertida a Base64: ./img/consolas/4.png -> Base64
   Imágenes adicionales convertidas a Base64: 4 de 4 imágenes
   Producto actualizado con imágenes Base64: CO001 - PlayStation 5
   Conversión de imágenes completada. Convertidos: 19, Ya convertidos: 0
   ```

5. **Probar en Postman**:
   ```
   GET http://localhost:8094/productos
   Headers:
     X-API-Key: levelup-2024-secret-api-key-change-in-production
   ```

6. **Verificar la respuesta** - Las imágenes deben comenzar con `data:image`:
   ```json
   {
       "id": 1,
       "titulo": "PlayStation 5",
       "imagen": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...",
       "imagenes": "[\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...\", ...]"
   }
   ```

### Opción 2: Verificar la Configuración

Asegúrate de que `application-dev.properties` tenga:

```properties
default.data.enabled=true
```

### Opción 3: Verificar que las Imágenes Existan

El inicializador busca las imágenes en `resources/static/img/`. Verifica que existan:

```bash
ls -la src/main/resources/static/img/
```

O en Windows:
```powershell
Get-ChildItem src\main\resources\static\img\
```

## Troubleshooting

### Las imágenes no se convierten

1. **Verifica los logs** al iniciar el microservicio:
   - Busca mensajes que empiecen con "Iniciando conversión de imágenes"
   - Busca errores o warnings sobre imágenes no encontradas

2. **Verifica que `default.data.enabled=true`** esté en `application-dev.properties`

3. **Verifica que las imágenes existan** en `resources/static/img/`

4. **Verifica que las rutas en la base de datos** sean correctas:
   - Deben ser como `"./img/consolas/4.png"` o `"img/consolas/4.png"`
   - El inicializador maneja ambos formatos

### Las imágenes aparecen como rutas después de reiniciar

1. **Verifica los logs** para ver si hay errores al convertir
2. **Verifica que las imágenes existan** en el directorio correcto
3. **Verifica que las rutas en la base de datos** sean correctas

### Error: "Imagen no encontrada"

Si ves errores como "Imagen no encontrada: ./img/consolas/4.png":

1. **Verifica que la imagen exista** en `resources/static/img/consolas/4.png`
2. **Verifica que la ruta en la base de datos** coincida con la estructura de directorios
3. **Verifica que el nombre del archivo** sea correcto (mayúsculas/minúsculas)

## Verificación Manual

Si quieres verificar manualmente que las imágenes se convirtieron:

1. **Abrir la consola H2**:
   ```
   http://localhost:8094/h2-console
   ```
   - JDBC URL: `jdbc:h2:file:./data/msvc_productos_dev`
   - Username: `sa`
   - Password: `sa`

2. **Ejecutar consulta**:
   ```sql
   SELECT id, titulo, imagen, imagenes FROM productos LIMIT 5;
   ```

3. **Verificar que `imagen` y `imagenes`** comiencen con `data:image`

## Notas

- El inicializador se ejecuta **solo si** `default.data.enabled=true`
- El inicializador **solo convierte** imágenes que NO son Base64 (no comienzan con `data:image`)
- Si las imágenes ya están en Base64, el inicializador las deja como están
- El inicializador maneja rutas con o sin prefijo `./`

