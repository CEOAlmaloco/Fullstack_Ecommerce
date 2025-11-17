# Solución: Rutas de Imágenes Antiguas

## Problema

El backend sigue devolviendo rutas antiguas como:
- `img/consolas/2.png` ❌
- `img/perifericos/1.png` ❌

En lugar de las rutas nuevas:
- `img/play4.png` ✅
- `img/audilogitech.png` ✅

## Solución Rápida

### Paso 1: Verificar qué Base de Datos está usando el Backend

En AWS, ejecuta:

```bash
# Ver las variables de entorno del proceso Java
ps aux | grep java | grep msvc-productos | head -1

# O verificar en los logs al inicio (busca "datasource" o "h2" o "postgres")
grep -i "datasource\|h2\|postgres" msvc-productos/productos.log | head -5
```

### Paso 2A: Si usa H2 (más probable en desarrollo)

1. Abre en el navegador: `http://44.209.152.110:8003/h2-console`
2. Credenciales:
   - **JDBC URL**: `jdbc:h2:file:./data/msvc_productos_dev`
   - **Usuario**: `sa`
   - **Password**: (vacío)
3. Ejecuta el script `verificar_y_actualizar_rutas.sql`

### Paso 2B: Si usa PostgreSQL

1. Conéctate a PostgreSQL:
```bash
psql -h levelup-db.cvcqjuf3hjcs.us-east-1.rds.amazonaws.com \
     -p 5432 \
     -U levelup_admin \
     -d postgres
```

2. Ejecuta el script `verificar_y_actualizar_rutas.sql`

### Paso 3: Verificar que se actualizaron

Después de ejecutar el script, verifica:

```sql
SELECT codigo_producto, titulo, imagen 
FROM productos 
WHERE imagen LIKE '%consolas/%' OR imagen LIKE '%perifericos/%';
```

Si esta consulta devuelve 0 filas, las rutas se actualizaron correctamente.

### Paso 4: Reiniciar el microservicio (opcional)

Si los cambios no se reflejan inmediatamente:

```bash
# Encontrar el PID
ps aux | grep "msvc-productos" | grep -v grep

# Matar el proceso (reemplaza PID)
kill -9 PID

# Reiniciar
cd msvc-productos
nohup java -Xms256m -Xmx512m -jar target/msvc-productos-0.0.1-SNAPSHOT.jar > productos.log 2>&1 &
```

## Nota sobre Ctrl+Z

Si usaste `Ctrl+Z` para salir de psql, el proceso quedó suspendido. Para terminarlo:

```bash
# Ver procesos suspendidos
jobs

# Terminar el proceso suspendido
kill %1  # o el número que muestre jobs
```

O simplemente ignóralo, no afecta el funcionamiento del backend.
