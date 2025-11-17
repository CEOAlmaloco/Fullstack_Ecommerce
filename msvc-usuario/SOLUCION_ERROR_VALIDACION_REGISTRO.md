# Solución: Error de Validación en Registro de Usuario

## Problema

Error 500 al registrar usuario:
```
Validation failed for classes [com.ampuero.msvc.usuario.entities.Usuario] 
during persist time for groups [jakarta.validation.groups.Default]
```

## Causa

El método `crearUsuario` en `UsuarioServiceImpl` usa `BeanUtils.copyProperties()` para copiar datos del DTO a la entidad. Cuando algunos campos obligatorios vienen como `null` desde el DTO (aunque tengan valores por defecto en el DTO), se copian como `null` a la entidad.

La entidad `Usuario` tiene varios campos con `nullable = false` que requieren valores:
- `tipoUsuario` (default: CLIENTE)
- `estado` (default: ACTIVO)
- `fechaRegistro` (default: LocalDateTime.now())
- `emailVerificado` (default: false)
- `telefonoVerificado` (default: false)
- `aceptaTerminos` (default: false)
- `aceptaMarketing` (default: false)
- `puntosLevelUp` (default: 0)
- `nivelUsuario` (default: NOVATO)

Cuando `BeanUtils.copyProperties()` copia un `null`, la entidad queda con `null` y falla la validación de JPA al intentar persistir.

## Solución

Se modificó el método `crearUsuario` en `UsuarioServiceImpl.java` para establecer explícitamente valores por defecto para todos los campos obligatorios antes de guardar:

```java
// Establecer valores por defecto para campos obligatorios que pueden ser null
if (usuario.getTipoUsuario() == null) {
    usuario.setTipoUsuario(Usuario.TipoUsuario.CLIENTE);
}
if (usuario.getEstado() == null) {
    usuario.setEstado(Usuario.EstadoUsuario.ACTIVO);
}
if (usuario.getFechaRegistro() == null) {
    usuario.setFechaRegistro(LocalDateTime.now());
}
if (usuario.getEmailVerificado() == null) {
    usuario.setEmailVerificado(false);
}
if (usuario.getTelefonoVerificado() == null) {
    usuario.setTelefonoVerificado(false);
}
if (usuario.getAceptaTerminos() == null) {
    usuario.setAceptaTerminos(usuarioCreationDTO.getAceptaTerminos() != null ? usuarioCreationDTO.getAceptaTerminos() : false);
}
if (usuario.getAceptaMarketing() == null) {
    usuario.setAceptaMarketing(usuarioCreationDTO.getAceptaMarketing() != null ? usuarioCreationDTO.getAceptaMarketing() : false);
}
if (usuario.getPuntosLevelUp() == null) {
    usuario.setPuntosLevelUp(0);
}
if (usuario.getNivelUsuario() == null) {
    usuario.setNivelUsuario(Usuario.NivelUsuario.NOVATO);
}
```

## Archivo Modificado

- `Backend_Java_Spring/Fullstack_Ecommerce/msvc-usuario/src/main/java/com/ampuero/msvc/usuario/services/UsuarioServiceImpl.java`

## Próximos Pasos

1. Rebuild del microservicio `msvc-usuario`:
   ```bash
   cd Backend_Java_Spring/Fullstack_Ecommerce/msvc-usuario
   mvn clean package -DskipTests
   ```

2. Reiniciar el microservicio en AWS

3. Probar registro de usuario desde Kotlin

## Notas

- El DTO `UsuarioCreationDTO` tiene valores por defecto, pero estos no se aplican automáticamente cuando se usa `BeanUtils.copyProperties()`.
- La entidad `Usuario` tiene valores por defecto en los campos, pero estos solo se aplican cuando se crea una nueva instancia con el constructor por defecto, no cuando se copian propiedades desde un DTO.
- Esta solución asegura que todos los campos obligatorios tengan valores válidos antes de la persistencia.

