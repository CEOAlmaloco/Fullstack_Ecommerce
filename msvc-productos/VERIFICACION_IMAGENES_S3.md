# Verificación de Imágenes S3 en Backend Java

## Estado Actual del Backend

### 1. Cómo Funciona el Backend

El backend Java está configurado correctamente para manejar imágenes de S3:

#### **S3Service.java** (`services/S3Service.java`)
- **Función**: Construye URLs de S3 a partir de referencias (keys) guardadas en BD
- **Lógica**:
  1. Si la key ya es una URL completa (`http://` o `https://`), la devuelve tal cual
  2. Si es Base64 (`data:image`), la devuelve tal cual
  3. Si hay `s3.base.url` configurado, la usa
  4. Si no, construye URL estándar: `https://{bucket}.s3.{region}.amazonaws.com/{key}`

#### **ProductoMapper.java** (`services/ProductoMapper.java`)
- Toma el campo `imagen` de la entidad `Producto`
- Llama a `s3Service.buildS3Url()` para construir la URL completa
- Asigna tanto `imagenS3Key` (key original) como `imagenUrl` (URL completa) al DTO

#### **ProductoResponseDTO.java** (`dtos/ProductoResponseDTO.java`)
- Incluye ambos campos:
  - `imagenUrl`: URL completa de S3 (ej: `https://bucket.s3.amazonaws.com/img/teclado.png`)
  - `imagenS3Key`: Key original guardada en BD (ej: `img/teclado.png` o URL completa)

### 2. Qué Está Guardado en la Base de Datos

Según `data.sql`, las imágenes están guardadas como **URLs completas**:

```sql
'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/teclado_rd_rgb.png'
'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/monitorasus.png'
'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/play5white.png'
```

**Problema potencial**: Si las imágenes en S3 están en rutas diferentes o con nombres diferentes, las URLs no funcionarán.

### 3. Configuración Actual

#### **application.properties**
```properties
s3.bucket.name=levelup-gamer-products
s3.region=us-east-1
s3.base.url=  # Vacío (usa construcción estándar)
```

## Verificaciones Necesarias

### ✅ Verificación 1: Rutas en S3 vs URLs en BD

**Problema**: Las URLs en la BD deben coincidir exactamente con las rutas reales en S3.

**Ejemplo**:
- BD tiene: `https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/teclado_rd_rgb.png`
- S3 debe tener: `img/teclado_rd_rgb.png` (sin el prefijo `https://...`)

**Acción**: Verificar en AWS S3 Console que las imágenes existen en las rutas especificadas.

### ✅ Verificación 2: Formato de Datos en BD

**Opciones**:

#### Opción A: Guardar solo Keys (Recomendado)
```sql
-- En lugar de URL completa:
'imagen' = 'img/teclado_rd_rgb.png'

-- El backend construirá:
'imagenUrl' = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/teclado_rd_rgb.png'
```

**Ventajas**:
- Más flexible (cambiar bucket/región sin tocar BD)
- Consistente con el diseño del `S3Service`
- Más fácil de mantener

#### Opción B: Guardar URLs Completas (Actual)
```sql
'imagen' = 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/teclado_rd_rgb.png'
```

**Ventajas**:
- Funciona si las URLs son correctas
- No requiere construcción adicional

**Desventajas**:
- Menos flexible
- Si cambias bucket/región, debes actualizar toda la BD

### ✅ Verificación 3: Compatibilidad con Android

El `MediaUrlResolver` en Kotlin puede manejar:
- ✅ URLs completas (`https://...`)
- ✅ Base64 (`data:image...`)
- ✅ Keys de S3 (`img/teclado.png`)

**Conclusión**: El backend está enviando URLs completas, lo cual es compatible con Android.

## Problemas Identificados

### 🔴 Problema 1: Inconsistencia en Rutas

El `seed_productos.sql` tiene rutas como:
- `img/consolas/4.png`
- `img/perifericos/2.png`

Pero `data.sql` tiene rutas como:
- `img/teclado_rd_rgb.png`
- `img/monitorasus.png`

**Solución**: Verificar qué estructura real existe en S3 y actualizar la BD en consecuencia.

### 🔴 Problema 2: URLs Hardcodeadas

Las URLs están hardcodeadas en los scripts SQL, lo que dificulta el mantenimiento.

**Solución**: Considerar guardar solo keys y dejar que el backend construya las URLs.

## Recomendaciones

### 1. Verificar Estructura Real en S3

```bash
# Conectarse a AWS CLI y listar imágenes
aws s3 ls s3://levelup-gamer-products/img/ --recursive
```

### 2. Actualizar BD con Keys (Opcional pero Recomendado)

Si decides cambiar a keys:

```sql
-- Extraer key de URL completa
UPDATE productos 
SET imagen = SUBSTRING(imagen, LOCATE('/img/', imagen))
WHERE imagen LIKE 'https://%';

-- Ejemplo:
-- 'https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/teclado_rd_rgb.png'
-- → 'img/teclado_rd_rgb.png'
```

### 3. Verificar que las URLs Funcionan

```bash
# Probar una URL directamente
curl -I https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/teclado_rd_rgb.png
```

Debería retornar `200 OK` y headers con `Content-Type: image/png`.

### 4. Verificar Bucket Policy

Asegúrate de que el bucket tiene la política correcta para lectura pública (ver `IMAGENES_S3.md` en el proyecto Kotlin).

## Próximos Pasos

1. ✅ Verificar que las imágenes existen en S3 en las rutas especificadas
2. ✅ Probar una URL directamente en el navegador
3. ✅ Verificar Bucket Policy y CORS
4. ✅ Revisar logs del backend cuando se consultan productos
5. ✅ Verificar que `ProductoResponseDTO` está enviando `imagenUrl` correctamente

## Logs Útiles

Para debuggear en el backend, agregar logs en `S3Service.buildS3Url()`:

```java
logger.debug("Construyendo URL S3 - Key: {}, Resultado: {}", s3Key, url);
```

Y en `ProductoMapper.toDTO()`:

```java
logger.debug("Mapeando producto {} - imagenUrl: {}", producto.getId(), dto.getImagenUrl());
```

