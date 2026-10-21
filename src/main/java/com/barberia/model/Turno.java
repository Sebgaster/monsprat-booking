package com.barberia.model;

import java.util.UUID;

public class Turno {
    private String id;
    private String cliente;
    private String email;
    private String telefono;
    private String servicio;
    private String fecha;
    private String hora;

    public Turno() {
        this.id = UUID.randomUUID().toString(); // Genera un ID único automático
    }

    public Turno(String cliente, String email, String telefono, String servicio, String fecha, String hora) {
        this.id = UUID.randomUUID().toString();
        this.cliente = cliente;
        this.email = email;
        this.telefono = telefono;
        this.servicio = servicio;
        this.fecha = fecha;
        this.hora = hora;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}