# Sistema de Imágenes en Base64

Este documento explica cómo funciona el sistema de imágenes en Base64 en el microservicio de productos.

## Flujo de Imágenes

### 1. Almacenamiento en Base de Datos

Las imágenes se almacenan en **Base64** en la base de datos H2 con el formato:
```
data:image/{mime};base64,{base64_string}
```

Ejemplo:
```
data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...
```

### 2. Conversión Automática

El inicializador `ProductoDataInitializerFromSQL` convierte automáticamente las rutas de imágenes a Base64:

1. **data.sql** inserta productos con rutas de imágenes (ej: `img/play5white.png`)
2. **ProductoDataInitializerFromSQL** lee todos los productos
3. Detecta imágenes que no son Base64 (rutas que contienen `img/`)
4. Convierte cada ruta a Base64 usando `ImageBase64Service`
5. Actualiza el producto en la base de datos con las imágenes en Base64

### 3. Respuesta JSON en Postman

Cuando se consulta un producto en Postman, la respuesta JSON contiene las imágenes en Base64:

```json
{
    "id": 1,
    "titulo": "PlayStation 5",
    "imagen": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...",
    "imagenes": [
        "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...",
        "data:image/webp;base64,iVBORw0KGgoAAAANSUhEUgAA..."
    ]
}
```

### 4. Uso en Frontend y Kotlin

El frontend y la app de Kotlin reciben las imágenes en Base64 con el formato `data:image` y **NO necesitan decodificar manualmente**. Pueden usarlas directamente:

**React/TypeScript (NO necesita decodificar):**
```typescript
// Usar directamente el Base64 como src - el navegador lo maneja automáticamente
<img src={producto.imagen} alt={producto.titulo} />

// O con TypeScript
<img src={producto.imagenUrl} alt={producto.titulo} />
```

**React con TypeScript - Componente:**
```typescript
interface Producto {
  imagen: string; // "data:image/png;base64,..."
  imagenes: string; // JSON array como string
}

function ProductoCard({ producto }: { producto: Producto }) {
  // Parsear el array de imágenes si es necesario
  const imagenesArray: string[] = JSON.parse(producto.imagenes);
  
  return (
    <div>
      <img src={producto.imagen} alt={producto.titulo} />
      {imagenesArray.map((img, index) => (
        <img key={index} src={img} alt={`${producto.titulo} ${index + 1}`} />
      ))}
    </div>
  );
}
```

**Kotlin/Android (Opción 1 - Usar directamente con librería como Coil o Glide):**
```kotlin
// Con Coil (recomendado - más fácil)
import coil.load

// Usar directamente el Base64 - Coil lo maneja automáticamente
imageView.load(producto.imagen) {
    crossfade(true)
    placeholder(R.drawable.placeholder)
}

// O con Glide
import com.bumptech.glide.Glide
Glide.with(context)
    .load(producto.imagen) // Usar directamente el Base64
    .into(imageView)
```

**Kotlin/Android (Opción 2 - Decodificar manualmente si es necesario):**
```kotlin
// Solo si necesitas decodificar manualmente (no recomendado)
import android.util.Base64
import android.graphics.BitmapFactory

fun mostrarImagenBase64(imageView: ImageView, base64String: String) {
    try {
        // Remover prefijo data:image si existe
        val base64Data = if (base64String.contains(",")) {
            base64String.substringAfter(",")
        } else {
            base64String
        }
        
        val imageBytes = Base64.decode(base64Data, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        imageView.setImageBitmap(bitmap)
    } catch (e: Exception) {
        Log.e("ImageError", "Error al decodificar Base64: ${e.message}")
    }
}
```

**Nota importante:** La mayoría de las librerías modernas de Android (Coil, Glide) pueden manejar directamente URLs `data:image`, por lo que **NO necesitas decodificar manualmente**.

## Ubicación de Archivos

### Inicializador de Conversión
```
Backend_Java_Spring/Fullstack_Ecommerce/msvc-productos/
  src/main/java/com/ampuero/msvc/producto/config/
    ProductoDataInitializerFromSQL.java
```

