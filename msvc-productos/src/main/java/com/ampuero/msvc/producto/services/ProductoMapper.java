package com.ampuero.msvc.producto.services;

import com.ampuero.msvc.producto.dtos.ProductoResponseDTO;
import com.ampuero.msvc.producto.models.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir Producto a ProductoResponseDTO con URLs de S3 construidas
 */
@Component
public class ProductoMapper {

    @Autowired
    private S3Service s3Service;

    /**
     * Convierte una entidad Producto a ProductoResponseDTO construyendo las URLs de S3
     * 
     * @param producto Entidad Producto
     * @return ProductoResponseDTO con URLs de S3 construidas
     */
    public ProductoResponseDTO toDTO(Producto producto) {
        if (producto == null) {
            return null;
        }

        ProductoResponseDTO dto = new ProductoResponseDTO();
        
        // Campos básicos
        dto.setIdProducto(producto.getId());
        dto.setId(producto.getId());
        dto.setNombreProducto(producto.getTitulo());
        dto.setTitulo(producto.getTitulo());
        dto.setDescripcionProducto(producto.getDescripcion());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecioProducto(producto.getPrecio());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setActivo(producto.getDisponible());
        dto.setFechaCreacion(producto.getCreatedAt());
        dto.setFechaModificacion(producto.getUpdatedAt());
        dto.setCodigoProducto(producto.getCodigoProducto());
        dto.setRating(producto.getRating());
        Double ratingPromedio = producto.getRatingPromedioCalculado() != null
                ? producto.getRatingPromedioCalculado()
                : producto.getRating();
        dto.setRatingPromedio(ratingPromedio);
        dto.setReviews(producto.getReviews());
        dto.setEnOferta(producto.getOfertaActiva());
        dto.setDescuento(producto.getDescuentoCalculado());
        dto.setPrecioConDescuento(producto.getPrecioConDescuentoCalculado());
        dto.setFabricante(producto.getFabricante());
        dto.setDistribuidor(producto.getDistribuidor());
        dto.setDestacado(producto.getDestacadoHome());

        if (producto.getCategoria() != null) {
            dto.setCategoriaId(producto.getCategoria().getId());
            dto.setCategoriaNombre(producto.getCategoria().getNombre());
        } else {
            dto.setCategoriaId(producto.getCategoriaId());
        }

        if (producto.getSubcategoria() != null) {
            dto.setSubcategoriaId(producto.getSubcategoria().getId());
            dto.setSubcategoriaNombre(producto.getSubcategoria().getNombre());
        } else {
            dto.setSubcategoriaId(producto.getSubcategoriaId());
        }

        // Construir URLs de S3 desde las referencias guardadas en BD
        String imagenS3Key = producto.getImagen();
        if (imagenS3Key != null && !imagenS3Key.isEmpty()) {
            // Guardar la referencia original (key) en el DTO
            dto.setImagenS3Key(imagenS3Key);
            
            // Construir la URL completa de S3
            String imagenUrl = s3Service.buildS3Url(imagenS3Key);
            dto.setImagenUrl(imagenUrl);
        }

        // Construir URLs de imágenes adicionales
        String imagenesS3Keys = producto.getImagenes();
        if (imagenesS3Keys != null && !imagenesS3Keys.isEmpty()) {
            // Guardar las referencias originales (keys) en el DTO
            dto.setImagenesS3Keys(imagenesS3Keys);
            
            // Construir las URLs completas de S3
            String imagenesUrls = s3Service.buildS3UrlsJson(imagenesS3Keys);
            dto.setImagenesUrls(imagenesUrls);
        }

        return dto;
    }

    /**
     * Convierte una lista de Producto a lista de ProductoResponseDTO
     * 
     * @param productos Lista de entidades Producto
     * @return Lista de ProductoResponseDTO con URLs de S3 construidas
     */
    public List<ProductoResponseDTO> toDTOList(List<Producto> productos) {
        if (productos == null) {
            return List.of();
        }
        
        return productos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}

