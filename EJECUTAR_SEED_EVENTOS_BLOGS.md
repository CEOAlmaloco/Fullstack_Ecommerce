# Ejecutar Seed de Eventos y Blogs en RDS

## Problema
- **Eventos**: El backend responde `400 - No hay eventos registrados`
- **Blogs**: El backend responde `0 blogs publicados`

## Solución
Ejecutar los scripts SQL de seed en las bases de datos correspondientes en RDS.

## Pasos

### 1. Conectar a RDS PostgreSQL

Desde tu máquina local o desde AWS EC2:

```bash
# Conectar a la base de datos de eventos
psql -h <RDS_ENDPOINT> -U <USUARIO> -d levelup_eventos

# Conectar a la base de datos de contenido (blogs)
psql -h <RDS_ENDPOINT> -U <USUARIO> -d levelup_contenido
```

O desde AWS Console:
1. Ir a RDS > Databases
2. Seleccionar la instancia
3. Usar "Query Editor" o conectarse vía EC2

### 2. Ejecutar seed_eventos.sql

**Base de datos**: `levelup_eventos`

**Ubicación del script**: 
```
Backend_Java_Spring/Fullstack_Ecommerce/msvc-eventos/seed_eventos.sql
```

**Contenido del script**:
- Inserta 7 eventos de ejemplo
- Usa `WHERE NOT EXISTS` para evitar duplicados
- Incluye verificación al final

**Comando**:
```sql
-- Copiar y pegar el contenido completo de seed_eventos.sql
-- O ejecutar desde archivo:
\i /ruta/a/seed_eventos.sql
```

**Verificar**:
```sql
SELECT COUNT(*) FROM eventos;
-- Debe retornar 7
```

### 3. Ejecutar seed_articulos.sql

**Base de datos**: `levelup_contenido`

**Ubicación del script**: 
```
Backend_Java_Spring/Fullstack_Ecommerce/msvc-contenido/seed_articulos.sql
```

**Contenido del script**:
- Inserta 6 artículos de ejemplo (blogs)
- Usa `WHERE NOT EXISTS` para evitar duplicados
- Asigna imágenes S3 según categoría
- Incluye verificación al final

**Comando**:
```sql
-- Copiar y pegar el contenido completo de seed_articulos.sql
-- O ejecutar desde archivo:
\i /ruta/a/seed_articulos.sql
```

**Verificar**:
```sql
SELECT COUNT(*) FROM articulos WHERE estado_articulo = 'PUBLICADO';
-- Debe retornar 6
```

### 4. Reiniciar microservicios (si es necesario)

Si los microservicios ya están corriendo, no es necesario reiniciarlos. Los datos estarán disponibles inmediatamente.

Si quieres reiniciar:
```bash
# En AWS EC2
cd /ruta/a/Fullstack_Ecommerce
./start-all-services.sh
```

### 5. Verificar en Kotlin

Después de ejecutar los scripts:
1. Reiniciar la app Android
2. Navegar a "Gaming Hub" (blogs)
3. Navegar a "Eventos"
4. Deben aparecer los datos

## Scripts completos

### seed_eventos.sql
Ubicación: `Backend_Java_Spring/Fullstack_Ecommerce/msvc-eventos/seed_eventos.sql`

### seed_articulos.sql
Ubicación: `Backend_Java_Spring/Fullstack_Ecommerce/msvc-contenido/seed_articulos.sql`

## Notas importantes

1. **Eventos**: Los eventos NO tienen imágenes (campo `imagen` es `NULL`). El backend asigna una imagen por defecto en `EventoController`.

2. **Blogs**: Los blogs SÍ tienen imágenes S3 según su categoría:
   - `TECNOLOGIA` o `NOTICIAS` → `img/ralidadv.jfif`
   - `LANZAMIENTOS` → `img/juegos_esperados.jpg`
   - Otros → `img/evento.jpg`

3. **Duplicados**: Los scripts usan `WHERE NOT EXISTS`, por lo que es seguro ejecutarlos múltiples veces.

4. **Estado**: Los artículos se insertan con `estado_articulo = 'PUBLICADO'` para que aparezcan en el endpoint `/contenido/articulos/publicados`.

## Troubleshooting

### Error: "relation eventos does not exist"
- Verificar que la tabla existe: `\dt` en psql
- Si no existe, verificar que `spring.jpa.hibernate.ddl-auto=update` está activo en `application-prod.properties`

### Error: "relation articulos does not exist"
- Verificar que la tabla existe: `\dt` en psql
- Si no existe, verificar que `spring.jpa.hibernate.ddl-auto=update` está activo en `application-prod.properties`

### Los datos no aparecen en Kotlin
- Verificar que los microservicios están corriendo
- Verificar logs del backend: `msvc-eventos` y `msvc-contenido`
- Verificar que el endpoint responde: `curl http://ec2-44-209-152-110.compute-1.amazonaws.com:8092/eventos`
- Verificar que el endpoint responde: `curl http://ec2-44-209-152-110.compute-1.amazonaws.com:8093/contenido/articulos/publicados`

