# Solución: Límite de Conexiones en RDS

## Problema
Error: `remaining connection slots are reserved for roles with privileges of the "rds_reserved" role`

Esto significa que la base de datos PostgreSQL ha alcanzado el límite máximo de conexiones concurrentes.

## Solución Rápida: Liberar Conexiones

### Opción 1: Matar Conexiones Inactivas (Recomendado)

Necesitas conectarte como superusuario o usar una conexión existente. Si tienes acceso a otra base de datos o puedes usar el usuario master:

```bash
# Intentar con el usuario master (si existe)
psql -h levelup-db.cvcqjuf3hjcs.us-east-1.rds.amazonaws.com \
     -p 5432 \
     -U postgres \
     -d postgres

# O intentar con levelup_admin pero a la base de datos postgres
psql -h levelup-db.cvcqjuf3hjcs.us-east-1.rds.amazonaws.com \
     -p 5432 \
     -U levelup_admin \
     -d postgres
```

Una vez conectado, ejecutar:

```sql
-- Ver todas las conexiones activas
SELECT 
    pid,
    usename,
    datname,
    application_name,
    client_addr,
    state,
    query_start,
    now() - query_start AS duration,
    query
FROM pg_stat_activity
WHERE datname IN ('levelup_productos', 'levelup_contenido', 'levelup_eventos')
ORDER BY query_start;

-- Matar conexiones inactivas (idle) de más de 5 minutos
SELECT pg_terminate_backend(pid)
FROM pg_stat_activity
WHERE datname IN ('levelup_productos', 'levelup_contenido', 'levelup_eventos')
  AND state = 'idle'
  AND now() - query_start > interval '5 minutes'
  AND pid != pg_backend_pid();

-- Matar conexiones de aplicaciones específicas (si es necesario)
SELECT pg_terminate_backend(pid)
FROM pg_stat_activity
WHERE application_name LIKE '%msvc%'
  AND state = 'idle'
  AND now() - query_start > interval '2 minutes'
  AND pid != pg_backend_pid();
```

### Opción 2: Reducir Pool de Conexiones en Microservicios

Los microservicios están configurados con `maximum-pool-size=3`, pero con 15 microservicios, eso son 45 conexiones solo para los pools. Necesitamos reducir esto.

Editar `application-prod.properties` en cada microservicio:

```properties
# Reducir el pool de conexiones
spring.datasource.hikari.maximum-pool-size=2
spring.datasource.hikari.minimum-idle=1
```

Luego recompilar y reiniciar los servicios.

### Opción 3: Reiniciar Microservicios (Libera Conexiones)

```bash
# Detener todos los servicios
pkill -9 java

# Esperar 10 segundos para que las conexiones se liberen
sleep 10

# Reiniciar solo los servicios críticos primero
./start-all-services.sh
```

### Opción 4: Aumentar Límite de Conexiones en RDS

Si tienes acceso a la consola de AWS:
1. Ir a RDS → Parameter Groups
2. Editar el parámetro `max_connections`
3. Aplicar al grupo de parámetros de la instancia

**NOTA**: El límite depende del tipo de instancia RDS. Para instancias pequeñas (db.t3.micro, db.t3.small) el límite es bajo.

## Verificar Conexiones Después

```sql
-- Ver cuántas conexiones hay por base de datos
SELECT 
    datname,
    count(*) as connections,
    count(*) FILTER (WHERE state = 'active') as active,
    count(*) FILTER (WHERE state = 'idle') as idle
FROM pg_stat_activity
WHERE datname IN ('levelup_productos', 'levelup_contenido', 'levelup_eventos')
GROUP BY datname;

-- Ver el límite máximo
SHOW max_connections;
```

## Solución Permanente

1. **Reducir pool de conexiones** a 1-2 por microservicio
2. **Usar connection pooling a nivel de aplicación** (si es posible)
3. **Aumentar el tamaño de la instancia RDS** si es necesario
4. **Considerar usar RDS Proxy** para manejar mejor las conexiones

