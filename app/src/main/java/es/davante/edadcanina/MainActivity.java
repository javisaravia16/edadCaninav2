package es.davante.edadcanina;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import es.davante.edadcanina.controlador.EdadCaninaControlador;
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

        // El controlador necesita el modelo para poder darle órdenes
        EdadCaninaControlador controlador = new EdadCaninaControlador(modelo);

        // Entrego las dos piezas a la vista: el XML lee del modelo y manda los
        // clics al controlador. Aquí ya no hay ningún listener.
        binding.setModelo(modelo);
        binding.setControlador(controlador);
    }
}
