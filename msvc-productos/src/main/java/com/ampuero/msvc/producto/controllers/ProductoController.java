package com.ampuero.msvc.producto.controllers;


import com.ampuero.msvc.producto.dtos.ErrorDTO;
import com.ampuero.msvc.producto.models.Producto;
import com.ampuero.msvc.producto.services.ProductoService;
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
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:3000", "http://10.0.2.2:8094"})
@Tag(name = "Producto API",
        description = "Aqui se generan todos los metodos crud para producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

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
    public ResponseEntity<List<Producto>> traerTodos() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(productoService.traerTodo());
        } catch (Exception ex) {
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
    public ResponseEntity<Producto> traerPorId(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.productoService.traerPorId(id));
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
    public ResponseEntity<List<Producto>> buscarProductos(@RequestParam(required = false) String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return ResponseEntity.ok(productoService.traerTodo());
        }
        return ResponseEntity.ok(productoService.buscarPorNombre(nombre.trim()));
    }

    // GET: Filtrar por categoría
    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Productos por categoría", description = "Obtiene productos de una categoría específica")
    public ResponseEntity<List<Producto>> productosPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(productoService.obtenerPorCategoria(categoria));
    }

    // GET: Productos disponibles
    @GetMapping("/disponibles")
    @Operation(summary = "Productos disponibles", description = "Solo productos con stock y disponibles")
    public ResponseEntity<List<Producto>> productosDisponibles() {
        return ResponseEntity.ok(productoService.obtenerDisponibles());
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

}
