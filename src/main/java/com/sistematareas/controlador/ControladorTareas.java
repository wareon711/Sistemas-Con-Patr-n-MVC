package com.sistematareas.controlador;

import com.sistematareas.modelo.DatoInvalidoException;
import com.sistematareas.modelo.EstadoInvalidoException;
import com.sistematareas.modelo.GestorTareas;
import com.sistematareas.modelo.LimiteTareasException;
import com.sistematareas.modelo.NoExisteException;
import com.sistematareas.modelo.Tarea;
import com.sistematareas.modelo.TareaDuplicadaException;
import com.sistematareas.vista.VistaTareas;
import java.util.ArrayList;
import java.util.List;

public class ControladorTareas {

    private final GestorTareas gestor;
    private final List<VistaTareas> vistas;

    public ControladorTareas(GestorTareas gestor) {
        this.gestor = gestor;
        this.vistas = new ArrayList<>();
    }

    public void agregarVista(VistaTareas vista) {
        if (vista != null && !vistas.contains(vista)) {
            vistas.add(vista);
            vista.mostrarTareas(gestor.listarTareas());
        }
    }

    public void agregarTarea(String titulo, String prioridad, VistaTareas origen) {
        try {
            Tarea nueva = gestor.agregarTarea(titulo, prioridad);
            notificarExito("Tarea agregada: " + nueva.getTitulo(), origen);
        } catch (DatoInvalidoException | LimiteTareasException | TareaDuplicadaException e) {
            notificarError(e.getMessage(), origen);
        }
    }

    public void completarTarea(int id, VistaTareas origen) {
        try {
            gestor.completarTarea(id);
            notificarExito("Tarea completada con exito.", origen);
        } catch (NoExisteException | EstadoInvalidoException e) {
            notificarError(e.getMessage(), origen);
        }
    }

    public void eliminarTarea(int id, VistaTareas origen) {
        try {
            gestor.eliminarTarea(id);
            notificarExito("Tarea eliminada.", origen);
        } catch (NoExisteException e) {
            notificarError(e.getMessage(), origen);
        }
    }

    public void actualizarVistas() {
        for (VistaTareas v : vistas) {
            v.mostrarTareas(gestor.listarTareas());
        }
    }

    private void notificarExito(String mensaje, VistaTareas origen) {
        if (origen != null) {
            origen.mostrarMensaje(mensaje);
        }
    }

    private void notificarError(String error, VistaTareas origen) {
        if (origen != null) {
            origen.mostrarError(error);
        }
    }
}
