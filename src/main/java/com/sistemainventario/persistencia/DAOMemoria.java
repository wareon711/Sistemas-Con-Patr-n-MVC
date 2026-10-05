package com.sistemainventario.persistencia;

import com.sistemainventario.modelo.Producto;
import com.sistemainventario.modelo.ProductoDAO;
import java.util.ArrayList;
import java.util.List;

public class DAOMemoria implements ProductoDAO {

    private List<Producto> productos = new ArrayList<>();

    @Override
    public void guardar(Producto producto) {
        Producto existente = buscar(producto.getNombre());
        if (existente != null) {
            actualizar(producto);
        } else {
            productos.add(producto);
        }
    }

    @Override
    public Producto buscar(String nombre) {
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public List<Producto> listar() {
        return new ArrayList<>(productos);
    }

    @Override
    public void actualizar(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getNombre().equalsIgnoreCase(producto.getNombre())) {
                productos.set(i, producto);
                return;
            }
        }
    }
}
