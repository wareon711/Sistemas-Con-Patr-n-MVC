package com.sistematareas.modelo;

import java.util.ArrayList;
import java.util.List;

public class GestorTareas {

    private final TareaDAO tareaDAO;
    private final List<ObservadorTareas> observadores;
    private int siguienteId;

    public GestorTareas(TareaDAO tareaDAO) {
        this.tareaDAO = tareaDAO;
        this.observadores = new ArrayList<>();
        this.siguienteId = calcularSiguienteId();
    }

    private int calcularSiguienteId() {
        int max = 0;
        for (Tarea t : tareaDAO.listar()) {
            if (t.getId() > max) {
                max = t.getId();
            }
        }
        return max + 1;
    }

    public void agregarObservador(ObservadorTareas observador) {
        if (observador != null && !observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    public Tarea agregarTarea(String titulo, String prioridad)
            throws DatoInvalidoException, LimiteTareasException, TareaDuplicadaException {

        if (titulo == null || titulo.trim().isEmpty()) {
            throw new DatoInvalidoException("El titulo no puede estar vacio");
        }

        long pendientes = tareaDAO.listar().stream()
                .filter(Tarea::esPendiente)
                .count();

        if (pendientes >= 5) {
            throw new LimiteTareasException("Limite alcanzado: No puedes tener mas de 5 tareas pendientes simultaneas");
        }

        boolean duplicada = tareaDAO.listar().stream()
                .filter(Tarea::esPendiente)
                .anyMatch(t -> t.getTitulo().equalsIgnoreCase(titulo.trim()));

        if (duplicada) {
            throw new TareaDuplicadaException("Ya tienes una tarea pendiente con ese mismo titulo");
        }

        Tarea nueva = new Tarea(siguienteId++, titulo, prioridad);
        tareaDAO.guardar(nueva);
        notificarCambio();
        return nueva;
    }

    public void completarTarea(int id) throws NoExisteException, EstadoInvalidoException {
        Tarea tarea = tareaDAO.buscarPorId(id);
        if (tarea == null) {
            throw new NoExisteException("No se encontro ninguna tarea con ID " + id);
        }

        tarea.completar();
        tareaDAO.actualizar(tarea);
        notificarCambio();
    }

    public void eliminarTarea(int id) throws NoExisteException {
        Tarea tarea = tareaDAO.buscarPorId(id);
        if (tarea == null) {
            throw new NoExisteException("No se encontro ninguna tarea con ID " + id);
        }

        tareaDAO.eliminar(id);
        notificarCambio();
    }

    public List<Tarea> listarTareas() {
        return tareaDAO.listar();
    }

    public Tarea buscarTarea(int id) throws NoExisteException {
        Tarea tarea = tareaDAO.buscarPorId(id);
        if (tarea == null) {
            throw new NoExisteException("No existe la tarea con ID " + id);
        }
        return tarea;
    }

    public int contarPendientes() {
        int contador = 0;
        for (Tarea t : tareaDAO.listar()) {
            if (t.esPendiente()) {
                contador++;
            }
        }
        return contador;
    }

    private void notificarCambio() {
        for (ObservadorTareas o : observadores) {
            o.tareasCambiaron();
        }
    }
}
