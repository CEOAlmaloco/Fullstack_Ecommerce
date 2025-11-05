-- Actualizar la columna imagen de VARCHAR(255) a TEXT para almacenar Base64
-- Este script se ejecuta automáticamente si se habilita en application-dev.properties

-- H2 no soporta ALTER COLUMN directamente, así que necesitamos recrear la tabla
-- Pero como estamos usando spring.jpa.hibernate.ddl-auto=update, Hibernate debería
-- actualizar automáticamente la columna cuando cambiamos el modelo.

-- Si necesitas actualizar manualmente, puedes ejecutar esto en la consola H2:
-- ALTER TABLE productos ALTER COLUMN imagen VARCHAR(2147483647);

-- O mejor, eliminar y recrear la base de datos (solo para desarrollo):
-- 1. Detener el microservicio
-- 2. Eliminar el archivo: ./data/msvc_productos_dev.mv.db
-- 3. Reiniciar el microservicio
-- Hibernate creará la tabla con el tipo correcto (TEXT)

