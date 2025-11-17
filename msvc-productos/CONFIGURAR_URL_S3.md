# Configuración de URLs de S3

Este documento explica dónde y cómo ajustar las URLs de S3 para las imágenes.

## Ubicaciones para configurar URLs de S3

### 1. Backend Java (Spring Boot)

**Archivo**: `src/main/resources/application.properties`

```properties
# S3 Configuration
s3.bucket.name=${S3_BUCKET_NAME:levelup-gamer-products}
s3.region=${S3_REGION:us-east-1}
s3.base.url=${S3_BASE_URL:}
```

**Cómo ajustar**:
- Si quieres usar una URL base personalizada (ej: CDN), configura `s3.base.url`
- Si está vacío, se construye automáticamente como: `https://{bucket}.s3.{region}.amazonaws.com/`
- Puedes usar variables de entorno: `S3_BASE_URL=https://cdn.tudominio.com/`

**Ejemplo con CDN**:
```properties
s3.base.url=https://d1234567890.cloudfront.net/
```

**Ejemplo con variable de entorno**:
```bash
export S3_BASE_URL=https://d1234567890.cloudfront.net/
```

### 2. Kotlin (Android App)

**Archivo**: `Kotlin_app/levelup-backend/config/api-config.properties`

```properties
# URL base para recursos multimedia (S3/CDN). Debe terminar en "/".
media.base.url=https://levelup-gamer-products.s3.us-east-1.amazonaws.com/
```

**Cómo ajustar**:
- Edita directamente el archivo `api-config.properties`
- Asegúrate de que termine con `/`
- Esta URL se usa por `MediaUrlResolver` cuando las imágenes vienen como keys de S3 (ej: `img/carrusel.png`)

**Ejemplo con CDN**:
```properties
media.base.url=https://d1234567890.cloudfront.net/
```

## Flujo de resolución de URLs

### Backend Java → Kotlin

1. **Backend Java** (`S3Service.buildS3Url()`):
   - Si la imagen en BD es una key (ej: `img/carrusel.png`), construye la URL completa
   - Usa `s3.base.url` si está configurado, sino construye: `https://{bucket}.s3.{region}.amazonaws.com/{key}`
   - Devuelve la URL completa al frontend

2. **Kotlin** (`MediaUrlResolver.resolve()`):
   - Si recibe una URL completa (http/https), la usa directamente
   - Si recibe una key de S3 (ej: `img/carrusel.png`), antepone `media.base.url`
   - Si es Base64, lo maneja directamente

## Ejemplos de configuración

### Configuración estándar (S3 directo)

**Backend**:
```properties
s3.base.url=
```

**Kotlin**:
```properties
media.base.url=https://levelup-gamer-products.s3.us-east-1.amazonaws.com/
```

### Configuración con CDN (CloudFront)

**Backend**:
```properties
s3.base.url=https://d1234567890.cloudfront.net/
```

**Kotlin**:
```properties
media.base.url=https://d1234567890.cloudfront.net/
```

### Configuración con dominio personalizado

**Backend**:
```properties
s3.base.url=https://media.levelupgamer.com/
```

**Kotlin**:
```properties
media.base.url=https://media.levelupgamer.com/
```

## Verificación

Para verificar que las URLs se están construyendo correctamente:

1. **Backend**: Revisa los logs cuando se llama a `S3Service.buildS3Url()`
2. **Kotlin**: Revisa los logs de `MediaUrlResolver` en Logcat:
   ```
   tag:MediaUrlResolver
   ```

## Notas importantes

- El backend Java construye URLs completas y las envía al frontend
- El frontend Kotlin solo necesita `media.base.url` si recibe keys de S3 directamente (raro)
- En la mayoría de casos, el backend ya envía URLs completas, así que `media.base.url` es un fallback
- Si cambias la configuración, reinicia el backend y recompila la app Android

