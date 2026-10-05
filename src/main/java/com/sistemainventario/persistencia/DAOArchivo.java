package com.sistemainventario.persistencia;

import com.sistemainventario.modelo.Producto;
import com.sistemainventario.modelo.ProductoDAO;
import com.sistemainventario.modelo.DatoInvalidoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class DAOArchivo implements ProductoDAO {

    private String rutaArchivo;
    private List<Producto> productos;

    public DAOArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.productos = new ArrayList<>();
        cargar();
    }

    private void cargar() {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            return;
        }
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    try {
                        String nombre = partes[0].trim();
                        int cantidad = Integer.parseInt(partes[1].trim());
                        double precio = Double.parseDouble(partes[2].trim());
                        productos.add(new Producto(nombre, cantidad, precio));
                    } catch (NumberFormatException | DatoInvalidoException e) {
                    }
                }
            }
        } catch (IOException e) {
        }
    }

    private void sincronizar() {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(rutaArchivo))) {
            for (Producto p : productos) {
                escritor.println(p.getNombre() + "," + p.getCantidad() + "," + p.getPrecio());
            }
        } catch (IOException e) {
        }
    }

    @Override
    public void guardar(Producto producto) {
        Producto existente = buscar(producto.getNombre());
        if (existente != null) {
            actualizar(producto);
        } else {
            productos.add(producto);
            sincronizar();
        }
    }

    @Override
    public Producto buscar(String nombre) {
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public List<Producto> listar() {
        return new ArrayList<>(productos);
    }

    @Override
    public void actualizar(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getNombre().equalsIgnoreCase(producto.getNombre())) {
                productos.set(i, producto);
                sincronizar();
                return;
            }
        }
    }
}
