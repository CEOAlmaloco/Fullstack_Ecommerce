# Cómo Usar Imágenes Base64 en Frontend y Kotlin

Esta guía explica cómo usar las imágenes Base64 que vienen de la API en React/TypeScript y Kotlin/Android.

## Respuesta de la API

La API devuelve imágenes en formato Base64 con prefijo `data:image`:

```json
{
    "id": 1,
    "titulo": "PlayStation 5",
    "imagen": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...",
    "imagenes": "[\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...\", \"data:image/webp;base64,...\"]"
}
```

## ⚠️ IMPORTANTE: NO Necesitas Decodificar Manualmente

Con el formato `data:image/png;base64,...`, **NO necesitas decodificar manualmente** el Base64. Puedes usarlo directamente:

- **React/TypeScript**: El navegador maneja automáticamente las URLs `data:image`
- **Kotlin/Android**: Librerías como Coil y Glide manejan automáticamente las URLs `data:image`

---

## React/TypeScript

### Método 1: Usar Directamente (Recomendado)

```typescript
interface Producto {
  id: number;
  titulo: string;
  imagen: string; // "data:image/png;base64,..."
  imagenes: string; // JSON array como string
  precio: number;
}

function ProductoCard({ producto }: { producto: Producto }) {
  return (
    <div className="producto-card">
      {/* Usar directamente - el navegador lo maneja automáticamente */}
      <img 
        src={producto.imagen} 
        alt={producto.titulo}
        className="producto-imagen"
      />
      <h3>{producto.titulo}</h3>
      <p>${producto.precio}</p>
    </div>
  );
}
```

**✅ El navegador decodifica automáticamente el Base64** cuando usas `data:image` como `src`.

### Método 2: Parsear Array de Imágenes

```typescript
function ProductoGallery({ producto }: { producto: Producto }) {
  // Parsear el JSON array de imágenes
  const imagenesArray: string[] = JSON.parse(producto.imagenes);
  
  return (
    <div>
      {/* Imagen principal */}
      <img src={producto.imagen} alt={producto.titulo} />
      
      {/* Galería de imágenes adicionales */}
      <div className="gallery">
        {imagenesArray.map((img, index) => (
          <img 
            key={index} 
            src={img} 
            alt={`${producto.titulo} ${index + 1}`}
            className="gallery-image"
          />
        ))}
      </div>
    </div>
  );
}
```

### Método 3: Hook Personalizado

```typescript
import { useState, useEffect } from 'react';

function useProductoImagenes(producto: Producto) {
  const [imagenesArray, setImagenesArray] = useState<string[]>([]);
  
  useEffect(() => {
    try {
      const parsed = JSON.parse(producto.imagenes);
      setImagenesArray(parsed);
    } catch (e) {
      console.error('Error al parsear imágenes:', e);
      setImagenesArray([]);
    }
  }, [producto.imagenes]);
  
  return imagenesArray;
}

function ProductoCard({ producto }: { producto: Producto }) {
  const imagenesArray = useProductoImagenes(producto);
  
  return (
    <div>
      <img src={producto.imagen} alt={producto.titulo} />
      {imagenesArray.map((img, i) => (
        <img key={i} src={img} alt={`${producto.titulo} ${i + 1}`} />
      ))}
    </div>
  );
}
```

---

## Kotlin/Android

### Método 1: Usar Coil (Recomendado - Más Fácil)

**Coil** es una librería moderna de Android que maneja automáticamente URLs `data:image`.

#### Paso 1: Agregar dependencia

```kotlin
// build.gradle.kts (Module: app)
dependencies {
    implementation("io.coil-kt:coil:2.5.0")
}
```

#### Paso 2: Usar en tu código

```kotlin
import coil.load
import android.widget.ImageView

// En tu Activity, Fragment o ViewHolder
fun mostrarImagenProducto(imageView: ImageView, producto: Producto) {
    // Usar directamente el Base64 - Coil lo maneja automáticamente
    imageView.load(producto.imagen) {
        crossfade(true)
        placeholder(R.drawable.placeholder)
        error(R.drawable.error)
    }
}

// Ejemplo completo en RecyclerView
class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imagenView: ImageView = itemView.findViewById(R.id.imagen_producto)
    
    fun bind(producto: Producto) {
        // Usar directamente el Base64
        imagenView.load(producto.imagen) {
            crossfade(true)
            placeholder(R.drawable.placeholder)
        }
    }
}
```

