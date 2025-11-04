# Guía de Despliegue en AWS

## Variables de Entorno Requeridas

Cada microservicio necesita las siguientes variables de entorno para funcionar en producción (AWS):

### Variables de Base de Datos (RDS PostgreSQL)

```bash
# URL completa de la base de datos (alternativa a las variables individuales)
DB_URL=jdbc:postgresql://your-rds-endpoint.region.rds.amazonaws.com:5432/levelup_usuario

# O usar variables individuales
DB_HOST=your-rds-endpoint.region.rds.amazonaws.com
DB_PORT=5432
DB_NAME=levelup_usuario
DB_USERNAME=your_db_user
DB_PASSWORD=your_secure_password
DB_DRIVER=org.postgresql.Driver
```

### Configuración JPA/Hibernate

```bash
# Validar esquema sin modificar (producción)
DDL_AUTO=validate
```

### Configuración de CORS

```bash
# Orígenes permitidos (separados por coma)
CORS_ORIGINS=https://yourdomain.com,https://www.yourdomain.com
```

### Configuración JWT (msvc-auth)

```bash
JWT_SECRET=your-very-secure-secret-key-minimum-256-bits
JWT_EXPIRATION=86400000
```

### URLs de Microservicios (para comunicación entre servicios)

```bash
# URLs internas del cluster (usar nombres de servicio en ECS/Kubernetes)
PRODUCTOS_URL=http://msvc-productos:8003/api/v1
USUARIO_URL=http://msvc-usuario:8095/api/v1
AUTH_URL=http://msvc-auth:8001/api/v1
# ... etc
```

## Configuración por Microservicio

### Base de Datos por Microservicio

Cada microservicio debe tener su propia base de datos en RDS:

1. **msvc-usuario**: `levelup_usuario`
2. **msvc-productos**: `levelup_productos`
3. **msvc-carrito**: `levelup_carrito`
4. **msvc-auth**: `levelup_auth`
5. **msvc-pedido**: `levelup_pedido`
6. **msvc-pagos**: `levelup_pagos`
7. **msvc-resenia**: `levelup_resenia`
8. **msvc-referidos**: `levelup_referidos`
9. **msvc-promociones**: `levelup_promociones`
10. **msvc-inventario**: `levelup_inventario`

## Perfiles de Spring Boot

### Desarrollo (Local)
```bash
spring.profiles.active=dev
```

### Producción (AWS)
```bash
spring.profiles.active=prod
```

## Configuración en AWS ECS/Fargate

### Task Definition - Variables de Entorno

```json
{
  "environment": [
    {
      "name": "SPRING_PROFILES_ACTIVE",
      "value": "prod"
    },
    {
      "name": "DB_HOST",
      "value": "your-rds-endpoint.region.rds.amazonaws.com"
    },
    {
      "name": "DB_NAME",
      "value": "levelup_usuario"
    },
    {
      "name": "DB_USERNAME",
      "value": "your_db_user"
    }
  ],
  "secrets": [
    {
      "name": "DB_PASSWORD",
      "valueFrom": "arn:aws:secretsmanager:region:account:secret:db-password"
    },
    {
      "name": "JWT_SECRET",
      "valueFrom": "arn:aws:secretsmanager:region:account:secret:jwt-secret"
    }
  ]
}
```

## Configuración en AWS Elastic Beanstalk

Crear un archivo `.ebextensions/environment.config`:

```yaml
option_settings:
  aws:elasticbeanstalk:application:environment:
    SPRING_PROFILES_ACTIVE: prod
    DB_HOST: your-rds-endpoint.region.rds.amazonaws.com
    DB_NAME: levelup_usuario
    DB_USERNAME: your_db_user
    # DB_PASSWORD debe configurarse mediante AWS Secrets Manager
    CORS_ORIGINS: https://yourdomain.com
```

## Dependencias Maven para PostgreSQL

Asegúrate de que todos los microservicios tengan en su `pom.xml`:

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

## Persistencia de Datos

### Desarrollo Local
- Los datos se guardan en archivos `.mv.db` en la carpeta `./data/` de cada microservicio
- **NO se pierden los datos** al reiniciar (usando `jdbc:h2:file:`)
- `ddl-auto=update` permite que las migraciones se apliquen automáticamente

### Producción AWS
- Usar **AWS RDS PostgreSQL** para bases de datos gestionadas
- Configurar **backups automáticos** en RDS
- Usar **Multi-AZ** para alta disponibilidad
- `ddl-auto=validate` solo valida el esquema sin modificarlo (recomendado para producción)

## Checklist de Despliegue

- [ ] Todas las bases de datos RDS creadas
- [ ] Variables de entorno configuradas en el servicio de contenedores
- [ ] Secrets Manager configurado para contraseñas y tokens JWT
- [ ] CORS configurado con dominios de producción
- [ ] Health checks configurados (`/actuator/health`)
- [ ] Logs configurados (CloudWatch)
- [ ] Balanceador de carga configurado (ALB/NLB)
- [ ] Certificados SSL configurados
- [ ] Seguridad de red (Security Groups) configurada
- [ ] Monitoreo y alertas configurados

## Modelo de Usuario - Campos Requeridos

El modelo de usuario ahora incluye todos los campos necesarios para compatibilidad con Kotlin y TypeScript:

- ✅ `idUsuario` (Long)
- ✅ `nombre`, `apellido`
- ✅ `correo`, `password`
- ✅ `telefono`, `fechaNacimiento`, `genero`
- ✅ `direccion`, `region`, `comuna`, `ciudad`, `pais`, `codigoPostal`
- ✅ `avatarUrl`
- ✅ `tipoUsuario`, `estado`
- ✅ `fechaRegistro`, `ultimoAcceso`
- ✅ `emailVerificado`, `telefonoVerificado`
- ✅ `aceptaTerminos`, `aceptaMarketing`
- ✅ `codigoReferido`, `referidoPor`
- ✅ `puntosLevelUp`, `nivelUsuario`
- ✅ `codigosCanjeados` (JSON array)
- ✅ `direcciones` (relación OneToMany)
- ✅ `preferencias` (relación OneToMany)

