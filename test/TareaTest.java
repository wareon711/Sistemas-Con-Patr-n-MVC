import com.sistematareas.modelo.DatoInvalidoException;
import com.sistematareas.modelo.EstadoInvalidoException;
import com.sistematareas.modelo.GestorTareas;
import com.sistematareas.modelo.LimiteTareasException;
import com.sistematareas.modelo.NoExisteException;
import com.sistematareas.modelo.Tarea;
import com.sistematareas.modelo.TareaDAO;
import com.sistematareas.modelo.TareaDuplicadaException;
import com.sistematareas.persistencia.TareaDAOMemoria;
import org.junit.Test;
import static org.junit.Assert.*;

public class TareaTest {

    @Test
    public void maximoCincoTareasPendientesLanzaLimiteTareasException() throws Exception {
        TareaDAO dao = new TareaDAOMemoria();
        GestorTareas gestor = new GestorTareas(dao);

        gestor.agregarTarea("Tarea 1", "Alta");
        gestor.agregarTarea("Tarea 2", "Media");
        gestor.agregarTarea("Tarea 3", "Baja");
        gestor.agregarTarea("Tarea 4", "Media");
        gestor.agregarTarea("Tarea 5", "Alta");

        try {
            gestor.agregarTarea("Tarea 6", "Baja");
            fail("Debio rechazar por superar el limite de 5 tareas pendientes");
        } catch (LimiteTareasException e) {
        }

        assertEquals(5, gestor.contarPendientes());
    }

    @Test
    public void tareaDuplicadaConMismoTituloLanzaExcepcion() throws Exception {
        TareaDAO dao = new TareaDAOMemoria();
        GestorTareas gestor = new GestorTareas(dao);

        gestor.agregarTarea("Estudiar arquitectura", "Alta");

        try {
            gestor.agregarTarea("estudiar arquitectura", "Media");
            fail("Debio rechazar tarea con titulo duplicado");
        } catch (TareaDuplicadaException e) {
        }
    }

    @Test
    public void completarTareaCambiaEstadoYLiberaCupo() throws Exception {
        TareaDAO dao = new TareaDAOMemoria();
        GestorTareas gestor = new GestorTareas(dao);

        gestor.agregarTarea("T1", "Alta");
        gestor.agregarTarea("T2", "Media");
        gestor.agregarTarea("T3", "Baja");
        gestor.agregarTarea("T4", "Media");
        Tarea t5 = gestor.agregarTarea("T5", "Alta");

        assertEquals(5, gestor.contarPendientes());

        gestor.completarTarea(t5.getId());

        assertEquals(4, gestor.contarPendientes());
        assertEquals("Completada", gestor.buscarTarea(t5.getId()).getEstado());

        Tarea t6 = gestor.agregarTarea("T6", "Media");
        assertNotNull(t6);
        assertEquals(5, gestor.contarPendientes());
    }

    @Test
    public void completarTareaYaCompletadaLanzaEstadoInvalidoException() throws Exception {
        TareaDAO dao = new TareaDAOMemoria();
        GestorTareas gestor = new GestorTareas(dao);

        Tarea t = gestor.agregarTarea("Comprar cafe", "Baja");
        gestor.completarTarea(t.getId());

        try {
            gestor.completarTarea(t.getId());
            fail("Debio rechazar completar una tarea ya completada");
        } catch (EstadoInvalidoException e) {
        }
    }

    @Test
    public void tituloVacioOPrioridadInvalidaLanzaDatoInvalidoException() {
        TareaDAO dao = new TareaDAOMemoria();
        GestorTareas gestor = new GestorTareas(dao);

        try {
            gestor.agregarTarea("", "Alta");
            fail("Debio rechazar titulo vacio");
        } catch (DatoInvalidoException e) {
        } catch (Exception e) {
            fail("Lanza excepcion incorrecta");
        }

        try {
            gestor.agregarTarea("Valida", "Urgente");
            fail("Debio rechazar prioridad invalida");
        } catch (DatoInvalidoException e) {
        } catch (Exception e) {
            fail("Lanza excepcion incorrecta");
        }
    }
}
