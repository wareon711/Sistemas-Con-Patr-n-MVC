package com.sistematareas.modelo;

public class Tarea {

    private final int id;
    private final String titulo;
    private final String prioridad;
    private String estado;

    public Tarea(int id, String titulo, String prioridad) throws DatoInvalidoException {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new DatoInvalidoException("El titulo de la tarea no puede estar vacio");
        }
        if (prioridad == null || (!prioridad.equalsIgnoreCase("Alta")
                && !prioridad.equalsIgnoreCase("Media")
                && !prioridad.equalsIgnoreCase("Baja"))) {
            throw new DatoInvalidoException("La prioridad debe ser Alta, Media o Baja");
        }
        this.id = id;
        this.titulo = titulo.trim();
        this.prioridad = normalizarPrioridad(prioridad);
        this.estado = "Pendiente";
    }

    public Tarea(int id, String titulo, String prioridad, String estado) throws DatoInvalidoException {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new DatoInvalidoException("El titulo de la tarea no puede estar vacio");
        }
        if (prioridad == null || (!prioridad.equalsIgnoreCase("Alta")
                && !prioridad.equalsIgnoreCase("Media")
                && !prioridad.equalsIgnoreCase("Baja"))) {
            throw new DatoInvalidoException("La prioridad debe ser Alta, Media o Baja");
        }
        if (estado == null || (!estado.equalsIgnoreCase("Pendiente") && !estado.equalsIgnoreCase("Completada"))) {
            throw new DatoInvalidoException("El estado debe ser Pendiente o Completada");
        }
        this.id = id;
        this.titulo = titulo.trim();
        this.prioridad = normalizarPrioridad(prioridad);
        this.estado = normalizarEstado(estado);
    }

    public void completar() throws EstadoInvalidoException {
        if (estado.equalsIgnoreCase("Completada")) {
            throw new EstadoInvalidoException("La tarea ya se encuentra completada");
        }
        this.estado = "Completada";
    }

    private String normalizarPrioridad(String valor) {
        if (valor.equalsIgnoreCase("Alta")) {
            return "Alta";
        }
        if (valor.equalsIgnoreCase("Baja")) {
            return "Baja";
        }
        return "Media";
    }

    private String normalizarEstado(String valor) {
        if (valor.equalsIgnoreCase("Completada")) {
            return "Completada";
        }
        return "Pendiente";
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public String getEstado() {
        return estado;
    }

    public boolean esPendiente() {
        return estado.equalsIgnoreCase("Pendiente");
    }
}
