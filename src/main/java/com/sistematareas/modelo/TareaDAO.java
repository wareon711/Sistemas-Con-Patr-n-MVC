package com.sistematareas.modelo;

import java.util.List;

public interface TareaDAO {
    void guardar(Tarea tarea);
    Tarea buscarPorId(int id);
    List<Tarea> listar();
    void actualizar(Tarea tarea);
    void eliminar(int id);
}
