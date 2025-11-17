# Comandos para Reiniciar el Microservicio en AWS

## Paso 1: Matar el proceso actual

```bash
# El PID correcto es 2094 (el proceso java)
kill -9 2094

# Verificar que se detuvo
ps aux | grep msvc-productos
```

## Paso 2: Recompilar (si es necesario)

```bash
# Navegar al directorio del microservicio
cd msvc-productos

# Recompilar el proyecto
mvn clean package -DskipTests

# O si ya está compilado, solo verificar
ls -la target/msvc-productos-*.jar
```

## Paso 3: Reiniciar el microservicio

```bash
# Desde el directorio msvc-productos
nohup java -Xms256m -Xmx512m -jar target/msvc-productos-0.0.1-SNAPSHOT.jar > /tmp/msvc-productos.log 2>&1 &

# O si prefieres ver los logs en tiempo real
java -Xms256m -Xmx512m -jar target/msvc-productos-0.0.1-SNAPSHOT.jar
```

## Paso 4: Verificar que está funcionando

```bash
# Ver los logs
tail -f /tmp/msvc-productos.log

# O verificar que el proceso está corriendo
ps aux | grep msvc-productos

# Probar el endpoint
curl http://localhost:8003/api/v1/productos/carrusel
```

## Nota sobre el comando kill

- `kill 2094` - Envía señal TERM (terminación suave)
- `kill -9 2094` - Envía señal KILL (terminación forzada)
- El PID 5854 es el proceso `grep` mismo, NO debe matarse

