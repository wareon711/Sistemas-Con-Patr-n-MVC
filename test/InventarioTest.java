import com.sistemainventario.modelo.DatoInvalidoException;
import com.sistemainventario.modelo.Inventario;
import com.sistemainventario.modelo.NoExisteException;
import com.sistemainventario.modelo.Producto;
import com.sistemainventario.modelo.ProductoDAO;
import com.sistemainventario.modelo.StockInsuficienteException;
import com.sistemainventario.persistencia.DAOMemoria;
import org.junit.Test;
import static org.junit.Assert.*;

public class InventarioTest {

    @Test
    public void venderMasDeLoDisponibleLanzaExcepcionYConservaStock() throws DatoInvalidoException {
        ProductoDAO dao = new DAOMemoria();
        Inventario inventario = new Inventario(dao);
        inventario.agregar("Cuaderno", 5, 25.0);

        try {
            inventario.vender("Cuaderno", 10);
            fail("Debio lanzar StockInsuficienteException");
        } catch (StockInsuficienteException e) {
        } catch (Exception e) {
            fail("Lanza excepcion incorrecta");
        }

        try {
            Producto producto = inventario.buscar("Cuaderno");
            assertEquals(5, producto.getCantidad());
        } catch (NoExisteException e) {
            fail("El producto deberia existir");
        }
    }

    @Test
    public void ventaValidaDescuentaCantidadExacta() throws Exception {
        ProductoDAO dao = new DAOMemoria();
        Inventario inventario = new Inventario(dao);
        inventario.agregar("Lapiz", 20, 5.0);

        int quedan = inventario.vender("Lapiz", 8);

        assertEquals(12, quedan);
        Producto producto = inventario.buscar("Lapiz");
        assertEquals(12, producto.getCantidad());
    }

    @Test
    public void precioNegativoOCeroLanzaDatoInvalidoException() {
        ProductoDAO dao = new DAOMemoria();
        Inventario inventario = new Inventario(dao);

        try {
            inventario.agregar("Borrador", 10, -5.0);
            fail("Debio rechazar precio negativo");
        } catch (DatoInvalidoException e) {
        }

        try {
            inventario.agregar("Regla", 10, 0.0);
            fail("Debio rechazar precio cero");
        } catch (DatoInvalidoException e) {
        }
    }

    @Test
    public void valorTotalCalculaCorrectamenteVariosProductos() throws DatoInvalidoException {
        ProductoDAO dao = new DAOMemoria();
        Inventario inventario = new Inventario(dao);
        inventario.agregar("Mochila", 2, 350.0);
        inventario.agregar("Pluma", 10, 15.0);

        double totalEsperado = (2 * 350.0) + (10 * 15.0);
        assertEquals(totalEsperado, inventario.valorTotal(), 0.001);
    }

    @Test
    public void buscarInexistenteLanzaNoExisteException() {
        ProductoDAO dao = new DAOMemoria();
        Inventario inventario = new Inventario(dao);

        try {
            inventario.buscar("Tijeras");
            fail("Debio lanzar NoExisteException");
        } catch (NoExisteException e) {
        }
    }
}
