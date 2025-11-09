#!/bin/bash
# Script para desplegar todos los microservicios en EC2
# Uso: ./deploy-backend.sh

echo "🚀 Iniciando despliegue de microservicios..."

# Variables de entorno (configurar según tu entorno)
export DB_HOST="${DB_HOST:-tu-rds-endpoint.rds.amazonaws.com}"
export DB_PORT="${DB_PORT:-5432}"
export DB_USERNAME="${DB_USERNAME:-levelup_admin}"
export DB_PASSWORD="${DB_PASSWORD:-tu_password}"
export JWT_SECRET="${JWT_SECRET:-tu-secret-jwt-muy-seguro}"

# Directorio base
BASE_DIR="/home/ec2-user/Backend_Java_Spring/Fullstack_Ecommerce"

# Microservicios a desplegar
SERVICES=(
    "msvc-gateway:8094"
    "msvc-auth:8001"
    "msvc-usuario:8095"
    "msvc-productos:8003"
    "msvc-carrito:8008"
    "msvc-pedido:8085"
    "msvc-pagos:8011"
    "msvc-resenia:8010"
    "msvc-referidos:8005"
    "msvc-promociones:8091"
    "msvc-inventario:8004"
)

# Función para compilar un microservicio
compile_service() {
    local service=$1
    echo "📦 Compilando $service..."
    cd "$BASE_DIR/$service" || exit 1
    mvn clean package -DskipTests
    if [ $? -ne 0 ]; then
        echo "❌ Error compilando $service"
        exit 1
    fi
    echo "✅ $service compilado exitosamente"
}

# Función para crear servicio systemd
create_systemd_service() {
    local service_name=$1
    local service_dir=$2
    local port=$3
    local db_name=$4
    
    cat > "/etc/systemd/system/${service_name}.service" << EOF
[Unit]
Description=LevelUp ${service_name} Microservice
After=network.target

[Service]
Type=simple
User=ec2-user
WorkingDirectory=${service_dir}
Environment="SPRING_PROFILES_ACTIVE=prod"
Environment="DB_URL=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${db_name}"
Environment="DB_USERNAME=${DB_USERNAME}"
Environment="DB_PASSWORD=${DB_PASSWORD}"
Environment="DB_DRIVER=org.postgresql.Driver"
Environment="JWT_SECRET=${JWT_SECRET}"
Environment="PRODUCTOS_URL=http://localhost:8003/api/v1"
Environment="JAVA_OPTS=-Xms256m -Xmx512m"
ExecStart=/usr/bin/java \$JAVA_OPTS -jar target/*.jar
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
EOF

    echo "📝 Servicio systemd creado para ${service_name}"
}

# Compilar todos los microservicios
echo "🔨 Compilando microservicios..."
for service_port in "${SERVICES[@]}"; do
    IFS=':' read -r service port <<< "$service_port"
    compile_service "$service"
done

# Crear servicios systemd
echo "⚙️  Creando servicios systemd..."
for service_port in "${SERVICES[@]}"; do
    IFS=':' read -r service port <<< "$service_port"
    service_name=$(echo "$service" | tr '-' '_')
    db_name="levelup_${service#msvc-}"
    
    create_systemd_service "$service" "$BASE_DIR/$service" "$port" "$db_name"
done

# Recargar systemd y activar servicios
echo "🔄 Activando servicios..."
sudo systemctl daemon-reload

for service_port in "${SERVICES[@]}"; do
    IFS=':' read -r service port <<< "$service_port"
    sudo systemctl enable "$service"
    sudo systemctl restart "$service"
    echo "✅ $service iniciado"
    sleep 5  # Esperar un poco entre servicios
done

echo "🎉 Despliegue completado!"
echo "📊 Verificar estado: sudo systemctl status msvc-*"

