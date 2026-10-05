package com.sistemainventario.modelo;

import java.util.ArrayList;
import java.util.List;

public class Inventario {

    private ProductoDAO dao;
    private List<Observador> observadores;

    public Inventario(ProductoDAO dao) {
        this.dao = dao;
        this.observadores = new ArrayList<>();
    }

    public Inventario() {
        this(new ProductoDAO() {
            private List<Producto> lista = new ArrayList<>();

            @Override
            public void guardar(Producto producto) {
                Producto existente = buscar(producto.getNombre());
                if (existente != null) {
                    actualizar(producto);
                } else {
                    lista.add(producto);
                }
            }

            @Override
            public Producto buscar(String nombre) {
                for (Producto p : lista) {
                    if (p.getNombre().equalsIgnoreCase(nombre)) {
                        return p;
                    }
                }
                return null;
            }

            @Override
            public List<Producto> listar() {
                return new ArrayList<>(lista);
            }

            @Override
            public void actualizar(Producto producto) {
                for (int i = 0; i < lista.size(); i++) {
                    if (lista.get(i).getNombre().equalsIgnoreCase(producto.getNombre())) {
                        lista.set(i, producto);
                        return;
                    }
                }
            }
        });
    }

    public void agregar(Producto producto) throws DatoInvalidoException {
        if (producto == null) {
            throw new DatoInvalidoException("Producto nulo");
        }
        dao.guardar(producto);
        notificarObservadores();
    }

    public void agregar(String nombre, int cantidad, double precio) throws DatoInvalidoException {
        Producto producto = new Producto(nombre, cantidad, precio);
        agregar(producto);
    }

    public int vender(String nombre, int cantidad) throws NoExisteException, StockInsuficienteException, DatoInvalidoException {
        Producto producto = dao.buscar(nombre);
        if (producto == null) {
            throw new NoExisteException("No existe");
        }
        producto.descontar(cantidad);
        dao.actualizar(producto);
        notificarObservadores();
        return producto.getCantidad();
    }

    public Producto buscar(String nombre) throws NoExisteException {
        Producto producto = dao.buscar(nombre);
        if (producto == null) {
            throw new NoExisteException("No existe");
        }
        return producto;
    }

    public List<Producto> listar() {
        return dao.listar();
    }

    public double valorTotal() {
        double total = 0.0;
        for (Producto p : dao.listar()) {
            total += p.getCantidad() * p.getPrecio();
        }
        return total;
    }

    public void agregarObservador(Observador observador) {
        observadores.add(observador);
    }

    private void notificarObservadores() {
        for (Observador observador : observadores) {
            observador.inventarioCambio();
        }
    }
}
