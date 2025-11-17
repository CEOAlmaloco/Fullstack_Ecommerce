# Flujo de Carga de Imágenes de Blogs

Este documento explica cómo se cargan las imágenes de blogs desde S3 hasta mostrarlas en la aplicación Kotlin.

## Flujo Completo

### 1. Backend Java (msvc-contenido)

#### Endpoint
```
GET /api/v1/contenido/articulos/publicados
```

#### Controller: `ContenidoController.java`

**Método:** `convertirArticuloAResponseDTO()`

```java
// Líneas 399-441
private ArticuloResponseDTO convertirArticuloAResponseDTO(Articulo articulo) {
    // ...
    String imagenOriginal = articulo.getImagenArticulo();
    
    if (imagenOriginal != null && !imagenOriginal.isEmpty()) {
        // Si es Base64, convertir a URL de S3 según categoría
        if (imagenOriginal.startsWith("data:image")) {
            String categoria = articulo.getCategoriaArticulo();
            String imagenUrl;
            if ("TECNOLOGIA".equals(categoria) || "NOTICIAS".equals(categoria)) {
                imagenUrl = "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/ralidadv.jfif";
            } else if ("LANZAMIENTOS".equals(categoria)) {
                imagenUrl = "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/juegos_esperados.jpg";
            } else {
                imagenUrl = "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/evento.jpg";
            }
            response.setImagenArticulo(imagenUrl);
        } else {
            // Si es una key de S3, construir la URL
            String imagenUrl = s3Service.buildS3Url(imagenOriginal);
            response.setImagenArticulo(imagenUrl != null ? imagenUrl : imagenOriginal);
        }
    } else {
        // Si no hay imagen, usar URL por defecto según categoría
        // ... (misma lógica)
    }
}
```

**Campos devueltos en JSON:**
- `imagenArticulo`: URL completa de S3 (ej: `https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/ralidadv.jfif`)
- `imagenUrl`: Alias de `imagenArticulo` (método `getImagenUrl()`)

### 2. Kotlin - API Service

**Archivo:** `ContenidoApiService.kt`

```kotlin
@GET("contenido/articulos/publicados")
suspend fun getArticulosPublicados(): Response<List<ArticuloResponse>>
```

**ArticuloResponse** mapea:
- `@SerializedName("imagenArticulo")` → `imagenArticulo: String?`
- `@SerializedName("imagenUrl")` → `imagenUrl: String?`

### 3. Kotlin - Repository

**Archivo:** `BlogRepository.kt`

**Método:** `obtenerBlogs()`

```kotlin
suspend fun obtenerBlogs(): List<Blog> {
    val response = ApiConfig.contenidoService.getArticulosPublicados()
    if (response.isSuccessful && response.body() != null) {
        val blogs = response.body()!!.map { articulo ->
            articulo.toBlog()  // Convierte ArticuloResponse a Blog
        }
        return blogs
    }
    return emptyList()
}
```

**Método de extensión:** `ArticuloResponse.toBlog()`

```kotlin
// Líneas 122-131
// Resolver imagen priorizando URLs remotas (S3) sobre Base64
val candidateImages = listOf(
    imagenUrl,              // Primera prioridad
    imagenPreviewUrl,
    imagenMiniaturaUrl,
    imagenStorageUrl,
    imagenArticulo          // Última prioridad
)

val resolvedImagen = MediaUrlResolver.resolveFirst(candidateImages)

return Blog(
    // ...
    imagenUrl = resolvedImagen,  // URL de S3 completa
    // ...
)
```

### 4. Kotlin - ViewModel

**Archivo:** `BlogViewModel.kt`

```kotlin
fun cargarBlogs() {
    viewModelScope.launch {
        val blogs = repository.obtenerBlogs()
        _estado.update {
            it.copy(blogs = blogs, isLoading = false)
        }
    }
}
```

### 5. Kotlin - UI Component

**Archivo:** `BlogListScreen.kt` → `BlogCard`

