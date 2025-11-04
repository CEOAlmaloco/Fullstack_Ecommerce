# 🚀 Guía Completa de Despliegue en AWS - Proyecto Escolar (Presupuesto $100)

## 📋 Resumen del Plan

**Arquitectura:**
- **1 EC2 (t2.micro)** para backend (microservicios Spring Boot) - ~$10/mes
- **1 EC2 (t2.micro)** para frontend (React/Vite) - ~$10/mes  
- **1 RDS PostgreSQL (db.t3.micro)** para todas las bases de datos - ~$15/mes
- **Security Groups** básicos (gratis)
- **Elastic IP** para EC2 (gratis mientras esté en uso)
- **Total aproximado: ~$35/mes** (cabe en tu presupuesto de $100 por ~3 meses)

**La app de Kotlin** se conectará directamente a los endpoints públicos del backend (no necesita servidor).

---

## 🎯 PASO 1: Preparar la Región y Cuenta AWS

### 1.1 Seleccionar Región
- En la consola de AWS, selecciona una región cercana (ej: **us-east-1** o **us-west-2**)
- **IMPORTANTE**: Todos los recursos deben estar en la **misma región**

### 1.2 Verificar Límites
- Ve a **EC2 Dashboard** → **Limits**
- Verifica que puedas crear 2 instancias t2.micro
- Si no, solicita aumento de límite (gratis)

---

## 🗄️ PASO 2: Crear Base de Datos RDS PostgreSQL

### 2.1 Crear Instancia RDS
1. Ve a **RDS** en la consola de AWS
2. Click **"Create database"**
3. Configuración:
   - **Database creation method**: Standard create
   - **Engine type**: PostgreSQL
   - **Version**: PostgreSQL 15.x (o la más reciente)
   - **Template**: Free tier (si está disponible) o **Production** para más control
   - **DB instance identifier**: `levelup-db`
   - **Master username**: `levelup_admin`
   - **Master password**: [Genera una contraseña segura y guárdala]
   - **DB instance class**: **db.t3.micro** (1 vCPU, 1 GB RAM) - ~$15/mes
   - **Storage**: **General Purpose SSD (gp3)**, 20 GB
   - **Storage autoscaling**: Desactivar (ahorra dinero)
   - **VPC**: Default VPC (o crea una nueva)
   - **Publicly accessible**: **SÍ** (necesario para conectar desde EC2 y app móvil)
   - **Security group**: Crea nuevo: `rds-levelup-sg`
   - **Initial database name**: `levelup_main` (crearemos más bases de datos después)

### 2.2 Configurar Security Group de RDS
1. Ve a **EC2** → **Security Groups**
2. Selecciona `rds-levelup-sg`
3. **Inbound rules** → **Edit inbound rules** → **Add rule**:
   - **Type**: PostgreSQL
   - **Port**: 5432
   - **Source**: 
     - `sg-xxxxx` (ID del security group de EC2 backend - lo crearemos después)
     - O `0.0.0.0/0` temporalmente (solo para testing, luego restringe)
   - **Description**: "PostgreSQL from EC2 backend"

### 2.3 Obtener Endpoint de RDS
1. En **RDS Dashboard**, selecciona tu base de datos
2. Copia el **Endpoint** (ej: `levelup-db.xxxxx.us-east-1.rds.amazonaws.com`)
3. **Guárdalo**, lo necesitarás para configurar los microservicios

### 2.4 Crear Todas las Bases de Datos
Conéctate a RDS desde tu máquina local o EC2 y ejecuta:

```sql
-- Conectarse con psql o pgAdmin
-- psql -h levelup-db.xxxxx.us-east-1.rds.amazonaws.com -U levelup_admin -d postgres

CREATE DATABASE levelup_auth;
CREATE DATABASE levelup_usuario;
CREATE DATABASE levelup_productos;
CREATE DATABASE levelup_carrito;
CREATE DATABASE levelup_pedido;
CREATE DATABASE levelup_pagos;
CREATE DATABASE levelup_resenia;
CREATE DATABASE levelup_referidos;
CREATE DATABASE levelup_promociones;
CREATE DATABASE levelup_inventario;
```

---

## 🖥️ PASO 3: Crear EC2 para Backend (Microservicios)

### 3.1 Crear Instancia EC2
1. Ve a **EC2 Dashboard** → **Instances** → **Launch instance**
2. Configuración:
   - **Name**: `levelup-backend`
   - **AMI**: **Amazon Linux 2023** (o Ubuntu Server 22.04 LTS)
   - **Instance type**: **t2.micro** (1 vCPU, 1 GB RAM) - Free tier elegible
   - **Key pair**: Crea uno nuevo o selecciona existente (ej: `levelup-key`)
   - **Network settings**: 
     - **VPC**: Default VPC
     - **Auto-assign Public IP**: Enable
     - **Security group**: Crea nuevo: `levelup-backend-sg`
   - **Configure storage**: 20 GB gp3 (gratis en free tier)

