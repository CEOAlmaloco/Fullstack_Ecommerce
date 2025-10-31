package com.ampuero.msvc.carrito.dtos;

public class CarritoItemDTO {
    private Long id;
    private Long productoId;
    private String nombreProducto;
    private String descripcionProducto;
    private Double precioUnitario;
    private Integer cantidad;
    private Double subtotal;
    private Double descuentoAplicado;
    private Double impuestoAplicado;
    private Double totalItem;
    private String imagenUrl;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }
    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
    public String getDescripcionProducto() { return descripcionProducto; }
    public void setDescripcionProducto(String descripcionProducto) { this.descripcionProducto = descripcionProducto; }
    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
    public Double getDescuentoAplicado() { return descuentoAplicado; }
    public void setDescuentoAplicado(Double descuentoAplicado) { this.descuentoAplicado = descuentoAplicado; }
    public Double getImpuestoAplicado() { return impuestoAplicado; }
    public void setImpuestoAplicado(Double impuestoAplicado) { this.impuestoAplicado = impuestoAplicado; }
    public Double getTotalItem() { return totalItem; }
    public void setTotalItem(Double totalItem) { this.totalItem = totalItem; }
    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
}


