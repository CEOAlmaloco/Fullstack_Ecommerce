# Inicializadores de Datos - Ubicación y Uso

Esta documentación explica dónde están ubicados los inicializadores de datos para cada microservicio y cómo usarlos.

## Ubicación de los Inicializadores

Todos los inicializadores de datos están ubicados en la carpeta `config` de cada microservicio:

```
msvc-{nombre}/
  src/main/java/com/ampuero/msvc/{nombre}/config/
    {Nombre}DataInitializer.java
```

## Estructura de Inicializadores

Los inicializadores implementan `CommandLineRunner` y se ejecutan automáticamente al iniciar el microservicio si la propiedad `default.data.enabled=true` está configurada.

---

## 1. MSVC-PRODUCTOS

### Ubicación
```
Backend_Java_Spring/Fullstack_Ecommerce/msvc-productos/
  src/main/java/com/ampuero/msvc/producto/config/
    ProductoDataInitializer.java
    DefaultDataConfig.java
  src/main/java/com/ampuero/msvc/producto/services/
    ImageBase64Service.java
```

### Configuración
Archivo: `msvc-productos/src/main/resources/application-dev.properties`

```properties
default.data.enabled=true
default.data.usuario=default
```

### Funcionalidad
- Inicializa categorías y subcategorías
- Inicializa productos con imágenes en Base64
- Convierte imágenes desde `resources/static/img/` a Base64
- Las imágenes se almacenan en Base64 en la base de datos H2

### Servicio de Imágenes
El servicio `ImageBase64Service` convierte imágenes a Base64:
- Lee imágenes desde `resources/static/img/`
- Convierte a Base64 con prefijo `data:image/{mime};base64,`
- El frontend y la app de Kotlin decodifican el Base64 para mostrar las imágenes

### Datos Iniciales
Los datos iniciales se pueden configurar en `application-dev.properties` o usar el archivo `data.sql` existente.

---

## 2. MSVC-USUARIO

### Ubicación
```
Backend_Java_Spring/Fullstack_Ecommerce/msvc-usuario/
  src/main/java/com/ampuero/msvc/usuario/config/
    UsuarioDataInitializer.java
```

### Configuración
Archivo: `msvc-usuario/src/main/resources/application-dev.properties`

```properties
default.data.enabled=true
default.data.usuario=default
```

### Funcionalidad
- Inicializa usuarios de prueba
- Genera códigos de referido automáticamente
- Asigna niveles y estados por defecto

### Ejemplo de Datos Iniciales
```java
// Usuario administrador
Usuario admin = new Usuario();
admin.setNombre("Admin");
admin.setApellido("Sistema");
admin.setCorreo("admin@levelup.com");
admin.setPassword("$2a$10$..."); // Password encriptado
admin.setTipoUsuario(Usuario.TipoUsuario.ADMINISTRADOR);
admin.setEstado(Usuario.EstadoUsuario.ACTIVO);
admin.setNivelUsuario(Usuario.NivelUsuario.GRAN_MAESTRO);
admin.setCodigoReferido("ADMIN001");
```

---

## 3. MSVC-INVENTARIO

### Ubicación
```
Backend_Java_Spring/Fullstack_Ecommerce/msvc-inventario/
  src/main/java/com/ampuero/msvc/inventario/config/
    InventarioDataInitializer.java
```

### Configuración
Archivo: `msvc-inventario/src/main/resources/application-dev.properties`

```properties
default.data.enabled=true
default.data.usuario=default
```

### Funcionalidad
- Inicializa registros de inventario para productos existentes
- Establece stock inicial basado en los productos
- Configura stock crítico y ubicación de almacén

### Ejemplo de Datos Iniciales
```java
// Inventario para producto ID 1
Inventario inventario = new Inventario();
inventario.setProductoId(1L);
inventario.setCantidadDisponible(50);
inventario.setCantidadReservada(0);
inventario.setStockCritico(10);
inventario.setUbicacionAlmacen("A-001");
inventario.setActivo(true);
```

---

## 4. MSVC-CARRITO

### Ubicación
```
Backend_Java_Spring/Fullstack_Ecommerce/msvc-carrito/
  src/main/java/com/ampuero/msvc/carrito/config/
    CarritoDataInitializer.java
```

### Configuración
Archivo: `msvc-carrito/src/main/resources/application-dev.properties`

```properties
default.data.enabled=true
default.data.usuario=default
```

### Funcionalidad
- Crea carritos de ejemplo para usuarios de prueba
- Agrega items de ejemplo a los carritos
- Configura estados y fechas de expiración

