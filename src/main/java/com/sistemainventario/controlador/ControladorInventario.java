package com.sistemainventario.controlador;

import com.sistemainventario.modelo.Inventario;
import com.sistemainventario.modelo.DatoInvalidoException;
import com.sistemainventario.modelo.NoExisteException;
import com.sistemainventario.modelo.StockInsuficienteException;
import com.sistemainventario.vista.Vista;
import java.util.ArrayList;
import java.util.List;

public class ControladorInventario {

    private Inventario inventario;
    private List<Vista> vistas;

    public ControladorInventario(Inventario inventario) {
        this.inventario = inventario;
        this.vistas = new ArrayList<>();
    }

    public void agregarVista(Vista vista) {
        vistas.add(vista);
        vista.mostrarProductos(inventario.listar());
    }

    private int parsearEntero(String texto) throws NumberFormatException {
        if (texto == null) {
            throw new NumberFormatException();
        }
        String limpio = texto.trim().replace(",", "").replace(".", "").replace(" ", "");
        return Integer.parseInt(limpio);
    }

    private double parsearDouble(String texto) throws NumberFormatException {
        if (texto == null) {
            throw new NumberFormatException();
        }
        String limpio = texto.trim().replace("$", "").replace(" ", "");
        if (limpio.contains(",") && !limpio.contains(".")) {
            limpio = limpio.replace(",", ".");
        } else if (limpio.contains(",") && limpio.contains(".")) {
            limpio = limpio.replace(",", "");
        }
        return Double.parseDouble(limpio);
    }

    public void agregarProducto(String nombre, String cantidadTexto, String precioTexto, Vista vista) {
        try {
            int cantidad = parsearEntero(cantidadTexto);
            double precio = parsearDouble(precioTexto);
            inventario.agregar(nombre, cantidad, precio);
            vista.mostrarMensaje("Agregado.");
        } catch (NumberFormatException | DatoInvalidoException e) {
            vista.mostrarError("Datos invalidos");
        }
    }

    public boolean existeProducto(String nombre) {
        try {
            inventario.buscar(nombre);
            return true;
        } catch (NoExisteException e) {
            return false;
        }
    }

    public void venderProducto(String nombre, String cantidadTexto, Vista vista) {
        try {
            int cantidad = parsearEntero(cantidadTexto);
            int quedan = inventario.vender(nombre, cantidad);
            vista.mostrarMensaje("Venta ok. Quedan " + quedan);
        } catch (NumberFormatException | DatoInvalidoException e) {
            vista.mostrarError("Datos invalidos");
        } catch (NoExisteException e) {
            vista.mostrarError("No existe");
        } catch (StockInsuficienteException e) {
            vista.mostrarError("Stock insuficiente");
        }
    }

    public void calcularValorTotal(Vista vista) {
        double total = inventario.valorTotal();
        vista.mostrarValorTotal(total);
    }

    public void listarProductos(Vista vista) {
        vista.mostrarProductos(inventario.listar());
    }

    public void cargarProductos(Vista vista) {
        vista.mostrarProductos(inventario.listar());
    }
}