### 3.2 Configurar Security Group del Backend
1. Ve a **EC2** → **Security Groups**
2. Selecciona `levelup-backend-sg`
3. **Inbound rules** → **Edit inbound rules** → **Add rules**:
   - **Rule 1**:
     - Type: SSH, Port: 22, Source: My IP
   - **Rule 2**:
     - Type: Custom TCP, Port: 8001, Source: 0.0.0.0/0 (msvc-auth)
   - **Rule 3**:
     - Type: Custom TCP, Port: 8003, Source: 0.0.0.0/0 (msvc-productos)
   - **Rule 4**:
     - Type: Custom TCP, Port: 8004, Source: 0.0.0.0/0 (msvc-inventario)
   - **Rule 5**:
     - Type: Custom TCP, Port: 8005, Source: 0.0.0.0/0 (msvc-referidos)
   - **Rule 6**:
     - Type: Custom TCP, Port: 8008, Source: 0.0.0.0/0 (msvc-carrito)
   - **Rule 7**:
     - Type: Custom TCP, Port: 8085, Source: 0.0.0.0/0 (msvc-pedido)
   - **Rule 8**:
     - Type: Custom TCP, Port: 8010, Source: 0.0.0.0/0 (msvc-resenia)
   - **Rule 9**:
     - Type: Custom TCP, Port: 8011, Source: 0.0.0.0/0 (msvc-pagos)
   - **Rule 10**:
     - Type: Custom TCP, Port: 8091, Source: 0.0.0.0/0 (msvc-promociones)
   - **Rule 11**:
     - Type: Custom TCP, Port: 8095, Source: 0.0.0.0/0 (msvc-usuario)
   - **Rule 12**:
     - Type: Custom TCP, Port: 8080, Source: 0.0.0.0/0 (msvc-gateway)

### 3.3 Asignar Elastic IP (Opcional pero Recomendado)
1. Ve a **EC2** → **Elastic IPs** → **Allocate Elastic IP address**
2. Asigna a tu instancia `levelup-backend`
3. **Nota**: Gratis mientras esté asignado a una instancia en ejecución

### 3.4 Conectarse a EC2 e Instalar Java/Maven
```bash
# Conectarse via SSH (usa tu key pair)
ssh -i levelup-key.pem ec2-user@[IP_DE_EC2]

# Actualizar sistema
sudo yum update -y  # Amazon Linux
# o
sudo apt update && sudo apt upgrade -y  # Ubuntu

# Instalar Java 17
sudo yum install java-17-amazon-corretto -y  # Amazon Linux
# o
sudo apt install openjdk-17-jdk -y  # Ubuntu

# Instalar Maven
sudo yum install maven -y  # Amazon Linux
# o
sudo apt install maven -y  # Ubuntu

# Verificar instalación
java -version
mvn -version
```

### 3.5 Subir Código del Backend a EC2
**Opción A: Usar Git**
```bash
# En EC2
cd /home/ec2-user
git clone https://github.com/tu-usuario/tu-repo-backend.git
cd tu-repo-backend/Backend_Java_Spring/Fullstack_Ecommerce
```

**Opción B: Usar SCP desde tu máquina local**
```bash
# Desde tu máquina local
scp -i levelup-key.pem -r Backend_Java_Spring/Fullstack_Ecommerce ec2-user@[IP_EC2]:/home/ec2-user/
```

### 3.6 Configurar Variables de Entorno en EC2
```bash
# Crear archivo de configuración
sudo nano /etc/environment

# Agregar (reemplaza con tus valores reales):
DB_HOST=levelup-db.xxxxx.us-east-1.rds.amazonaws.com
DB_PORT=5432
DB_USERNAME=levelup_admin
DB_PASSWORD=tu_password_segura
DB_DRIVER=org.postgresql.Driver
JWT_SECRET=tu-secret-jwt-muy-seguro-minimo-256-bits
CORS_ORIGINS=http://[IP_FRONTEND]:5173,https://tu-dominio.com

# Guardar y cargar
source /etc/environment
```

