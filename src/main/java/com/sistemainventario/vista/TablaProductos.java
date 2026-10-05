package com.sistemainventario.vista;

import com.sistemainventario.modelo.Producto;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TablaProductos extends AbstractTableModel {

    private String[] columnas = {"Nombre", "Cantidad", "Precio"};
    private List<Producto> productos = new ArrayList<>();

    public void actualizar(List<Producto> nuevosProductos) {
        this.productos = new ArrayList<>(nuevosProductos);
        fireTableDataChanged();
    }

    public Producto obtenerProducto(int fila) {
        if (fila >= 0 && fila < productos.size()) {
            return productos.get(fila);
        }
        return null;
    }

    @Override
    public int getRowCount() {
        return productos.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int columna) {
        return columnas[columna];
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        Producto producto = productos.get(fila);
        switch (columna) {
            case 0:
                return producto.getNombre();
            case 1:
                return producto.getCantidad();
            case 2:
                return Formato.moneda(producto.getPrecio());
            default:
                return null;
        }
    }
}
