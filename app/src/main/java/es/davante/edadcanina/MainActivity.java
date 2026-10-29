package es.davante.edadcanina;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Constantes (en lugar de escribir los números directamente en el código)
    private static final int FACTOR_CANINO = 7;
    private static final int EDAD_MINIMA = 1;
    private static final int EDAD_MAXIMA = 30; // ningún perro supera esta edad

    // Vistas que se usan desde varios métodos
    private EditText ageEdit;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Enlazamos las vistas del layout (sin volver a escribir el tipo)
        ageEdit = findViewById(R.id.age_input);
        resultText = findViewById(R.id.result_text);
        Button button1 = findViewById(R.id.btn_calc);

        // Al pulsar el botón se calcula la edad
        button1.setOnClickListener(view -> calcularEdad());
    }

    private void calcularEdad() {
        // 1. Leemos el texto quitando los espacios
        String ageString = ageEdit.getText().toString().trim();

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
        ageEdit.setError(null);
        int dogAge = age * FACTOR_CANINO;
        resultText.setText(getString(R.string.tu_edad_format, dogAge));
    }

    private void mostrarError(String mensaje) {
        ageEdit.setError(mensaje);   // 1. Mensaje en el EditText
        ageEdit.requestFocus();      // 2. Foco en el EditText
        resultText.setText("");      // 3. Borramos el resultado anterior
    }
}
