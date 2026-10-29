# 🐶 EdadCanina · Versión 2

Esta app calcula cuántos años tendría un perro si fuera una persona. Para eso multiplica su edad por 7.

**Autor:** Francisco Javier Saravia Ogazón

## Qué fallaba en la versión 1

La primera versión funcionaba bien si escribías un número normal, pero si dejabas el campo vacío o ponías letras, decimales o un número muy largo, la app se cerraba de golpe.

También daba resultados sin sentido. Con un 0 decía que el perro tendría 0 años, con un -4 salía -28, y con un número enorme salía un resultado negativo porque el número era demasiado grande y "se pasaba de la raya".

## Qué he hecho en la versión 2

- He arreglado algunos fallos pequeños que ya traía la app, como una falta de ortografía ("fuero" en vez de "fuera") y un símbolo que sobraba en el diseño de la pantalla.
- He puesto todos los textos de la app juntos en un mismo archivo, en vez de tenerlos repartidos.
- Ahora al escribir la edad sale el teclado de números y solo se pueden poner dos cifras.
- Antes de hacer la cuenta, la app comprueba lo que ha escrito el usuario. Si algo no está bien, sale un aviso en rojo en vez de cerrarse.
- He ordenado el código para que sea más fácil de leer, y los números importantes (el 7 y los límites de edad) tienen ahora un nombre que explica qué son.

## Los avisos que puede mostrar la app

- Si no escribes nada: "Introduce la edad de tu perro".
- Si lo que escribes no es un número entero: "Introduce un número entero válido".
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
