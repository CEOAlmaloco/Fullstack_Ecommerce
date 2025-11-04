# Resumen de Configuración de Persistencia y AWS

## ✅ Cambios Implementados

### 1. Persistencia de Datos - Cambios Realizados

#### ✅ Bases de Datos File-Based (No en Memoria)
Todos los microservicios ahora usan **bases de datos persistentes** en lugar de memoria:

**Antes (❌ perdía datos):**
- `jdbc:h2:mem:database` - Los datos se perdían al reiniciar
- `ddl-auto=create-drop` - Borraba todas las tablas al reiniciar

**Ahora (✅ persiste datos):**
- `jdbc:h2:file:./data/msvc_[nombre]_dev` - Los datos se guardan en archivos `.mv.db`
- `ddl-auto=update` - Mantiene los datos y actualiza el esquema si es necesario

#### Microservicios Corregidos:
1. ✅ **msvc-productos**: Cambiado de `mem` a `file`, `create-drop` a `update`
2. ✅ **msvc-referidos**: Cambiado de `mem` a `file`
3. ✅ **msvc-promociones**: Cambiado de `mem` a `file`
4. ✅ **msvc-resenia**: Cambiado de `mem` a `file`
5. ✅ **msvc-pedido**: Cambiado de `mem` a `file`
6. ✅ **msvc-pagos**: Cambiado de `mem` a `file`, `create-drop` a `update`
7. ✅ **msvc-inventario**: Cambiado de `mem` a `file`
8. ✅ **msvc-usuario**: Cambiado `create-drop` a `update`
9. ✅ **msvc-carrito**: Ya estaba configurado correctamente (file-based)
10. ✅ **msvc-auth**: Ya estaba configurado correctamente (file-based)

### 2. Perfiles de Producción para AWS

Se han creado archivos `application-prod.properties` para todos los microservicios:

- ✅ msvc-usuario
- ✅ msvc-productos
- ✅ msvc-carrito
- ✅ msvc-auth
- ✅ msvc-pedido
- ✅ msvc-pagos
- ✅ msvc-resenia
- ✅ msvc-referidos
- ✅ msvc-promociones
- ✅ msvc-inventario

**Características de los perfiles de producción:**
- Configuración para **PostgreSQL** (RDS de AWS)
- Variables de entorno para configuración flexible
- `ddl-auto=validate` (solo valida, no modifica esquema)
- Connection pooling optimizado (HikariCP)
- Logging reducido (INFO en lugar de DEBUG)
- H2 console deshabilitado
- CORS configurable mediante variables de entorno

### 3. Modelo de Usuario - Campos Agregados

Se agregaron los campos faltantes para compatibilidad con Kotlin y TypeScript:

**Nuevos campos agregados:**
- ✅ `region` (String) - Región/estado del usuario
- ✅ `comuna` (String) - Comuna/municipio del usuario
- ✅ `codigosCanjeados` (String, JSON) - Lista de códigos de promoción canjeados

**Campos ya existentes verificados:**
- ✅ `idUsuario`, `nombre`, `apellido`
- ✅ `correo`, `password`, `telefono`, `fechaNacimiento`, `genero`
- ✅ `direccion`, `ciudad`, `pais`, `codigoPostal`
- ✅ `avatarUrl`, `tipoUsuario`, `estado`
- ✅ `fechaRegistro`, `ultimoAcceso`
- ✅ `emailVerificado`, `telefonoVerificado`
- ✅ `aceptaTerminos`, `aceptaMarketing`
- ✅ `codigoReferido`, `referidoPor`
- ✅ `puntosLevelUp`, `nivelUsuario`
- ✅ `direcciones` (OneToMany)
- ✅ `preferencias` (OneToMany)

## 📁 Estructura de Archivos de Base de Datos

En desarrollo, los datos se guardan en:
```
Backend_Java_Spring/Fullstack_Ecommerce/
├── data/
│   ├── msvc_auth_dev.mv.db
│   ├── msvc_carrito_dev.mv.db
│   ├── msvc_inventario_dev.mv.db
│   ├── msvc_pagos_dev.mv.db
│   ├── msvc_pedido_dev.mv.db
│   ├── msvc_productos_dev.mv.db
│   ├── msvc_promociones_dev.mv.db
│   ├── msvc_referidos_dev.mv.db
│   ├── msvc_resenia_dev.mv.db
│   └── msvc_usuario_dev.mv.db
└── [cada microservicio]/data/
    └── [archivo específico del microservicio]
```

## 🔧 Variables de Entorno Requeridas

Ver `AWS_DEPLOYMENT.md` para la lista completa de variables de entorno necesarias para el despliegue en AWS.

## 📝 Inicialización de Datos

**Microservicios con data.sql:**
- ✅ `msvc-productos`: Tiene `data.sql` con productos iniciales, categorías y subcategorías

**Microservicios sin data.sql (los datos se crean mediante APIs):**
- `msvc-usuario`: Los usuarios se crean mediante registro/login
- `msvc-carrito`: Los carritos se crean cuando un usuario agrega productos
- `msvc-auth`: Los tokens se generan dinámicamente
- `msvc-pedido`: Los pedidos se crean mediante checkout
- `msvc-pagos`: Las transacciones se crean mediante procesamiento de pago
- `msvc-resenia`: Las reseñas se crean mediante el frontend
- `msvc-referidos`: Los referidos se crean cuando un usuario usa un código
- `msvc-promociones`: Las promociones se crean mediante administración
- `msvc-inventario`: El inventario se sincroniza desde productos

## ✅ Verificación de Funcionamiento

### Para Probar la Persistencia Local:

1. **Iniciar un microservicio:**
   ```bash
   cd msvc-usuario
   mvnw.cmd spring-boot:run
   ```

2. **Crear un usuario** mediante la API

3. **Detener el microservicio** (Ctrl+C)

4. **Reiniciar el microservicio**

5. **Verificar que el usuario sigue existiendo** - Los datos deben persistir

### Verificar Archivos de Base de Datos:

Los archivos `.mv.db` en la carpeta `data/` deben existir y no deben borrarse al reiniciar.

## 🚀 Próximos Pasos para AWS

1. Crear instancias RDS PostgreSQL para cada microservicio
2. Configurar variables de entorno en ECS/Elastic Beanstalk
3. Usar AWS Secrets Manager para contraseñas y tokens
4. Configurar backups automáticos en RDS
5. Configurar Multi-AZ para alta disponibilidad

## 📚 Documentación Adicional

- Ver `AWS_DEPLOYMENT.md` para guía completa de despliegue
- Cada microservicio tiene `README.md` con información específica

