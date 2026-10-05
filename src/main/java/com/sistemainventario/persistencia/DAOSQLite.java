package com.sistemainventario.persistencia;

import com.sistemainventario.modelo.DatoInvalidoException;
import com.sistemainventario.modelo.Producto;
import com.sistemainventario.modelo.ProductoDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOSQLite implements ProductoDAO {

    private String urlConexion;

    public DAOSQLite(String rutaArchivo) {
        this.urlConexion = "jdbc:sqlite:" + rutaArchivo;
        inicializarBaseDeDatos();
    }

    public DAOSQLite() {
        this("inventario.db");
    }

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(urlConexion);
    }

    private void inicializarBaseDeDatos() {
        String sql = "CREATE TABLE IF NOT EXISTS productos ("
                + "nombre TEXT PRIMARY KEY, "
                + "cantidad INTEGER NOT NULL, "
                + "precio REAL NOT NULL)";
        try (Connection con = conectar(); Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
        }
    }

    @Override
    public void guardar(Producto producto) {
        String sql = "INSERT OR REPLACE INTO productos (nombre, cantidad, precio) VALUES (?, ?, ?)";
        try (Connection con = conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, producto.getNombre());
            ps.setInt(2, producto.getCantidad());
            ps.setDouble(3, producto.getPrecio());
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public Producto buscar(String nombre) {
        String sql = "SELECT nombre, cantidad, precio FROM productos WHERE LOWER(nombre) = LOWER(?)";
        try (Connection con = conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String n = rs.getString("nombre");
                    int c = rs.getInt("cantidad");
                    double p = rs.getDouble("precio");
                    return new Producto(n, c, p);
                }
            } catch (DatoInvalidoException e) {
            }
        } catch (SQLException e) {
        }
        return null;
    }

    @Override
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT nombre, cantidad, precio FROM productos";
        try (Connection con = conectar(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                try {
                    String n = rs.getString("nombre");
                    int c = rs.getInt("cantidad");
                    double p = rs.getDouble("precio");
                    lista.add(new Producto(n, c, p));
                } catch (DatoInvalidoException e) {
                }
            }
        } catch (SQLException e) {
        }
        return lista;
    }

    @Override
    public void actualizar(Producto producto) {
        String sql = "UPDATE productos SET cantidad = ?, precio = ? WHERE LOWER(nombre) = LOWER(?)";
        try (Connection con = conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, producto.getCantidad());
            ps.setDouble(2, producto.getPrecio());
            ps.setString(3, producto.getNombre());
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }
}