### Servicio de Conversión
```
Backend_Java_Spring/Fullstack_Ecommerce/msvc-productos/
  src/main/java/com/ampuero/msvc/producto/services/
    ImageBase64Service.java
```

### Archivo SQL con Rutas
```
Backend_Java_Spring/Fullstack_Ecommerce/msvc-productos/
  src/main/resources/
    data.sql
```

### Imágenes Originales
```
Backend_Java_Spring/Fullstack_Ecommerce/msvc-productos/
  src/main/resources/static/img/
    play5white.png
    play4.png
    mandoplay.png
    ...
```

## Proceso de Conversión

### Paso 1: Inserción desde data.sql

`data.sql` inserta productos con rutas de imágenes:
```sql
INSERT INTO productos (..., imagen, imagenes, ...)
VALUES (..., 'img/play5white.png', '["img/play5white.png", "img/play5.webp"]', ...)
```

### Paso 2: Detección de Rutas

El inicializador detecta si una imagen es una ruta (no Base64):
- Si contiene `img/` o `./img/` → Es una ruta, necesita conversión
- Si comienza con `data:image` → Ya es Base64, no necesita conversión

### Paso 3: Conversión a Base64

Para cada ruta detectada:
1. Lee el archivo desde `resources/static/img/`
2. Convierte el archivo a bytes
3. Codifica los bytes a Base64
4. Agrega el prefijo `data:image/{mime};base64,`
5. Guarda el resultado en la base de datos

### Paso 4: Actualización en BD

El producto se actualiza con las imágenes en Base64:
```java
producto.setImagen("data:image/png;base64,iVBORw0KGgo...");
producto.setImagenes("[\"data:image/png;base64,iVBORw0KGgo...\", \"data:image/webp;base64,...\"]");
productoRepository.save(producto);
```

## Formato de Respuesta en Postman

### Ejemplo de Producto con Imagen Base64

```json
{
    "id": 1,
    "titulo": "PlayStation 5",
    "imagen": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...",
    "imagenes": "[\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...\", \"data:image/webp;base64,iVBORw0KGgoAAAANSUhEUgAA...\"]",
    "precio": 549990.0,
    "disponible": true,
    "rating": 1.7,
    "descripcion": "La consola de última generación de Sony...",
    "stock": 50,
    "codigoProducto": "CO001",
    "categoriaId": "CO",
    "subcategoriaId": "HA",
    "nombre": "PlayStation 5",
    "imagenUrl": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA..."
}
```

## Notas Importantes

1. **Las imágenes se almacenan en Base64** en la base de datos H2
2. **En Postman se muestran como URLs Base64** (data:image/png;base64,...)
3. **El frontend y Kotlin decodifican el Base64** para mostrar las imágenes
4. **La conversión es automática** al iniciar el microservicio
5. **Solo se convierten imágenes que aún son rutas** (no se reconvierten Base64)

## Verificar Conversión

Para verificar que las imágenes se convirtieron correctamente:

1. **Iniciar el microservicio**:
   ```bash
   cd Backend_Java_Spring/Fullstack_Ecommerce/msvc-productos
   mvn spring-boot:run
   ```

2. **Revisar los logs**:
   Deberías ver mensajes como:
   ```
   Imagen principal convertida a Base64: img/play5white.png -> Base64
   Imágenes adicionales convertidas a Base64: 2 imágenes
   Producto actualizado con imágenes Base64: CO001 - PlayStation 5
   ```

3. **Probar en Postman**:
   ```
   GET http://localhost:8094/productos/1
   Headers:
     X-API-Key: levelup-2024-secret-api-key-change-in-production
   ```

4. **Verificar la respuesta**:
   El campo `imagen` debe comenzar con `data:image/png;base64,` o similar.

## Troubleshooting

### Las imágenes no se convierten

