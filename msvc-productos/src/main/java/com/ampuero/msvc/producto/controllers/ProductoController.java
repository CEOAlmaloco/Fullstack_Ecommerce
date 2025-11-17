package com.ampuero.msvc.producto.controllers;


import com.ampuero.msvc.producto.dtos.ErrorDTO;
import com.ampuero.msvc.producto.dtos.ProductoResponseDTO;
import com.ampuero.msvc.producto.models.Producto;
import com.ampuero.msvc.producto.services.ImageBase64Service;
import com.ampuero.msvc.producto.services.ProductoMapper;
import com.ampuero.msvc.producto.services.ProductoService;
import com.ampuero.msvc.producto.services.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar productos dentro del sistema.
 * Forma parte del microservicio "msvc-productos".
 * Expone operaciones CRUD a través de endpoints HTTP.
 */


@RestController
@RequestMapping("/productos")
@Validated
@Tag(name = "Producto API",
        description = "Aqui se generan todos los metodos crud para producto")
public class ProductoController {

    private static final Logger log = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ImageBase64Service imageBase64Service;

    @Autowired
    private ProductoMapper productoMapper;

    @Autowired
    private S3Service s3Service;

    // GET: Traer todos los productos
    @GetMapping
    @Operation(
            summary = "metodo que obtiene todos los productos",
            description = "este endpoint devuleve todos los productos que se encuentren" +
                    "en la base de datos"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "operacion de extraccion de productos exitosa")
    })
    public ResponseEntity<List<ProductoResponseDTO>> traerTodos() {
        try {
            log.info("GET /productos - Iniciando obtención de todos los productos");
            List<Producto> productos = productoService.traerTodo();
            log.info("GET /productos - Productos obtenidos del servicio: {}", productos.size());
            
            List<ProductoResponseDTO> productosDTO = productoMapper.toDTOList(productos);
            log.info("GET /productos - Productos mapeados a DTO: {}", productosDTO.size());
            
            if (productosDTO.isEmpty()) {
                log.warn("GET /productos - ADVERTENCIA: No se encontraron productos. Verificar inicialización de BD.");
            }
            
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(productosDTO);
        } catch (Exception ex) {
            log.error("GET /productos - Error al obtener productos: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.OK).body(java.util.Collections.emptyList());
        }
    }

    // GET: Traer producto por ID
    @GetMapping("/{id}")
    @Operation(
            summary = "endpoint que devuelve un producto por id",
            description = "endpoint que devuleve un producto.class al momento de buscarlo por id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "obtencion por id correcta"),
            @ApiResponse(responseCode = "404",
                    description = "error el producto con esa id no existe")

    })
    @Parameters(value = {
            @Parameter(
                    name = "id",
                    description = "primary KEY - entidad producto",
                    content = @Content(
                            mediaType = "application/json",
                            //schema = @Schema(implementation = ErrorDTO.class)
                            examples = @ExampleObject(
                                    name = "Error no encontrado",
                                    value = "{\"status\":\"200\",\"error\":\"medico no encontrado\"}"
                            )
                    )
            )
    })
    public ResponseEntity<ProductoResponseDTO> traerPorId(@PathVariable Long id) {
        log.info("GET /productos/{} - Iniciando obtención de producto por ID", id);
        Producto producto = this.productoService.traerPorId(id);
        if (producto == null) {
            log.warn("GET /productos/{} - Producto no encontrado", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("GET /productos/{} - Producto encontrado: {}", id, producto.getTitulo());
        log.info("GET /productos/{} - Imagen: {}, Descripción: {}, Rating: {}", 
                id, producto.getImagen(), 
                producto.getDescripcion() != null ? producto.getDescripcion().substring(0, Math.min(50, producto.getDescripcion().length())) : "null",
                producto.getRating());
        ProductoResponseDTO productoDTO = productoMapper.toDTO(producto);
        log.info("GET /productos/{} - DTO mapeado - imagenUrl: {}, descripcion: {}, rating: {}", 
                id, productoDTO.getImagenUrl(), 
                productoDTO.getDescripcion() != null ? productoDTO.getDescripcion().substring(0, Math.min(50, productoDTO.getDescripcion().length())) : "null",
                productoDTO.getRating());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productoDTO);
    }

    // POST: Crear nuevo producto
    @PostMapping
    @Operation(
            summary = "endpoint guardado de un medico",
            description = "endpoint que permite capturar un elemento producto.class y lo guarda" +
                    "dentro de la base de datos"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Creación exitosa",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Producto.class) // O tu clase de respuesta exitosa
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Algún elemento del microservicio no se encuentra",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "El elemento que intentaste crear ya existe",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })

    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "estructura de datos que me permite realizar la creacion de un producto",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Producto.class)
            )
    )
    public ResponseEntity<Producto> crearProducto(@RequestBody @Valid Producto producto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.productoService.crearProducto(producto));
    }

    // PUT: Actualizar producto por ID
    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar producto",
            description = "Permite modificar los datos de un producto existente por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        return ResponseEntity.status(HttpStatus.OK).body(productoService.actualizarProducto(id, producto));
    }

    // DELETE: Eliminar producto por ID
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar producto",
            description = "Elimina un producto de la base de datos según su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    // GET: Obtener categorías
    @GetMapping("/categorias")
    @Operation(
            summary = "Obtener categorías y subcategorías",
            description = "Devuelve la lista de categorías y subcategorías disponibles"
    )
    @ApiResponse(responseCode = "200", description = "Categorías obtenidas correctamente")
    public ResponseEntity<?> obtenerCategorias() {
        return ResponseEntity.ok(productoService.obtenerCategorias());
    }

    // GET: Buscar productos
    @GetMapping("/buscar")
    @Operation(summary = "Buscar productos por nombre", description = "Busca productos que coincidan con el término")
    public ResponseEntity<List<ProductoResponseDTO>> buscarProductos(@RequestParam(required = false) String nombre) {
        List<Producto> productos;
        if (nombre == null || nombre.trim().isEmpty()) {
            productos = productoService.traerTodo();
        } else {
            productos = productoService.buscarPorNombre(nombre.trim());
        }
        List<ProductoResponseDTO> productosDTO = productoMapper.toDTOList(productos);
        return ResponseEntity.ok(productosDTO);
    }

    // GET: Filtrar por categoría
    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Productos por categoría", description = "Obtiene productos de una categoría específica")
    public ResponseEntity<List<ProductoResponseDTO>> productosPorCategoria(@PathVariable String categoria) {
        List<Producto> productos = productoService.obtenerPorCategoria(categoria);
        List<ProductoResponseDTO> productosDTO = productoMapper.toDTOList(productos);
        return ResponseEntity.ok(productosDTO);
    }

    // GET: Productos disponibles
    @GetMapping("/disponibles")
    @Operation(summary = "Productos disponibles", description = "Solo productos con stock y disponibles")
    public ResponseEntity<List<ProductoResponseDTO>> productosDisponibles() {
        List<Producto> productos = productoService.obtenerDisponibles();
        List<ProductoResponseDTO> productosDTO = productoMapper.toDTOList(productos);
        return ResponseEntity.ok(productosDTO);
    }

    // POST: Filtrar productos con paginación
    @PostMapping("/filtrar")
    @Operation(
            summary = "Filtrar productos con paginación",
            description = "Filtra productos según criterios (categoría, subcategorías, texto, precio, rating, etc.) y devuelve resultados paginados"
    )
    @ApiResponse(responseCode = "200", description = "Productos filtrados obtenidos correctamente")
    public ResponseEntity<com.ampuero.msvc.producto.dtos.ProductoPaginadoResponseDTO> filtrarProductos(
            @RequestBody com.ampuero.msvc.producto.dtos.ProductoFiltroDTO filtros) {
        // Log para verificar qué se está recibiendo
        System.out.println("CONTROLLER - Filtros recibidos: categoria=" + filtros.getCategoria() + 
                ", subcategorias=" + filtros.getSubcategorias() + 
                ", texto=" + filtros.getTexto() + 
                ", precioMin=" + filtros.getPrecioMin() + 
                ", precioMax=" + filtros.getPrecioMax() + 
                ", disponible=" + filtros.getDisponible() + 
                ", rating=" + filtros.getRating() + 
                ", orden=" + filtros.getOrden() + 
                ", pagina=" + filtros.getPagina() + 
                ", tamano=" + filtros.getTamano());
        return ResponseEntity.ok(productoService.filtrarProductos(filtros));
    }

    // GET: Filtrar productos con paginación (versión GET para compatibilidad)
    @GetMapping("/filtrar")
    @Operation(
            summary = "Filtrar productos con paginación (GET)",
            description = "Filtra productos según criterios usando parámetros de URL"
    )
    @ApiResponse(responseCode = "200", description = "Productos filtrados obtenidos correctamente")
    public ResponseEntity<com.ampuero.msvc.producto.dtos.ProductoPaginadoResponseDTO> filtrarProductosGet(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) List<String> subcategorias,
            @RequestParam(required = false) String texto,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax,
            @RequestParam(required = false) Boolean disponible,
            @RequestParam(required = false) Double rating,
            @RequestParam(required = false) String orden,
            @RequestParam(required = false, defaultValue = "0") Integer pagina,
            @RequestParam(required = false, defaultValue = "10") Integer tamano) {
        
        com.ampuero.msvc.producto.dtos.ProductoFiltroDTO filtros = new com.ampuero.msvc.producto.dtos.ProductoFiltroDTO();
        filtros.setCategoria(categoria);
        filtros.setSubcategorias(subcategorias);
        filtros.setTexto(texto);
        filtros.setPrecioMin(precioMin);
        filtros.setPrecioMax(precioMax);
        filtros.setDisponible(disponible);
        filtros.setRating(rating);
        filtros.setOrden(orden);
        filtros.setPagina(pagina);
        filtros.setTamano(tamano);
        
        return ResponseEntity.ok(productoService.filtrarProductos(filtros));
    }

    // GET: Obtener imágenes del carrusel
    @GetMapping("/carrusel")
    @Operation(summary = "Obtener imágenes del carrusel", description = "Devuelve las 3 imágenes del carrusel como URLs de S3")
    @ApiResponse(responseCode = "200", description = "Imágenes del carrusel obtenidas correctamente")
    public ResponseEntity<List<java.util.Map<String, String>>> obtenerImagenesCarrusel() {
        try {
            java.util.List<java.util.Map<String, String>> carrusel = new java.util.ArrayList<>();
            
            // Imagen 1: PlayStation 5 (play5white.png)
            String imagen1Url = "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/carruselproductos.png";
            java.util.Map<String, String> item1 = new java.util.HashMap<>();
            item1.put("id", "1");
            item1.put("url", imagen1Url);
            item1.put("titulo", "¡Bienvenido a Level-Up Gamer!");
            item1.put("nombre", "¡Bienvenido a Level-Up Gamer!");
            item1.put("descripcion", "La tienda gamer lider en todo Chile");
            item1.put("enlace", "");
            carrusel.add(item1);

            // Imagen 2: Monitor ASUS (monitorasus.png)
            String imagen2Url = "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/carruselnoticias.png";
            java.util.Map<String, String> item2 = new java.util.HashMap<>();
            item2.put("id", "2");
            item2.put("url", imagen2Url);
            item2.put("titulo", "¡Explora nuestros productos gamer de alta calidad!");
            item2.put("nombre", "¡Explora nuestros productos gamer de alta calidad!");
            item2.put("descripcion", "Tenemos una gama alta de productos para ti y tu amor por el gaming");
            item2.put("enlace", "");
            carrusel.add(item2);

            // Imagen 3: Eventos (evento.jpg)
            String imagen3Url = "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/blog.png";
            java.util.Map<String, String> item3 = new java.util.HashMap<>();
            item3.put("id", "3");
            item3.put("url", imagen3Url);
            item3.put("titulo", "¡Lee desde noticias a guias del mundo gaming!");
            item3.put("nombre", "¡Lee desde noticias a guias del mundo gaming!");
            item3.put("descripcion", "Con nuestros blogs estarás atento a todo");
            item3.put("enlace", "");
            carrusel.add(item3);

            return ResponseEntity.ok(carrusel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(java.util.Collections.emptyList());
        }
    }

    // GET: Obtener logo
    @GetMapping("/logo")
    @Operation(summary = "Obtener logo", description = "Devuelve el logo como URL de S3 (primero intenta URL completa, luego fallback)")
    @ApiResponse(responseCode = "200", description = "Logo obtenido correctamente")
    public ResponseEntity<java.util.Map<String, String>> obtenerLogo() {
        try {
            // Primero intentar con la URL completa directa
            String logoUrl = "https://levelup-gamer-products.s3.us-east-1.amazonaws.com/img/logo.png";
            String fallbackUrl = s3Service.buildS3Url("img/levelup_logo.png");
            
            java.util.Map<String, String> response = new java.util.HashMap<>();
            // Devolver la URL principal, el frontend puede usar el fallback si falla
            response.put("url", logoUrl);
            response.put("fallback", fallbackUrl != null ? fallbackUrl : "");
            response.put("alt", "Logo Level Up");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // En caso de error, intentar con el fallback
            try {
                String logoKey = "img/levelup_logo.png";
                String logoUrl = s3Service.buildS3Url(logoKey);
                java.util.Map<String, String> response = new java.util.HashMap<>();
                response.put("url", logoUrl != null ? logoUrl : "");
                response.put("fallback", "");
                response.put("alt", "Logo Level Up");
                return ResponseEntity.ok(response);
            } catch (Exception e2) {
                java.util.Map<String, String> response = new java.util.HashMap<>();
                response.put("url", "");
                response.put("fallback", "");
                response.put("alt", "Logo Level Up");
                return ResponseEntity.ok(response);
            }
        }
    }

}
