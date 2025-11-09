#!/bin/bash

# Script para iniciar todos los microservicios de LevelUp Gamer
# Uso: ./start-all-services.sh

# Configurar Java 21 (Amazon Corretto)
export JAVA_HOME=/usr/lib/jvm/java-21-amazon-corretto.x86_64
export PATH=$JAVA_HOME/bin:$PATH

# Variables de entorno del proyecto
export SPRING_PROFILES_ACTIVE=prod

# Configuración de Base de Datos RDS
export DB_HOST=levelup-db.xxxxx.us-east-1.rds.amazonaws.com
export DB_PORT=5432
export DB_USERNAME=levelup_admin
export DB_PASSWORD=tu_password_segura_aqui
export DB_DRIVER=org.postgresql.Driver

# Configuración JWT
export JWT_SECRET=levelUpGamerSecretKey2024SecureForJWT256BitsMinimum

# Configuración CORS
export CORS_ORIGINS=http://[IP_FRONTEND]:5173,http://[IP_FRONTEND]:80,https://tu-dominio.com

# Configuración S3
export S3_BUCKET_NAME=levelup-gamer-products
export S3_REGION=us-east-1
export S3_BASE_URL=

# Configuración AWS
export AWS_REGION=us-east-1

# Ir al directorio base del proyecto
cd "$(dirname "$0")" || exit 1

echo "=========================================="
echo "Iniciando microservicios LevelUp Gamer"
echo "=========================================="
echo "Java Version:"
java -version
echo ""
echo "JAVA_HOME: $JAVA_HOME"
echo "SPRING_PROFILES_ACTIVE: $SPRING_PROFILES_ACTIVE"
echo "=========================================="
echo ""