```kotlin
@Composable
fun BlogCard(blog: Blog) {
    // ...
    val resolvedImageUrl = MediaUrlResolver.resolve(blog.imagenUrl)
    
    Log.d("BlogCard", "Blog: ${blog.titulo}")
    Log.d("BlogCard", "Imagen original: ${blog.imagenUrl}")
    Log.d("BlogCard", "Imagen resuelta: $resolvedImageUrl")
    
    val imageData = if (resolvedImageUrl.isNotBlank()) {
        resolvedImageUrl  // URL completa de S3
    } else {
        null
    }

    if (imageData != null) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageData)  // URL de S3: https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/ralidadv.jfif
                .crossfade(true)
                .error(android.R.drawable.ic_menu_report_image)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .build(),
            contentDescription = blog.titulo,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}
```

### 6. MediaUrlResolver

**Archivo:** `MediaUrlResolver.kt`

```kotlin
fun resolve(raw: String?): String {
    // Si ya es una URL HTTP/HTTPS, la devuelve tal cual
    if (value.startsWith("http://", ignoreCase = true) ||
        value.startsWith("https://", ignoreCase = true)) {
        return value  // Ya es URL completa de S3
    }
    
    // Si es una clave S3 (ej: "img/ralidadv.jfif"), construye la URL completa
    return buildUrl(sanitized)  // mediaBaseUrl + path
}

private fun buildUrl(path: String): String {
    val fullUrl = mediaBaseUrl + cleanPath
    // mediaBaseUrl = "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/"
    // path = "img/ralidadv.jfif"
    // Resultado: "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/ralidadv.jfif"
    return fullUrl
}
```

## ¿Por qué no se mostraban las imágenes?

### Problema 1: Inicializador guardaba Base64 en BD

**Archivo:** `ContenidoDataInitializer.java`

```java
// Líneas 51-58
String imagen1 = imageBase64Service.convertImageToBase64("img/play5white.png");
if (imagen1 == null || imagen1.isEmpty()) {
    imagen1 = "";
} else {
    logger.debug("Imagen cargada para blog 1: {} caracteres", imagen1.length());
}
blog1.setImagenArticulo(imagen1);  // Guarda Base64 en BD
```

**Solución:** Deshabilitar inicializador en producción:
```properties
# application-prod.properties
default.data.enabled=false
```

### Problema 2: Controller convertía Base64 a URLs de S3

El `ContenidoController` detecta Base64 y lo convierte a URLs de S3 según categoría:

```java
if (imagenOriginal.startsWith("data:image")) {
    // Convierte Base64 a URL de S3 según categoría
    String imagenUrl = "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/ralidadv.jfif";
    response.setImagenArticulo(imagenUrl);
}
```

**Esto funciona**, pero si el inicializador se ejecuta en producción, puede sobrescribir datos.

### Problema 3: MediaUrlResolver no procesaba URLs completas

**Solución:** `MediaUrlResolver` ya maneja URLs completas correctamente:

```kotlin
if (value.startsWith("https://")) {
    return value  // Devuelve la URL tal cual
}
```

## Resumen del Flujo

1. **Backend Java** → Devuelve `imagenArticulo` con URL completa de S3
2. **Kotlin API Service** → Mapea JSON a `ArticuloResponse`
3. **BlogRepository** → Convierte `ArticuloResponse` a `Blog` usando `MediaUrlResolver.resolveFirst()`
4. **BlogViewModel** → Carga blogs y actualiza estado
5. **BlogCard (UI)** → Usa `MediaUrlResolver.resolve()` y `AsyncImage` para cargar desde S3

## URLs de S3 Devueltas por Categoría

- **TECNOLOGIA / NOTICIAS** → `https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/ralidadv.jfif`
- **LANZAMIENTOS** → `https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/juegos_esperados.jpg`
- **Otros** → `https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/evento.jpg`

## Verificación

Para verificar que las imágenes se cargan correctamente, revisa los logs de Android:

```bash
# Filtrar logs de BlogCard
adb logcat | grep "BlogCard"

# Deberías ver:
# BlogCard: Blog: PlayStation 5 Pro: Todo lo que necesitas saber
# BlogCard: Imagen original: https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/ralidadv.jfif
# BlogCard: Imagen resuelta: https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/ralidadv.jfif
```

