#!/bin/bash

# Script para verificar qué base de datos está usando el backend

echo "=========================================="
echo "Verificación de Base de Datos del Backend"
echo "=========================================="
echo ""

# Obtener el PID del proceso msvc-productos
PID=$(ps aux | grep "msvc-productos" | grep -v grep | awk '{print $2}')

if [ -z "$PID" ]; then
    echo "❌ ERROR: No se encontró el proceso msvc-productos en ejecución"
    exit 1
fi

echo "✅ Proceso encontrado: PID $PID"
echo ""

# Verificar variables de entorno del proceso
echo "📋 Variables de entorno relacionadas con BD:"
echo "--------------------------------------------"
cat /proc/$PID/environ 2>/dev/null | tr '\0' '\n' | grep -iE "DB_|SPRING_PROFILES|DATASOURCE" | sort

echo ""
echo "📋 Comando completo del proceso:"
echo "--------------------------------------------"
ps -p $PID -o args --no-headers

echo ""
echo "🔍 Análisis:"
echo "--------------------------------------------"

# Verificar si tiene DB_URL configurado
if cat /proc/$PID/environ 2>/dev/null | tr '\0' '\n' | grep -qi "DB_URL"; then
    DB_URL=$(cat /proc/$PID/environ 2>/dev/null | tr '\0' '\n' | grep -i "DB_URL" | cut -d'=' -f2-)
    echo "✅ DB_URL encontrado: $DB_URL"
    
    if echo "$DB_URL" | grep -qi "postgresql"; then
        echo "✅ El backend está usando PostgreSQL"
        DB_NAME=$(echo "$DB_URL" | sed -n 's/.*\/\([^?]*\).*/\1/p')
        echo "   Base de datos: $DB_NAME"
    elif echo "$DB_URL" | grep -qi "h2"; then
        echo "⚠️  El backend está usando H2 (base de datos local)"
        echo "   Necesitas actualizar la BD H2 también"
    fi
else
    echo "⚠️  No se encontró DB_URL en variables de entorno"
    echo "   El backend está usando la configuración por defecto (H2)"
    echo "   Ruta H2: ./data/msvc_productos_dev"
fi

# Verificar perfil activo
SPRING_PROFILE=$(cat /proc/$PID/environ 2>/dev/null | tr '\0' '\n' | grep -i "SPRING_PROFILES_ACTIVE" | cut -d'=' -f2-)
if [ -n "$SPRING_PROFILE" ]; then
    echo ""
    echo "📌 Perfil Spring activo: $SPRING_PROFILE"
else
    echo ""
    echo "📌 Perfil Spring: dev (por defecto)"
fi

echo ""
echo "=========================================="
echo "Recomendación:"
echo "=========================================="

if cat /proc/$PID/environ 2>/dev/null | tr '\0' '\n' | grep -qi "postgresql"; then
    echo "✅ El backend está usando PostgreSQL"
    echo "   Las actualizaciones que hiciste en PostgreSQL deberían funcionar"
    echo "   Verifica que actualizaste la base de datos correcta:"
    echo "   - Base de datos: levelup_productos"
    echo ""
    echo "   Si actualizaste 'postgres' en lugar de 'levelup_productos',"
    echo "   necesitas ejecutar el script SQL en la base correcta:"
    echo ""
    echo "   psql -h levelup-db.cvcqjuf3hjcs.us-east-1.rds.amazonaws.com \\"
    echo "        -p 5432 -U levelup_admin -d levelup_productos"
else
    echo "⚠️  El backend está usando H2 local"
    echo "   Necesitas actualizar la base de datos H2 también:"
    echo ""
    echo "   1. Accede a H2 Console: http://44.209.152.110:8003/h2-console"
    echo "   2. JDBC URL: jdbc:h2:file:./data/msvc_productos_dev"
    echo "   3. Usuario: sa"
    echo "   4. Password: (vacío)"
    echo "   5. Ejecuta el script actualizar_rutas_imagenes.sql"
fi

