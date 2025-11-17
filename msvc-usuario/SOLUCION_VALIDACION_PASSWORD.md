# Solución: Error de Validación de Contraseña

## Problema Identificado

El error 400 muestra:
```
"password":"La contraseña debe tener entre 4 y 10 caracteres"
```

La validación de contraseña estaba limitada a **4-10 caracteres**, lo cual es muy restrictivo y no permite contraseñas seguras modernas.

## Solución

Se actualizó la validación de contraseña para permitir contraseñas más largas:

### Antes:
- Mínimo: 4 caracteres
- Máximo: 10 caracteres

### Después:
- Mínimo: 4 caracteres
- Máximo: 128 caracteres

## Archivos Modificados

1. **UsuarioCreationDTO.java**
   - Cambiado `@Size(min = 4, max = 10)` a `@Size(min = 4, max = 128)`

2. **Usuario.java** (entidad)
   - Cambiado `@Size(min = 4, max = 10)` a `@Size(min = 4, max = 128)`
   - Actualizado `@Column(length = 255)` para soportar contraseñas más largas (después de encriptación)

## Próximos Pasos

### 1. Rebuild del microservicio msvc-usuario

```bash
cd Backend_Java_Spring/Fullstack_Ecommerce/msvc-usuario
mvn clean package -DskipTests
```

### 2. Reiniciar el microservicio en AWS

```bash
# Detener proceso actual
pkill -f msvc-usuario

# Reiniciar con nuevo JAR
nohup java -jar msvc-usuario-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod > msvc-usuario.log 2>&1 &
```

### 3. Probar registro nuevamente

Después de reiniciar, el registro debería funcionar con contraseñas de cualquier longitud entre 4 y 128 caracteres.

## Notas

- La contraseña se encripta antes de guardarse en la base de datos, por lo que el campo `password` en la BD puede ser más largo que 128 caracteres (dependiendo del algoritmo de hash usado).
- El límite de 128 caracteres es razonable para contraseñas en texto plano antes de encriptación.
- Si necesitas contraseñas aún más largas, puedes aumentar el máximo a 255 o más, pero 128 es suficiente para la mayoría de casos de uso.

