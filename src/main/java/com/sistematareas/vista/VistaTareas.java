package com.sistematareas.vista;

import com.sistematareas.modelo.Tarea;
import java.util.List;

public interface VistaTareas {
    void mostrarTareas(List<Tarea> tareas);
    void mostrarMensaje(String mensaje);
    void mostrarError(String error);
}
