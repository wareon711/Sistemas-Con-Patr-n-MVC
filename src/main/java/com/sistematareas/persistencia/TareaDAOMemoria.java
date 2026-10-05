package com.sistematareas.persistencia;

import com.sistematareas.modelo.Tarea;
import com.sistematareas.modelo.TareaDAO;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TareaDAOMemoria implements TareaDAO {

    private final Map<Integer, Tarea> almacenamiento = new LinkedHashMap<>();

    @Override
    public void guardar(Tarea tarea) {
        if (tarea != null) {
            almacenamiento.put(tarea.getId(), tarea);
        }
    }

    @Override
    public Tarea buscarPorId(int id) {
        return almacenamiento.get(id);
    }

    @Override
    public List<Tarea> listar() {
        return new ArrayList<>(almacenamiento.values());
    }

    @Override
    public void actualizar(Tarea tarea) {
        if (tarea != null) {
            almacenamiento.put(tarea.getId(), tarea);
        }
    }

    @Override
    public void eliminar(int id) {
        almacenamiento.remove(id);
    }
}
