package com.sistematareas.persistencia;

import com.sistematareas.modelo.DatoInvalidoException;
import com.sistematareas.modelo.Tarea;
import com.sistematareas.modelo.TareaDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TareaDAOSQLite implements TareaDAO {

    private final String url;

    public TareaDAOSQLite(String rutaBd) {
        this.url = "jdbc:sqlite:" + rutaBd;
        inicializarBaseDatos();
    }

    private void inicializarBaseDatos() {
        String sql = "CREATE TABLE IF NOT EXISTS tareas ("
                + "id INTEGER PRIMARY KEY, "
                + "titulo TEXT NOT NULL, "
                + "prioridad TEXT NOT NULL, "
                + "estado TEXT NOT NULL);";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
        }
    }

    @Override
    public void guardar(Tarea tarea) {
        if (tarea == null) {
            return;
        }
        String sql = "INSERT OR REPLACE INTO tareas(id, titulo, prioridad, estado) VALUES(?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tarea.getId());
            pstmt.setString(2, tarea.getTitulo());
            pstmt.setString(3, tarea.getPrioridad());
            pstmt.setString(4, tarea.getEstado());
            pstmt.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public Tarea buscarPorId(int id) {
        String sql = "SELECT id, titulo, prioridad, estado FROM tareas WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Tarea(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("prioridad"),
                            rs.getString("estado")
                    );
                }
            }
        } catch (SQLException | DatoInvalidoException e) {
        }
        return null;
    }

    @Override
    public List<Tarea> listar() {
        List<Tarea> lista = new ArrayList<>();
        String sql = "SELECT id, titulo, prioridad, estado FROM tareas ORDER BY id ASC";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                try {
                    Tarea t = new Tarea(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("prioridad"),
                            rs.getString("estado")
                    );
                    lista.add(t);
                } catch (DatoInvalidoException e) {
                }
            }
        } catch (SQLException e) {
        }
        return lista;
    }

    @Override
    public void actualizar(Tarea tarea) {
        guardar(tarea);
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM tareas WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
        }
    }
}