### 3.7 Compilar y Ejecutar Microservicios
```bash
# Ir a cada microservicio y compilar
cd msvc-auth
mvn clean package -DskipTests
# Crear archivo para ejecutar
cat > start-auth.sh << 'EOF'
#!/bin/bash
export SPRING_PROFILES_ACTIVE=prod
export DB_URL=jdbc:postgresql://${DB_HOST}:${DB_PORT}/levelup_auth
export DB_USERNAME=${DB_USERNAME}
export DB_PASSWORD=${DB_PASSWORD}
export JWT_SECRET=${JWT_SECRET}
nohup java -jar target/*.jar > auth.log 2>&1 &
EOF
chmod +x start-auth.sh
./start-auth.sh

# Repetir para cada microservicio (productos, usuario, carrito, etc.)
```

**Mejor opción: Usar systemd para gestión de servicios**
```bash
# Crear servicio systemd para cada microservicio
sudo nano /etc/systemd/system/msvc-auth.service

[Unit]
Description=LevelUp Auth Microservice
After=network.target

[Service]
Type=simple
User=ec2-user
WorkingDirectory=/home/ec2-user/tu-repo-backend/Backend_Java_Spring/Fullstack_Ecommerce/msvc-auth
Environment="SPRING_PROFILES_ACTIVE=prod"
Environment="DB_URL=jdbc:postgresql://levelup-db.xxxxx.us-east-1.rds.amazonaws.com:5432/levelup_auth"
Environment="DB_USERNAME=levelup_admin"
Environment="DB_PASSWORD=tu_password"
Environment="JWT_SECRET=tu-secret-jwt"
ExecStart=/usr/bin/java -jar target/*.jar
Restart=always

[Install]
WantedBy=multi-user.target

# Activar y iniciar
sudo systemctl daemon-reload
sudo systemctl enable msvc-auth
sudo systemctl start msvc-auth
sudo systemctl status msvc-auth
```

---

## 🌐 PASO 4: Crear EC2 para Frontend (React)

### 4.1 Crear Instancia EC2
1. Ve a **EC2 Dashboard** → **Launch instance**
2. Configuración:
   - **Name**: `levelup-frontend`
   - **AMI**: **Amazon Linux 2023** o Ubuntu Server 22.04 LTS
   - **Instance type**: **t2.micro**
   - **Key pair**: Usa el mismo `levelup-key`
   - **Network settings**:
     - **Security group**: Crea nuevo: `levelup-frontend-sg`
   - **Configure storage**: 20 GB

### 4.2 Configurar Security Group del Frontend
1. **Inbound rules**:
   - **SSH** (22) desde My IP
   - **HTTP** (80) desde 0.0.0.0/0
   - **HTTPS** (443) desde 0.0.0.0/0
   - **Custom TCP** (5173) desde 0.0.0.0/0 (Vite dev server)

### 4.3 Instalar Node.js en EC2 Frontend
```bash
# Conectarse
ssh -i levelup-key.pem ec2-user@[IP_FRONTEND]

# Instalar Node.js 20 (LTS)
curl -fsSL https://rpm.nodesource.com/setup_20.x | sudo bash -
sudo yum install -y nodejs  # Amazon Linux
# o
curl -fsSL https://deb.nodesource.com/setup_20.x | sudo -E bash -
sudo apt install -y nodejs  # Ubuntu

# Verificar
node --version
npm --version
```

### 4.4 Instalar Nginx (Opcional pero Recomendado)
```bash
sudo yum install nginx -y  # Amazon Linux
# o
sudo apt install nginx -y  # Ubuntu

sudo systemctl enable nginx
sudo systemctl start nginx
```

### 4.5 Subir Código del Frontend
```bash
# Opción A: Git
cd /home/ec2-user
git clone https://github.com/tu-usuario/tu-repo-frontend.git
cd tu-repo-frontend/Frontend_React_Typescript/levelup-react/proyecto-levelup-frontend/levelup-react

# Opción B: SCP
# Desde tu máquina local:
scp -i levelup-key.pem -r Frontend_React_Typescript/levelup-react ec2-user@[IP_FRONTEND]:/home/ec2-user/
```

### 4.6 Configurar Frontend para Producción
```bash
# Instalar dependencias
npm install

# Crear archivo .env para producción
nano .env.production

# Contenido (reemplaza con IP de tu backend):
VITE_API_BASE_URL=http://[IP_BACKEND_EC2]:8080/api/v1
VITE_IMAGE_BASE_URL=http://[IP_BACKEND_EC2]:8003
VITE_GATEWAY_URL=http://[IP_BACKEND_EC2]:8080

# Compilar para producción
npm run build

# Los archivos estarán en la carpeta dist/
```

### 4.7 Configurar Nginx para Servir Frontend
```bash
sudo nano /etc/nginx/conf.d/levelup.conf

# Contenido:
server {
    listen 80;
    server_name [IP_FRONTEND_EC2] o tu-dominio.com;
    
    root /home/ec2-user/tu-repo-frontend/Frontend_React_Typescript/levelup-react/proyecto-levelup-frontend/levelup-react/dist;
    index index.html;
    
    location / {
        try_files $uri $uri/ /index.html;
    }
    
    # Proxy para APIs si es necesario
    location /api {
        proxy_pass http://[IP_BACKEND_EC2]:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}

# Reiniciar Nginx
sudo systemctl restart nginx
```

