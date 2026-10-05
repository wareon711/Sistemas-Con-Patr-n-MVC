package com.sistemainventario.vista;

import com.sistemainventario.controlador.ControladorInventario;
import com.sistemainventario.modelo.Observador;
import com.sistemainventario.modelo.Producto;
import java.util.List;
import java.util.Scanner;

public class VistaConsola implements Vista, Observador {

    private final ControladorInventario controlador;
    private final Scanner scanner;
    private volatile boolean ejecutando;

    public VistaConsola(ControladorInventario controlador) {
        this.controlador = controlador;
        this.scanner = new Scanner(System.in);
        this.ejecutando = false;
    }

    public void iniciar() {
        ejecutando = true;
        while (ejecutando) {
            System.out.println("\n--- SISTEMA DE INVENTARIO (CONSOLA) ---");
            System.out.println("1) Agregar producto");
            System.out.println("2) Vender producto");
            System.out.println("3) Listar productos");
            System.out.println("4) Valor total del inventario");
            System.out.println("0) Salir");
            System.out.print("Opcion: ");

            String opcion = scanner.nextLine().trim();
            if (opcion.equals("0")) {
                ejecutando = false;
                break;
            }

            switch (opcion) {
                case "1":
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Cantidad: ");
                    String cantidad = scanner.nextLine();
                    System.out.print("Precio: ");
                    String precio = scanner.nextLine();
                    controlador.agregarProducto(nombre, cantidad, precio, this);
                    break;
                case "2":
                    System.out.print("Producto a vender: ");
                    String prodVenta = scanner.nextLine();
                    if (controlador.existeProducto(prodVenta)) {
                        System.out.print("Cantidad a vender: ");
                        String cantVenta = scanner.nextLine();
                        controlador.venderProducto(prodVenta, cantVenta, this);
                    } else {
                        mostrarError("No existe");
                    }
                    break;
                case "3":
                    controlador.listarProductos(this);
                    break;
                case "4":
                    controlador.calcularValorTotal(this);
                    break;
                default:
                    System.out.println("Opcion no valida.");
                    break;
            }
        }
    }

    @Override
    public void mostrarProductos(List<Producto> productos) {
        System.out.println("\n--- Listado de Productos ---");
        System.out.printf("%-20s %-12s %-12s%n", "NOMBRE", "CANTIDAD", "PRECIO");
        System.out.println("----------------------------------------------");
        if (productos.isEmpty()) {
            System.out.println("(No hay productos en inventario)");
            return;
        }
        for (Producto p : productos) {
            System.out.printf("%-20s %-12d $%-11.2f%n", p.getNombre(), p.getCantidad(), p.getPrecio());
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
    public void mostrarValorTotal(double total) {
        System.out.printf("\n[INFO] Valor total del inventario: $%.2f%n", total);
    }

    @Override
    public void inventarioCambio() {
        System.out.println("\n[AVISO CONSOLA] El inventario fue actualizado.");
        controlador.listarProductos(this);
    }
}
