package com.barberia.model;

import java.util.UUID;

public class Turno {
    private String id;
    private String cliente;
    private String telefono;
    private String servicio;
    private String fecha;
    private String hora;
    private String barbero;

    public Turno() {
        this.id = UUID.randomUUID().toString(); 
    }

    public Turno(String cliente, String telefono, String servicio, String fecha, String hora, String barbero) {
        this.id = UUID.randomUUID().toString();
        this.cliente = cliente;
        this.telefono = telefono;
        this.servicio = servicio;
        this.fecha = fecha;
        this.hora = hora;
        this.barbero = barbero;
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
    
    public String getBarbero(){
        return barbero;
    }
    
    public void setBarbero(String barbero) {
        this.barbero = barbero;
    }
}