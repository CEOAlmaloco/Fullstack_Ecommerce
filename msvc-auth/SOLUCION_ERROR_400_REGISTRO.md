# Solución: Error 400 en Registro de Usuario

## Progreso

El error cambió de **500 (Internal Server Error)** a **400 (Bad Request)**, lo que indica que:
1. ✅ Los cambios en `msvc-usuario` se aplicaron correctamente
2. ✅ El error de validación ahora se maneja correctamente
3. ⚠️ Hay un problema con los datos que se están enviando

## Cambios Realizados en msvc-auth

### 1. Mejora en el manejo de campos opcionales
- Solo se envían campos opcionales si tienen valor (no se envían strings vacíos o null)
- Esto evita problemas de validación con campos que pueden ser null

### 2. Logging mejorado
- Se agregó logging de los datos que se envían (sin password por seguridad)
- Se agregó manejo específico de errores 400 con mensajes más claros

### 3. Manejo de errores mejorado
- Errores 400 ahora muestran el mensaje completo del backend
- Errores de Feign ahora se capturan y muestran información detallada

## Archivos Modificados

- `Backend_Java_Spring/Fullstack_Ecommerce/msvc-auth/src/main/java/com/ampuero/msvc/auth/services/AuthServiceImpl.java`

## Próximos Pasos

### 1. Rebuild del microservicio msvc-auth

```bash
cd Backend_Java_Spring/Fullstack_Ecommerce/msvc-auth
mvn clean package -DskipTests
```

### 2. Reiniciar el microservicio en AWS

```bash
# Detener proceso actual
pkill -f msvc-auth

# Reiniciar con nuevo JAR
nohup java -jar msvc-auth-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod > msvc-auth.log 2>&1 &
```

### 3. Probar registro y revisar logs

Después de reiniciar, intentar registrar un usuario y revisar los logs:

```bash
tail -f msvc-auth.log | grep -i "registrando usuario\|error 400\|error de validación"
```

Los logs ahora mostrarán:
- Los datos exactos que se están enviando (sin password)
- El mensaje completo del error 400 del backend

## Posibles Causas del Error 400

1. **Campos requeridos faltantes**: Alguno de los campos obligatorios (`nombre`, `apellido`, `correo`, `password`) no se está enviando correctamente
2. **Validación de tamaño**: Algún campo no cumple con las restricciones de tamaño (nombre/apellido: 2-50 caracteres, password: 4-10 caracteres)
3. **Formato de email inválido**: El correo no cumple con el formato de email válido
4. **Fecha de nacimiento**: Si se envía, debe estar en formato ISO (YYYY-MM-DD)

## Verificación

Con los nuevos logs, podrás ver:
1. Qué datos exactos se están enviando a `msvc-usuario`
2. El mensaje completo del error 400 que retorna el backend
3. Qué campo específico está fallando la validación

## Notas

- El error 400 es mejor que el 500 porque indica que el problema está en los datos, no en el código
- Los nuevos logs ayudarán a identificar exactamente qué campo está causando el problema
- Una vez identificado el campo problemático, se puede corregir en el frontend (Kotlin) o en el mapeo de datos en `AuthServiceImpl`

