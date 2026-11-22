package es.davante.edadcanina.modelo;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas del modelo. Se ejecutan en el ordenador, sin emulador, porque el modelo
 * no depende de ninguna vista.
 */
public class EdadCaninaModeloTest {

    private EdadCaninaModelo modelo;

    @Before
    public void preparar() {
        modelo = new EdadCaninaModelo(); // un modelo limpio para cada prueba
    }

    @Test
    public void modeloNuevo_estaSinCalcular() {
        assertEquals("", modelo.getEdadTexto());
        assertEquals(0, modelo.getEdadHumana());
        assertEquals(Validacion.SIN_CALCULAR, modelo.getValidacion());
    }

    @Test
    public void edadValida_calculaPorSiete() {
        modelo.setEdadTexto("6");

        assertEquals(Validacion.OK, modelo.calcular());
        assertEquals(42, modelo.getEdadHumana());
        assertEquals(Validacion.OK, modelo.getValidacion());
    }

    @Test
    public void limites_sonValidos() {
        modelo.setEdadTexto("1");
        assertEquals(Validacion.OK, modelo.calcular());
        assertEquals(7, modelo.getEdadHumana());

        modelo.setEdadTexto("30");
        assertEquals(Validacion.OK, modelo.calcular());
        assertEquals(210, modelo.getEdadHumana());
    }

    @Test
    public void campoVacio_devuelveVacio() {
        modelo.setEdadTexto("");
        assertEquals(Validacion.VACIO, modelo.calcular());
    }

    @Test
    public void soloEspacios_cuentaComoVacio() {
        modelo.setEdadTexto("   ");
        assertEquals(Validacion.VACIO, modelo.calcular());
    }

    @Test
    public void texto_devuelveNoEsNumero() {
        modelo.setEdadTexto("abc");
        assertEquals(Validacion.NO_ES_NUMERO, modelo.calcular());
    }

    @Test
    public void decimal_devuelveNoEsNumero() {
        modelo.setEdadTexto("3.5");
        assertEquals(Validacion.NO_ES_NUMERO, modelo.calcular());
    }

    @Test
    public void numeroGigante_noSeCierraYDevuelveNoEsNumero() {
        // En la v1 esto cerraba la app; no cabe en un int y parseInt falla
        modelo.setEdadTexto("99999999999");
        assertEquals(Validacion.NO_ES_NUMERO, modelo.calcular());
    }

    @Test
    public void cero_estaFueraDeRango() {
        modelo.setEdadTexto("0");
        assertEquals(Validacion.FUERA_DE_RANGO, modelo.calcular());
    }

    @Test
    public void treintaYUno_estaFueraDeRango() {
        modelo.setEdadTexto("31");
        assertEquals(Validacion.FUERA_DE_RANGO, modelo.calcular());
    }

    @Test
    public void negativo_estaFueraDeRango() {
        modelo.setEdadTexto("-4");
        assertEquals(Validacion.FUERA_DE_RANGO, modelo.calcular());
    }

    @Test
    public void espaciosAlrededor_seIgnoran() {
        modelo.setEdadTexto(" 5 ");
        assertEquals(Validacion.OK, modelo.calcular());
        assertEquals(35, modelo.getEdadHumana());
    }

    @Test
    public void cambiarElTexto_invalidaElResultadoAnterior() {
        modelo.setEdadTexto("6");
        modelo.calcular();

        modelo.setEdadTexto("7");

        assertEquals(Validacion.SIN_CALCULAR, modelo.getValidacion());
        assertEquals(0, modelo.getEdadHumana());
    }

    @Test
    public void mismoTexto_noInvalidaElResultado() {
        modelo.setEdadTexto("6");
        modelo.calcular();

        modelo.setEdadTexto("6"); // el mismo texto: no ha cambiado nada

        assertEquals(Validacion.OK, modelo.getValidacion());
        assertEquals(42, modelo.getEdadHumana());
    }

    @Test
    public void unErrorDespuesDeUnAcierto_noDejaElResultadoViejo() {
        modelo.setEdadTexto("6");
        modelo.calcular();

        modelo.setEdadTexto("0");
        modelo.calcular();

        assertEquals(Validacion.FUERA_DE_RANGO, modelo.getValidacion());
        assertEquals(0, modelo.getEdadHumana());
    }

    @Test
    public void textoNull_cuentaComoVacio() {
        modelo.setEdadTexto(null);
        assertEquals("", modelo.getEdadTexto());
        assertEquals(Validacion.VACIO, modelo.calcular());
    }
}
