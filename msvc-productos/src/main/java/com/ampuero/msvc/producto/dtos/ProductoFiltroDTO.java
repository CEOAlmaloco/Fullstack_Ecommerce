package com.ampuero.msvc.producto.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ProductoFiltroDTO {
    private String categoria;
    private List<String> subcategorias;
    private String texto;
    private Double precioMin;
    private Double precioMax;
    private Boolean disponible;
    private Double rating;
    private String orden; // relevancia, precio-asc, precio-desc, rating-desc
    private Integer pagina;
    private Integer tamano;
    
    // Getters con valores por defecto solo si son null
    public Integer getPagina() {
        return pagina != null ? pagina : 0;
    }
    
    public Integer getTamano() {
        return tamano != null ? tamano : 10; // Cambiar a 10 para coincidir con frontend
    }
}

