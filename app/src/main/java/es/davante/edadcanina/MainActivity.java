package es.davante.edadcanina;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import es.davante.edadcanina.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Constantes (en lugar de escribir los números directamente en el código)
    private static final int FACTOR_CANINO = 7;
    private static final int EDAD_MINIMA = 1;
    private static final int EDAD_MAXIMA = 30; // ningún perro supera esta edad

    // Es un atributo porque calcularEdad() y mostrarError() también lo necesitan.
    // Esta clase la genera DataBinding a partir de activity_main.xml.
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Infla el layout y devuelve un objeto con un campo por cada vista que
        // tiene id, así no hace falta buscar las vistas una a una
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Al pulsar el botón se calcula la edad
        binding.btnCalc.setOnClickListener(view -> calcularEdad());
    }

    private void calcularEdad() {
        // 1. Leemos el texto quitando los espacios
        String ageString = binding.ageInput.getText().toString().trim();

        // 2. ¿Está vacío?
        if (ageString.isEmpty()) {
            mostrarError(getString(R.string.error_vacio));
            return;
        }

        // 3. Lo pasamos a número; si no se puede, avisamos en vez de cerrar la app
        int age;
        try {
            age = Integer.parseInt(ageString);
        } catch (NumberFormatException e) {
            mostrarError(getString(R.string.error_no_numero));
            return;
        }

        // 4. ¿Está dentro del rango permitido?
        if (age < EDAD_MINIMA || age > EDAD_MAXIMA) {
            mostrarError(getString(R.string.error_rango, EDAD_MINIMA, EDAD_MAXIMA));
            return;
        }

        // 5. Todo correcto: quitamos el error y mostramos el resultado
        binding.ageInput.setError(null);
        int dogAge = age * FACTOR_CANINO;
        binding.resultText.setText(getString(R.string.tu_edad_format, dogAge));
    }

    private void mostrarError(String mensaje) {
        binding.ageInput.setError(mensaje);   // 1. Mensaje en el EditText
        binding.ageInput.requestFocus();      // 2. Foco en el EditText
        binding.resultText.setText("");       // 3. Borramos el resultado anterior
    }
}
