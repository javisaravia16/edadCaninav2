#  EdadCaninaV2

Esta aplicación Android calcula cuántos años tendría tu perro si fuera una persona. Multiplica la edad del perro por 7.

**Javier Saravia Ogazón**  

## ¿Qué fallaba en la versión 1?

La primera versión funcionaba bien si escribías un número normal, pero si dejabas el campo vacío o ponías letras, decimales o un número muy largo, la app se cerraba de golpe.

También daba resultados sin sentido. Con un 0 decía que el perro tendría 0 años, con un -4 salía -28, y con un número enorme salía un resultado negativo porque el número era demasiado grande y "se pasaba de la raya".

## ¿Qué he hecho en la versión 2?

- He arreglado algunos fallos pequeños que ya traía la app, como una falta de ortografía ("fuero" en vez de "fuera") y un símbolo que sobraba en el diseño de la pantalla.
- He puesto todos los textos de la app juntos en un mismo archivo, en vez de tenerlos repartidos.
- Ahora al escribir la edad sale el teclado de números y solo se pueden poner dos cifras.
- Antes de hacer la cuenta, la app comprueba lo que ha escrito el usuario. Si algo no está bien, sale un aviso en rojo en vez de cerrarse.
- He ordenado el código para que sea más fácil de leer, y los números importantes (el 7 y los límites de edad) tienen ahora un nombre que explica qué son.

## Los avisos que puede mostrar la app

- Si no escribes nada: "Introduce la edad de tu perro".
- Si lo que escribes no es un número entero: "Introduce un número".
- Si la edad es menor de 1 o mayor de 30: "La edad debe estar entre 1 y 30 años".

Si todo está bien, la app muestra el resultado. Por ejemplo, con 1 dice que tendría 7 años y con 30 dice que tendría 210.

## Pruebas

He probado la app dejando el campo vacío y con 0, 31, 1 y 30. También he quitado un momento el teclado de números para probar con letras. En ningún caso se ha cerrado y siempre ha salido el aviso o el resultado correcto.

## Respuestas a las preguntas

**¿Qué significan `%1$d` y `%2$d`?**
Son huecos dentro de un texto que luego se rellenan con números. El primero se rellena con el primer número que se le pasa (el 1) y el segundo con el segundo número (el 30).

**¿Qué pasaría si volviera a declarar el EditText dentro de onCreate()?**
Se crearía una copia que solo sirve dentro de ese trozo de código, y el resto de la app no la vería. Al pulsar el botón la app se cerraría.

**¿Por qué limitar la edad a 30 evita también el número gigante?**
Porque como mucho la cuenta es 30 × 7 = 210, que es un número pequeño y no da problemas.

**¿Cómo se comprueba el aviso de "número no válido" si el teclado solo deja poner números?**
Quitando un momento el teclado de números del diseño para poder escribir letras. Después se vuelve a poner.

## Dónde está el trabajo

Todo está en la rama `v2-validacion`. La rama `main` se ha quedado como estaba, con la versión 1.

---

## EdadCaninaMVC

Esta es la versión 3 de la app (rama `v3-mvc`). Hace lo mismo que la versión 2: calcula la edad humana del perro y avisa si lo que se ha escrito está mal. Lo único nuevo en pantalla es un botón **Limpiar**. Lo que ha cambiado es cómo está ordenado el código: ahora usa **DataBinding** y el patrón **MVC** (Modelo, Vista, Controlador).

Al terminar, `MainActivity` ya no tiene ningún `findViewById`, ningún `setText` ni ningún `setOnClickListener`.

El `applicationId` es `es.davante.edadcanina.mvc`, así que esta app se puede tener instalada a la vez que la versión 2.

### Cómo está organizado

- `modelo/`: `EdadCaninaModelo` y `Validacion`. Guardan los datos, validan, calculan y avisan cuando algo cambia. No saben nada de pantallas ni tienen textos para el usuario.
- `vista/`: `AdaptadoresVista`, que inventa el atributo `app:errorValidacion` del XML. Junto con `activity_main.xml` forma la vista, que se pinta leyendo el modelo.
- `controlador/`: `EdadCaninaControlador`. Recibe los clics y le da órdenes al modelo (calcular y limpiar). No tiene ninguna vista dentro.
- `MainActivity`: crea las tres piezas, las conecta y guarda el estado cuando se gira la pantalla. Nada más.

### Los siete pasos

Hay un commit por paso y cada uno tiene su etiqueta (`paso-1` ... `paso-7`):

1. Activar DataBinding en `build.gradle.kts` (y cambiar el `applicationId`).
2. Envolver el layout en `<layout>` y usar el binding en lugar de `findViewById`.
3. Crear el modelo (`EdadCaninaModelo` y `Validacion`) y probarlo con JUnit.
4. Hacer el modelo observable y enlazar la vista con `@{}` y `@={}`.
5. Mostrar los errores desde el XML con un `@BindingAdapter` propio.
6. Crear el controlador, añadir el botón Limpiar y enlazar los clics desde el XML.
7. Guardar el estado con `onSaveInstanceState` para que el giro no borre el resultado.

Después hay un commit más con el reto que he elegido.

### Preguntas «Piensa» de cada paso

