package es.davante.edadcanina;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import es.davante.edadcanina.databinding.ActivityMainBinding;
import es.davante.edadcanina.modelo.EdadCaninaModelo;
import es.davante.edadcanina.modelo.Validacion;

public class MainActivity extends AppCompatActivity {

    // Es un atributo porque calcularEdad() y mostrarError() también lo necesitan.
    // Esta clase la genera DataBinding a partir de activity_main.xml.
    private ActivityMainBinding binding;

    // Los datos y las reglas ya no están aquí, están en el modelo
    private EdadCaninaModelo modelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Infla el layout y devuelve un objeto con un campo por cada vista que
        // tiene id, así no hace falta buscar las vistas una a una
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        modelo = new EdadCaninaModelo();

        // Entrego el modelo a la vista: desde aquí el XML lee y escribe en él
        binding.setModelo(modelo);

        // Al pulsar el botón se calcula la edad
        binding.btnCalc.setOnClickListener(view -> calcularEdad());
    }

    private void calcularEdad() {
        // El texto ya llega solo al modelo (@={modelo.edadTexto}), así que no hace
        // falta leer el campo: solo le pido al modelo que calcule. El resultado
        // se pinta solo; el error, de momento, lo sigo pintando a mano.
        Validacion resultado = modelo.calcular();

        // Los mensajes los pongo aquí (y no en el modelo) porque usan getString,
        // que es de Android y depende del idioma
        switch (resultado) {
            case OK:
                binding.ageInput.setError(null);
                break;
            case VACIO:
                mostrarError(getString(R.string.error_vacio));
                break;
            case NO_ES_NUMERO:
                mostrarError(getString(R.string.error_no_numero));
                break;
            case FUERA_DE_RANGO:
                mostrarError(getString(R.string.error_rango,
                        EdadCaninaModelo.EDAD_MINIMA, EdadCaninaModelo.EDAD_MAXIMA));
                break;
            default:
                break; // SIN_CALCULAR no puede salir de calcular()
        }
    }

    private void mostrarError(String mensaje) {
        binding.ageInput.setError(mensaje);   // 1. Mensaje en el EditText
        binding.ageInput.requestFocus();      // 2. Foco en el EditText
        // El resultado anterior ya no hay que borrarlo: el modelo lo pone a 0 y
        // la vista lo oculta sola
    }
}
