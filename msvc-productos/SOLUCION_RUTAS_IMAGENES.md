# Solución: Mapeo de Rutas de Imágenes

## Problema

Las rutas de imágenes en la base de datos (`"./img/consolas/4.png"`) no coinciden con los archivos reales en `resources/static/img/` (no hay carpetas `consolas/`, `polerones/`, etc.).

## Solución Implementada

Se creó un servicio `ImagePathMapper` que mapea rutas incorrectas a rutas correctas:

### Mapeo de Rutas

- `img/consolas/4.png` → `img/play5white.png` (PlayStation 5)
- `img/consolas/1.png` → `img/play5white.png` (PlayStation 5)
- `img/consolas/2.png` → `img/play4.png` (PlayStation 4 Slim)
- `img/consolas/3.png` → `img/mandoplay.png` (DualShock 4)
- `img/polerones/4.png` → `img/poleronplay.png` (Poleron PlayStation Retro Negro)
- Y más...

## Pasos para Aplicar la Solución

### 1. Reiniciar el Microservicio

```bash
cd Backend_Java_Spring/Fullstack_Ecommerce/msvc-productos
mvn spring-boot:run
```

### 2. Verificar los Logs

Deberías ver mensajes como:

```
Iniciando conversión de imágenes a Base64 para productos existentes
Ruta mapeada: ./img/consolas/4.png -> img/play5white.png
Imagen principal convertida a Base64: ./img/consolas/4.png -> Base64
Producto actualizado con imágenes Base64: CO001 - PlayStation 5
Conversión de imágenes completada. Convertidos: 19, Ya convertidos: 0
```

### 3. Probar en Postman

```
GET http://localhost:8094/productos
Headers:
  X-API-Key: levelup-2024-secret-api-key-change-in-production
```

### 4. Verificar la Respuesta

Las imágenes deberían estar en Base64:

```json
{
    "id": 1,
    "titulo": "PlayStation 5",
    "imagen": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...",
    "imagenes": "[\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...\", ...]"
}
```

## Archivos Creados/Modificados

1. **`ImagePathMapper.java`** - Servicio que mapea rutas incorrectas a rutas correctas
2. **`ProductoDataInitializerFromSQL.java`** - Actualizado para usar el mapeo antes de convertir a Base64

## Cómo Funciona

1. El inicializador lee todos los productos de la base de datos
2. Para cada producto con rutas (no Base64):
   - Limpia la ruta (remueve prefijo `./` si existe)
   - Usa `ImagePathMapper` para mapear a la ruta correcta
   - Usa `ImageBase64Service` para convertir a Base64
   - Guarda el producto actualizado

## Troubleshooting

### Las imágenes no se convierten

1. **Verifica los logs** al iniciar el microservicio
2. **Verifica que `default.data.enabled=true`** en `application-dev.properties`
3. **Verifica que las imágenes existan** en `resources/static/img/`

### Error: "Ruta mapeada: ... -> ..." pero "No se pudo convertir imagen"

1. **Verifica que el archivo exista** en `resources/static/img/`
2. **Verifica que el nombre del archivo** coincida exactamente (mayúsculas/minúsculas)

### Necesitas agregar más mapeos

Edita `ImagePathMapper.java` y agrega más entradas en el `PATH_MAP`:

```java
static {
    // Agregar más mapeos aquí
    PATH_MAP.put("img/ruta/incorrecta.png", "img/ruta_correcta.png");
}
```

## Notas

- El mapeo maneja rutas con o sin prefijo `./`
- Si no hay mapeo para una ruta, se usa la ruta original (limpia)
- El inicializador solo convierte imágenes que NO son Base64 (no comienzan con `data:image`)