---

## 📱 PASO 5: Configurar App Kotlin (Cliente Externo)

La app de Kotlin solo necesita conectarse a los endpoints públicos del backend. **No requiere servidor**.

### 5.1 Configurar URLs en App Kotlin
En tu app de Kotlin, configura las URLs base:

```kotlin
// En tu archivo de configuración (ej: Config.kt o ApiClient.kt)
object ApiConfig {
    const val BASE_URL = "http://[IP_BACKEND_EC2]:8080/api/v1"
    const val AUTH_URL = "http://[IP_BACKEND_EC2]:8001/api/v1"
    const val PRODUCTOS_URL = "http://[IP_BACKEND_EC2]:8003/api/v1"
    // ... etc
}
```

### 5.2 Permisos de Internet en AndroidManifest.xml
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

---

## 🔒 PASO 6: Configuración de Seguridad Básica

### 6.1 Restringir Security Groups
Después de que todo funcione, restringe los Security Groups:

**Backend Security Group:**
- Solo permite acceso desde:
  - Security Group del Frontend (para comunicación interna)
  - Tu IP personal (para SSH)
  - 0.0.0.0/0 para puertos HTTP (solo para APIs públicas)

**RDS Security Group:**
- Solo permite PostgreSQL (5432) desde:
  - Security Group del Backend EC2
  - Tu IP personal (para administración)

### 6.2 Configurar HTTPS (Opcional pero Recomendado)
1. Obtén un certificado SSL gratuito de **Let's Encrypt** usando Certbot
2. O usa **AWS Certificate Manager (ACM)** con **Application Load Balancer**

---

## 📊 PASO 7: Verificación y Testing

### 7.1 Verificar Backend
```bash
# Desde tu máquina local o navegador
curl http://[IP_BACKEND]:8080/api/v1/health
curl http://[IP_BACKEND]:8003/api/v1/productos
```

### 7.2 Verificar Frontend
- Abre en navegador: `http://[IP_FRONTEND]`
- Debería cargar la aplicación React

### 7.3 Verificar desde App Kotlin
- Configura las URLs en la app
- Prueba login, obtener productos, etc.

---

## 💰 PASO 8: Monitoreo de Costos

### 8.1 Configurar Budget Alert
1. Ve a **AWS Cost Management** → **Budgets**
2. Crea un budget de $100
3. Configura alerta al 80% ($80)

### 8.2 Estimación de Costos Mensuales
- EC2 t2.micro (2 instancias): ~$10-15/mes cada una = **$20-30/mes**
- RDS db.t3.micro: ~**$15/mes**
- Storage (20GB x 3): ~**$2/mes**
- Transferencia de datos: **Gratis hasta 100GB**
- **Total: ~$37-47/mes**

Con $100, tendrás **aprox. 2-3 meses** de ejecución.

---

## 🛠️ Troubleshooting Común

### Backend no se conecta a RDS
- Verifica Security Group de RDS permite acceso desde EC2 backend
- Verifica que DB_PASSWORD sea correcta
- Verifica que el endpoint de RDS sea correcto

### Frontend no carga
- Verifica que Nginx esté ejecutándose: `sudo systemctl status nginx`
- Verifica que los archivos estén en `/dist/`
- Verifica logs: `sudo tail -f /var/log/nginx/error.log`

### App Kotlin no conecta
- Verifica que las IPs en la app sean correctas
- Verifica que Security Groups permitan tráfico desde internet
- Verifica logs de EC2: `sudo journalctl -u msvc-auth -f`

---

## 📝 Checklist Final

- [ ] RDS PostgreSQL creado y configurado
- [ ] Todas las bases de datos creadas
- [ ] EC2 Backend creado y configurado
- [ ] Microservicios compilados y ejecutándose
- [ ] EC2 Frontend creado y configurado
- [ ] Frontend compilado y servido por Nginx
- [ ] Security Groups configurados correctamente
- [ ] App Kotlin configurada con URLs correctas
- [ ] Pruebas de conectividad exitosas
- [ ] Budget alert configurado

---

## 🎉 ¡Listo!

Tu aplicación fullstack está desplegada en AWS y lista para uso. La app de Kotlin puede conectarse directamente a los endpoints públicos del backend.

**Nota**: Para producción real, considera usar un dominio personalizado, certificados SSL, y un balanceador de carga, pero para un proyecto escolar esto es suficiente.

