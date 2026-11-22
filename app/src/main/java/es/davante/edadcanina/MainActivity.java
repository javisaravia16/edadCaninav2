package es.davante.edadcanina;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import es.davante.edadcanina.databinding.ActivityMainBinding;
import es.davante.edadcanina.modelo.EdadCaninaModelo;

public class MainActivity extends AppCompatActivity {

    // Los datos y las reglas están en el modelo
    private EdadCaninaModelo modelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Infla el layout y devuelve un objeto con un campo por cada vista que
        // tiene id, así no hace falta buscar las vistas una a una
        ActivityMainBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_main);
        modelo = new EdadCaninaModelo();

        // Entrego el modelo a la vista: desde aquí el XML lee y escribe en él
        binding.setModelo(modelo);

        // Al pulsar el botón solo le pido al modelo que calcule. El resultado y
        // el error se pintan solos desde el XML (app:errorValidacion).
        binding.btnCalc.setOnClickListener(view -> modelo.calcular());
    }
}
