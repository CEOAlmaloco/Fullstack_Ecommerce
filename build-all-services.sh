#!/bin/bash

# Script para compilar todos los microservicios de LevelUp Gamer
# Uso: ./build-all-services.sh

echo "=========================================="
echo "Compilando todos los microservicios"
echo "=========================================="
echo ""

# Ir al directorio base del proyecto
cd "$(dirname "$0")" || exit 1

# Función para compilar un microservicio
build_service() {
    local SERVICE_NAME=$1
    local SERVICE_DIR=$2
    
    echo "=========================================="
    echo "Compilando $SERVICE_NAME..."
    echo "=========================================="
    
    if [ ! -d "$SERVICE_DIR" ]; then
        echo "ERROR: No se encontró el directorio $SERVICE_DIR"
        return 1
    fi
    
    cd "$SERVICE_DIR" || return 1
    
    echo "Ejecutando: mvn clean package -DskipTests"
    mvn clean package -DskipTests
    
    if [ $? -eq 0 ]; then
        echo "✓ $SERVICE_NAME compilado correctamente"
    else
        echo "✗ ERROR al compilar $SERVICE_NAME"
        cd .. || return 1
        return 1
    fi
    
    cd .. || return 1
    echo ""
    
    return 0
}

# Compilar microservicios en orden

build_service "msvc-gateway" "msvc-gateway"
build_service "msvc-auth" "msvc-auth"
build_service "msvc-usuario" "msvc-usuario"
build_service "msvc-productos" "msvc-productos"
build_service "msvc-inventario" "msvc-inventario"
build_service "msvc-referidos" "msvc-referidos"
build_service "msvc-notificaciones" "msvc-notificaciones"
build_service "msvc-carrito" "msvc-carrito"
build_service "msvc-resenia" "msvc-resenia"
build_service "msvc-pagos" "msvc-pagos"
build_service "msvc-pedido" "msvc-pedido"
build_service "msvc-promociones" "msvc-promociones"
build_service "msvc-eventos" "msvc-eventos"
build_service "msvc-contenido" "msvc-contenido"

echo "=========================================="
echo "Compilación completada"
echo "=========================================="
echo ""
echo "Los JARs están en:"
echo "  msvc-*/target/*.jar"
echo ""
echo "Para iniciar los servicios, ejecuta:"
echo "  ./start-all-services.sh"
echo "=========================================="

