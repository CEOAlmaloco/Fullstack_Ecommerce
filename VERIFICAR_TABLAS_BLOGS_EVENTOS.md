# Verificar y Crear Tablas de Blogs y Eventos

## Problema Identificado

Las tablas `articulos` y `eventos` no existen en las bases de datos PostgreSQL, lo que impide que los blogs y eventos se muestren en la aplicación.

## Solución

Las tablas deberían crearse automáticamente con JPA/Hibernate si `spring.jpa.hibernate.ddl-auto=update` está configurado. Si no se crean, hay dos opciones:

### Opción 1: Verificar que JPA cree las tablas automáticamente

1. Verificar que los microservicios estén usando el perfil `prod`:
```bash
ps aux | grep java | grep SPRING_PROFILES_ACTIVE
```

2. Verificar los logs de los microservicios para ver si hay errores:
```bash
tail -f msvc-contenido/contenido.log
tail -f msvc-eventos/eventos.log
```

3. Si las tablas no se crean automáticamente, reiniciar los microservicios:
```bash
# Detener todos los servicios
pkill -9 java

# Reiniciar
./start-all-services.sh
```

### Opción 2: Crear las tablas manualmente (si JPA no las crea)

Si después de reiniciar las tablas siguen sin existir, ejecutar estos scripts SQL:

#### Para Blogs (msvc-contenido):

```sql
-- Conectarse a la BD
psql -h levelup-db.cvcqjuf3hjcs.us-east-1.rds.amazonaws.com \
     -p 5432 \
     -U levelup_admin \
     -d levelup_contenido

-- Verificar si la tabla existe
\dt articulos

-- Si no existe, JPA debería crearla automáticamente al iniciar el microservicio
-- Si aún no se crea, verificar los logs del microservicio
```

#### Para Eventos (msvc-eventos):

```sql
-- Conectarse a la BD
psql -h levelup-db.cvcqjuf3hjcs.us-east-1.rds.amazonaws.com \
     -p 5432 \
     -U levelup_admin \
     -d levelup_eventos

-- Verificar si la tabla existe
\dt eventos

-- Si no existe, JPA debería crearla automáticamente al iniciar el microservicio
```

## Verificar el Carrusel

El código Java del carrusel está correcto y devuelve:
- `ralidadv.jfif`
- `juegos_esperados.jpg`
- `evento.jpg`

Si el carrusel muestra URLs incorrectas, el backend necesita recompilarse:

```bash
# En AWS, recompilar el microservicio de productos
cd msvc-productos
mvn clean package -DskipTests

# Reiniciar el servicio
pkill -f msvc-productos
nohup java -Xms256m -Xmx512m -jar target/msvc-productos-0.0.1-SNAPSHOT.jar > productos.log 2>&1 &
```

## Verificar que los cambios se aplicaron

1. Verificar que las tablas existen:
```sql
-- En levelup_contenido
SELECT COUNT(*) FROM articulos;

-- En levelup_eventos
SELECT COUNT(*) FROM eventos;
```

2. Probar el endpoint del carrusel:
```bash
curl http://localhost:8094/productos/carrusel
```

Debería devolver las 3 URLs correctas.

