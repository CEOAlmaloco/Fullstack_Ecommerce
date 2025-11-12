package com.ampuero.msvc.producto.dtos;

import java.time.LocalDateTime;

public class PromocionResumenDTO {
    private Long idPromocion;
    private String codigoPromocion;
    private String nombrePromocion;
    private String descripcionPromocion;
    private String tipoDescuento;
    private Double valorDescuento;
    private Double montoMinimo;
    private Double montoMaximoDescuento;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Boolean activo;
    private Boolean aplicableDuoc;
    private String categoriaAplicable;
    private Integer usosMaximos;
    private Integer usosActuales;

    public Long getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(Long idPromocion) {
        this.idPromocion = idPromocion;
    }

    public String getCodigoPromocion() {
        return codigoPromocion;
    }

    public void setCodigoPromocion(String codigoPromocion) {
        this.codigoPromocion = codigoPromocion;
    }

    public String getNombrePromocion() {
        return nombrePromocion;
    }

    public void setNombrePromocion(String nombrePromocion) {
        this.nombrePromocion = nombrePromocion;
    }

    public String getDescripcionPromocion() {
        return descripcionPromocion;
    }

    public void setDescripcionPromocion(String descripcionPromocion) {
        this.descripcionPromocion = descripcionPromocion;
    }

    public String getTipoDescuento() {
        return tipoDescuento;
    }

    public void setTipoDescuento(String tipoDescuento) {
        this.tipoDescuento = tipoDescuento;
    }

    public Double getValorDescuento() {
        return valorDescuento;
    }

    public void setValorDescuento(Double valorDescuento) {
        this.valorDescuento = valorDescuento;
    }

    public Double getMontoMinimo() {
        return montoMinimo;
    }

    public void setMontoMinimo(Double montoMinimo) {
        this.montoMinimo = montoMinimo;
    }

    public Double getMontoMaximoDescuento() {
        return montoMaximoDescuento;
    }

    public void setMontoMaximoDescuento(Double montoMaximoDescuento) {
        this.montoMaximoDescuento = montoMaximoDescuento;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Boolean getAplicableDuoc() {
        return aplicableDuoc;
    }

    public void setAplicableDuoc(Boolean aplicableDuoc) {
        this.aplicableDuoc = aplicableDuoc;
    }

    public String getCategoriaAplicable() {
        return categoriaAplicable;
    }

    public void setCategoriaAplicable(String categoriaAplicable) {
        this.categoriaAplicable = categoriaAplicable;
    }

    public Integer getUsosMaximos() {
        return usosMaximos;
    }

    public void setUsosMaximos(Integer usosMaximos) {
        this.usosMaximos = usosMaximos;
    }

    public Integer getUsosActuales() {
        return usosActuales;
    }

    public void setUsosActuales(Integer usosActuales) {
        this.usosActuales = usosActuales;
    }
}
