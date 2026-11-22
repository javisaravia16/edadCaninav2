package es.davante.edadcanina;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import es.davante.edadcanina.controlador.EdadCaninaControlador;
import es.davante.edadcanina.databinding.ActivityMainBinding;
import es.davante.edadcanina.modelo.EdadCaninaModelo;
import es.davante.edadcanina.modelo.Validacion;

/**
 * Solo ensambla las tres piezas (modelo, vista y controlador) y se ocupa del
 * ciclo de vida de Android: guardar y recuperar el estado al girar la pantalla.
 * No tiene lógica de la app.
 */
public class MainActivity extends AppCompatActivity {

    // Claves del Bundle: constantes para no equivocarme al escribir dos veces
    // el mismo texto (una al guardar y otra al leer)
    private static final String CLAVE_EDAD_TEXTO = "edad_texto";
    private static final String CLAVE_CALCULADO = "calculado";

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

        // Al girar, Android crea una Activity nueva y con ella un modelo vacío.
        // Restauro ANTES de entregar el modelo a la vista para que nazca ya con
        // los datos buenos y no se pinte primero vacía.
        if (savedInstanceState != null) {
            restaurarEstado(savedInstanceState);
        }

        // El controlador necesita el modelo para poder darle órdenes
        EdadCaninaControlador controlador = new EdadCaninaControlador(modelo);

        // Entrego las dos piezas a la vista: el XML lee del modelo y manda los
        // clics al controlador. Aquí ya no hay ningún listener.
        binding.setModelo(modelo);
        binding.setControlador(controlador);
    }

    // Android lo llama justo antes de destruir la Activity (al girar, cambiar a
    // modo oscuro, etc.). Lo que guarde aquí me llegará de nuevo en onCreate.
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Guardo lo mínimo para reconstruir el modelo: el texto y si se había
        // calculado. No hace falta guardar la edad humana ni el error, porque el
        // cálculo da siempre lo mismo para la misma entrada: basta con repetirlo.
        outState.putString(CLAVE_EDAD_TEXTO, modelo.getEdadTexto());
        outState.putBoolean(CLAVE_CALCULADO,
                modelo.getValidacion() != Validacion.SIN_CALCULAR);
    }

    private void restaurarEstado(Bundle estado) {
        modelo.setEdadTexto(estado.getString(CLAVE_EDAD_TEXTO, ""));
        if (estado.getBoolean(CLAVE_CALCULADO, false)) {
            modelo.calcular(); // vuelve a salir el mismo resultado (o el mismo error)
        }
    }
}
