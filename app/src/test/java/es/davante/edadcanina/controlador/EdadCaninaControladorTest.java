package es.davante.edadcanina.controlador;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import es.davante.edadcanina.modelo.EdadCaninaModelo;
import es.davante.edadcanina.modelo.Validacion;

/**
 * Pruebas del controlador: simulo los clics llamando a sus métodos y miro cómo
 * queda el modelo. No hace falta ninguna pantalla.
 */
public class EdadCaninaControladorTest {

    private EdadCaninaModelo modelo;
    private EdadCaninaControlador controlador;

    @Before
    public void preparar() {
        modelo = new EdadCaninaModelo();
        controlador = new EdadCaninaControlador(modelo);
    }

    @Test
    public void clicEnCalcular_conEdadValida_calculaLaEdadHumana() {
        modelo.setEdadTexto("6");

        controlador.onCalcularClick();

        assertEquals(Validacion.OK, modelo.getValidacion());
        assertEquals(42, modelo.getEdadHumana());
    }

    @Test
    public void clicEnCalcular_conElCampoVacio_dejaElErrorEnElModelo() {
        controlador.onCalcularClick();

        assertEquals(Validacion.VACIO, modelo.getValidacion());
    }

    @Test
    public void clicEnLimpiar_borraTodo() {
        modelo.setEdadTexto("6");
        controlador.onCalcularClick();

        controlador.onLimpiarClick();

        assertEquals("", modelo.getEdadTexto());
        assertEquals(0, modelo.getEdadHumana());
        assertEquals(Validacion.SIN_CALCULAR, modelo.getValidacion());
    }

    @Test
    public void clicEnLimpiar_quitaElErrorDeUnCampoVacio() {
        controlador.onCalcularClick(); // error: campo vacío

        controlador.onLimpiarClick();

        assertEquals(Validacion.SIN_CALCULAR, modelo.getValidacion());
    }
}
