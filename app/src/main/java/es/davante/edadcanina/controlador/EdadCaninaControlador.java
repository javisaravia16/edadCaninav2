package es.davante.edadcanina.controlador;

import es.davante.edadcanina.modelo.EdadCaninaModelo;

/**
 * CONTROLADOR: recibe lo que hace el usuario (los clics) y se lo traduce al
 * modelo en forma de órdenes. Nada más.
 *
 * No tiene ninguna referencia a vistas, y no calcula ni pinta: es corto a
 * propósito, y eso es buena señal (si crece, está haciendo trabajo de otro).
 */
public class EdadCaninaControlador {

    private final EdadCaninaModelo modelo;

    public EdadCaninaControlador(EdadCaninaModelo modelo) {
        this.modelo = modelo;
    }

    // Un método público por cada acción del usuario; el XML los llama desde onClick

    public void onCalcularClick() {
        modelo.calcular();
    }

    public void onLimpiarClick() {
        modelo.limpiar();
    }
}
