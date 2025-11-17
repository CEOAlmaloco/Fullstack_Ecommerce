# Solución: seed_productos.sql con Rutas Antiguas

## Problema

El backend está ejecutando `seed_productos.sql` en producción que contiene rutas antiguas de imágenes:
- `img/consolas/4.png` en lugar de `img/play5white.png`
- `img/consolas/2.png` en lugar de `img/play4.png`
- `img/perifericos/1.png` en lugar de `img/audilogitech.png`
- etc.

Este script se ejecuta mediante `RdsSeedConfig` que está activo en el perfil `prod`.

## Causa

1. **`RdsSeedConfig.java`** se ejecuta en producción (`@Profile({"docker", "prod"})`)
2. **`seed.productos.enabled=true`** por defecto en `application-prod.properties`
3. **`seed_productos.sql`** tiene rutas antiguas y se ejecuta al iniciar

## Solución Implementada

### 1. Deshabilitar seed en producción

**Archivo:** `msvc-productos/src/main/resources/application-prod.properties`

```properties
# ANTES:
seed.productos.enabled=${SEED_PRODUCTOS_ENABLED:true}

# DESPUÉS:
seed.productos.enabled=${SEED_PRODUCTOS_ENABLED:false}
```

Esto evita que `RdsSeedConfig` ejecute `seed_productos.sql` en producción.

### 2. Usar verificar_y_actualizar_rutas.sql manualmente

Para actualizar las rutas en la BD, ejecutar manualmente:

```bash
psql -h levelup-db.cvcqjuf3hjcs.us-east-1.rds.amazonaws.com \
     -p 5432 \
     -U levelup_admin \
     -d levelup_productos < verificar_y_actualizar_rutas.sql
```

## Verificación

Después de reiniciar el servicio, verificar en los logs:

```bash
grep -i "seed" /tmp/msvc-productos.log
```

Deberías ver:
```
Seed de productos deshabilitado (seed.productos.enabled=false)
```

Y NO deberías ver:
```
Ejecutando seed_productos.sql sobre la base de datos actual
```

## Notas

- El `data.sql` tiene las rutas correctas, pero solo se ejecuta en desarrollo
- El `seed_productos.sql` tiene rutas antiguas y NO debe ejecutarse en producción
- Las rutas deben actualizarse manualmente usando `verificar_y_actualizar_rutas.sql`

