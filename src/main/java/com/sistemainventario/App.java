package com.sistemainventario;

import com.sistemainventario.controlador.ControladorInventario;
import com.sistemainventario.modelo.Inventario;
import com.sistemainventario.modelo.ProductoDAO;
import com.sistemainventario.persistencia.DAOMemoria;
import com.sistemainventario.persistencia.DAOArchivo;
import com.sistemainventario.persistencia.DAOSQLite;
import com.sistemainventario.vista.VentanaInventario;
import com.sistemainventario.vista.VistaConsola;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class App {

    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        String[] opciones = {"Consola", "Interfaz grafica", "Ambas"};
        int seleccion = JOptionPane.showOptionDialog(
            null,
            "Seleccione el modo de ejecucion:",
            "Modo de ejecucion",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );

        if (seleccion == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }

        ProductoDAO dao = new DAOSQLite("inventario.db");
        Inventario inventario = new Inventario(dao);
        ControladorInventario controlador = new ControladorInventario(inventario);

        if (seleccion == 0) {
            VistaConsola consola = new VistaConsola(controlador);
            controlador.agregarVista(consola);
            inventario.agregarObservador(consola);
            consola.iniciar();
            System.exit(0);
        } else if (seleccion == 1) {
            SwingUtilities.invokeLater(() -> {
                VentanaInventario ventana = new VentanaInventario(controlador);
                controlador.agregarVista(ventana);
                inventario.agregarObservador(ventana);
                ventana.setVisible(true);
            });
        } else if (seleccion == 2) {
            SwingUtilities.invokeLater(() -> {
                VentanaInventario ventana = new VentanaInventario(controlador);
                controlador.agregarVista(ventana);
                inventario.agregarObservador(ventana);
                ventana.setVisible(true);
            });

            Thread hiloConsola = new Thread(() -> {
                VistaConsola consola = new VistaConsola(controlador);
                controlador.agregarVista(consola);
                inventario.agregarObservador(consola);
                consola.iniciar();
            });
            hiloConsola.start();
        }
    }
}
