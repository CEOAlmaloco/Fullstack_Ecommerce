# Guía Paso a Paso: Configurar Amazon S3 para Imágenes de Productos

## 📋 Tabla de Contenidos

1. [Crear cuenta en AWS](#1-crear-cuenta-en-aws)
2. [Crear bucket S3](#2-crear-bucket-s3)
3. [Configurar permisos del bucket](#3-configurar-permisos-del-bucket)
4. [Configurar políticas de acceso (CORS)](#4-configurar-políticas-de-acceso-cors)
5. [Configurar grupos de seguridad (si es necesario)](#5-configurar-grupos-de-seguridad-si-es-necesario)
6. [Crear usuario IAM para acceso programático](#6-crear-usuario-iam-para-acceso-programático)
7. [Configurar variables de entorno](#7-configurar-variables-de-entorno)
8. [Subir imágenes al bucket](#8-subir-imagenes-al-bucket)
9. [Actualizar base de datos](#9-actualizar-base-de-datos)
10. [Verificar funcionamiento](#10-verificar-funcionamiento)

---

## 1. Crear cuenta en AWS

### Paso 1.1: Registrarse en AWS
1. Ve a [https://aws.amazon.com/](https://aws.amazon.com/)
2. Click en **"Crear una cuenta de AWS"**
3. Completa el formulario de registro:
   - Correo electrónico
   - Contraseña
   - Nombre de cuenta
4. Verifica tu correo electrónico
5. Completa la verificación de tarjeta de crédito (requerida, pero no se cobrará si usas el Free Tier)

### Paso 1.2: Acceder a la consola
1. Inicia sesión en [https://console.aws.amazon.com/](https://console.aws.amazon.com/)
2. Selecciona la región: **us-east-1 (N. Virginia)** o la región más cercana a tu ubicación

---

## 2. Crear bucket S3

### Paso 2.1: Ir al servicio S3
1. En la consola de AWS, busca **"S3"** en la barra de búsqueda
2. Click en **"S3"** → **"Buckets"**
3. Click en **"Create bucket"**

### Paso 2.2: Configurar el bucket
1. **Bucket name** (Nombre del bucket):
   - Ingresa: `levelup-gamer-products`
   - ⚠️ **IMPORTANTE**: El nombre debe ser único globalmente en AWS
   - Si no está disponible, usa: `levelup-gamer-products-[tu-nombre]` o `levelup-gamer-products-[tu-email]`

2. **AWS Region** (Región):
   - Selecciona: **us-east-1 (US East (N. Virginia))**
   - O la región más cercana a tu ubicación

3. **Object Ownership** (Propiedad de objetos):
   - Selecciona: **"ACLs disabled"** (recomendado)
   - O **"ACLs enabled"** si necesitas control granular

4. **Block Public Access settings** (Configuración de acceso público):
   - **Desmarca** "Block all public access" (para permitir acceso público a las imágenes)
   - O mantenlo marcado si quieres acceso privado (requerirá autenticación)

5. **Bucket Versioning** (Versionado):
   - Selecciona: **"Disable"** (para desarrollo)
   - O **"Enable"** si necesitas versionado

6. **Default encryption** (Encriptación por defecto):
   - Selecciona: **"Enable"**
   - Encryption type: **"Amazon S3 managed keys (SSE-S3)"**

7. **Advanced settings** (Configuración avanzada):
   - Object Lock: **"Disable"**

### Paso 2.3: Crear el bucket
1. Click en **"Create bucket"**
2. Espera a que se cree el bucket (puede tardar unos segundos)
3. Verás el mensaje: **"Successfully created bucket"**

---

## 3. Configurar permisos del bucket

### Paso 3.1: Configurar política de bucket pública (para acceso público)
1. Click en el nombre del bucket: `levelup-gamer-products`
2. Ve a la pestaña **"Permissions"** (Permisos)
3. Scroll down hasta **"Bucket policy"** (Política del bucket)
4. Click en **"Edit"**

### Paso 3.2: Agregar política pública
Copia y pega esta política (reemplaza `levelup-gamer-products` con el nombre de tu bucket):

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

5. Click en **"Save changes"**

### Paso 3.3: Configurar CORS (Cross-Origin Resource Sharing)
1. En la misma pestaña **"Permissions"**, scroll down hasta **"Cross-origin resource sharing (CORS)"**
2. Click en **"Edit"**
3. Copia y pega esta configuración CORS:

```json
[
  {
    "AllowedHeaders": [
      "*"
    ],
    "AllowedMethods": [
      "GET",
      "HEAD"
    ],
    "AllowedOrigins": [
      "http://localhost:5173",
      "http://localhost:3000",
      "http://10.0.2.2:8094",
      "https://tu-dominio.com"
    ],
    "ExposeHeaders": [
      "ETag"
    ],
    "MaxAgeSeconds": 3000
  }
]
```

4. Click en **"Save changes"**

---

## 4. Configurar políticas de acceso (CORS)

### Paso 4.1: Verificar CORS
La configuración CORS ya está hecha en el paso anterior. Si necesitas agregar más dominios:

1. Ve a **"Permissions"** → **"Cross-origin resource sharing (CORS)"**
2. Agrega los dominios necesarios en `AllowedOrigins`:
   - `http://localhost:5173` (React dev)
   - `http://localhost:3000` (React prod)
   - `http://10.0.2.2:8094` (Android emulator)
   - `https://tu-dominio.com` (Producción)

---

## 5. Configurar grupos de seguridad (si es necesario)

> **Nota**: Los grupos de seguridad son para EC2, no para S3. Si necesitas restringir acceso por IP a S3, usa políticas de bucket con condiciones de IP.

### Paso 5.1: Política de bucket con restricción por IP (Opcional)
Si necesitas restringir acceso por IP:

1. Ve a **"Permissions"** → **"Bucket policy"** → **"Edit"**
2. Reemplaza la política con esta (incluye restricción por IP):

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "PublicReadGetObject",
      "Effect": "Allow",
      "Principal": "*",
      "Action": "s3:GetObject",
      "Resource": "arn:aws:s3:::levelup-gamer-products/*",
      "Condition": {
        "IpAddress": {
          "aws:SourceIp": [
            "0.0.0.0/0"
          ]
        }
      }
    }
  ]
}
```

3. Reemplaza `"0.0.0.0/0"` con tus IPs específicas:
   - Ejemplo: `"203.0.113.0/24"` (rango de IPs)
   - Ejemplo: `"203.0.113.1/32"` (IP específica)

### Paso 5.2: Obtener tu IP pública
Para agregar tu IP a la política:

1. Ve a [https://whatismyipaddress.com/](https://whatismyipaddress.com/)
2. Copia tu **IPv4 Address**
3. Agrega `/32` al final para IP específica (ej: `203.0.113.1/32`)
4. O usa `/24` para rango (ej: `203.0.113.0/24`)

---

## 6. Crear usuario IAM para acceso programático

### Paso 6.1: Ir a IAM
1. En la consola de AWS, busca **"IAM"** en la barra de búsqueda
2. Click en **"IAM"** → **"Users"**
3. Click en **"Create user"**

### Paso 6.2: Configurar usuario
1. **User name**: `levelup-s3-user`
2. **Select AWS credential type**:
   - ✅ Marca: **"Access key - Programmatic access"**
3. Click en **"Next: Permissions"**

### Paso 6.3: Asignar permisos
1. Selecciona: **"Attach policies directly"**
2. Busca y selecciona: **"AmazonS3FullAccess"** (para desarrollo)
   - O crea una política personalizada con solo permisos de lectura/escritura en tu bucket específico
3. Click en **"Next: Tags"** (opcional)
4. Click en **"Next: Review"**
5. Click en **"Create user"**

### Paso 6.4: Guardar credenciales
1. **⚠️ IMPORTANTE**: Guarda las credenciales ahora, no podrás verlas después
2. **Access key ID**: `AKIAIOSFODNN7EXAMPLE`
   - Copia y guarda en un lugar seguro
3. **Secret access key**: `wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY`
   - Copia y guarda en un lugar seguro
4. Click en **"Download .csv"** para descargar las credenciales
5. Click en **"Done"**

### Paso 6.5: Crear política personalizada (Opcional - Más seguro)
Si quieres restringir el acceso solo a tu bucket:

1. Ve a **"IAM"** → **"Policies"** → **"Create policy"**
2. Click en **"JSON"**
3. Copia y pega esta política (reemplaza `levelup-gamer-products`):

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "s3:PutObject",
        "s3:GetObject",
        "s3:DeleteObject",
        "s3:ListBucket"
      ],
      "Resource": [
        "arn:aws:s3:::levelup-gamer-products",
        "arn:aws:s3:::levelup-gamer-products/*"
      ]
    }
  ]
}
```

4. Click en **"Next"**
5. **Policy name**: `LevelUpS3BucketPolicy`
6. Click en **"Create policy"**
7. Asigna esta política al usuario creado anteriormente

---

## 7. Configurar variables de entorno

### Paso 7.1: Variables de entorno en el backend
Edita `Backend_Java_Spring/Fullstack_Ecommerce/msvc-productos/src/main/resources/application.properties`:

```properties
# S3 Configuration
s3.bucket.name=levelup-gamer-products
s3.region=us-east-1
s3.base.url=
```

### Paso 7.2: Variables de entorno para acceso programático (Opcional)
Si necesitas subir imágenes desde el backend:

1. Crea un archivo `.env` en la raíz del proyecto (o configura variables de entorno del sistema):
```bash
AWS_ACCESS_KEY_ID=AKIAIOSFODNN7EXAMPLE
AWS_SECRET_ACCESS_KEY=wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY
AWS_REGION=us-east-1
```

2. O configura en `application.properties`:
```properties
aws.access.key=${AWS_ACCESS_KEY_ID}
aws.secret.key=${AWS_SECRET_ACCESS_KEY}
aws.region=${AWS_REGION:us-east-1}
```

---

## 8. Subir imágenes al bucket

### Paso 8.1: Crear estructura de carpetas
1. En el bucket S3, click en **"Create folder"**
2. Crea la carpeta: `productos`
3. Dentro de `productos`, crea subcarpetas por ID de producto:
   - `productos/1/`
   - `productos/2/`
   - etc.

### Paso 8.2: Subir imágenes
1. Click en la carpeta del producto (ej: `productos/1/`)
2. Click en **"Upload"**
3. Click en **"Add files"** o **"Add folder"**
4. Selecciona las imágenes:
   - `imagen.jpg` (imagen principal)
   - `img1.jpg`, `img2.jpg` (imágenes adicionales)
5. Scroll down y click en **"Upload"**
6. Espera a que se suban las imágenes

### Paso 8.3: Obtener la URL de la imagen
1. Click en la imagen subida
2. Click en **"Object URL"** en la parte superior
3. Copia la URL (ejemplo):
   ```
   https://levelup-gamer-products.s3.us-east-1.amazonaws.com/productos/1/imagen.jpg
   ```

### Paso 8.4: Nombrar las imágenes correctamente
- **Imagen principal**: `imagen.jpg` o `imagen.png`
- **Imágenes adicionales**: `img1.jpg`, `img2.jpg`, etc.
- **Estructura recomendada**:
  ```
  productos/
    ├── 1/
    │   ├── imagen.jpg
    │   ├── img1.jpg
    │   └── img2.jpg
    ├── 2/
    │   ├── imagen.jpg
    │   └── img1.jpg
  ```

---

## 9. Actualizar base de datos

### Paso 9.1: Obtener referencias S3
Para cada producto, guarda solo la referencia (key) en la base de datos:

- **Imagen principal**: `productos/1/imagen.jpg`
- **Imágenes adicionales**: `["productos/1/img1.jpg","productos/1/img2.jpg"]`

### Paso 9.2: Script SQL para actualizar
Ejecuta este script SQL en tu base de datos:

```sql
-- Actualizar imagen principal a referencia S3
UPDATE productos 
SET imagen = 'productos/' || id_producto || '/imagen.jpg'
WHERE imagen LIKE 'data:image%' OR imagen LIKE 'http%';

-- Actualizar imágenes adicionales a referencias S3
UPDATE productos 
SET imagenes = '["productos/' || id_producto || '/img1.jpg","productos/' || id_producto || '/img2.jpg"]'
WHERE imagenes LIKE '%data:image%' OR imagenes LIKE '%http%';
```

### Paso 9.3: Verificar actualización
Consulta la base de datos para verificar:

```sql
SELECT id_producto, nombre_producto, imagen, imagenes 
FROM productos 
LIMIT 10;
```

Deberías ver:
- `imagen`: `productos/1/imagen.jpg`
- `imagenes`: `["productos/1/img1.jpg","productos/1/img2.jpg"]`

---

## 10. Verificar funcionamiento

### Paso 10.1: Verificar URL de S3
1. Abre el navegador
2. Ve a la URL de una imagen:
   ```
   https://levelup-gamer-products.s3.us-east-1.amazonaws.com/productos/1/imagen.jpg
   ```
3. Deberías ver la imagen cargada

### Paso 10.2: Probar desde el backend
1. Inicia el microservicio `msvc-productos`
2. Haz una petición GET a: `http://localhost:8003/api/v1/productos`
3. Verifica que el campo `imagenUrl` tenga la URL completa de S3:
   ```json
   {
     "idProducto": 1,
     "nombreProducto": "Laptop Gaming",
     "imagenUrl": "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/productos/1/imagen.jpg",
     "imagenS3Key": "productos/1/imagen.jpg"
   }
   ```

### Paso 10.3: Probar desde el frontend
1. Inicia el frontend React: `npm run dev`
2. Ve a la página de productos
3. Verifica que las imágenes se carguen correctamente desde S3
4. Verifica en la consola del navegador que las URLs sean de S3

---

## 🔧 Configuración Avanzada

### Configurar CloudFront (CDN) - Opcional
Para mejorar el rendimiento y reducir costos:

1. Ve a **CloudFront** en la consola de AWS
2. Click en **"Create distribution"**
3. **Origin domain**: Selecciona tu bucket S3
4. **Origin path**: `/productos`
5. Click en **"Create distribution"**
6. Espera a que se cree (puede tardar 15-20 minutos)
7. Usa la URL de CloudFront en lugar de la URL directa de S3

### Configurar acceso privado con signed URLs
Si quieres que las imágenes sean privadas:

1. Configura el bucket como privado (Block Public Access)
2. Usa AWS SDK para generar signed URLs con expiración
3. Las URLs expiran después de un tiempo (ej: 1 hora)

---

## 📝 Resumen de Configuración

### Variables importantes:
- **Bucket name**: `levelup-gamer-products`
- **Region**: `us-east-1`
- **Access Key ID**: `AKIAIOSFODNN7EXAMPLE` (tu clave real)
- **Secret Access Key**: `wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY` (tu clave real)

### URLs de ejemplo:
- **URL directa S3**: `https://levelup-gamer-products.s3.us-east-1.amazonaws.com/productos/1/imagen.jpg`
- **Referencia en BD**: `productos/1/imagen.jpg`

### Estructura de carpetas:
```
levelup-gamer-products/
  └── productos/
      ├── 1/
      │   ├── imagen.jpg
      │   ├── img1.jpg
      │   └── img2.jpg
      ├── 2/
      │   ├── imagen.jpg
      └── ...
```

---

## ⚠️ Notas Importantes

1. **Costo**: S3 tiene un tier gratuito (5GB por 12 meses), luego se cobra por almacenamiento y transferencia
2. **Seguridad**: Nunca subas las credenciales de AWS al repositorio
3. **Backup**: Considera habilitar versionado para hacer backup de imágenes
4. **Lifecycle**: Configura reglas de lifecycle para eliminar imágenes antiguas automáticamente

---

## 🆘 Solución de Problemas

### Error: "Access Denied"
- Verifica que la política de bucket permita acceso público
- Verifica que CORS esté configurado correctamente
- Verifica que las credenciales IAM sean correctas

### Error: "CORS policy"
- Verifica que los dominios estén en la configuración CORS
- Verifica que los métodos permitidos sean `GET` y `HEAD`

### Error: "Bucket name already exists"
- El nombre del bucket debe ser único globalmente
- Usa un nombre diferente (ej: `levelup-gamer-products-[tu-nombre]`)

---

## 📚 Recursos Adicionales

- [Documentación oficial de S3](https://docs.aws.amazon.com/s3/)
- [Precios de S3](https://aws.amazon.com/s3/pricing/)
- [Free Tier de AWS](https://aws.amazon.com/free/)

---

**¡Listo!** Tu bucket S3 está configurado y listo para almacenar imágenes de productos. 🚀

