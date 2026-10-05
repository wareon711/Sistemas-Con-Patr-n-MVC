package com.sistematareas.persistencia;

import com.sistematareas.modelo.DatoInvalidoException;
import com.sistematareas.modelo.Tarea;
import com.sistematareas.modelo.TareaDAO;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TareaDAOArchivo implements TareaDAO {

    private final String rutaArchivo;
    private final Map<Integer, Tarea> cache = new LinkedHashMap<>();

    public TareaDAOArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        cargarDesdeArchivo();
    }

    private void cargarDesdeArchivo() {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] partes = linea.split(";");
                if (partes.length == 4) {
                    try {
                        int id = Integer.parseInt(partes[0].trim());
                        String titulo = partes[1].trim();
                        String prioridad = partes[2].trim();
                        String estado = partes[3].trim();
                        Tarea tarea = new Tarea(id, titulo, prioridad, estado);
                        cache.put(id, tarea);
                    } catch (DatoInvalidoException | NumberFormatException e) {
                    }
                }
            }
        } catch (IOException e) {
        }
    }

    private void sincronizarArchivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Tarea t : cache.values()) {
                bw.write(t.getId() + ";" + t.getTitulo() + ";" + t.getPrioridad() + ";" + t.getEstado());
                bw.newLine();
            }
        } catch (IOException e) {
        }
    }

    @Override
    public void guardar(Tarea tarea) {
        if (tarea != null) {
            cache.put(tarea.getId(), tarea);
            sincronizarArchivo();
        }
    }

    @Override
    public Tarea buscarPorId(int id) {
        return cache.get(id);
    }

    @Override
    public List<Tarea> listar() {
        return new ArrayList<>(cache.values());
    }

    @Override
    public void actualizar(Tarea tarea) {
        if (tarea != null) {
            cache.put(tarea.getId(), tarea);
            sincronizarArchivo();
        }
    }

    @Override
    public void eliminar(int id) {
        cache.remove(id);
        sincronizarArchivo();
    }
}