1. Verifica que `default.data.enabled=true` esté configurado
2. Revisa los logs del microservicio al iniciar
3. Verifica que las imágenes existan en `resources/static/img/`
4. Verifica que las rutas en `data.sql` sean correctas (ej: `img/play5white.png`)

### Las imágenes aparecen como rutas en Postman

1. Verifica que el inicializador se haya ejecutado (revisa logs)
2. Verifica que la base de datos tenga las imágenes en Base64
3. Limpia la base de datos y reinicia el microservicio

### Error al convertir imágenes

1. Verifica que las imágenes existan en `resources/static/img/`
2. Verifica los permisos de lectura de los archivos
3. Revisa los logs para ver qué imagen específica falla

## Ejemplo de Uso en Frontend (React/TypeScript)

### Método 1: Usar Directamente (Recomendado)

```typescript
// React Component con TypeScript
interface Producto {
  id: number;
  titulo: string;
  imagen: string; // "data:image/png;base64,..."
  imagenes: string; // JSON array como string
  precio: number;
}

function ProductoCard({ producto }: { producto: Producto }) {
  // El navegador maneja automáticamente el Base64
  return (
    <div>
      <img 
        src={producto.imagen} 
        alt={producto.titulo}
        style={{ maxWidth: '100%' }}
      />
      <h3>{producto.titulo}</h3>
      <p>${producto.precio}</p>
    </div>
  );
}
```

**✅ NO necesitas decodificar** - El navegador maneja automáticamente las URLs `data:image`.

### Método 2: Parsear Array de Imágenes

```typescript
function ProductoGallery({ producto }: { producto: Producto }) {
  // Parsear el JSON array de imágenes
  const imagenesArray: string[] = JSON.parse(producto.imagenes);
  
  return (
    <div>
      <img src={producto.imagen} alt={producto.titulo} />
      <div className="gallery">
        {imagenesArray.map((img, index) => (
          <img 
            key={index} 
            src={img} 
            alt={`${producto.titulo} ${index + 1}`} 
          />
        ))}
      </div>
    </div>
  );
}
```

## Ejemplo de Uso en Kotlin/Android

### Método 1: Usar Coil (Recomendado - Más Fácil)

```kotlin
// build.gradle.kts
dependencies {
    implementation("io.coil-kt:coil:2.5.0")
}

// En tu Activity/Fragment
import coil.load

// Usar directamente el Base64 - Coil lo maneja automáticamente
binding.imageView.load(producto.imagen) {
    crossfade(true)
    placeholder(R.drawable.placeholder)
    error(R.drawable.error)
}

// ✅ NO necesitas decodificar manualmente con Coil
```

### Método 2: Usar Glide

```kotlin
// build.gradle.kts
dependencies {
    implementation("com.github.bumptech.glide:glide:4.16.0")
}

// En tu Activity/Fragment
import com.bumptech.glide.Glide

// Usar directamente el Base64 - Glide lo maneja automáticamente
Glide.with(context)
    .load(producto.imagen) // "data:image/png;base64,..."
    .placeholder(R.drawable.placeholder)
    .error(R.drawable.error)
    .into(binding.imageView)

// ✅ NO necesitas decodificar manualmente con Glide
```

### Método 3: Decodificar Manualmente (Solo si es necesario)

```kotlin
// Solo usar si las librerías no funcionan (raro)
import android.util.Base64
import android.graphics.BitmapFactory

fun mostrarImagenBase64(imageView: ImageView, base64String: String) {
    try {
        // Remover prefijo data:image si existe
        val base64Data = base64String.substringAfter(",")
        
        val imageBytes = Base64.decode(base64Data, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        imageView.setImageBitmap(bitmap)
    } catch (e: Exception) {
        Log.e("ImageError", "Error al decodificar Base64: ${e.message}")
    }
}
```

**Nota:** En la mayoría de casos, **NO necesitas decodificar manualmente** porque:
- Los navegadores web manejan automáticamente `data:image` URLs
- Librerías como Coil y Glide manejan automáticamente `data:image` URLs en Android

