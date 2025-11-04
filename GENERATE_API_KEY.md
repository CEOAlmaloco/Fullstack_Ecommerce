# 🔑 Generar y Configurar API Keys

## 📋 Resumen

Ahora el sistema usa **API Keys** además de JWT tokens para mayor seguridad.

**Flujo:**
```
Frontend/App Móvil
    ↓
    Header: X-API-Key: tu-api-key-segura
    ↓
Gateway/Microservicios
    ↓
    Valida API Key
    ↓
    Si válida → Continúa con JWT validation
    Si inválida → 401 Unauthorized
```

---

## 🔑 Generar API Key Segura

### Opción 1: Usando OpenSSL (Recomendado)
```bash
openssl rand -hex 32
```

**Ejemplo de salida:**
```
a1b2c3d4e5f6789012345678901234567890abcdef1234567890abcdef123456
```

### Opción 2: Usando Python
```bash
python -c "import secrets; print(secrets.token_hex(32))"
```

### Opción 3: Online Generator
- https://randomkeygen.com/
- Genera una clave de 64 caracteres hexadecimal

---

## ⚙️ Configurar API Key en Backend

### 1. Gateway (msvc-gateway)

Editar `msvc-gateway/src/main/resources/application.properties`:

```properties
api.key=${API_KEY:tu-api-key-generada-aqui}
api.key.enabled=${API_KEY_ENABLED:true}
```

O usar variable de entorno:
```bash
export API_KEY="tu-api-key-generada-aqui"
```

### 2. Microservicios Individuales

Editar cada `application.properties`:

```properties
api.key=${API_KEY:tu-api-key-generada-aqui}
api.key.enabled=${API_KEY_ENABLED:true}
```

**Usar la misma API key en todos los microservicios.**

---

## 🌐 Configurar API Key en Frontend

### 1. Crear archivo `.env.production`
```bash
VITE_API_KEY=tu-api-key-generada-aqui
VITE_API_BASE_URL=https://api.tudominio.com/api/v1
```

### 2. Actualizar `.env` local para desarrollo
```bash
VITE_API_KEY=levelup-2024-secret-api-key-change-in-production
VITE_API_BASE_URL=http://localhost:8080/api/v1
```

### 3. Recompilar frontend
```bash
npm run build
```

**El interceptor de axios ya está configurado** para incluir la API key automáticamente.

---

## 📱 Configurar API Key en App Kotlin

### 1. Actualizar `ApiConfig.kt`

```kotlin
object ApiConfig {
    // API Key compartida con backend
    private const val API_KEY = "tu-api-key-generada-aqui"
    
    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                
                // Agregar API Key a todos los requests
                val newRequest = originalRequest.newBuilder()
                    .addHeader("X-API-Key", API_KEY)
                    // Agregar JWT si existe
                    .addHeader("Authorization", "Bearer ${getAuthToken()}")
                    .build()
                
                chain.proceed(newRequest)
            }
            .build()
    }
    
    private fun getAuthToken(): String? {
        // Obtener token de SharedPreferences o DataStore
        return sharedPreferences.getString("auth_token", null)
    }
}
```

---

## 🚀 Configurar en AWS

### Variables de Entorno en EC2

```bash
# En EC2 Backend, editar /home/ec2-user/.env
export API_KEY="tu-api-key-generada-aqui"
export API_KEY_ENABLED="true"
```

### En `application-prod.properties`

```properties
api.key=${API_KEY}
api.key.enabled=${API_KEY_ENABLED:true}
```

---

## 🔒 Seguridad de API Keys

### ✅ Buenas Prácticas

1. **NO commitear API keys en Git**
   - Agregar a `.gitignore`
   - Usar variables de entorno
   - Usar secrets managers (AWS Secrets Manager)

2. **Rotar API keys periódicamente**
   - Cambiar cada 3-6 meses
   - Notificar a todos los clientes

3. **Usar diferentes keys por entorno**
   - Dev: `levelup-dev-xxx`
   - Prod: `levelup-prod-xxx`

4. **Validar origen (opcional)**
   - Verificar IP del cliente
   - Rate limiting por API key

### ❌ NO Hacer

- ❌ Hardcodear API keys en código
- ❌ Compartir API keys en chat/email
- ❌ Usar la misma key en dev y prod
- ❌ Loggear API keys en logs

---

## 🧪 Verificar que Funciona

### Test desde Terminal

```bash
# Sin API key (debe fallar)
curl http://localhost:8003/api/v1/productos

# Con API key válida (debe funcionar)
curl -H "X-API-Key: tu-api-key-aqui" http://localhost:8003/api/v1/productos

# Con API key inválida (debe fallar)
curl -H "X-API-Key: api-key-invalida" http://localhost:8003/api/v1/productos
```

### Test desde Frontend

Abrir consola del navegador y verificar que:
1. Requests incluyen header `X-API-Key`
2. Sin API key, recibe 401 Unauthorized
3. Con API key válida, funciona normalmente

---

## 🔄 Rotar API Key

### 1. Generar Nueva API Key
```bash
openssl rand -hex 32 > nueva-api-key.txt
```

### 2. Actualizar Backend
- Actualizar en `application.properties` o variables de entorno
- Reiniciar microservicios

### 3. Actualizar Frontend
- Actualizar `.env.production`
- Recompilar y redesplegar

### 4. Actualizar App Móvil
- Actualizar `ApiConfig.kt`
- Compilar nueva versión

### 5. Verificar
- Probar que todo funciona
- Revocar antigua API key después de 24-48 horas

---

## 📝 Checklist

- [ ] API key generada (64 caracteres hexadecimal)
- [ ] API key configurada en Gateway
- [ ] API key configurada en todos los microservicios
- [ ] API key configurada en frontend (`.env.production`)
- [ ] API key configurada en app Kotlin
- [ ] Verificar que funciona con API key
- [ ] Verificar que falla sin API key
- [ ] API key agregada a `.gitignore`
- [ ] Documentación actualizada

---

## 🎉 ¡Listo!

Ahora tienes **doble capa de seguridad**:
1. ✅ **API Key**: Valida que el cliente esté autorizado
2. ✅ **JWT Token**: Valida que el usuario esté autenticado

**Nadie puede usar tus APIs sin tu API key** 🔒

