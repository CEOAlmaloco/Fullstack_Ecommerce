# 🔐 Implementación de API Keys para Seguridad

## 📋 Situación Actual

**Actual:**
- ✅ **JWT Tokens** para autenticación de usuarios (Bearer token)
- ❌ **NO hay API Keys** para validar origen de las peticiones
- ⚠️ Cualquier cliente puede hacer peticiones a los microservicios

**Problema:**
- Sin API keys, cualquier cliente puede consumir tus APIs
- No hay validación del origen de las peticiones
- Vulnerable a scraping y abuso

---

## ✅ Solución: Implementar API Keys

### Arquitectura

```
Frontend/App Móvil
    ↓
    Incluye: X-API-Key header
    ↓
Gateway/Microservicios
    ↓
    Valida API Key
    ↓
    Si válida → Procesa request
    Si inválida → 401 Unauthorized
```

### Ventajas

- ✅ **Validación de origen**: Solo tu frontend/app pueden usar las APIs
- ✅ **Control de acceso**: Puedes revocar API keys fácilmente
- ✅ **Rate limiting**: Puedes limitar requests por API key
- ✅ **Monitoreo**: Puedes rastrear qué cliente hace qué requests
- ✅ **Capa adicional de seguridad**: Incluso si alguien obtiene un JWT, necesita la API key

---

## 🔑 Tipos de API Keys

### 1. API Key Simple (Recomendado para tu caso)
- Una clave compartida entre frontend y backend
- Fácil de implementar
- Suficiente para proyecto escolar

### 2. API Key por Cliente (Avanzado)
- Diferentes keys para frontend web y app móvil
- Permite control granular
- Más complejo de gestionar

---

## 📝 Plan de Implementación

1. **Crear componente de validación de API Key** (Backend)
2. **Configurar API Key en variables de entorno** (Backend)
3. **Actualizar frontend** para incluir API Key en headers
4. **Actualizar app Kotlin** para incluir API Key
5. **Documentar** cómo generar y rotar API keys

---

## 🚀 Implementación Rápida

Ver archivos:
- `ApiKeyFilter.java` - Filtro para validar API keys
- `ApiKeyConfig.java` - Configuración de API keys
- `update-frontend-api-key.sh` - Script para actualizar frontend

---

## 📱 Configuración para App Kotlin

```kotlin
object ApiConfig {
    const val API_KEY = "tu-api-key-segura-aqui"
    
    // Interceptor para agregar API key
    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("X-API-Key", API_KEY)
                    .build()
                chain.proceed(request)
            }
            .build()
    }
}
```

