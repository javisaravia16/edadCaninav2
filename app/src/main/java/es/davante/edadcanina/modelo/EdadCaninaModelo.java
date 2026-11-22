package es.davante.edadcanina.modelo;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import es.davante.edadcanina.BR;

/**
 * MODELO: guarda los datos de la app (el texto escrito, la edad humana y el
 * resultado de validar) y contiene las reglas: validar y calcular.
 *
 * Hereda de BaseObservable para poder AVISAR cuando algo cambia: la vista se
 * suscribe y se repinta sola, sin que el modelo sepa nada de ella.
 * Lo único de Android que importa es androidx.databinding; no usa textos para
 * el usuario, así que se sigue pudiendo probar con JUnit sin emulador.
 */
public class EdadCaninaModelo extends BaseObservable {

    // Reglas de negocio: viven aquí porque son del problema, no de la pantalla.
    // Son públicas para que la vista pueda poner "entre 1 y 30" en su mensaje.
    public static final int FACTOR_CANINO = 7;
    public static final int EDAD_MINIMA = 1;
    public static final int EDAD_MAXIMA = 30; // ningún perro supera esta edad

    private String edadTexto = "";
    private int edadHumana = 0;
    private Validacion validacion = Validacion.SIN_CALCULAR;

    // @Bindable crea la constante BR.xxx de cada propiedad, que es el "número"
    // con el que el modelo avisa de qué ha cambiado
    @Bindable
    public String getEdadTexto() {
        return edadTexto;
    }

    @Bindable
    public int getEdadHumana() {
        return edadHumana;
    }

    @Bindable
    public Validacion getValidacion() {
        return validacion;
    }

    // Este setter es público porque la vista escribe aquí lo que teclea el usuario
    public void setEdadTexto(String nuevo) {
        if (nuevo == null) {
            nuevo = ""; // así nunca tengo que preocuparme por un null más adelante
        }
        // IMPORTANTE: si no ha cambiado, no avisamos. Con el enlace en los dos
        // sentidos, avisar siempre haría: la vista escribe en el modelo, el modelo
        // avisa, la vista vuelve a escribir el texto... y así sin parar. Este if
        // es el que corta la cadena.
        if (nuevo.equals(edadTexto)) {
            return;
        }
        edadTexto = nuevo;
        notifyPropertyChanged(BR.edadTexto);

        // Regla de negocio: si cambia el texto, el resultado anterior ya no vale.
        // La pongo en el modelo (y no en la Activity) para que valga siempre,
        // lo cambie quien lo cambie.
        setEdadHumana(0);
        setValidacion(Validacion.SIN_CALCULAR);
    }

    // Los dos siguientes son privados: solo calcular() y setEdadTexto() deben
    // poder tocarlos, para que nadie deje la edad y la validación contradictorias
    private void setEdadHumana(int nueva) {
        if (nueva == edadHumana) {
            return;
        }
        edadHumana = nueva;
        notifyPropertyChanged(BR.edadHumana);
    }

    private void setValidacion(Validacion nueva) {
        if (nueva == validacion) {
            return;
        }
        validacion = nueva;
        notifyPropertyChanged(BR.validacion);
    }

    /**
     * Valida el texto y, si es correcto, calcula la edad humana.
     * Devuelve qué ha pasado, no un mensaje: los mensajes son cosa de la vista.
     */
    public Validacion calcular() {
        // Quito los espacios, igual que en la v2
        String texto = edadTexto.trim();
        if (texto.isEmpty()) {
            return terminar(Validacion.VACIO, 0);
        }

        // parseInt lanza NumberFormatException con letras, decimales o números
        // demasiado grandes; la capturo para que la app no se cierre
        int edadPerro;
        try {
            edadPerro = Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            return terminar(Validacion.NO_ES_NUMERO, 0);
        }

        if (edadPerro < EDAD_MINIMA || edadPerro > EDAD_MAXIMA) {
            return terminar(Validacion.FUERA_DE_RANGO, 0);
        }

        return terminar(Validacion.OK, edadPerro * FACTOR_CANINO);
    }

    // Deja guardado el resultado del cálculo. Si hay error la edad es 0, así
    // nunca queda un resultado antiguo junto a un error.
    private Validacion terminar(Validacion resultado, int edad) {
        setEdadHumana(edad);
        setValidacion(resultado);
        return resultado;
    }
}
