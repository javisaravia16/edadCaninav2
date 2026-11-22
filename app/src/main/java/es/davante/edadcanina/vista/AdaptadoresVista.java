package es.davante.edadcanina.vista;

import android.content.Context;
import android.widget.EditText;

import androidx.databinding.BindingAdapter;

import es.davante.edadcanina.R;
import es.davante.edadcanina.modelo.EdadCaninaModelo;
import es.davante.edadcanina.modelo.Validacion;

/**
 * VISTA: atributos propios para usar en el XML.
 *
 * Esto va en el paquete vista y no en modelo porque traduce una Validacion a un
 * texto para el usuario (getString, idioma) y toca un EditText: eso es presentación.
 */
public final class AdaptadoresVista {

    // Clase de utilidades: no tiene sentido crear objetos de ella
    private AdaptadoresVista() {
    }

    /**
     * Se usa en el XML así: app:errorValidacion="@{modelo.validacion}".
     * Tiene que ser public static; el primer parámetro es la vista y el segundo
     * el valor que viene de la expresión.
     */
    @BindingAdapter("errorValidacion")
    public static void mostrarErrorValidacion(EditText campo, Validacion validacion) {
        String mensaje = mensajeDeError(campo.getContext(), validacion);

        // Con mensaje == null setError quita el error. Así, cuando el usuario
        // empieza a escribir (validacion vuelve a SIN_CALCULAR), el error
        // desaparece sin tener que hacer nada más.
        campo.setError(mensaje);

        // Si hay error, el foco al campo para que el usuario pueda corregirlo
        if (mensaje != null) {
            campo.requestFocus();
        }
    }

    // El switch que antes estaba en la Activity; el Context lo saco de la propia vista
    private static String mensajeDeError(Context contexto, Validacion validacion) {
        if (validacion == null) {
            return null; // por si el modelo todavía no se ha entregado al binding
        }
        switch (validacion) {
            case VACIO:
                return contexto.getString(R.string.error_vacio);
            case NO_ES_NUMERO:
                return contexto.getString(R.string.error_no_numero);
            case FUERA_DE_RANGO:
                return contexto.getString(R.string.error_rango,
                        EdadCaninaModelo.EDAD_MINIMA, EdadCaninaModelo.EDAD_MAXIMA);
            default:
                return null; // SIN_CALCULAR y OK: no hay nada que corregir
        }
    }
}
