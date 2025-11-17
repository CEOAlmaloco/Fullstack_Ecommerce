# Guía para Commit y Deploy en AWS

## Pasos para hacer commit y push

### 1. Agregar los archivos modificados

```bash
# Agregar solo los archivos del microservicio de productos
git add src/main/java/com/ampuero/msvc/producto/controllers/ProductoController.java
git add VERIFICACION_IMAGENES_S3.md

# O agregar todos los cambios si quieres incluir los otros microservicios
git add .
```

### 2. Hacer commit

```bash
git commit -m "feat: Cambiar carrusel y logo de Base64 a URLs de S3

- Endpoint /productos/carrusel ahora devuelve URLs de S3 en lugar de Base64
- Endpoint /productos/logo ahora devuelve URL de S3 en lugar de Base64
- Agregado S3Service para construir URLs de S3
- Agregada documentación VERIFICACION_IMAGENES_S3.md"
```

### 3. Hacer push

```bash
git push origin Rama-principal
```

## Pasos para hacer pull en AWS

### Opción 1: SSH directo a la instancia

```bash
# Conectarse a la instancia AWS
ssh -i /ruta/a/tu/key.pem ec2-user@44.209.152.110

# O usando el DNS
ssh -i /ruta/a/tu/key.pem ec2-user@ec2-44-209-152-110.compute-1.amazonaws.com
```

### Opción 2: Desde AWS Systems Manager (si está configurado)

1. Ve a AWS Console → EC2 → Instances
2. Selecciona la instancia `i-09cd2ff1efffdb4a2`
3. Click en "Connect" → "Session Manager"
4. Se abrirá una terminal en el navegador

### Una vez conectado a AWS:

```bash
# Navegar al directorio del proyecto
cd /ruta/del/proyecto/Backend_Java_Spring/Fullstack_Ecommerce/msvc-productos

# Verificar la rama actual
git branch

# Cambiar a la rama correcta si es necesario
git checkout Rama-principal

# Hacer pull de los últimos cambios
git pull origin Rama-principal

# Si hay conflictos, resolverlos y luego:
git add .
git commit -m "Merge: Resolver conflictos"
```

### Reiniciar el microservicio

```bash
# Si usas systemd
sudo systemctl restart msvc-productos

# O si lo ejecutas manualmente, detener y reiniciar
# Buscar el proceso
ps aux | grep msvc-productos

# Matar el proceso (reemplazar PID con el número del proceso)
kill -9 PID

# Reiniciar (ajustar según tu método de ejecución)
cd /ruta/del/proyecto/Backend_Java_Spring/Fullstack_Ecommerce/msvc-productos
mvn spring-boot:run
# O
java -jar target/msvc-productos-*.jar
```

## Verificar que funciona

```bash
# Probar el endpoint del carrusel
curl http://localhost:8003/api/v1/productos/carrusel

# Probar el endpoint del logo
curl http://localhost:8003/api/v1/productos/logo
```

Las respuestas deberían contener URLs de S3 en lugar de Base64.

