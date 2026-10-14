# Árbol binario de departamentos universitarios

## Problema

La aplicación representa la organización jerárquica de dependencias universitarias mediante un árbol binario. Cada nodo almacena el nombre de un departamento y puede tener como máximo un hijo izquierdo y uno derecho.

## Estructura del árbol

```text
UNIVERSIDAD
|-- FISEI
|   |-- SOFTWARE
|   |   |-- NIVEL1
|   |   \\-- NIVEL3
|   \\-- TI
\\-- DIDE
    |-- INVEST.
    \\-- VINCULACIÓN
```

El árbol contiene 9 nodos, 5 hojas y tiene altura 3, medida en aristas. FISEI y SOFTWARE tienen grado 2.

## Organización

```text
src/
├── modelo/Nodo.java
├── negocio/ArbolBinario.java
└── app/Main.java
```

`Nodo<T>` contiene el dato y sus referencias a los hijos. `ArbolBinario<T>` implementa inserción, búsqueda, visualización y conteos recursivos. `Main` ofrece el menú interactivo solicitado.

## Requisitos

Se requiere un JDK 8 o posterior y `javac`/`java` disponibles en la terminal. No se utilizan librerías externas ni las estructuras prohibidas en la consigna.

## Compilar y ejecutar

Desde la carpeta raíz del proyecto:

```powershell
javac -encoding UTF-8 -d out src/modelo/Nodo.java src/negocio/ArbolBinario.java src/app/Main.java
java -cp out app.Main
```

En el menú, seleccione `1` para crear el árbol inicial. Esta opción también muestra los casos de prueba obligatorios. Después puede visualizarlo y consultar sus datos; para buscar un nodo, escriba su nombre exactamente como aparece en el árbol.

## Resultados esperados

- Árbol vacío: 0 nodos y altura -1.
- Árbol con un nodo: 1 nodo, 1 hoja y altura 0.
- Árbol inicial: 9 nodos, 5 hojas y altura 3.
- Grado de FISEI: 2.
- Grado de SOFTWARE: 2.
- Insertar un hijo izquierdo adicional en FISEI: operación rechazada porque la posición ya está ocupada.

## Reflexión

1. La altura se define por el camino más largo desde la raíz hasta una hoja porque ese camino representa la máxima cantidad de niveles que se deben recorrer para llegar al extremo más profundo. Se mide en aristas; por eso un árbol con una sola raíz tiene altura 0.
2. Una lista enlazada organiza sus elementos en una secuencia y normalmente se recorre nodo por nodo. Un árbol binario organiza los datos jerárquicamente, con hasta dos referencias por nodo; según su forma, puede ofrecer caminos de búsqueda más cortos, aunque esta implementación no impone un orden de búsqueda.
3. En SmartETL, un árbol binario podría representar reglas jerárquicas de transformación o decisiones para clasificar registros. Cada nodo almacenaría una condición y sus ramas conducirían a la siguiente regla según el resultado. Para buscar por clave, sería necesario definir un criterio de orden o usar una estructura de árbol de búsqueda adecuada.

## Entrega

Antes de entregar, publique el proyecto en el repositorio individual solicitado por la asignatura, agregue capturas propias de la ejecución y prepare el PDF con el enlace, las evidencias y las respuestas de reflexión. Registre al menos tres commits significativos durante su propio proceso de desarrollo; no sustituya el historial real por commits creados de una sola vez.
