package com.sistematareas;

import com.sistematareas.controlador.ControladorTareas;
import com.sistematareas.modelo.GestorTareas;
import com.sistematareas.modelo.TareaDAO;
import com.sistematareas.persistencia.TareaDAOSQLite;
import com.sistematareas.vista.VentanaTareas;
import com.sistematareas.vista.VistaConsolaTareas;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class AppTareas {

    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        String[] opciones = {"Consola", "Interfaz grafica", "Ambas"};
        int seleccion = JOptionPane.showOptionDialog(
                null,
                "Seleccione el modo de ejecucion del Gestor de Tareas:",
                "Gestor de Tareas - MVC",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (seleccion == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }

        TareaDAO dao = new TareaDAOSQLite("tareas.db");
        GestorTareas gestor = new GestorTareas(dao);
        ControladorTareas controlador = new ControladorTareas(gestor);

        if (seleccion == 0) {
            VistaConsolaTareas consola = new VistaConsolaTareas(controlador);
            controlador.agregarVista(consola);
            gestor.agregarObservador(consola);
            consola.iniciar();
            System.exit(0);
        } else if (seleccion == 1) {
            SwingUtilities.invokeLater(() -> {
                VentanaTareas ventana = new VentanaTareas(controlador);
                controlador.agregarVista(ventana);
                gestor.agregarObservador(ventana);
                ventana.setVisible(true);
            });
        } else if (seleccion == 2) {
            SwingUtilities.invokeLater(() -> {
                VentanaTareas ventana = new VentanaTareas(controlador);
                controlador.agregarVista(ventana);
                gestor.agregarObservador(ventana);
                ventana.setVisible(true);
            });

            Thread hiloConsola = new Thread(() -> {
                VistaConsolaTareas consola = new VistaConsolaTareas(controlador);
                controlador.agregarVista(consola);
                gestor.agregarObservador(consola);
                consola.iniciar();
            });
            hiloConsola.start();
        }
    }
}
