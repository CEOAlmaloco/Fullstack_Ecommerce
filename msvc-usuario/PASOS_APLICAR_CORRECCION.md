# Pasos para Aplicar Corrección de Validación en Registro

## Cambios Realizados

1. **UsuarioServiceImpl.java**: Se agregaron validaciones para establecer valores por defecto en campos obligatorios antes de persistir.
2. **GlobalExceptionHandler.java**: Se agregaron manejadores de excepciones para errores de validación de JPA.

## Pasos para Aplicar

### 1. Rebuild del Microservicio

```bash
cd Backend_Java_Spring/Fullstack_Ecommerce/msvc-usuario
mvn clean package -DskipTests
```

O desde el directorio raíz:
```bash
cd Backend_Java_Spring/Fullstack_Ecommerce
./build-all-services.sh
# O en Windows:
build-all-services.bat
```

### 2. Detener el Microservicio Actual

En AWS EC2:
```bash
# Buscar el proceso
ps aux | grep msvc-usuario

# Detener el proceso (reemplazar PID con el ID del proceso)
kill <PID>

# O si está corriendo con nohup:
pkill -f msvc-usuario
```

### 3. Copiar el Nuevo JAR

```bash
# Desde tu máquina local (si tienes acceso SSH)
scp target/msvc-usuario-0.0.1-SNAPSHOT.jar usuario@ec2-ip:/ruta/a/microservicios/

# O desde EC2, si compilaste allí:
cp target/msvc-usuario-0.0.1-SNAPSHOT.jar /ruta/a/microservicios/
```

### 4. Reiniciar el Microservicio

```bash
cd /ruta/a/microservicios
nohup java -jar msvc-usuario-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod > msvc-usuario.log 2>&1 &
```

O usar el script de inicio:
```bash
./start-all-services.sh
```

### 5. Verificar que Funciona

1. Revisar logs:
```bash
tail -f msvc-usuario.log
```

2. Probar registro desde Kotlin:
   - Abrir la app Android
   - Intentar registrar un nuevo usuario
   - Verificar que no aparezca el error 500

## Verificación

Si el error persiste después de reiniciar:

1. **Verificar logs completos**:
   ```bash
   tail -100 msvc-usuario.log | grep -i "error\|exception\|validation"
   ```

2. **Verificar que el JAR es nuevo**:
   ```bash
   ls -lh msvc-usuario-0.0.1-SNAPSHOT.jar
   # Verificar fecha de modificación
   ```

3. **Verificar que el puerto está libre**:
   ```bash
   netstat -tulpn | grep 8095
   ```

## Notas

- El error 500 seguirá apareciendo hasta que el microservicio se reinicie con el nuevo código.
- Los cambios aseguran que todos los campos obligatorios tengan valores por defecto antes de persistir.
- Los nuevos manejadores de excepciones proporcionarán mensajes de error más claros si hay problemas de validación.

