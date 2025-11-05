# Configuración de API Key - Guía Completa

Esta guía explica cómo configurar el API Key en Postman y otros clientes HTTP para acceder a los microservicios.

## ⚠️ Error Común: 401 Unauthorized

Si recibes este error:
```json
{
    "error": "Unauthorized",
    "message": "Invalid or missing API key. Please include X-API-Key header.",
    "status": 401
}
```

**Significa que no estás enviando el header `X-API-Key` o el valor es incorrecto.**

## Valor del API Key

El API Key por defecto para desarrollo es:
```
levelup-2024-secret-api-key-change-in-production
```

## Configuración en Postman

### Método 1: Configurar a Nivel de Colección (Recomendado)

Este método aplica el API Key a todos los requests de la colección automáticamente.

1. **Crear o abrir una colección en Postman**

2. **Configurar variable de colección:**
   - Haz clic en la colección
   - Ve a la pestaña "Variables"
   - Agrega una nueva variable:
     - **Nombre**: `api_key`
     - **Valor inicial**: `levelup-2024-secret-api-key-change-in-production`
     - **Valor actual**: `levelup-2024-secret-api-key-change-in-production`
   - Guarda los cambios

3. **Configurar Authorization a nivel de colección:**
   - Haz clic en la colección
   - Ve a la pestaña "Authorization"
   - Selecciona "API Key" como tipo
   - Configura:
     - **Key**: `X-API-Key`
     - **Value**: `{{api_key}}`
     - **Add to**: `Header`
   - Guarda los cambios

4. **Aplicar a todos los requests:**
   - En la pestaña "Authorization" de la colección
   - Selecciona "Inherit auth from parent" en cada request (esto es automático)

### Método 2: Configurar a Nivel de Request Individual

Si prefieres configurar el header en cada request individualmente:

1. Abre el request en Postman
2. Ve a la pestaña "Headers"
3. Agrega un nuevo header:
   - **Key**: `X-API-Key`
   - **Value**: `levelup-2024-secret-api-key-change-in-production`
4. Guarda el request

### Método 3: Usar Pre-request Script (Avanzado)

Para configurar el header automáticamente con un script:

1. En la colección o request, ve a la pestaña "Pre-request Script"
2. Agrega este código:
```javascript
pm.request.headers.add({
    key: 'X-API-Key',
    value: 'levelup-2024-secret-api-key-change-in-production'
});
```

## Configuración en Thunder Client (VS Code)

1. Abre Thunder Client en VS Code
2. Crea una nueva colección o abre una existente
3. Ve a "Collection Variables"
4. Agrega una variable:
   - **Name**: `api_key`
   - **Value**: `levelup-2024-secret-api-key-change-in-production`
5. En cada request, ve a "Headers"
6. Agrega:
   - **Key**: `X-API-Key`
   - **Value**: `{{api_key}}`

## Configuración en cURL

```bash
curl -X GET "http://localhost:8094/productos" \
  -H "X-API-Key: levelup-2024-secret-api-key-change-in-production"
```

## Configuración en JavaScript (Fetch)

```javascript
fetch('http://localhost:8094/productos', {
  method: 'GET',
  headers: {
    'X-API-Key': 'levelup-2024-secret-api-key-change-in-production',
    'Content-Type': 'application/json'
  }
})
.then(response => response.json())
.then(data => console.log(data));
```

## Configuración en Axios

```javascript
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8094',
  headers: {
    'X-API-Key': 'levelup-2024-secret-api-key-change-in-production'
  }
});

// Usar la instancia
api.get('/productos')
  .then(response => console.log(response.data));
```

## Configuración en React

```javascript
// config/api.js
export const API_CONFIG = {
  baseURL: 'http://localhost:8094',
  headers: {
    'X-API-Key': 'levelup-2024-secret-api-key-change-in-production',
    'Content-Type': 'application/json'
  }
};

// En tu componente o servicio
import axios from 'axios';
import { API_CONFIG } from '../config/api';

const api = axios.create(API_CONFIG);

export const getProductos = () => {
  return api.get('/productos');
};
```

## Configuración en Kotlin (Android)

```kotlin
// ApiConfig.kt
object ApiConfig {
    const val BASE_URL = "http://10.0.2.2:8094/"
    const val API_KEY = "levelup-2024-secret-api-key-change-in-production"
}

// ProductoService.kt
interface ProductoService {
    @GET("productos")
    suspend fun getProductos(
        @Header("X-API-Key") apiKey: String = ApiConfig.API_KEY
    ): Response<List<Producto>>
}
```

## Verificar que el API Key Funciona

### Prueba Rápida en Postman

1. Crea un nuevo GET request
2. URL: `http://localhost:8094/productos`
3. Headers: `X-API-Key: levelup-2024-secret-api-key-change-in-production`
4. Envía el request

**Si funciona correctamente**, deberías recibir una lista de productos (puede estar vacía si no hay datos).

**Si no funciona**, verifica:
- ✅ El header `X-API-Key` está presente
- ✅ El valor es exactamente: `levelup-2024-secret-api-key-change-in-production`
- ✅ No hay espacios extra al inicio o final
- ✅ El microservicio está corriendo
- ✅ El puerto es correcto (8094 para gateway, 8003 para productos directo)

## Cambiar el API Key

Si necesitas cambiar el API Key:

1. **En el microservicio:**
   - Edita `application.properties` o `application-dev.properties`
   - Cambia el valor de `api.key`
   - Reinicia el microservicio

2. **En Postman:**
   - Actualiza la variable `api_key` en la colección
   - O actualiza el header en cada request

## Deshabilitar API Key (Solo para Desarrollo)

Si quieres deshabilitar temporalmente el API Key para desarrollo:

1. Edita `application-dev.properties` del microservicio
2. Cambia:
   ```properties
   api.key.enabled=false
   ```
3. Reinicia el microservicio

**⚠️ ADVERTENCIA**: Nunca deshabilites el API Key en producción.

## Troubleshooting

### Error 401: Unauthorized

**Causa**: El header `X-API-Key` no está presente o el valor es incorrecto.

**Solución**:
1. Verifica que el header esté configurado en Postman
2. Verifica que el valor sea exactamente: `levelup-2024-secret-api-key-change-in-production`
3. Verifica que no haya espacios extra

### Error 404: Not Found

**Causa**: El endpoint no existe o la URL es incorrecta.

**Solución**:
1. Verifica la URL del endpoint
2. Verifica que el microservicio esté corriendo
3. Verifica el puerto (8094 para gateway)

### Error 500: Internal Server Error

**Causa**: Error en el servidor.

**Solución**:
1. Revisa los logs del microservicio
2. Verifica que la base de datos esté configurada correctamente
3. Verifica que los datos iniciales se hayan cargado

---

## Ejemplo Completo de Request en Postman

```
Method: GET
URL: http://localhost:8094/productos
Headers:
  X-API-Key: levelup-2024-secret-api-key-change-in-production
  Content-Type: application/json
```

---

## Notas Importantes

1. **El API Key es requerido** para todos los endpoints excepto:
   - `/health`
   - `/h2-console/**`
   - `/doc/**`
   - `/swagger-ui/**`
   - `/v3/api-docs/**`

2. **En producción**, cambia el API Key por uno más seguro:
   ```bash
   openssl rand -hex 32
   ```

3. **El API Key es el mismo** para todos los microservicios en desarrollo.

4. **El Gateway** puede requerir el API Key también, dependiendo de su configuración.

