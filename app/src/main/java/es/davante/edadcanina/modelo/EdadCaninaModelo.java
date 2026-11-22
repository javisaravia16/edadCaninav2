package es.davante.edadcanina.modelo;

/**
 * MODELO: guarda los datos de la app (el texto escrito, la edad humana y el
 * resultado de validar) y contiene las reglas: validar y calcular.
 *
 * No importa nada de android.* ni usa textos para el usuario. Así se puede probar
 * con JUnit sin abrir el emulador.
 */
public class EdadCaninaModelo {

    // Reglas de negocio: viven aquí porque son del problema, no de la pantalla.
    // Son públicas para que la vista pueda poner "entre 1 y 30" en su mensaje.
    public static final int FACTOR_CANINO = 7;
    public static final int EDAD_MINIMA = 1;
    public static final int EDAD_MAXIMA = 30; // ningún perro supera esta edad

    private String edadTexto = "";
    private int edadHumana = 0;
    private Validacion validacion = Validacion.SIN_CALCULAR;

    public String getEdadTexto() {
        return edadTexto;
    }

    public int getEdadHumana() {
        return edadHumana;
    }

    public Validacion getValidacion() {
        return validacion;
    }

    public void setEdadTexto(String nuevo) {
        if (nuevo == null) {
            nuevo = ""; // así nunca tengo que preocuparme por un null más adelante
        }
        if (nuevo.equals(edadTexto)) {
            return; // no ha cambiado nada, no hay que tocar el resultado
        }
        edadTexto = nuevo;

        // Regla de negocio: si cambia el texto, el resultado anterior ya no vale.
        // La pongo en el modelo (y no en la Activity) para que valga siempre,
        // lo cambie quien lo cambie.
        edadHumana = 0;
        validacion = Validacion.SIN_CALCULAR;
    }

    /**
     * Valida el texto y, si es correcto, calcula la edad humana.
     * Devuelve qué ha pasado, no un mensaje: los mensajes son cosa de la vista.
     */
    public Validacion calcular() {
        edadHumana = 0; // si hay error no debe quedar un resultado antiguo

        // Quito los espacios, igual que en la v2
        String texto = edadTexto.trim();
        if (texto.isEmpty()) {
            validacion = Validacion.VACIO;
            return validacion;
        }

        // parseInt lanza NumberFormatException con letras, decimales o números
        // demasiado grandes; la capturo para que la app no se cierre
        int edadPerro;
        try {
            edadPerro = Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            validacion = Validacion.NO_ES_NUMERO;
            return validacion;
        }

        if (edadPerro < EDAD_MINIMA || edadPerro > EDAD_MAXIMA) {
            validacion = Validacion.FUERA_DE_RANGO;
            return validacion;
        }

        edadHumana = edadPerro * FACTOR_CANINO;
        validacion = Validacion.OK;
        return validacion;
    }
}
