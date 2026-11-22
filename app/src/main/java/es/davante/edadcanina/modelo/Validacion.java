package es.davante.edadcanina.modelo;

/**
 * Resultado de validar lo que ha escrito el usuario.
 * Es un enum y no un texto a propósito: el modelo solo dice QUÉ ha pasado y la
 * vista decide cómo contárselo al usuario (así el idioma no afecta al modelo).
 */
public enum Validacion {
    SIN_CALCULAR,    // todavía no se ha pulsado Calcular (o el texto ha cambiado después)
    OK,              // la edad es correcta y ya hay resultado
    VACIO,           // el campo está vacío
    NO_ES_NUMERO,    // no se puede convertir a un número entero
    FUERA_DE_RANGO   // es un número, pero no está entre EDAD_MINIMA y EDAD_MAXIMA
}
