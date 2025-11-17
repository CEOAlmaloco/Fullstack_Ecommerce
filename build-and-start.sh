#!/bin/bash

# Script combinado para compilar e iniciar todos los microservicios
# Uso: ./build-and-start.sh

echo "=========================================="
echo "LEVEL UP GAMER - BUILD Y START"
echo "=========================================="
echo ""

# Ir al directorio base del proyecto
cd "$(dirname "$0")" || exit 1

# Verificar si los JARs ya existen
echo "Verificando si los microservicios ya están compilados..."
JARS_EXIST=true

if [ ! -f "msvc-gateway/target"/*.jar ] || [ ! -f "msvc-productos/target"/*.jar ]; then
    JARS_EXIST=false
fi

if [ "$JARS_EXIST" = true ]; then
    echo "[OK] Los JARs ya existen"
    read -p "¿Deseas recompilar de todos modos? (s/N): " REBUILD
    if [[ "$REBUILD" =~ ^[Ss]$ ]]; then
        echo "Recompilando todos los microservicios..."
        ./build-all-services.sh
    else
        echo "Usando JARs existentes..."
    fi
else
    echo "[INFO] Algunos JARs no existen, compilando..."
    ./build-all-services.sh
fi

echo ""
echo "=========================================="
echo "Iniciando servicios..."
echo "=========================================="
echo ""

# Ejecutar start-all-services.sh
./start-all-services.sh

