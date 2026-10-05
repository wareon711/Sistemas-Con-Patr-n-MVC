package com.sistematareas.vista;

import com.sistematareas.controlador.ControladorTareas;
import com.sistematareas.modelo.ObservadorTareas;
import com.sistematareas.modelo.Tarea;
import java.util.List;
import java.util.Scanner;

public class VistaConsolaTareas implements VistaTareas, ObservadorTareas {

    private final ControladorTareas controlador;
    private final Scanner scanner;
    private volatile boolean ejecutando;

    public VistaConsolaTareas(ControladorTareas controlador) {
        this.controlador = controlador;
        this.scanner = new Scanner(System.in);
        this.ejecutando = false;
    }

    public void iniciar() {
        ejecutando = true;
        while (ejecutando) {
            System.out.println("\n--- GESTOR DE TAREAS (CONSOLA) ---");
            System.out.println("1) Agregar tarea");
            System.out.println("2) Completar tarea");
            System.out.println("3) Eliminar tarea");
            System.out.println("4) Listar tareas");
            System.out.println("0) Salir");
            System.out.print("Opcion: ");

            String opcion = scanner.nextLine();
            if (opcion.equals("0")) {
                ejecutando = false;
                break;
            }

            switch (opcion) {
                case "1":
                    System.out.print("Titulo de la tarea: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Prioridad (Alta/Media/Baja): ");
                    String prioridad = scanner.nextLine();
                    controlador.agregarTarea(titulo, prioridad, this);
                    break;
                case "2":
                    System.out.print("ID de la tarea a completar: ");
                    try {
                        int idComp = Integer.parseInt(scanner.nextLine().trim());
                        controlador.completarTarea(idComp, this);
                    } catch (NumberFormatException e) {
                        System.out.println("[ERROR] El ID debe ser un numero entero.");
                    }
                    break;
                case "3":
                    System.out.print("ID de la tarea a eliminar: ");
                    try {
                        int idElim = Integer.parseInt(scanner.nextLine().trim());
                        controlador.eliminarTarea(idElim, this);
                    } catch (NumberFormatException e) {
                        System.out.println("[ERROR] El ID debe ser un numero entero.");
                    }
                    break;
                case "4":
                    controlador.actualizarVistas();
                    break;
                default:
                    System.out.println("Opcion no valida.");
                    break;
            }
        }
    }

    @Override
    public void mostrarTareas(List<Tarea> tareas) {
        System.out.println("\n--- Listado de Tareas ---");
        System.out.printf("%-5s %-30s %-12s %-12s%n", "ID", "TITULO", "PRIORIDAD", "ESTADO");
        System.out.println("---------------------------------------------------------------");
        for (Tarea t : tareas) {
            System.out.printf("%-5d %-30s %-12s %-12s%n",
                    t.getId(), t.getTitulo(), t.getPrioridad(), t.getEstado());
        }
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        System.out.println("[INFO] " + mensaje);
    }

    @Override
    public void mostrarError(String error) {
        System.out.println("[ERROR] " + error);
    }

    @Override
    public void tareasCambiaron() {
        System.out.println("\n[AVISO CONSOLA] Las tareas fueron actualizadas.");
        controlador.actualizarVistas();
    }
}
