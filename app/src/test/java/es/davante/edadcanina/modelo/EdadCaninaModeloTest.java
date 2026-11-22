package es.davante.edadcanina.modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.databinding.Observable;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import es.davante.edadcanina.BR;

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

    // ---- Avisos del modelo (lo que hace que la vista se repinte sola) ----

    // Guarda los números BR de todas las propiedades de las que avisa el modelo
    private List<Integer> escucharAvisos() {
        final List<Integer> avisos = new ArrayList<>();
        modelo.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                avisos.add(propertyId);
            }
        });
        return avisos;
    }

    @Test
    public void calcular_avisaDeLaEdadYDeLaValidacion() {
        modelo.setEdadTexto("6");
        List<Integer> avisos = escucharAvisos();

        modelo.calcular();

        assertTrue(avisos.contains(BR.edadHumana));
        assertTrue(avisos.contains(BR.validacion));
    }

    @Test
    public void cambiarElTexto_avisaDelTexto() {
        List<Integer> avisos = escucharAvisos();

        modelo.setEdadTexto("6");

        assertTrue(avisos.contains(BR.edadTexto));
    }

    @Test
    public void mismoTexto_noAvisaDeNada() {
        // Esto es lo que corta el viaje de ida y vuelta del enlace en dos sentidos
        modelo.setEdadTexto("6");
        List<Integer> avisos = escucharAvisos();

        modelo.setEdadTexto("6");

        assertTrue(avisos.isEmpty());
    }

    @Test
    public void calcularDosVecesLoMismo_soloAvisaLaPrimera() {
        modelo.setEdadTexto("6");
        modelo.calcular();
        List<Integer> avisos = escucharAvisos();

        modelo.calcular(); // sale lo mismo: ni la edad ni la validación cambian

        assertTrue(avisos.isEmpty());
    }
}
