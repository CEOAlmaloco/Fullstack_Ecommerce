# Implementación de S3 para Imágenes de Productos

## Resumen

Se ha implementado un sistema de referencias a S3 para las imágenes de productos. En lugar de guardar las imágenes en Base64 directamente en la base de datos (lo cual es ineficiente), ahora se guarda solo una referencia (key) a S3 y se construye la URL completa al momento de retornar los productos.

## Arquitectura

### Componentes

1. **S3Service** (`services/S3Service.java`)
   - Construye URLs completas de S3 a partir de referencias (keys) guardadas en BD
   - Soporta formato estándar de S3: `https://bucket.s3.region.amazonaws.com/key`
   - Soporta URLs personalizadas configuradas en `s3.base.url`
   - Mantiene compatibilidad con Base64 existente

2. **ProductoMapper** (`services/ProductoMapper.java`)
   - Convierte entidades `Producto` a DTOs `ProductoResponseDTO`
   - Construye las URLs de S3 usando `S3Service`
   - Mapea todos los campos necesarios

3. **ProductoResponseDTO** (`dtos/ProductoResponseDTO.java`)
   - Incluye campos para URLs de S3 (`imagenUrl`, `imagenesUrls`)
   - Incluye campos para referencias originales (`imagenS3Key`, `imagenesS3Keys`)
   - Mantiene compatibilidad con campos anteriores

## Configuración

### application.properties

```properties
# S3 Configuration
s3.bucket.name=${S3_BUCKET_NAME:levelup-gamer-products}
s3.region=${S3_REGION:us-east-1}
s3.base.url=${S3_BASE_URL:}
```

### Variables de Entorno (Opcional)

```bash
S3_BUCKET_NAME=levelup-gamer-products
S3_REGION=us-east-1
S3_BASE_URL=https://cdn.levelupgamer.com  # Opcional: URL personalizada
```

## Formato de Datos en Base de Datos

### Imagen Principal
- **Campo**: `imagen` (TEXT)
- **Formato**: Referencia a S3 (ej: `productos/123/imagen.jpg`)
- **Ejemplo**: `productos/123/imagen.jpg`

### Imágenes Adicionales
- **Campo**: `imagenes` (TEXT)
- **Formato**: JSON array de referencias a S3
- **Ejemplo**: `["productos/123/img1.jpg","productos/123/img2.jpg"]`

## Respuesta de la API

### Antes (Base64)
```json
{
  "idProducto": 123,
  "nombreProducto": "Laptop Gaming",
  "imagen": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA..."
}
```

### Ahora (S3)
```json
{
  "idProducto": 123,
  "nombreProducto": "Laptop Gaming",
  "imagenUrl": "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/productos/123/imagen.jpg",
  "imagenS3Key": "productos/123/imagen.jpg",
  "imagenesUrls": "[\"https://levelup-gamer-products.s3.us-east-1.amazonaws.com/productos/123/img1.jpg\",\"https://levelup-gamer-products.s3.us-east-1.amazonaws.com/productos/123/img2.jpg\"]",
  "imagenesS3Keys": "[\"productos/123/img1.jpg\",\"productos/123/img2.jpg\"]"
}
```

## Migración de Datos Existentes

### Si tienes datos con Base64:

El sistema mantiene compatibilidad con Base64. Si el campo `imagen` contiene Base64 (comienza con `data:image`), se retornará directamente sin modificación.

### Para migrar a S3:

1. Sube las imágenes a S3 con la estructura: `productos/{id}/imagen.jpg`
2. Actualiza la base de datos para guardar solo la referencia: `productos/123/imagen.jpg`
3. El sistema construirá automáticamente la URL completa al retornar

## Ejemplo de Script SQL para Migración

```sql
-- Ejemplo: Actualizar imagen principal a referencia S3
-- (Asumiendo que las imágenes ya están en S3)
UPDATE productos 
SET imagen = 'productos/' || id_producto || '/imagen.jpg'
WHERE imagen LIKE 'data:image%';

-- Ejemplo: Actualizar imágenes adicionales a referencias S3
UPDATE productos 
SET imagenes = '["productos/' || id_producto || '/img1.jpg","productos/' || id_producto || '/img2.jpg"]'
WHERE imagenes LIKE '%data:image%';
```

## Endpoints Actualizados

Todos los endpoints que retornan productos ahora retornan `ProductoResponseDTO` con URLs de S3:

- `GET /api/v1/productos` - Lista todos los productos
- `GET /api/v1/productos/{id}` - Obtiene un producto por ID
- `GET /api/v1/productos/buscar` - Busca productos
- `GET /api/v1/productos/categoria/{categoria}` - Productos por categoría
- `GET /api/v1/productos/disponibles` - Productos disponibles
- `POST /api/v1/productos/filtrar` - Filtrar productos con paginación

## Beneficios

1. **Rendimiento**: Las respuestas son mucho más pequeñas (solo referencias, no Base64 completo)
2. **Escalabilidad**: Las imágenes se sirven directamente desde S3/CDN
3. **Carga rápida**: El frontend carga las imágenes en paralelo desde S3
4. **Compatibilidad**: Mantiene compatibilidad con datos existentes en Base64

## Uso en Frontend

### React/TypeScript
```typescript
// La imagenUrl ya viene construida desde el backend
<img src={producto.imagenUrl} alt={producto.nombreProducto} />

// Para imágenes adicionales
const imagenesAdicionales = JSON.parse(producto.imagenesUrls || '[]');
```

### Kotlin/Android
```kotlin
// La imagenUrl ya viene construida desde el backend
AsyncImage(
    model = ImageRequest.Builder(context)
        .data(producto.imagenUrl)
        .build(),
    contentDescription = producto.nombreProducto
)
```

## Notas Importantes

1. **Compatibilidad hacia atrás**: El sistema detecta automáticamente si una imagen es Base64 o una referencia S3
2. **URLs personalizadas**: Si configuras `s3.base.url`, se usará esa URL en lugar del formato estándar de S3
3. **Referencias originales**: Los campos `imagenS3Key` e `imagenesS3Keys` contienen las referencias originales guardadas en BD
4. **URLs construidas**: Los campos `imagenUrl` e `imagenesUrls` contienen las URLs completas construidas por el sistema

