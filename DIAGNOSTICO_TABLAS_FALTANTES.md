# Diagnóstico: Tablas de Blogs y Eventos No Se Crean

## Problema
Las tablas `articulos` y `eventos` no existen en las bases de datos PostgreSQL, a pesar de que `spring.jpa.hibernate.ddl-auto=update` está configurado.

## Pasos de Diagnóstico

### 1. Verificar logs completos de los microservicios

```bash
# Ver los últimos 100 líneas de los logs
tail -100 msvc-contenido/contenido.log
tail -100 msvc-eventos/eventos.log

# Buscar mensajes de inicio de Spring Boot
grep -i "started\|application\|database\|jpa\|hibernate" msvc-contenido/contenido.log | tail -20
grep -i "started\|application\|database\|jpa\|hibernate" msvc-eventos/eventos.log | tail -20
```

### 2. Verificar variables de entorno y configuración

```bash
# Verificar que los servicios estén usando el perfil correcto
ps aux | grep msvc-contenido | grep -i "spring"
ps aux | grep msvc-eventos | grep -i "spring"

# Verificar las variables de entorno del proceso
# (Reemplazar PID con el PID real del proceso)
cat /proc/$(pgrep -f msvc-contenido | head -1)/environ | tr '\0' '\n' | grep -i "DB\|SPRING"
cat /proc/$(pgrep -f msvc-eventos | head -1)/environ | tr '\0' '\n' | grep -i "DB\|SPRING"
```

### 3. Verificar conexión a la base de datos

```bash
# Probar conexión manual a las bases de datos
psql -h levelup-db.cvcqjuf3hjcs.us-east-1.rds.amazonaws.com \
     -p 5432 \
     -U levelup_admin \
     -d levelup_contenido \
     -c "SELECT version();"

psql -h levelup-db.cvcqjuf3hjcs.us-east-1.rds.amazonaws.com \
     -p 5432 \
     -U levelup_admin \
     -d levelup_eventos \
     -c "SELECT version();"
```

### 4. Verificar que los microservicios se iniciaron correctamente

```bash
# Verificar que los servicios están corriendo
ps aux | grep msvc-contenido
ps aux | grep msvc-eventos

# Verificar que están escuchando en los puertos correctos
netstat -tuln | grep -E "8093|8092"
```

### 5. Forzar creación de tablas (si es necesario)

Si los logs muestran que los servicios se iniciaron pero las tablas no se crearon, puede ser un problema de permisos o configuración. En ese caso:

```bash
# Detener los servicios
pkill -f msvc-contenido
pkill -f msvc-eventos

# Verificar que se detuvieron
ps aux | grep -E "msvc-contenido|msvc-eventos"

# Reiniciar solo estos dos servicios
cd msvc-contenido
nohup java -Xms256m -Xmx512m \
  -Dspring.profiles.active=prod \
  -jar target/msvc-contenido-0.0.1-SNAPSHOT.jar > ../msvc-contenido/contenido.log 2>&1 &

cd ../msvc-eventos
nohup java -Xms256m -Xmx512m \
  -Dspring.profiles.active=prod \
  -jar target/msvc-eventos-0.0.1-SNAPSHOT.jar > ../msvc-eventos/eventos.log 2>&1 &

# Esperar 30 segundos y verificar los logs
sleep 30
tail -50 msvc-contenido/contenido.log
tail -50 msvc-eventos/eventos.log

# Verificar que las tablas se crearon
psql -h levelup-db.cvcqjuf3hjcs.us-east-1.rds.amazonaws.com \
     -p 5432 \
     -U levelup_admin \
     -d levelup_contenido \
     -c "\dt"

psql -h levelup-db.cvcqjuf3hjcs.us-east-1.rds.amazonaws.com \
     -p 5432 \
     -U levelup_admin \
     -d levelup_eventos \
     -c "\dt"
```

## Posibles Causas

1. **Variables de entorno no configuradas**: Los servicios pueden no estar recibiendo las credenciales de la base de datos.
2. **Error de conexión**: Los servicios pueden estar fallando al conectarse a PostgreSQL.
3. **Permisos insuficientes**: El usuario de la base de datos puede no tener permisos para crear tablas.
4. **Configuración incorrecta**: Las URLs de conexión pueden estar mal configuradas.

## Solución Temporal: Crear Tablas Manualmente

Si después de todos los diagnósticos las tablas aún no se crean, se pueden crear manualmente usando los scripts SQL que JPA genera. Sin embargo, es mejor solucionar el problema raíz.

