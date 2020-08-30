package com.example.bienestarproveedores.meals;

public class Meal {
    private String id;
    private String nombre;
    private String descripcion;
    private String address;

    public Meal(String id, String nombre, String descripcion, String address) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.address = address;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
