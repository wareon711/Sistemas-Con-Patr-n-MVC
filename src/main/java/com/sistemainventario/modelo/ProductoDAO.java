package com.sistemainventario.modelo;

import java.util.List;

public interface ProductoDAO {
    void guardar(Producto producto);
    Producto buscar(String nombre);
    List<Producto> listar();
    void actualizar(Producto producto);
}
