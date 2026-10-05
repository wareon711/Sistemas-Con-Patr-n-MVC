package com.sistemainventario.vista;

import com.sistemainventario.modelo.Producto;
import java.util.List;

public interface Vista {
    void mostrarProductos(List<Producto> productos);
    void mostrarMensaje(String mensaje);
    void mostrarError(String mensaje);
    void mostrarValorTotal(double total);
}