**✅ NO necesitas decodificar manualmente** - Coil maneja automáticamente las URLs `data:image`.

### Método 2: Usar Glide

**Glide** también maneja automáticamente URLs `data:image`.

#### Paso 1: Agregar dependencia

```kotlin
// build.gradle.kts (Module: app)
dependencies {
    implementation("com.github.bumptech.glide:glide:4.16.0")
    kapt("com.github.bumptech.glide:compiler:4.16.0")
}
```

#### Paso 2: Usar en tu código

```kotlin
import com.bumptech.glide.Glide
import android.widget.ImageView

// En tu Activity, Fragment o ViewHolder
fun mostrarImagenProducto(imageView: ImageView, producto: Producto) {
    // Usar directamente el Base64 - Glide lo maneja automáticamente
    Glide.with(context)
        .load(producto.imagen) // "data:image/png;base64,..."
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.error)
        .into(imageView)
}

// Ejemplo completo
class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imagenView: ImageView = itemView.findViewById(R.id.imagen_producto)
    
    fun bind(producto: Producto) {
        Glide.with(itemView.context)
            .load(producto.imagen)
            .placeholder(R.drawable.placeholder)
            .into(imagenView)
    }
}
```

**✅ NO necesitas decodificar manualmente** - Glide maneja automáticamente las URLs `data:image`.

### Método 3: Decodificar Manualmente (Solo si es necesario)

**Solo usar este método si las librerías no funcionan o si necesitas más control.**

```kotlin
import android.util.Base64
import android.graphics.BitmapFactory
import android.widget.ImageView
import android.util.Log

fun mostrarImagenBase64(imageView: ImageView, base64String: String) {
    try {
        // Remover prefijo data:image si existe
        val base64Data = if (base64String.contains(",")) {
            base64String.substringAfter(",")
        } else {
            base64String
        }
        
        // Decodificar Base64 a bytes
        val imageBytes = Base64.decode(base64Data, Base64.DEFAULT)
        
        // Convertir bytes a Bitmap
        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        
        // Mostrar en ImageView
        imageView.setImageBitmap(bitmap)
    } catch (e: Exception) {
        Log.e("ImageError", "Error al decodificar Base64: ${e.message}", e)
        // Mostrar imagen de error
        imageView.setImageResource(R.drawable.error)
    }
}

// Uso
mostrarImagenBase64(binding.imageView, producto.imagen)
```

### Método 4: Parsear Array de Imágenes en Kotlin

```kotlin
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun parsearImagenesArray(imagenesJson: String): List<String> {
    return try {
        val gson = Gson()
        val listType = object : TypeToken<List<String>>() {}.type
        gson.fromJson<List<String>>(imagenesJson, listType)
    } catch (e: Exception) {
        Log.e("ParseError", "Error al parsear imágenes: ${e.message}", e)
        emptyList()
    }
}

// Uso
val imagenesArray = parsearImagenesArray(producto.imagenes)
imagenesArray.forEach { imagenBase64 ->
    // Usar cada imagen con Coil o Glide
    imageView.load(imagenBase64)
}
```

---

## Comparación de Métodos

### React/TypeScript

| Método | Dificultad | Recomendado |
|--------|-----------|-------------|
| Usar directamente `<img src={...} />` | ⭐ Fácil | ✅ Sí |
| Parsear JSON array | ⭐⭐ Media | ✅ Sí |
| Hook personalizado | ⭐⭐⭐ Avanzado | Opcional |

### Kotlin/Android

| Método | Dificultad | Recomendado |
|--------|-----------|-------------|
| Coil | ⭐ Fácil | ✅ Sí (Más moderno) |
| Glide | ⭐ Fácil | ✅ Sí (Más establecido) |
| Decodificar manualmente | ⭐⭐⭐ Avanzado | ❌ Solo si es necesario |

---

## Resumen

### ✅ SÍ puedes hacer (Recomendado)

**React/TypeScript:**
```typescript
<img src={producto.imagen} alt={producto.titulo} />
```

**Kotlin/Android con Coil:**
```kotlin
imageView.load(producto.imagen)
```