### Ejemplo de Datos Iniciales
```java
// Carrito para usuario ID 1
Carrito carrito = new Carrito();
carrito.setIdUsuario(1L);
carrito.setEstadoCarrito("ACTIVO");
carrito.setFechaCreacion(LocalDateTime.now());
carrito.setFechaExpiracion(LocalDateTime.now().plusDays(7));
carrito.setActivo(true);
```

---

## 5. MSVC-REFERIDOS

### Ubicación
```
Backend_Java_Spring/Fullstack_Ecommerce/msvc-referidos/
  src/main/java/com/ampuero/msvc/referidos/config/
    ReferidoDataInitializer.java
```

### Configuración
Archivo: `msvc-referidos/src/main/resources/application-dev.properties`

```properties
default.data.enabled=true
default.data.usuario=default
```

### Funcionalidad
- Inicializa usuarios referidos de prueba
- Genera códigos de referido únicos
- Asigna puntos LevelUp iniciales
- Configura relaciones de referidor/referido

### Ejemplo de Datos Iniciales
```java
// Usuario referido
Referido referido = new Referido();
referido.setNombreReferido("Juan");
referido.setApellidosReferido("Pérez");
referido.setEmailReferido("juan@example.com");
referido.setRunReferido("12345678-9");
referido.setCodigoReferido("REF001");
referido.setPuntosLevelup(100);
referido.setNivelUsuario("BRONZE");
referido.setIdReferidor(null); // Usuario raíz
referido.setActivo(true);
```

---

## Cómo Usar los Inicializadores

### 1. Habilitar Inicialización

En el archivo `application-dev.properties` de cada microservicio:

```properties
default.data.enabled=true
default.data.usuario=default
```

### 2. Deshabilitar Inicialización

```properties
default.data.enabled=false
```

### 3. Ejecutar Inicialización

Los inicializadores se ejecutan automáticamente al iniciar el microservicio:

```bash
cd Backend_Java_Spring/Fullstack_Ecommerce/msvc-{nombre}
mvn spring-boot:run
```

### 4. Verificar Datos Iniciales

Usa los endpoints GET documentados en `ENDPOINTS_GET_POSTMAN.md` para verificar que los datos se cargaron correctamente.

---

## Personalización de Datos Iniciales

### Opción 1: Modificar el Código Java

Edita el archivo `{Nombre}DataInitializer.java` y modifica los arrays de datos por defecto:

```java
private void initializeProductos(...) {
    // Agregar más productos aquí
    Producto producto = new Producto();
    producto.setTitulo("Nuevo Producto");
    // ... más campos
    productoRepository.save(producto);
}
```

### Opción 2: Usar Configuración Externa

Para productos, puedes usar `DefaultDataConfig` y configurar los datos en `application-dev.properties` o un archivo YAML.

### Opción 3: Usar data.sql

Muchos microservicios pueden usar archivos `data.sql` en `src/main/resources/`:

```sql
INSERT INTO productos (titulo, precio, ...) 
VALUES ('Producto', 10000, ...);
```

---

## Notas Importantes

1. **Imágenes en Base64**: Solo el microservicio de productos convierte imágenes a Base64. Las imágenes se almacenan en la base de datos H2 y se transfieren por la API.

2. **Datos por Usuario**: La propiedad `default.data.usuario` permite cargar diferentes conjuntos de datos según el usuario/perfil.

3. **Evitar Duplicados**: Los inicializadores verifican si los datos ya existen antes de crearlos para evitar duplicados en reinicios.

4. **Orden de Ejecución**: Los inicializadores se ejecutan después de que Spring Boot haya inicializado el contexto y la base de datos.

5. **Logs**: Los inicializadores registran información sobre qué datos se están creando. Revisa los logs al iniciar el microservicio.

---

## Troubleshooting

### Los datos no se cargan

1. Verifica que `default.data.enabled=true` esté configurado
2. Revisa los logs del microservicio al iniciar
3. Verifica que la base de datos H2 esté configurada correctamente
4. Asegúrate de que las entidades y repositorios estén correctamente configurados

### Errores de duplicados

Los inicializadores verifican duplicados, pero si hay errores:
1. Limpia la base de datos H2 (elimina el archivo `.mv.db`)
2. Reinicia el microservicio

### Imágenes no se muestran

1. Verifica que las imágenes existan en `resources/static/img/`
2. Revisa que `ImageBase64Service` esté funcionando correctamente
3. Verifica que el frontend/Kotlin esté decodificando el Base64 correctamente

---

## Próximos Pasos

1. Iniciar los microservicios
2. Verificar que los inicializadores se ejecuten (revisar logs)
3. Probar los endpoints GET en Postman para verificar los datos
4. Personalizar los datos iniciales según tus necesidades

