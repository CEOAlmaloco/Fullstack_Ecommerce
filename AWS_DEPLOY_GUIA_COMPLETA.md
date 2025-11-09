# 🚀 Guía Completa de Despliegue en AWS - Paso a Paso

## 📋 Tabla de Contenidos

1. [Crear cuenta en AWS](#1-crear-cuenta-en-aws)
2. [Configurar región y verificar límites](#2-configurar-región-y-verificar-límites)
3. [Crear Security Groups (ANTES de crear instancias)](#3-crear-security-groups-antes-de-crear-instancias)
4. [Crear bucket S3 para imágenes](#4-crear-bucket-s3-para-imágenes)
5. [Crear instancia EC2 para Backend (t3.micro)](#5-crear-instancia-ec2-para-backend-t3micro)
6. [Configurar EC2 Backend](#6-configurar-ec2-backend)
7. [Crear instancia RDS PostgreSQL](#7-crear-instancia-rds-postgresql)
8. [Crear instancia EC2 para Frontend (t3.micro)](#8-crear-instancia-ec2-para-frontend-t3micro)
9. [Configurar EC2 Frontend](#9-configurar-ec2-frontend)
10. [Configurar App Kotlin](#10-configurar-app-kotlin)
11. [Verificar funcionamiento](#11-verificar-funcionamiento)
12. [Monitoreo y costos](#12-monitoreo-y-costos)

---

## 1. Crear cuenta en AWS

### Paso 1.1: Registrarse en AWS
1. Ve a [https://aws.amazon.com/](https://aws.amazon.com/)
2. Click en **"Crear una cuenta de AWS"** (esquina superior derecha)
3. Completa el formulario de registro:
   - **Correo electrónico**: Tu correo principal
   - **Contraseña**: Mínimo 8 caracteres, mayúsculas, minúsculas, números y símbolos
   - **Nombre de cuenta**: `LevelUp-Gamer` (o el que prefieras)
4. Verifica tu correo electrónico
5. Completa la verificación de tarjeta de crédito:
   - ⚠️ **No se cobrará** si usas el Free Tier
   - AWS puede hacer una verificación de $1 que se reembolsa
6. Verifica tu identidad por teléfono (SMS)

### Paso 1.2: Acceder a la consola
1. Inicia sesión en [https://console.aws.amazon.com/](https://console.aws.amazon.com/)
2. Verás el dashboard de AWS

---

## 2. Configurar región y verificar límites

### Paso 2.1: Seleccionar región
1. En la esquina superior derecha, busca el selector de región
2. Click y selecciona: **us-east-1 (US East - N. Virginia)**
   - ⚠️ **IMPORTANTE**: Todos los recursos deben estar en la **misma región**
   - O selecciona la región más cercana a tu ubicación

### Paso 2.2: Verificar límites de EC2
1. En la barra de búsqueda, escribe **"EC2"**
2. Click en **EC2** → **Dashboard**
3. En el menú izquierdo, click en **"Limits"** (Límites)
4. Busca **"Running On-Demand t3.micro instances"**
5. Verifica que puedas crear al menos **2 instancias t3.micro**
   - Si el límite es menor, click en **"Request limit increase"**
   - Solicita aumento a **2 instancias** (gratis)

### Paso 2.3: Verificar límites de RDS
1. En la barra de búsqueda, escribe **"RDS"**
2. Click en **RDS** → **Dashboard**
3. En el menú izquierdo, click en **"Limits"**
4. Verifica que puedas crear **1 instancia db.t3.micro**
   - Si no, solicita aumento (gratis)

---

## 3. Crear Security Groups (ANTES de crear instancias)

⚠️ **IMPORTANTE**: Crea los Security Groups ANTES de crear las instancias EC2 para poder seleccionarlos.

### Paso 3.1: Obtener tu IP pública
1. Ve a [https://whatismyipaddress.com/](https://whatismyipaddress.com/)
2. Copia tu **IPv4 Address** (ej: `203.0.113.45`)
3. **Guárdala**, la necesitarás para configurar los Security Groups

### Paso 3.2: Crear Security Group para Backend
1. En la consola de AWS, ve a **EC2** → **Security Groups**
2. Click en **"Create security group"**
3. Configuración:
   - **Name**: `levelup-backend-sg`
   - **Description**: `Security group para microservicios backend`
   - **VPC**: Selecciona **"Default VPC"** (o la que prefieras)

4. **Inbound rules** (Reglas de entrada) → Click en **"Add rule"** y agrega:

   **Regla 1 - SSH (tu IP):**
   - **Type**: SSH
   - **Protocol**: TCP
   - **Port range**: 22
   - **Source**: **My IP** (o escribe tu IP: `203.0.113.45/32`)
   - **Description**: `SSH desde mi IP`

   **Regla 2 - Gateway (público):**
   - **Type**: Custom TCP
   - **Protocol**: TCP
   - **Port range**: 8080
   - **Source**: `0.0.0.0/0` (público)
   - **Description**: `Gateway API`

   **Regla 3 - Auth Service:**
   - **Type**: Custom TCP
   - **Protocol**: TCP
   - **Port range**: 8001
   - **Source**: `0.0.0.0/0`
   - **Description**: `Auth Service`

   **Regla 4 - Usuario Service:**
   - **Type**: Custom TCP
   - **Protocol**: TCP
   - **Port range**: 8095
   - **Source**: `0.0.0.0/0`
   - **Description**: `Usuario Service`

   **Regla 5 - Productos Service:**
   - **Type**: Custom TCP
   - **Protocol**: TCP
   - **Port range**: 8003
   - **Source**: `0.0.0.0/0`
   - **Description**: `Productos Service`

   **Regla 6 - Carrito Service:**
   - **Type**: Custom TCP
   - **Protocol**: TCP
   - **Port range**: 8008
   - **Source**: `0.0.0.0/0`
   - **Description**: `Carrito Service`

   **Regla 7 - Pedido Service:**
   - **Type**: Custom TCP
   - **Protocol**: TCP
   - **Port range**: 8085
   - **Source**: `0.0.0.0/0`
   - **Description**: `Pedido Service`

   **Regla 8 - Pagos Service:**
   - **Type**: Custom TCP
   - **Protocol**: TCP
   - **Port range**: 8011
   - **Source**: `0.0.0.0/0`
   - **Description**: `Pagos Service`

   **Regla 9 - Reseñas Service:**
   - **Type**: Custom TCP
   - **Protocol**: TCP
   - **Port range**: 8010
   - **Source**: `0.0.0.0/0`
   - **Description**: `Reseñas Service`

   **Regla 10 - Referidos Service:**
   - **Type**: Custom TCP
   - **Protocol**: TCP
   - **Port range**: 8005
   - **Source**: `0.0.0.0/0`
   - **Description**: `Referidos Service`

   **Regla 11 - Promociones Service:**
   - **Type**: Custom TCP
   - **Protocol**: TCP
   - **Port range**: 8091
   - **Source**: `0.0.0.0/0`
   - **Description**: `Promociones Service`

   **Regla 12 - Inventario Service:**
   - **Type**: Custom TCP
   - **Protocol**: TCP
   - **Port range**: 8004
   - **Source**: `0.0.0.0/0`
   - **Description**: `Inventario Service`

   **Regla 13 - Notificaciones Service:**
   - **Type**: Custom TCP
   - **Protocol**: TCP
   - **Port range**: 8006
   - **Source**: `0.0.0.0/0`
   - **Description**: `Notificaciones Service`

   **Regla 14 - Eventos Service:**
   - **Type**: Custom TCP
   - **Protocol**: TCP
   - **Port range**: 8092
   - **Source**: `0.0.0.0/0`
   - **Description**: `Eventos Service`

   **Regla 15 - Contenido Service:**
   - **Type**: Custom TCP
   - **Protocol**: TCP
   - **Port range**: 8093
   - **Source**: `0.0.0.0/0`
   - **Description**: `Contenido Service`

5. **Outbound rules** (Reglas de salida):
   - Deja el default: **"All traffic"** a `0.0.0.0/0`

6. Click en **"Create security group"**

### Paso 3.3: Crear Security Group para Frontend
1. Click en **"Create security group"** nuevamente
2. Configuración:
   - **Name**: `levelup-frontend-sg`
   - **Description**: `Security group para frontend React`
   - **VPC**: Selecciona **"Default VPC"**

3. **Inbound rules** → Click en **"Add rule"** y agrega:

   **Regla 1 - SSH (tu IP):**
   - **Type**: SSH
   - **Protocol**: TCP
   - **Port range**: 22
   - **Source**: **My IP** (o tu IP: `203.0.113.45/32`)
   - **Description**: `SSH desde mi IP`

   **Regla 2 - HTTP (público):**
   - **Type**: HTTP
   - **Protocol**: TCP
   - **Port range**: 80
   - **Source**: `0.0.0.0/0`
   - **Description**: `HTTP público`

   **Regla 3 - HTTPS (público):**
   - **Type**: HTTPS
   - **Protocol**: TCP
   - **Port range**: 443
   - **Source**: `0.0.0.0/0`
   - **Description**: `HTTPS público`

   **Regla 4 - Vite Dev Server (opcional):**
   - **Type**: Custom TCP
   - **Protocol**: TCP
   - **Port range**: 5173
   - **Source**: `0.0.0.0/0`
   - **Description**: `Vite dev server`

4. **Outbound rules**: Deja el default

5. Click en **"Create security group"**

### Paso 3.4: Crear Security Group para RDS
1. Click en **"Create security group"** nuevamente
2. Configuración:
   - **Name**: `levelup-rds-sg`
   - **Description**: `Security group para RDS PostgreSQL`
   - **VPC**: Selecciona **"Default VPC"**

3. **Inbound rules** → Click en **"Add rule"** y agrega:

   **Regla 1 - PostgreSQL (desde Backend):**
   - **Type**: PostgreSQL
   - **Protocol**: TCP
   - **Port range**: 5432
   - **Source**: 
     - Selecciona: **"Custom"** → **"Security group"**
     - Busca y selecciona: `levelup-backend-sg`
     - O escribe: `sg-xxxxx` (ID del security group de backend - lo verás después)
   - **Description**: `PostgreSQL desde backend EC2`

   **Regla 2 - PostgreSQL (tu IP - para administración):**
   - **Type**: PostgreSQL
   - **Protocol**: TCP
   - **Port range**: 5432
   - **Source**: **My IP** (o tu IP: `203.0.113.45/32`)
   - **Description**: `PostgreSQL desde mi IP para administración`

4. **Outbound rules**: Deja el default

5. Click en **"Create security group"**

6. **⚠️ IMPORTANTE**: Guarda el **Security Group ID** de `levelup-rds-sg` (ej: `sg-0123456789abcdef0`)
   - Lo necesitarás al crear la instancia RDS

---

## 4. Crear bucket S3 para imágenes

### Paso 4.1: Ir al servicio S3
1. En la consola de AWS, busca **"S3"** en la barra de búsqueda
2. Click en **"S3"** → **"Buckets"**
3. Click en **"Create bucket"**

### Paso 4.2: Configurar el bucket
1. **Bucket name**:
   - Ingresa: `levelup-gamer-products`
   - ⚠️ **IMPORTANTE**: El nombre debe ser único globalmente
   - Si no está disponible, usa: `levelup-gamer-products-[tu-nombre]`

2. **AWS Region**:
   - Selecciona: **us-east-1** (o la misma región que elegiste antes)

3. **Object Ownership**:
   - Selecciona: **"ACLs disabled"** (recomendado)

4. **Block Public Access settings**:
   - **Desmarca** "Block all public access" (para permitir acceso público a las imágenes)
   - Confirma marcando el checkbox de confirmación

5. **Bucket Versioning**:
   - Selecciona: **"Disable"** (para desarrollo)

6. **Default encryption**:
   - Selecciona: **"Enable"**
   - Encryption type: **"Amazon S3 managed keys (SSE-S3)"**

7. Click en **"Create bucket"**

### Paso 4.3: Configurar CORS del bucket
1. Click en el nombre del bucket: `levelup-gamer-products`
2. Ve a la pestaña **"Permissions"**
3. Scroll down hasta **"Cross-origin resource sharing (CORS)"**
4. Click en **"Edit"**
5. Copia y pega esta configuración:

```json
[
  {
    "AllowedHeaders": ["*"],
    "AllowedMethods": ["GET", "HEAD"],
    "AllowedOrigins": [
      "http://localhost:5173",
      "http://localhost:3000",
      "http://10.0.2.2:8094",
      "https://tu-dominio.com"
    ],
    "ExposeHeaders": ["ETag"],
    "MaxAgeSeconds": 3000
  }
]
```

6. Click en **"Save changes"**

### Paso 4.4: Configurar política pública del bucket
1. En la misma pestaña **"Permissions"**, scroll down hasta **"Bucket policy"**
2. Click en **"Edit"**
3. Copia y pega esta política (reemplaza `levelup-gamer-products` con tu nombre de bucket):

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "PublicReadGetObject",
      "Effect": "Allow",
      "Principal": "*",
      "Action": "s3:GetObject",
      "Resource": "arn:aws:s3:::levelup-gamer-products/*"
    }
  ]
}
```

4. Click en **"Save changes"**

---

## 5. Crear instancia EC2 para Backend (t3.micro)

### Paso 5.1: Crear Key Pair como .ppk para PuTTY
1. En **EC2 Dashboard**, ve a **"Key Pairs"** en el menú izquierdo
2. Click en **"Create key pair"**
3. Configuración:
   - **Name**: `levelup-backend-key`
   - **Key pair type**: **RSA**
   - **Private key file format**: **.ppk** (para PuTTY en Windows)
4. Click en **"Create key pair"**
5. **⚠️ IMPORTANTE**: Se descargará automáticamente el archivo `.ppk`
   - **Guárdalo en un lugar seguro** (ej: `C:\Users\alm\Desktop\levelup-backend-key.ppk`)
   - **No lo subas al repositorio**
   - Si lo pierdes, no podrás conectarte a la instancia
   - Este archivo `.ppk` es el que usarás con PuTTY

### Paso 5.2: Crear instancia EC2
1. En **EC2 Dashboard**, click en **"Instances"** → **"Launch instance"**
2. Configuración paso a paso:

   **Paso 2.1 - Name and tags:**
   - **Name**: `levelup-backend`

   **Paso 2.2 - Application and OS Images (AMI):**
   - Selecciona: **"Amazon Linux 2023 AMI"** (gratis)
   - O **"Ubuntu Server 22.04 LTS"** (gratis)

   **Paso 2.3 - Instance type:**
   - Selecciona: **t3.micro**
     - 2 vCPU, 1 GB RAM
     - ✅ **Eligible for free tier** (si es tu primera vez)

   **Paso 2.4 - Key pair (login):**
   - Selecciona: **"levelup-backend-key"** (el que creaste antes)
   - ⚠️ **IMPORTANTE**: Sin esto no podrás conectarte

   **Paso 2.5 - Network settings:**
   - **VPC**: Selecciona **"Default VPC"**
   - **Subnet**: Selecciona cualquier subnet (default)
   - **Auto-assign Public IP**: **Enable**
   - **Security group**: Selecciona **"Select existing security group"**
   - **Common security groups**: Selecciona **"levelup-backend-sg"** (el que creaste antes)

   **Paso 2.6 - Configure storage:**
   - **Volume 1**: **20 GB gp3** (gratis en free tier)
   - **Delete on termination**: Marca el checkbox (para que se elimine al terminar la instancia)

   **Paso 2.7 - Advanced details (opcional):**
   - Puedes dejar los defaults

3. Click en **"Launch instance"**
4. Espera a que se cree (puede tardar 1-2 minutos)
5. Click en **"View all instances"**

### Paso 5.3: Obtener IP pública de la instancia
1. En la lista de instancias, busca `levelup-backend`
2. Espera a que el estado sea **"Running"** (verde)
3. Copia estos valores (los necesitarás después):
   - **Public IPv4 address**: `54.123.45.67` (ejemplo)
   - **Public IPv4 DNS**: `ec2-54-123-45-67.compute-1.amazonaws.com`
   - **Instance ID**: `i-0123456789abcdef0`

### Paso 5.4: Asignar Elastic IP (Opcional pero Recomendado)
1. En **EC2 Dashboard**, ve a **"Elastic IPs"** en el menú izquierdo
2. Click en **"Allocate Elastic IP address"**
3. Configuración:
   - **Network border group**: Selecciona la región
   - **Public IPv4 address pool**: **Amazon's pool of IPv4 addresses**
4. Click en **"Allocate"**
5. Selecciona la Elastic IP creada
6. Click en **"Actions"** → **"Associate Elastic IP address"**
7. **Instance**: Selecciona `levelup-backend`
8. **Private IP address**: Selecciona la IP privada de la instancia
9. Click en **"Associate"**
10. **Guarda la Elastic IP** (ej: `54.123.45.67`) - esta IP no cambiará aunque reinicies la instancia

---

## 6. Configurar EC2 Backend

### Paso 6.1: Descargar e instalar PuTTY

1. Descarga PuTTY desde: [https://www.putty.org/](https://www.putty.org/)
2. Click en **"Download PuTTY"**
3. Descarga la versión para Windows (ej: `putty-64bit-0.78-installer.msi`)
4. Ejecuta el instalador
5. Instala PuTTY (incluye PuTTYgen, PSCP, PSFTP)
6. Click en **"Finish"** cuando termine la instalación

### Paso 6.2: Conectarse a EC2 usando PuTTY

**Nota**: Si ya descargaste el Key Pair como `.ppk` en el Paso 5.1, usa ese archivo directamente.

1. Abre **PuTTY** (búscalo en el menú de inicio de Windows)

2. **Configuración de conexión:**
   - **Host Name (or IP address)**: Ingresa solo la IP (sin usuario)
     - Ejemplo: `54.123.45.67` (tu IP pública o Elastic IP)
     - **NO escribas** `ec2-user@` aquí
   - **Port**: `22`
   - **Connection type**: **SSH**

3. **Configurar Key Pair (autenticación):**
   - En el panel izquierdo, expande **"SSH"**
   - Click en **"Auth"** (en SSH)
   - Click en **"Credentials"** (en Auth)
   - En **"Private key file for authentication"**, click en **"Browse"**
   - Navega a donde guardaste tu archivo `.ppk` (ej: `C:\Users\alm\Desktop\levelup-backend-key.ppk`)
   - Selecciona el archivo `.ppk`
   - Click en **"Open"**
   - Verás la ruta del archivo en el campo

4. **Configurar usuario (opcional pero recomendado):**
   - En el panel izquierdo, expande **"SSH"** → **"Auth"**
   - En **"Username"**, escribe:
     - `ec2-user` (para Amazon Linux)
     - `ubuntu` (para Ubuntu AMI)

5. **Guardar configuración (recomendado):**
   - En el panel izquierdo, click en **"Session"** (arriba del todo)
   - **Saved Sessions**: Escribe `levelup-backend`
   - Click en **"Save"**
   - La próxima vez, solo selecciona `levelup-backend` y click en **"Load"**

6. **Conectarse:**
   - Verifica que la configuración esté correcta
   - Click en **"Open"** (abajo)

7. **Primera conexión:**
   - Aparecerá una ventana de advertencia: **"PuTTY Security Alert"**
   - Dice: **"The server's host key is not cached in the registry"**
   - Click en **"Accept"** (esto guarda la clave del servidor y no volverá a aparecer)

8. **Login:**
   - Si configuraste el usuario en el paso 4, deberías conectarte automáticamente
   - Si no configuraste el usuario, escribe:
     - `ec2-user` (para Amazon Linux) y presiona Enter
     - `ubuntu` (para Ubuntu) y presiona Enter
   - No deberías necesitar contraseña (si configuraste el key pair correctamente)
   - Si todo está bien, verás el prompt: `[ec2-user@ip-xxx-xxx-xxx-xxx ~]$`

9. **Si aparece error de autenticación:**
   - Verifica que el archivo `.ppk` sea correcto
   - Verifica que el usuario sea correcto (`ec2-user` o `ubuntu`)
   - Verifica que la IP sea correcta
   - Verifica que el Security Group permita SSH desde tu IP

### Paso 6.3: Actualizar sistema e instalar dependencias
Una vez conectado a EC2, ejecuta:

**Si usas Amazon Linux 2023:**
```bash
# Actualizar sistema
sudo yum update -y

# Instalar Java 21 (Amazon Corretto - Recomendado para AWS)
sudo yum install java-21-amazon-corretto -y

# Si java-21-amazon-corretto no está disponible, instalar desde repositorio de Amazon
# sudo yum install java-21-amazon-corretto-headless -y

# Verificar instalación de Java
java -version
# Debería mostrar: openjdk version "21.x.x" (Amazon Corretto)

# Instalar Maven
sudo yum install maven -y

# Instalar Git
sudo yum install git -y

# Instalar PostgreSQL client (para conexión a RDS)
sudo yum install postgresql15 -y

# Verificar instalaciones
java -version
mvn -version
git --version
```

**Si usas Ubuntu:**
```bash
# Actualizar sistema
sudo apt update && sudo apt upgrade -y

# Instalar Java 21 (OpenJDK)
sudo apt install openjdk-21-jdk -y

# Verificar instalación de Java
java -version
# Debería mostrar: openjdk version "21.x.x"

# Instalar Maven
sudo apt install maven -y

# Instalar Git
sudo apt install git -y

# Instalar PostgreSQL client
sudo apt install postgresql-client -y

# Verificar instalaciones
java -version
mvn -version
git --version
```

### Paso 6.4: Subir código del backend

**Opción A: Usar Git (Recomendado)**
```bash
# En EC2 (en la terminal de PuTTY)
cd /home/ec2-user
git clone https://github.com/tu-usuario/tu-repo.git
cd tu-repo/Backend_Java_Spring/Fullstack_Ecommerce
```

**Opción B: Usar PSCP (PuTTY) desde tu máquina local**
1. Abre **PowerShell** o **CMD** en tu máquina local
2. Navega a la carpeta donde está tu proyecto:
   ```powershell
   cd C:\Users\alm\Desktop\3REPOSITORY_GLOBAL_FULLSTACK
   ```
3. Usa PSCP (incluido con PuTTY) para subir archivos:
   ```powershell
   # Formato: pscp -i [key.ppk] -r [carpeta_local] [usuario]@[IP]:[ruta_remota]
   pscp -i levelup-backend-key.ppk -r Backend_Java_Spring\Fullstack_Ecommerce ec2-user@54.123.45.67:/home/ec2-user/
   ```
4. Si te pide confirmación, escribe `yes` y presiona Enter
5. Espera a que termine la subida

**Opción C: Usar WinSCP (Interfaz gráfica - Recomendado para Windows)**
1. Descarga WinSCP desde: [https://winscp.net/](https://winscp.net/)
2. Instala WinSCP
3. Abre WinSCP
4. Configuración:
   - **File protocol**: **SFTP**
   - **Host name**: Tu IP pública (ej: `54.123.45.67`)
   - **Port number**: `22`
   - **User name**: `ec2-user` (o `ubuntu`)
   - **Password**: Déjalo vacío (usaremos key pair)
   - Click en **"Advanced"** → **"SSH"** → **"Authentication"**
   - En **"Private key file"**, selecciona tu archivo `.ppk`
   - Click en **"OK"**
5. Click en **"Login"**
6. Arrastra y suelta la carpeta `Backend_Java_Spring\Fullstack_Ecommerce` a `/home/ec2-user/`

### Paso 6.5: Configurar variables de entorno
```bash
# En EC2, crear archivo de configuración
cd /home/ec2-user/Backend_Java_Spring/Fullstack_Ecommerce
sudo nano /etc/environment

# Agrega estas líneas (reemplaza con tus valores reales):
DB_HOST=levelup-db.xxxxx.us-east-1.rds.amazonaws.com
DB_PORT=5432
DB_USERNAME=levelup_admin
DB_PASSWORD=tu_password_segura_aqui
DB_DRIVER=org.postgresql.Driver
JWT_SECRET=levelUpGamerSecretKey2024SecureForJWT256BitsMinimum
CORS_ORIGINS=http://[IP_FRONTEND]:5173,http://[IP_FRONTEND]:80,https://tu-dominio.com
S3_BUCKET_NAME=levelup-gamer-products
S3_REGION=us-east-1
AWS_REGION=us-east-1

# Guardar: Ctrl+O, Enter, Ctrl+X
# Cargar variables
source /etc/environment
```

### Paso 6.6: Compilar microservicios
```bash
# Ir al directorio principal del backend
cd /home/ec2-user/levelup/Backend_Java_Spring/Fullstack_Ecommerce

# Compilar cada microservicio con el wrapper de Maven
for service in \
  msvc-gateway \
  msvc-auth \
  msvc-usuario \
  msvc-productos \
  msvc-inventario \
  msvc-referidos \
  msvc-notificaciones \
  msvc-carrito \
  msvc-resenia \
  msvc-pagos \
  msvc-pedido \
  msvc-promociones \
  msvc-eventos \
  msvc-contenido
do
  echo "Compilando $service..."
  cd "$service"
  ./mvnw clean package -DskipTests
  cd ..
done
```

### Paso 6.7: Crear script de inicio seguro
```bash
# Crear el script de arranque
cd /home/ec2-user/levelup/Backend_Java_Spring/Fullstack_Ecommerce
nano start.sh

# Contenido del script:
#!/bin/bash

export JAVA_HOME=/usr/lib/jvm/java-21-amazon-corretto.x86_64
export PATH=$JAVA_HOME/bin:$PATH
export SPRING_PROFILES_ACTIVE=prod

export DB_HOST=levelup-db.cvcqjuf3hjcs.us-east-1.rds.amazonaws.com
export DB_PORT=5432
export DB_USERNAME=levelup_admin
export DB_PASSWORD='$Hola1234'
export DB_DRIVER=org.postgresql.Driver

export JWT_SECRET=levelUpGamerSecretKey2024SecureForJWT256BitsMinimum
export CORS_ORIGINS=http://54.161.72.45:5173,http://54.161.72.45:80,https://ec2-54-161-72-45.compute.amazonaws.com

export S3_BUCKET_NAME=levelup-gamer-products
export S3_REGION=us-east-1
export S3_BASE_URL=https://levelup-gamer-products.s3.us-east-1.amazonaws.com
export AWS_REGION=us-east-1

cd "$(dirname "$0")" || exit 1
mkdir -p logs

echo "=========================================="
echo "🚀 Iniciando microservicios LevelUp Gamer (modo seguro)"
echo "=========================================="
java -version
echo "JAVA_HOME: $JAVA_HOME"
echo "SPRING_PROFILES_ACTIVE: $SPRING_PROFILES_ACTIVE"
echo "=========================================="

start_service() {
  local SERVICE_NAME=$1
  local SERVICE_DIR=$2
  local PORT=$3
  local LOG_FILE="logs/${SERVICE_NAME}.log"

  echo ""
  echo "Iniciando $SERVICE_NAME en puerto $PORT..."

  if [ ! -f "$SERVICE_DIR/target/"*.jar ]; then
    echo "Compilando $SERVICE_NAME..."
    cd "$SERVICE_DIR" || { echo "Error: no existe $SERVICE_DIR"; return 1; }
    ./mvnw clean package -DskipTests || { echo "Fallo la compilacion de $SERVICE_NAME"; cd - >/dev/null; return 1; }
    cd - >/dev/null
  fi

  cd "$SERVICE_DIR" || return 1

  local DB_NAME="levelup_main"
  case $SERVICE_NAME in
    msvc-auth) DB_NAME="levelup_auth" ;;
    msvc-usuario) DB_NAME="levelup_usuario" ;;
    msvc-productos) DB_NAME="levelup_productos" ;;
    msvc-carrito) DB_NAME="levelup_carrito" ;;
    msvc-pedido) DB_NAME="levelup_pedido" ;;
    msvc-pagos) DB_NAME="levelup_pagos" ;;
    msvc-resenia) DB_NAME="levelup_resenia" ;;
    msvc-referidos) DB_NAME="levelup_referidos" ;;
    msvc-promociones) DB_NAME="levelup_promociones" ;;
    msvc-inventario) DB_NAME="levelup_inventario" ;;
    msvc-notificaciones) DB_NAME="levelup_notificaciones" ;;
    msvc-eventos) DB_NAME="levelup_eventos" ;;
    msvc-contenido) DB_NAME="levelup_contenido" ;;
  esac

  export DB_URL="jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}"

  local JAVA_OPTS="-Xms256m -Xmx512m"
  nohup nice -n 10 java $JAVA_OPTS -jar target/*.jar --server.port=$PORT > "../${LOG_FILE}" 2>&1 &

  local PID=$!
  echo "Servicio $SERVICE_NAME iniciado (PID $PID, log: $LOG_FILE)"

  cd - >/dev/null || return 1

  echo "Esperando a que el puerto $PORT este disponible..."
  for i in {1..25}; do
    if netstat -tuln | grep -q ":$PORT "; then
      echo "$SERVICE_NAME activo en puerto $PORT"
      break
    fi
    sleep 1
  done

  sleep 5
}

SERVICES=(
  "msvc-gateway:8094"
  "msvc-auth:8001"
  "msvc-usuario:8095"
  "msvc-productos:8003"
  "msvc-inventario:8004"
  "msvc-referidos:8005"
  "msvc-notificaciones:8006"
  "msvc-carrito:8008"
  "msvc-resenia:8010"
  "msvc-pagos:8011"
  "msvc-pedido:8085"
  "msvc-promociones:8091"
  "msvc-eventos:8092"
  "msvc-contenido:8093"
)

for entry in "${SERVICES[@]}"; do
  IFS=":" read -r NAME PORT <<<"$entry"
  start_service "$NAME" "$NAME" "$PORT"
done

echo ""
echo "=========================================="
echo "Todos los microservicios iniciados en modo seguro."
echo "Logs disponibles en ./logs/"
echo "=========================================="
```

Guarda el archivo (Ctrl+O, Enter, Ctrl+X) y aplica permisos de ejecución:

```bash
chmod +x start.sh
```

---

## 7. Crear instancia RDS PostgreSQL

### Paso 7.1: Crear instancia RDS
1. En la consola de AWS, busca **"RDS"**
2. Click en **"RDS"** → **"Databases"**
3. Click en **"Create database"**

### Paso 7.2: Configurar base de datos
1. **Database creation method**: **Standard create**

2. **Engine options**:
   - **Engine type**: **PostgreSQL**
   - **Version**: **PostgreSQL 15.x** (o la más reciente)

3. **Templates**:
   - **Production** (para más control)
   - O **Free tier** si está disponible

4. **Settings**:
   - **DB instance identifier**: `levelup-db`
   - **Master username**: `levelup_admin`
   - **Master password**: [Genera una contraseña segura y guárdala]
   - **Confirm password**: [Misma contraseña]

5. **Instance configuration**:
   - **DB instance class**: **db.t3.micro**
     - 2 vCPU, 1 GB RAM
     - ✅ **Eligible for free tier** (si es tu primera vez)

6. **Storage**:
   - **Storage type**: **General Purpose SSD (gp3)**
   - **Allocated storage**: **20 GB**
   - **Storage autoscaling**: **Desactivar** (ahorra dinero)

7. **Connectivity**:
   - **VPC**: **Default VPC**
   - **Subnet group**: **default** (creado automáticamente)
   - **Publicly accessible**: **SÍ** ✅ (necesario para conectar desde EC2 y app móvil)
   - **VPC security group**: **Select existing**
   - **Existing VPC security groups**: Selecciona **"levelup-rds-sg"** (el que creaste antes)
   - **Availability Zone**: **No preference**

8. **Database authentication**:
   - **Password authentication**

9. **Additional configuration**:
   - **Initial database name**: `levelup_main`
   - **DB parameter group**: **default**
   - **Backup**: **Disable automated backups** (para ahorrar, o habilita si quieres)

10. **Monitoring**:
    - **Enable Enhanced monitoring**: Desactivar (ahorra dinero)

11. Click en **"Create database"**
12. Espera a que se cree (puede tardar 5-10 minutos)

### Paso 7.3: Obtener endpoint de RDS
1. En **RDS Dashboard**, click en tu base de datos: `levelup-db`
2. En la sección **"Connectivity & security"**, copia:
   - **Endpoint**: `levelup-db.xxxxx.us-east-1.rds.amazonaws.com`
   - **Port**: `5432`
3. **Guárdalos**, los necesitarás para configurar los microservicios

### Paso 7.4: Crear todas las bases de datos
Conéctate a RDS desde EC2 o desde tu máquina local:

```bash
# Desde EC2 (si instalaste postgresql client)
psql -h levelup-db.xxxxx.us-east-1.rds.amazonaws.com -U levelup_admin -d postgres

# Ingresa la contraseña cuando se solicite
# Luego ejecuta:

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
CREATE DATABASE levelup_notificaciones;
CREATE DATABASE levelup_eventos;
CREATE DATABASE levelup_contenido;

# Salir
\q
```

---

## 8. Crear instancia EC2 para Frontend (t3.micro)

### Paso 8.1: Crear Key Pair para Frontend (o reutilizar el mismo)
Puedes usar el mismo key pair `levelup-backend-key` o crear uno nuevo.

### Paso 8.2: Crear instancia EC2 Frontend
1. En **EC2 Dashboard**, click en **"Launch instance"**
2. Configuración:

   **Name**: `levelup-frontend`
   **AMI**: **Amazon Linux 2023** o **Ubuntu Server 22.04 LTS**
   **Instance type**: **t3.micro**
   **Key pair**: **levelup-backend-key** (o el que prefieras)
   **Network settings**:
   - **Security group**: Selecciona **"levelup-frontend-sg"**
   - **Auto-assign Public IP**: **Enable**
   **Configure storage**: **20 GB gp3**

3. Click en **"Launch instance"**
4. Espera a que se cree
5. **Guarda la IP pública** de la instancia frontend

### Paso 8.3: Asignar Elastic IP al Frontend (Opcional)
Sigue los mismos pasos que en el Paso 5.4 para asignar una Elastic IP al frontend.

---

## 9. Configurar EC2 Frontend

### Paso 9.1: Conectarse a EC2 Frontend
```bash
ssh -i levelup-backend-key.pem ec2-user@[IP_FRONTEND]
```

### Paso 9.2: Instalar Node.js y Nginx
```bash
# Amazon Linux
sudo yum update -y
curl -fsSL https://rpm.nodesource.com/setup_20.x | sudo bash -
sudo yum install -y nodejs nginx git

# Ubuntu
sudo apt update && sudo apt upgrade -y
curl -fsSL https://deb.nodesource.com/setup_20.x | sudo -E bash -
sudo apt install -y nodejs nginx git

# Verificar
node --version
npm --version
nginx -v
```

### Paso 9.3: Subir código del frontend
```bash
# En EC2
cd /home/ec2-user
git clone https://github.com/tu-usuario/tu-repo.git
cd tu-repo/Frontend_React_Typescript/levelup-react/proyecto-levelup-frontend/levelup-react
```

### Paso 9.4: Configurar variables de entorno
```bash
# Crear archivo .env.production
nano .env.production

# Contenido (reemplaza con IP de tu backend):
VITE_API_BASE_URL=http://[IP_BACKEND]:8080/api/v1
VITE_IMAGE_BASE_URL=http://[IP_BACKEND]:8003
VITE_GATEWAY_URL=http://[IP_BACKEND]:8080
VITE_S3_BASE_URL=https://levelup-gamer-products.s3.us-east-1.amazonaws.com

# Guardar: Ctrl+O, Enter, Ctrl+X
```

### Paso 9.5: Compilar frontend
```bash
# Instalar dependencias
npm install

# Compilar para producción
npm run build

# Los archivos estarán en dist/
ls -la dist/
```

### Paso 9.6: Configurar Nginx
```bash
# Crear configuración de Nginx
sudo nano /etc/nginx/conf.d/levelup.conf

# Contenido:
server {
    listen 80;
    server_name [IP_FRONTEND] o tu-dominio.com;
    
    root /home/ec2-user/tu-repo/Frontend_React_Typescript/levelup-react/proyecto-levelup-frontend/levelup-react/dist;
    index index.html;
    
    location / {
        try_files $uri $uri/ /index.html;
    }
    
    # Proxy para APIs si es necesario
    location /api {
        proxy_pass http://[IP_BACKEND]:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}

# Guardar: Ctrl+O, Enter, Ctrl+X
# Verificar configuración
sudo nginx -t

# Reiniciar Nginx
sudo systemctl enable nginx
sudo systemctl start nginx
sudo systemctl status nginx
```

---

## 10. Configurar App Kotlin

### Paso 10.1: Actualizar URLs en Kotlin
En tu proyecto Kotlin, actualiza el archivo de configuración de API:

```kotlin
// En ApiConfig.kt o similar
object ApiConfig {
    const val BASE_URL = "http://[IP_BACKEND]:8080/api/v1"
    const val GATEWAY_URL = "http://[IP_BACKEND]:8080"
    const val AUTH_URL = "http://[IP_BACKEND]:8001/api/v1"
    const val PRODUCTOS_URL = "http://[IP_BACKEND]:8003/api/v1"
    // ... etc
}
```

### Paso 10.2: Verificar permisos de Internet
En `AndroidManifest.xml`, asegúrate de tener:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

---

## 11. Verificar funcionamiento

### Paso 11.1: Verificar Backend
```bash
# Desde tu navegador o terminal
curl http://[IP_BACKEND]:8080/api/v1/productos
curl http://[IP_BACKEND]:8001/api/v1/auth/login
```

### Paso 11.2: Verificar Frontend
- Abre en navegador: `http://[IP_FRONTEND]`
- Debería cargar la aplicación React

### Paso 11.3: Verificar S3
- Abre en navegador: `https://levelup-gamer-products.s3.us-east-1.amazonaws.com/productos/1/imagen.jpg`
- Debería mostrar la imagen (si la subiste)

---

## 12. Monitoreo y costos

### Paso 12.1: Configurar Budget Alert
1. Ve a **AWS Cost Management** → **Budgets**
2. Click en **"Create budget"**
3. **Budget type**: **Cost budget**
4. **Budget amount**: **$100**
5. **Alert threshold**: **80%** ($80)
6. Click en **"Create"**

### Paso 12.2: Estimación de costos mensuales
- **EC2 t3.micro** (2 instancias): ~$15/mes cada una = **$30/mes**
- **RDS db.t3.micro**: ~**$15/mes**
- **S3 Storage** (20GB): ~**$0.50/mes**
- **Elastic IP** (2 IPs): **Gratis** (mientras estén en uso)
- **Transferencia de datos**: **Gratis hasta 100GB**
- **Total: ~$45-50/mes**

Con $100, tendrás **aprox. 2 meses** de ejecución.

---

## 📝 Checklist Final

- [ ] Cuenta AWS creada y verificada
- [ ] Región seleccionada (us-east-1)
- [ ] Security Groups creados (backend, frontend, RDS)
- [ ] Bucket S3 creado y configurado (CORS y política pública)
- [ ] EC2 Backend creado (t3.micro)
- [ ] EC2 Backend configurado (Java, Maven, código subido)
- [ ] RDS PostgreSQL creado (db.t3.micro)
- [ ] Todas las bases de datos creadas en RDS
- [ ] EC2 Frontend creado (t3.micro)
- [ ] EC2 Frontend configurado (Node.js, Nginx, código compilado)
- [ ] App Kotlin configurada con URLs correctas
- [ ] Pruebas de conectividad exitosas
- [ ] Budget alert configurado

---

## 🆘 Solución de Problemas

### Error: "Cannot connect to EC2 via SSH"
- Verifica que el Security Group permita SSH desde tu IP
- Verifica que la key pair sea correcta
- Verifica que la IP pública sea correcta

### Error: "Cannot connect to RDS"
- Verifica que el Security Group de RDS permita PostgreSQL desde el Security Group del Backend
- Verifica que el endpoint de RDS sea correcto
- Verifica que la contraseña sea correcta

### Error: "Microservicios no inician"
- Verifica logs: `tail -f msvc-auth/auth.log`
- Verifica que las variables de entorno estén configuradas
- Verifica que la base de datos exista y sea accesible

---

## 🎉 ¡Listo!

Tu aplicación fullstack está desplegada en AWS. La app de Kotlin puede conectarse directamente a los endpoints públicos del backend.

**Nota**: Para producción real, considera usar un dominio personalizado, certificados SSL, y un balanceador de carga, pero para un proyecto escolar esto es suficiente.
