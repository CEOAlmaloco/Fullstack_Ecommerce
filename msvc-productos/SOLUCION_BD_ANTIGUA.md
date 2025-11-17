# Solución: Base de Datos Antigua que se Reinicializa

## Problema

El backend Java está usando una versión antigua de la base de datos que se reinicializa cada vez que se reinicia el servicio. Esto causa que:

1. Las rutas de imágenes vuelvan a las antiguas (`img/consolas/2.png` en lugar de `img/play4.png`)
2. Se cree un nuevo RDS cada vez
3. Los datos se pierdan al reiniciar

## Causa Raíz

1. **`spring.jpa.hibernate.ddl-auto=update`** en producción puede modificar el esquema
2. **`spring.sql.init.mode=always`** en `application.properties` (desarrollo) puede ejecutarse si el perfil no está configurado correctamente
3. El perfil activo puede no estar configurado como `prod` en AWS

## Solución Implementada

### 1. Cambiar `ddl-auto` a `validate` en producción

**Archivo:** `msvc-productos/src/main/resources/application-prod.properties`

```properties
# ANTES (peligroso):
spring.jpa.hibernate.ddl-auto=update

# DESPUÉS (seguro):
spring.jpa.hibernate.ddl-auto=validate
```

Esto evita que Hibernate modifique el esquema de la base de datos.

### 2. Verificar Perfil Activo en AWS

Asegúrate de que el microservicio esté ejecutándose con el perfil `prod`:

```bash
# Verificar variables de entorno del proceso
ps aux | grep msvc-productos
cat /proc/PID/environ | tr '\0' '\n' | grep SPRING_PROFILES_ACTIVE
```

Si no está configurado, agregar al script de inicio:

```bash
export SPRING_PROFILES_ACTIVE=prod
```

### 3. Actualizar Rutas de Imágenes en la BD

Ejecutar el script SQL para actualizar las rutas antiguas:

```bash
# Conectarse a PostgreSQL
psql -h levelup-db.cvcqjuf3hjcs.us-east-1.rds.amazonaws.com \
     -p 5432 \
     -U levelup_admin \
     -d levelup_productos

# Ejecutar el script
\i verificar_y_actualizar_rutas.sql
```

O copiar y pegar el contenido del archivo `verificar_y_actualizar_rutas.sql` en la consola de PostgreSQL.

### 4. Verificar Configuración de Producción

Asegúrate de que estas propiedades estén configuradas en `application-prod.properties`:

```properties
# NO inicializar desde data.sql
spring.sql.init.mode=never

# NO modificar esquema
spring.jpa.hibernate.ddl-auto=validate

# Usar PostgreSQL (no H2)
spring.datasource.url=${DB_URL:jdbc:postgresql://...}
spring.datasource.driverClassName=org.postgresql.Driver
```

## Pasos para Aplicar en AWS

1. **Hacer pull de los cambios:**
   ```bash
   cd /ruta/del/proyecto/Backend_Java_Spring/Fullstack_Ecommerce/msvc-productos
   git pull origin Rama-principal
   ```

2. **Recompilar:**
   ```bash
   mvn clean package -DskipTests
   ```

3. **Detener el servicio actual:**
   ```bash
   pkill -9 -f msvc-productos
   ```

4. **Actualizar rutas en la BD (si es necesario):**
   ```bash
   psql -h levelup-db.cvcqjuf3hjcs.us-east-1.rds.amazonaws.com \
        -p 5432 \
        -U levelup_admin \
        -d levelup_productos < verificar_y_actualizar_rutas.sql
   ```

5. **Reiniciar con perfil prod:**
   ```bash
   export SPRING_PROFILES_ACTIVE=prod
   export DB_URL=jdbc:postgresql://levelup-db.cvcqjuf3hjcs.us-east-1.rds.amazonaws.com:5432/levelup_productos
   export DB_USERNAME=levelup_admin
   export DB_PASSWORD=tu_password
   
   nohup java -Xms256m -Xmx512m \
     -Dspring.profiles.active=prod \
     -jar target/msvc-productos-0.0.1-SNAPSHOT.jar > /tmp/msvc-productos.log 2>&1 &
   ```

6. **Verificar logs:**
   ```bash
   tail -f /tmp/msvc-productos.log | grep -E "ddl-auto|init.mode|datasource"
   ```

   Deberías ver:
   - `ddl-auto=validate`
   - `init.mode=never`
   - `datasource.url` apuntando a PostgreSQL (no H2)

## Verificación

Después de reiniciar, verificar que:

1. **Las rutas de imágenes sean correctas:**
   ```sql
   SELECT codigo_producto, titulo, imagen 
   FROM productos 
   WHERE imagen LIKE '%consolas/%' OR imagen LIKE '%perifericos/%';
   ```
   
   Esta consulta NO debería devolver resultados (todas las rutas deberían estar actualizadas).

2. **El servicio use PostgreSQL:**
   ```bash
   grep "PostgreSQL" /tmp/msvc-productos.log
   ```

3. **No se ejecute data.sql:**
   ```bash
   grep -i "data.sql" /tmp/msvc-productos.log
   ```
   
   No debería aparecer nada.

## Notas Importantes

- **NUNCA** usar `ddl-auto=update` en producción con datos reales
- **SIEMPRE** usar `spring.sql.init.mode=never` en producción
- **VERIFICAR** que el perfil activo sea `prod` antes de iniciar
- **HACER BACKUP** de la BD antes de ejecutar scripts SQL

