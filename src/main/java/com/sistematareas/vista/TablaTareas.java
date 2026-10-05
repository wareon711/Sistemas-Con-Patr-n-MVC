package com.sistematareas.vista;

import com.sistematareas.modelo.Tarea;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TablaTareas extends AbstractTableModel {

    private final String[] columnas = {"#", "Titulo", "Prioridad", "Estado"};
    private List<Tarea> tareas = new ArrayList<>();

    public void actualizar(List<Tarea> nuevas) {
        this.tareas = new ArrayList<>(nuevas);
        fireTableDataChanged();
    }

    public Tarea obtenerTarea(int fila) {
        if (fila >= 0 && fila < tareas.size()) {
            return tareas.get(fila);
        }
        return null;
    }

    @Override
    public int getRowCount() {
        return tareas.size();
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
        Tarea t = tareas.get(fila);
        switch (columna) {
            case 0:
                return fila + 1;
            case 1:
                return t.getTitulo();
            case 2:
                return t.getPrioridad();
            case 3:
                return t.getEstado();
            default:
                return null;
        }
    }
}
