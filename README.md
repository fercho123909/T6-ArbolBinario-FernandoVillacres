# Implementación de un árbol binario

En esta práctica hice un programa de consola para representar la organización de los departamentos universitarios. Usé un árbol binario porque cada dependencia puede tener como máximo dos hijos.

La práctica es individual. Usé Java, Visual Studio Code y GitHub; también utilicé inteligencia artificial como apoyo para revisar y documentar, y comprobé los resultados ejecutando el programa y sus pruebas.

Repositorio: <https://github.com/fercho123909/T6-ArbolBinario-FernandoVillacres>

## Árbol inicial

```text
UNIVERSIDAD
|-- [I] FISEI
|   |-- [I] SOFTWARE
|   |   |-- [I] NIVEL1
|   |   `-- [D] NIVEL3
|   `-- [D] TI
`-- [D] DIDE
    |-- [I] INVESTIGACIÓN
    `-- [D] VINCULACIÓN
```

`[I]` indica un hijo izquierdo y `[D]` uno derecho. El árbol tiene 9 nodos, 5 hojas y altura 3, medida en aristas.

## Cómo organicé el código

- [Nodo.java](src/modelo/Nodo.java): almacena el dato y los dos hijos. Incluye getters, setters, `esHoja()` y `grado()`.
- [ArbolBinario.java](src/negocio/ArbolBinario.java): crea la raíz, agrega hijos, busca, muestra el árbol y calcula recursivamente nodos, hojas y altura.
- [Main.java](src/app/Main.java): contiene el menú de `Scanner` y la opción 9 para ejecutar los cinco casos obligatorios.
- [PruebasArbolBinario.java](test/PruebasArbolBinario.java): ejecuta pruebas adicionales y comprueba los resultados con aserciones.

No usé las colecciones prohibidas en la práctica. Si intento insertar un hijo donde ya existe uno, se lanza `IllegalStateException` y el árbol queda sin cambios.

## Cómo compilo y ejecuto

Necesito Java 8 o posterior. Desde la carpeta principal del proyecto ejecuto:

```powershell
javac -encoding UTF-8 -d out src/modelo/Nodo.java src/negocio/ArbolBinario.java src/app/Main.java
java -cp out app.Main
```

En el menú, la opción 1 crea el árbol. Las opciones 2 a 7 permiten visualizarlo y consultar sus datos; para buscar un nodo escribo su nombre completo. La opción 9 ejecuta los casos obligatorios.

Para correr también las pruebas automatizadas:

```powershell
javac -encoding UTF-8 -d out src/modelo/Nodo.java src/negocio/ArbolBinario.java test/PruebasArbolBinario.java
java -cp out test.PruebasArbolBinario
```

## Resultados

| Caso | Resultado esperado | Resultado obtenido |
|---|---|---|
| Árbol vacío | 0 nodos, altura -1 | 0 nodos, altura -1 |
| Árbol con una raíz | 1 nodo, 1 hoja, altura 0 | 1 nodo, 1 hoja, altura 0 |
| Árbol universitario | 9 nodos, 5 hojas, altura 3 | 9 nodos, 5 hojas, altura 3 |
| Hijo izquierdo de FISEI ocupado | Inserción rechazada; árbol intacto | Inserción rechazada; quedan 9 nodos |
| Grado de FISEI y SOFTWARE | 2 y 2 | 2 y 2 |

También consulté que `TI` es hoja, que `SOFTWARE` no es hoja y que una búsqueda sin coincidencia informa que el nodo no existe.

## Complejidad

Aquí `n` es el número de nodos y `h` la altura. Los conteos, la altura y la búsqueda usan recursión; por eso ocupan espacio adicional en la pila proporcional a `h`.

| Operación | Tiempo | Espacio adicional |
|---|---:|---:|
| `contarNodos`, `contarHojas`, `altura` | O(n) | O(h) |
| `buscar` | O(n) en el peor caso | O(h) |
| `agregarIzquierdo`, `agregarDerecho` | O(1) | O(1) |
| `esVacio`, `esHoja`, `grado` | O(1) | O(1) |
| `mostrarArbol` | O(n) | O(h) |

