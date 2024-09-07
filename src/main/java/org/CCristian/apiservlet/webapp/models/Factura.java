package org.CCristian.apiservlet.webapp.models;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named
@RequestScoped
public class Factura {

    private Integer numeroFactura;
    private String descripcion;
    private Cliente cliente;

    @Inject
    private List<LineaFactura> lineasFactura;

    // Método para inicializar valores por defecto
    @PostConstruct
    public void init() {
        this.numeroFactura = 1001;
        this.descripcion = "Compra de productos varios";
    }

    public Factura() {
    }

    public Integer getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(Integer numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    // Método para inyectar el cliente usando setter
    @Inject
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<LineaFactura> getLineasFactura() {
        return lineasFactura;
    }

    public void setLineasFactura(List<LineaFactura> lineasFactura) {
        this.lineasFactura = lineasFactura;
    }
}