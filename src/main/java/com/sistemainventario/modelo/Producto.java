package com.sistemainventario.modelo;

public class Producto {

    private String nombre;
    private int cantidad;
    private double precio;

    public Producto(String nombre, int cantidad, double precio) throws DatoInvalidoException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DatoInvalidoException("Nombre vacio");
        }
        if (cantidad < 0) {
            throw new DatoInvalidoException("Cantidad negativa");
        }
        if (precio <= 0) {
            throw new DatoInvalidoException("Precio invalido");
        }
        this.nombre = nombre.trim();
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) throws DatoInvalidoException {
        if (cantidad < 0) {
            throw new DatoInvalidoException("Cantidad negativa");
        }
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) throws DatoInvalidoException {
        if (precio <= 0) {
            throw new DatoInvalidoException("Precio invalido");
        }
        this.precio = precio;
    }

    public void descontar(int cantidadADescontar) throws StockInsuficienteException, DatoInvalidoException {
        if (cantidadADescontar <= 0) {
            throw new DatoInvalidoException("Cantidad a descontar invalida");
        }
        if (cantidadADescontar > this.cantidad) {
            throw new StockInsuficienteException("Stock insuficiente");
        }
        this.cantidad -= cantidadADescontar;
    }
}
