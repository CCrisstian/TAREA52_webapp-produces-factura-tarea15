package org.CCristian.apiservlet.webapp.models;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Cliente {
    private String email;
    private String nombre;
    private String apellidos;

    // Método para inicializar valores por defecto
    @PostConstruct
    public void init() {
        this.email = "Cristian_Cristaldo@correo.com";
        this.nombre = "Cristian";
        this.apellidos = "Cristaldo";
    }

    public Cliente() {
    }

    public Cliente(String email, String nombre, String apellidos) {
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

}