**Paso 1. ¿Ha aparecido ya alguna clase nueva?** No. DataBinding crea las clases al compilar a partir de los layouts que empiezan por `<layout>`, y en este paso todavía ninguno lo hace. Solo está activado.

**Paso 2. Si escribo mal un id, ¿cuándo me entero?** Ahora me entero al compilar, porque el campo no existe en el binding y da error. Con `findViewById` compilaba bien y fallaba al ejecutar, cuando la vista salía `null`.

**Paso 3. Si la app tiene que estar en inglés, ¿toco el modelo?** No. El modelo solo dice qué ha pasado (`VACIO`, `OK`...) y los textos están en `strings.xml` y en el adaptador de la vista. Solo habría que traducir los textos.

**Paso 4. ¿Dónde se corta la cadena gracias al `if`?** Si el modelo avisara siempre, pasaría esto: escribo una cifra, la vista se la pasa al modelo, el modelo avisa, la vista vuelve a escribir el texto, y así sin parar. Con el `if`, cuando la vista vuelve a escribir el mismo texto, el modelo ve que no ha cambiado y no avisa. Ahí se acaba.

**Paso 5. ¿Por qué el adaptador va en `vista` y no en `modelo`?** Porque pone un mensaje de error en un `EditText` y usa `getString`. Eso es cómo se ve algo, no una regla de la app, y el modelo no puede depender de Android.

**Paso 6. ¿Es un problema que el controlador tenga pocas líneas?** Es una buena señal. Solo traduce clics en órdenes. Si fuera largo, estaría haciendo el trabajo del modelo o de la vista.

**Paso 7. ¿Dónde podría vivir el modelo para no morir al girar?** En un `ViewModel`, que sobrevive al giro. Eso es lo que se verá con MVVM. Sin el paso 7, al girar el número escrito se queda (el `EditText` lo guarda solo, porque tiene id), pero el resultado se pierde, porque el modelo nuevo está vacío.

### Explícalo con tus palabras

**1. Qué diferencia hay entre `@{...}` y `@={...}`.**
`@{...}` va en un solo sentido: lee del modelo y lo enseña en la vista. `@={...}` va en los dos: además, lo que escribe el usuario llega solo al modelo. Uso `@={}` en el campo de la edad, porque ahí el usuario escribe, y `@{}` en el resultado, que solo se muestra.

**2. Qué ocurre, paso a paso, desde que pulsas Calcular hasta que aparece «42».**
1. Escribo `6`. Gracias a `@={}`, la vista llama a `modelo.setEdadTexto("6")`.
2. Pulso Calcular. El `onClick` del XML llama a `controlador.onCalcularClick()`.
3. El controlador le da la orden al modelo: `calcular()`.
4. El modelo valida (que no esté vacío, que sea un número y que esté entre 1 y 30) y calcula 6 × 7 = 42. Guarda la edad humana y la validación `OK`, y avisa de que han cambiado.
5. El binding estaba suscrito al modelo, así que repinta: el resultado pasa a ser visible y muestra «Si tu perro fuera humano tendría 42 años».

**3. Qué cambiarías para que el factor fuera 6 en vez de 7, y por qué solo tocas un fichero.**
Cambiaría `FACTOR_CANINO` en `EdadCaninaModelo`. Solo toco ese fichero de la app porque la regla del cálculo vive únicamente en el modelo: la vista, el controlador y la Activity no calculan nada. Las pruebas sí tendría que actualizarlas, porque tienen los resultados escritos (42, 210...).

### Reto elegido: casos raros

Qué hace la app con `05` y con `+5`:

- **`05` es válido.** Se entiende como el 5 de siempre, así que sale 35. Lo dejo pasar porque es una forma normal de escribirlo y el teclado numérico lo permite.
- **`+5` no es válido.** Aunque Java lo convertiría en 5, nadie escribe así una edad y el teclado numérico ni deja poner el `+`. Sale el aviso «Introduce un número». Para esto he añadido una comprobación en el modelo.
- **`00` da fuera de rango**, porque es el 0.

Las tres decisiones tienen su prueba en `EdadCaninaModeloTest`.

### Pruebas JUnit

Hay 29 pruebas en `app/src/test`, 25 del modelo y 4 del controlador. Se ejecutan sin emulador. Comprueban los cuatro errores, los límites (1 y 30), que el resultado se borre al cambiar el texto, que el modelo avise cuando cambia algo (y que no avise si no cambia), el botón Limpiar y los casos raros.

### Autoevaluación

- [x] La app hace lo mismo que la v2: calcula, valida los cuatro casos de error y no se cierra con ninguna entrada.
- [x] `MainActivity` solo crea las piezas, las conecta y guarda y restaura el estado.
- [x] El modelo no importa nada de `android.*` (solo `androidx.databinding`) y no contiene textos para el usuario.
- [x] El controlador no tiene referencias a vistas.
- [x] La vista usa `@={}` en la entrada, `@{}` en el resultado y un atributo propio para el error.
- [x] Al girar la pantalla no se pierde nada.
- [x] Hay más de 5 pruebas JUnit en verde.
- [x] Los comentarios explican por qué hago las cosas.
- [x] Un commit por paso, con su etiqueta.
