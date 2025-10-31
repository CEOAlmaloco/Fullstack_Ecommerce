package com.ampuero.msvc.producto.dtos;

import com.ampuero.msvc.producto.models.Producto;
import lombok.Data;

import java.util.List;

@Data
public class ProductoPaginadoResponseDTO {
    private List<Producto> productos;
    private Integer pagina;
    private Integer tamano;
    private Long totalElementos;
    private Integer totalPaginas;
    private Boolean primeraPagina;
    private Boolean ultimaPagina;
}

