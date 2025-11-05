package com.ampuero.msvc.producto.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ProductoPaginadoResponseDTO {
    private List<ProductoResponseDTO> productos;
    private Integer pagina;
    private Integer tamano;
    private Long totalElementos;
    private Integer totalPaginas;
    private Boolean primeraPagina;
    private Boolean ultimaPagina;
}

