package test;

import modelo.Nodo;
import negocio.ArbolBinario;

public class PruebasArbolBinario {
    public static void main(String[] args) {
        probarArbolVacio();
        System.out.println("[Caso 1] Árbol vacío: esVacio=true; nodos=0; altura=-1");
        probarUnNodo();
        System.out.println("[Caso 2] Un nodo: nodos=1; hojas=1; altura=0");
        ArbolBinario<String> arbol = construirArbolUniversitario();
        probarArbolUniversitario(arbol);
        System.out.println("[Caso 3] Árbol inicial: nodos=9; hojas=5; altura=3");
        probarInsercionOcupada(arbol);
        System.out.println("[Caso 4] Inserción ocupada: rechazada; nodos=9 sin cambios");
        probarGrados(arbol);
        System.out.println("[Caso 5] Grado: FISEI=2; SOFTWARE=2");
        System.out.println("Todas las pruebas pasaron correctamente.");
    }

    private static void probarArbolVacio() {
        ArbolBinario<String> arbol = new ArbolBinario<>();
        verificar(arbol.esVacio(), "El árbol nuevo debe estar vacío.");
        verificar(arbol.contarNodos() == 0, "El árbol vacío debe tener cero nodos.");
        verificar(arbol.contarHojas() == 0, "El árbol vacío debe tener cero hojas.");
        verificar(arbol.altura() == -1, "La altura del árbol vacío debe ser -1.");
    }

    private static void probarUnNodo() {
        ArbolBinario<String> arbol = new ArbolBinario<>();
        Nodo<String> raiz = arbol.crearRaiz("PRUEBA");
        verificar(!arbol.esVacio(), "El árbol con raíz no debe estar vacío.");
        verificar(arbol.contarNodos() == 1, "El árbol debe tener un nodo.");
        verificar(arbol.contarHojas() == 1, "La raíz aislada debe ser una hoja.");
        verificar(arbol.altura() == 0, "La altura del árbol unitario debe ser cero.");
        verificar(raiz.esHoja(), "La raíz aislada debe reportarse como hoja.");
        verificar(raiz.grado() == 0, "La raíz aislada debe tener grado cero.");
    }

    private static ArbolBinario<String> construirArbolUniversitario() {
        ArbolBinario<String> arbol = new ArbolBinario<>();
        Nodo<String> universidad = arbol.crearRaiz("UNIVERSIDAD");
        Nodo<String> fisei = arbol.agregarIzquierdo(universidad, "FISEI");
        Nodo<String> dide = arbol.agregarDerecho(universidad, "DIDE");
        Nodo<String> software = arbol.agregarIzquierdo(fisei, "SOFTWARE");
        arbol.agregarDerecho(fisei, "TI");
        arbol.agregarIzquierdo(dide, "INVEST.");
        arbol.agregarDerecho(dide, "VINCULACIÓN");
        arbol.agregarIzquierdo(software, "NIVEL1");
        arbol.agregarDerecho(software, "NIVEL3");
        return arbol;
    }

    private static void probarArbolUniversitario(ArbolBinario<String> arbol) {
        verificar(arbol.contarNodos() == 9, "El árbol universitario debe tener nueve nodos.");
        verificar(arbol.contarHojas() == 5, "El árbol universitario debe tener cinco hojas.");
        verificar(arbol.altura() == 3, "El árbol universitario debe tener altura tres.");
        verificar(arbol.buscar("NIVEL1").esHoja(), "NIVEL1 debe ser una hoja.");
        verificar(arbol.buscar("NO EXISTE") == null, "Una búsqueda ausente debe devolver null.");
    }

    private static void probarInsercionOcupada(ArbolBinario<String> arbol) {
        Nodo<String> fisei = arbol.buscar("FISEI");
        int nodosAntes = arbol.contarNodos();
        boolean rechazada = false;
        try {
            arbol.agregarIzquierdo(fisei, "DUPLICADO");
        } catch (IllegalStateException e) {
            rechazada = true;
        }
        verificar(rechazada, "Debe rechazarse la inserción en una posición ocupada.");
        verificar(arbol.contarNodos() == nodosAntes, "El intento rechazado no debe modificar el árbol.");
    }

    private static void probarGrados(ArbolBinario<String> arbol) {
        verificar(arbol.buscar("FISEI").grado() == 2, "FISEI debe tener grado dos.");
        verificar(arbol.buscar("SOFTWARE").grado() == 2, "SOFTWARE debe tener grado dos.");
    }

    private static void verificar(boolean condicion, String mensaje) {
        if (!condicion) {
            throw new AssertionError(mensaje);
        }
    }
}