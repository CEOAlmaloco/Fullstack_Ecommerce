# Debug: Error al Iniciar msvc-usuario

## Pasos para Obtener el Error Completo

### Opción 1: Ejecutar con -e (errors)

```bash
cd Backend_Java_Spring/Fullstack_Ecommerce/msvc-usuario
mvn spring-boot:run -e
```

### Opción 2: Ejecutar con -X (debug)

```bash
cd Backend_Java_Spring/Fullstack_Ecommerce/msvc-usuario
mvn spring-boot:run -X
```

### Opción 3: Compilar y Ver Errores

```bash
cd Backend_Java_Spring/Fullstack_Ecommerce/msvc-usuario
mvn clean compile
```

## Posibles Causas del Error

1. **Problema con PasswordEncoder**: Ya corregido - ahora se inyecta el bean en lugar de crear uno nuevo
2. **Problema con la entidad Usuario**: Verificar que todos los campos estén correctamente definidos
3. **Problema con la base de datos**: Verificar que H2 esté configurado correctamente
4. **Problema con dependencias**: Verificar que todas las dependencias estén en el `pom.xml`

## Correcciones Aplicadas

- ✅ Corregido `UsuarioDataInitializer` para inyectar `PasswordEncoder` en lugar de crear uno nuevo
- ✅ Removido import innecesario de `BCryptPasswordEncoder`

## Siguiente Paso

Ejecuta el microservicio con `-e` o `-X` para ver el error completo y compártelo para poder solucionarlo.

