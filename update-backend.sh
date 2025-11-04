#!/bin/bash
# Script para actualizar backend desde GitHub
# Uso: ./update-backend.sh

echo "🔄 Actualizando backend..."

# Directorio del proyecto
PROJECT_DIR="/home/ec2-user/Backend_Java_Spring/Fullstack_Ecommerce"

# Verificar si el directorio existe
if [ ! -d "$PROJECT_DIR" ]; then
    echo "❌ Directorio del proyecto no encontrado: $PROJECT_DIR"
    exit 1
fi

cd "$PROJECT_DIR" || exit 1

# Pull del repositorio
echo "📥 Descargando cambios desde Git..."
git pull origin main 2>&1 || git pull origin master 2>&1

# Cargar variables de entorno si existe archivo .env
if [ -f "/home/ec2-user/.env" ]; then
    echo "📋 Cargando variables de entorno..."
    set -a
    source /home/ec2-user/.env
    set +a
fi

# Variables por defecto si no están en .env
export DB_HOST="${DB_HOST:-localhost}"
export DB_PORT="${DB_PORT:-5432}"
export DB_USERNAME="${DB_USERNAME:-levelup_admin}"
export DB_PASSWORD="${DB_PASSWORD}"
export JWT_SECRET="${JWT_SECRET}"
export SPRING_PROFILES_ACTIVE="${SPRING_PROFILES_ACTIVE:-prod}"

# Microservicios a actualizar
SERVICES=("msvc-gateway" "msvc-auth" "msvc-usuario" "msvc-productos" 
          "msvc-carrito" "msvc-pedido" "msvc-pagos" "msvc-resenia" 
          "msvc-referidos" "msvc-promociones" "msvc-inventario")

# Contador de servicios actualizados
UPDATED=0
FAILED=0

# Actualizar cada microservicio
for service in "${SERVICES[@]}"; do
    echo ""
    echo "🔄 Actualizando $service..."
    
    SERVICE_DIR="$PROJECT_DIR/$service"
    
    if [ ! -d "$SERVICE_DIR" ]; then
        echo "⚠️  Directorio $service no encontrado, omitiendo..."
        continue
    fi
    
    cd "$SERVICE_DIR" || continue
    
    # Compilar
    echo "   📦 Compilando..."
    mvn clean package -DskipTests -q
    
    if [ $? -eq 0 ]; then
        # Reiniciar servicio systemd si existe
        if sudo systemctl is-active --quiet "$service" 2>/dev/null; then
            echo "   🔄 Reiniciando servicio..."
            sudo systemctl restart "$service"
            sleep 2
            
            # Verificar estado
            if sudo systemctl is-active --quiet "$service"; then
                echo "   ✅ $service actualizado y reiniciado exitosamente"
                ((UPDATED++))
            else
                echo "   ⚠️  $service compilado pero falló al reiniciar"
                ((FAILED++))
            fi
        else
            echo "   ✅ $service compilado (servicio no está corriendo como systemd)"
            ((UPDATED++))
        fi
    else
        echo "   ❌ Error compilando $service"
        ((FAILED++))
    fi
    
    sleep 1
done

echo ""
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "📊 Resumen de actualización:"
echo "   ✅ Actualizados exitosamente: $UPDATED"
if [ $FAILED -gt 0 ]; then
    echo "   ❌ Fallidos: $FAILED"
fi
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

if [ $FAILED -eq 0 ]; then
    echo "🎉 Actualización completada exitosamente!"
else
    echo "⚠️  Actualización completada con errores"
    exit 1
fi

echo ""
echo "📊 Verificar estado de servicios:"
echo "   sudo systemctl status msvc-*"