**Kotlin/Android con Glide:**
```kotlin
Glide.with(context).load(producto.imagen).into(imageView)
```

### ❌ NO necesitas hacer

**NO necesitas:**
- Decodificar manualmente el Base64 en React/TypeScript
- Decodificar manualmente el Base64 en Kotlin si usas Coil o Glide
- Usar `atob()` o `btoa()` en JavaScript
- Usar `Base64.decode()` en Kotlin si usas librerías

### ⚠️ Solo necesitas decodificar manualmente si:

- No usas librerías en Android (Coil/Glide)
- Necesitas más control sobre el proceso
- Las librerías no funcionan por alguna razón

---

## Ejemplos Completos

### React Component Completo

```typescript
import React from 'react';

interface Producto {
  id: number;
  titulo: string;
  imagen: string;
  imagenes: string;
  precio: number;
  descripcion: string;
}

export function ProductoCard({ producto }: { producto: Producto }) {
  const imagenesArray: string[] = JSON.parse(producto.imagenes || '[]');
  
  return (
    <div className="producto-card">
      <img 
        src={producto.imagen} 
        alt={producto.titulo}
        className="producto-imagen"
      />
      <h3>{producto.titulo}</h3>
      <p className="precio">${producto.precio.toLocaleString()}</p>
      <p className="descripcion">{producto.descripcion}</p>
      
      {imagenesArray.length > 0 && (
        <div className="gallery">
          <h4>Imágenes adicionales:</h4>
          {imagenesArray.map((img, index) => (
            <img 
              key={index} 
              src={img} 
              alt={`${producto.titulo} ${index + 1}`}
              className="gallery-image"
            />
          ))}
        </div>
      )}
    </div>
  );
}
```

### Kotlin RecyclerView Completo con Coil

```kotlin
// build.gradle.kts
dependencies {
    implementation("io.coil-kt:coil:2.5.0")
}

// Producto.kt
data class Producto(
    val id: Long,
    val titulo: String,
    val imagen: String,
    val imagenes: String,
    val precio: Double
)

// ProductoAdapter.kt
class ProductoAdapter(private val productos: List<Producto>) :
    RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {
    
    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imagenView: ImageView = itemView.findViewById(R.id.imagen_producto)
        private val tituloView: TextView = itemView.findViewById(R.id.titulo_producto)
        private val precioView: TextView = itemView.findViewById(R.id.precio_producto)
        
        fun bind(producto: Producto) {
            // Usar directamente el Base64 - Coil lo maneja automáticamente
            imagenView.load(producto.imagen) {
                crossfade(true)
                placeholder(R.drawable.placeholder)
                error(R.drawable.error)
            }
            
            tituloView.text = producto.titulo
            precioView.text = "$${producto.precio}"
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        holder.bind(productos[position])
    }
    
    override fun getItemCount() = productos.size
}
```

---

## Troubleshooting

### La imagen no se muestra en React

1. Verifica que el Base64 tenga el prefijo `data:image`
2. Verifica que el string no esté truncado
3. Verifica la consola del navegador para errores

### La imagen no se muestra en Kotlin con Coil/Glide

1. Verifica que la dependencia esté agregada correctamente
2. Verifica que el Base64 tenga el prefijo `data:image`
3. Revisa los logs de Coil/Glide para errores

### Error al parsear JSON array de imágenes

```typescript
// En React - manejar errores
try {
  const imagenesArray = JSON.parse(producto.imagenes || '[]');
} catch (e) {
  console.error('Error al parsear imágenes:', e);
  // Usar array vacío como fallback
  const imagenesArray = [];
}
```

```kotlin
// En Kotlin - manejar errores
val imagenesArray = try {
    gson.fromJson<List<String>>(producto.imagenes, listType)
} catch (e: Exception) {
    Log.e("ParseError", "Error al parsear imágenes: ${e.message}", e)
    emptyList()
}
```

---

## Conclusión

**NO necesitas decodificar manualmente el Base64** porque:

1. **React/TypeScript**: El navegador maneja automáticamente URLs `data:image`
2. **Kotlin/Android**: Librerías como Coil y Glide manejan automáticamente URLs `data:image`

Simplemente usa el string Base64 directamente como fuente de imagen.

