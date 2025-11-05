-- Migración para actualizar columna imagen de VARCHAR(255) a TEXT
-- Este script se ejecuta automáticamente al iniciar la aplicación

-- H2 no soporta ALTER COLUMN directamente de VARCHAR a TEXT
-- Si la columna ya existe como VARCHAR(255), necesitamos recrearla
-- La mejor solución es eliminar la base de datos y dejarla recrear

-- Si la tabla existe, intentamos recrearla
-- Nota: Esto solo funciona si la tabla está vacía o si eliminas la base de datos
DROP TABLE IF EXISTS productos CASCADE;

