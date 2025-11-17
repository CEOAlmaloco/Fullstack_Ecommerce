# Actualizar Base de Datos sin Reiniciar el Microservicio

Este documento explica cómo actualizar las rutas de imágenes en la base de datos sin necesidad de reiniciar el microservicio de productos.

## Método 1: Usando H2 Console (Recomendado)

### Paso 1: Acceder a H2 Console

1. Abre tu navegador y ve a:
   ```
   http://44.209.152.110:8003/h2-console
   ```

2. Ingresa las credenciales:
   - **JDBC URL**: `jdbc:h2:file:./data/msvc_productos_dev`
   - **Usuario**: `sa`
   - **Password**: (dejar vacío)

3. Haz clic en **Connect**

### Paso 2: Ejecutar el Script SQL

1. Abre el archivo `actualizar_rutas_imagenes.sql` en un editor de texto
2. Copia todo el contenido del script
3. En H2 Console, pega el script en el área de texto
4. Haz clic en **Run** (o presiona `Ctrl+Enter`)

### Paso 3: Verificar los Cambios

Ejecuta esta consulta para verificar que las rutas se actualizaron correctamente:

```sql
SELECT codigo_producto, titulo, imagen 
FROM productos 
ORDER BY codigo_producto;
```

### Paso 4: Probar en la App

Los cambios se reflejan **inmediatamente** sin necesidad de reiniciar el microservicio. Simplemente:

1. Recarga la app Android
2. Las imágenes deberían cargar correctamente ahora

## Método 2: Usando SSH y línea de comandos

Si prefieres usar la línea de comandos desde AWS:

### Paso 1: Conectarte por SSH

```bash
ssh -i tu-clave.pem ec2-user@44.209.152.110
```

### Paso 2: Navegar al directorio del proyecto

```bash
cd /ruta/del/proyecto/Backend_Java_Spring/Fullstack_Ecommerce/msvc-productos
```

### Paso 3: Ejecutar el script SQL usando H2

```bash
# Opción A: Si tienes acceso directo a H2
java -cp target/msvc-productos-0.0.1-SNAPSHOT.jar org.h2.tools.Shell \
  -url jdbc:h2:file:./data/msvc_productos_dev \
  -user sa \
  -password "" \
  -sql "$(cat actualizar_rutas_imagenes.sql)"
```

### Paso 4: Verificar

```bash
java -cp target/msvc-productos-0.0.1-SNAPSHOT.jar org.h2.tools.Shell \
  -url jdbc:h2:file:./data/msvc_productos_dev \
  -user sa \
  -password "" \
  -sql "SELECT codigo_producto, titulo, imagen FROM productos ORDER BY codigo_producto;"
```

## Notas Importantes

1. **No es necesario reiniciar**: Los cambios en la base de datos se reflejan inmediatamente en las consultas del microservicio.

2. **Backup recomendado**: Antes de ejecutar el script, puedes hacer un backup de la BD:
   ```sql
   -- En H2 Console, ejecuta:
   SCRIPT TO 'backup_productos.sql';
   ```

3. **Si algo sale mal**: Puedes restaurar desde el backup o ejecutar `data.sql` nuevamente (esto requiere reiniciar el microservicio).

4. **Verificar rutas en S3**: Asegúrate de que las imágenes existan en S3 con los nombres correctos:
   - `img/play5white.png`
   - `img/play4.png`
   - `img/mandoplay.png`
   - etc.

## Solución de Problemas

### Las imágenes aún no cargan después de actualizar

1. Verifica que las rutas en S3 sean correctas
2. Verifica el Bucket Policy de S3 (debe permitir lectura pública)
3. Revisa los logs del microservicio para ver si hay errores
4. Verifica que las URLs en la BD sean exactamente como se espera

### Error al conectar a H2 Console

1. Verifica que el microservicio esté corriendo
2. Verifica que el puerto 8003 esté abierto en el Security Group de AWS
3. Verifica que la URL sea correcta: `http://44.209.152.110:8003/h2-console`