# Función para iniciar un microservicio
start_service() {
    local SERVICE_NAME=$1
    local SERVICE_DIR=$2
    local LOG_FILE=$3
    local PORT=$4
    
    echo "Iniciando $SERVICE_NAME en puerto $PORT..."
    
    if [ ! -f "$SERVICE_DIR/target"/*.jar ]; then
        echo "ERROR: No se encontró el JAR compilado en $SERVICE_DIR/target/"
        echo "Por favor, compila el microservicio primero: cd $SERVICE_DIR && mvn clean package -DskipTests"
        return 1
    fi
    
    cd "$SERVICE_DIR" || return 1
    
    # Configurar variables de entorno específicas por base de datos
    local DB_NAME
    case $SERVICE_NAME in
        "msvc-gateway")
            DB_NAME="levelup_main"
            ;;
        "msvc-auth")
            DB_NAME="levelup_auth"
            ;;
        "msvc-usuario")
            DB_NAME="levelup_usuario"
            ;;
        "msvc-productos")
            DB_NAME="levelup_productos"
            ;;
        "msvc-carrito")
            DB_NAME="levelup_carrito"
            ;;
        "msvc-pedido")
            DB_NAME="levelup_pedido"
            ;;
        "msvc-pagos")
            DB_NAME="levelup_pagos"
            ;;
        "msvc-resenia")
            DB_NAME="levelup_resenia"
            ;;
        "msvc-referidos")
            DB_NAME="levelup_referidos"
            ;;
        "msvc-promociones")
            DB_NAME="levelup_promociones"
            ;;
        "msvc-inventario")
            DB_NAME="levelup_inventario"
            ;;
        "msvc-notificaciones")
            DB_NAME="levelup_notificaciones"
            ;;
        "msvc-eventos")
            DB_NAME="levelup_eventos"
            ;;
        "msvc-contenido")
            DB_NAME="levelup_contenido"
            ;;
        *)
            DB_NAME="levelup_main"
            ;;
    esac
    
    export DB_URL="jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}"
    
    # Iniciar el servicio en background
    local JAVA_OPTS="-Xms256m -Xmx512m"
    nohup java $JAVA_OPTS -jar target/*.jar > "$LOG_FILE" 2>&1 &
    
    local PID=$!
    echo "  ✓ $SERVICE_NAME iniciado (PID: $PID, Puerto: $PORT)"
    echo "  Log: $SERVICE_DIR/$LOG_FILE"
    
    # Esperar un poco antes de iniciar el siguiente servicio
    sleep 3
    
    cd .. || return 1
    
    return 0
}

# Iniciar microservicios en orden

# 1. Gateway (debe iniciarse primero o último, dependiendo de la configuración)
echo "--- Iniciando Gateway (Puerto 8094) ---"
start_service "msvc-gateway" "msvc-gateway" "gateway.log" "8094"

# 2. Auth Service
echo ""
echo "--- Iniciando Auth Service (Puerto 8001) ---"
start_service "msvc-auth" "msvc-auth" "auth.log" "8001"

# 3. Usuario Service
echo ""
echo "--- Iniciando Usuario Service (Puerto 8095) ---"
start_service "msvc-usuario" "msvc-usuario" "usuario.log" "8095"

# 4. Productos Service
echo ""
echo "--- Iniciando Productos Service (Puerto 8003) ---"
start_service "msvc-productos" "msvc-productos" "productos.log" "8003"

# 5. Inventario Service
echo ""
echo "--- Iniciando Inventario Service (Puerto 8004) ---"
start_service "msvc-inventario" "msvc-inventario" "inventario.log" "8004"

# 6. Referidos Service
echo ""
echo "--- Iniciando Referidos Service (Puerto 8005) ---"
start_service "msvc-referidos" "msvc-referidos" "referidos.log" "8005"

# 7. Notificaciones Service
echo ""
echo "--- Iniciando Notificaciones Service (Puerto 8006) ---"
start_service "msvc-notificaciones" "msvc-notificaciones" "notificaciones.log" "8006"

# 8. Carrito Service
echo ""
echo "--- Iniciando Carrito Service (Puerto 8008) ---"
start_service "msvc-carrito" "msvc-carrito" "carrito.log" "8008"

# 9. Reseñas Service
echo ""
echo "--- Iniciando Reseñas Service (Puerto 8010) ---"
start_service "msvc-resenia" "msvc-resenia" "resenia.log" "8010"

# 10. Pagos Service
echo ""
echo "--- Iniciando Pagos Service (Puerto 8011) ---"
start_service "msvc-pagos" "msvc-pagos" "pagos.log" "8011"

# 11. Pedido Service
echo ""
echo "--- Iniciando Pedido Service (Puerto 8085) ---"
start_service "msvc-pedido" "msvc-pedido" "pedido.log" "8085"

# 12. Promociones Service
echo ""
echo "--- Iniciando Promociones Service (Puerto 8091) ---"
start_service "msvc-promociones" "msvc-promociones" "promociones.log" "8091"

# 13. Eventos Service
echo ""
echo "--- Iniciando Eventos Service (Puerto 8092) ---"
start_service "msvc-eventos" "msvc-eventos" "eventos.log" "8092"

# 14. Contenido Service
echo ""
echo "--- Iniciando Contenido Service (Puerto 8093) ---"
start_service "msvc-contenido" "msvc-contenido" "contenido.log" "8093"

echo ""
echo "=========================================="
echo "Inicio de microservicios completado"
echo "=========================================="
echo ""
echo "Para ver los logs de un servicio:"
echo "  tail -f msvc-auth/auth.log"
echo "  tail -f msvc-gateway/gateway.log"
echo ""
echo "Para verificar que los servicios están corriendo:"
echo "  ps aux | grep java"
echo "  netstat -tuln | grep -E '8094|8001|8003|8004|8005|8006|8008|8010|8011|8085|8091|8092|8093|8095'"
echo ""
echo "Para detener todos los servicios:"
echo "  pkill -f 'java -jar target'"
echo "=========================================="