La búsqueda no es O(log n), porque este árbol representa una jerarquía y no está ordenado como un árbol binario de búsqueda.

## Cómo cubrí la rúbrica

| Criterio | Dónde se puede revisar |
|---|---|
| Clase `Nodo<T>`, hoja, grado y accesores | [Nodo.java](src/modelo/Nodo.java) |
| Construcción del árbol e inserciones protegidas | [ArbolBinario.java](src/negocio/ArbolBinario.java) y caso 4 del menú |
| Conteos y altura recursivos | Métodos recursivos de [ArbolBinario.java](src/negocio/ArbolBinario.java) y las pruebas |
| Menú, visualización y cinco casos | [Main.java](src/app/Main.java), opción 9, y capturas |
| Repositorio, README e historial | Este archivo y el historial público de GitHub |
| Evidencias y reflexión | [Informe.pdf](evidencias/Informe.pdf) y las capturas de `evidencias/` |

## Lo que aprendí

1. La altura depende del camino más largo desde la raíz hasta una hoja porque mide cuántas aristas hacen falta para llegar al nivel más profundo. En este árbol, `UNIVERSIDAD → FISEI → SOFTWARE → NIVEL1` tiene 3 aristas; la rama más corta no cambia la altura.
2. Una lista enlazada guarda elementos en secuencia y normalmente se recorre nodo por nodo. El árbol binario organiza relaciones padre-hijo y permite representar jerarquías. Si se degenera en una sola rama, una búsqueda también puede requerir O(n); solo un árbol ordenado y equilibrado puede acercarse a O(log n).
3. En SmartETL podría organizar reglas de limpieza como un árbol de decisión, donde cada condición dirige un registro por una rama. También podría indexar registros por clave, pero para eso necesitaría un árbol de búsqueda equilibrado; el árbol de esta práctica no ordena los datos.

## Conclusiones y recomendaciones

Separar el nodo, la lógica del árbol y el menú me ayudó a organizar el programa. Las operaciones recursivas resolvieron el conteo y la altura con casos base claros, y las pruebas confirmaron los límites de altura y que una inserción ocupada no modifica la estructura.

Para mantener el árbol correcto, conviene probar siempre el árbol vacío y el de un nodo, y rechazar las inserciones en posiciones ocupadas. Si SmartETL necesita buscar muchos registros, sería mejor evaluar una estructura ordenada y equilibrada, como AVL o rojo-negro.

## Evidencias de esta ejecución

- [Captura del menú y el árbol](evidencias/captura_ejecucion.png)
- [Salida real de los cinco casos obligatorios](evidencias/salida_casos.txt)
- [Vista visual de la salida de pruebas](evidencias/salida_casos.png), generada desde la transcripción real; no es una captura directa.
- [Script para volver a ejecutar los cinco casos](evidencias/demo_pruebas.cmd)
- [Salida de consola completa](evidencias/salida_consola.txt)
- [Informe para entregar](evidencias/Informe.pdf)
- [Código que genera el informe](evidencias/generar_informe.py)
- [Logotipo institucional usado en el informe](evidencias/logo_uta.png)

Para volver a ejecutar los cinco casos uso `evidencias/demo_pruebas.cmd`. El archivo `salida_casos.txt` conserva el resultado completo de la ejecución, y el informe también incluye una vista visual claramente identificada como generada desde esa transcripción.

## Referencias

- T. H. Cormen, C. E. Leiserson, R. L. Rivest y C. Stein, *Introduction to Algorithms*, 4.ª ed., MIT Press, 2022.
- M. A. Weiss, *Data Structures and Algorithm Analysis in Java*, 3.ª ed., Pearson, 2012.
- Oracle, [Java Platform, Standard Edition Documentation](https://docs.oracle.com/en/java/).
