package app;

import java.util.Scanner;
import modelo.Nodo;
import negocio.ArbolBinario;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static ArbolBinario<String> arbol = new ArbolBinario<>();

    public static void main(String[] args) {
        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1:
                    crearArbolInicial();
                    break;
                case 2:
                    System.out.println("=== ESTRUCTURA DEL ÁRBOL ===");
                    arbol.mostrarArbol();
                    break;
                case 3:
                    System.out.println("Cantidad de nodos: " + arbol.contarNodos());
                    break;
                case 4:
                    System.out.println("Cantidad de hojas: " + arbol.contarHojas());
                    break;
                case 5:
                    System.out.println("Altura en aristas: " + arbol.altura());
                    break;
                case 6:
                    consultarGrado();
                    break;
                case 7:
                    consultarHoja();
                    break;
                case 8:
                    System.out.println("Saliendo del programa. ¡Hasta pronto!");
                    continuar = false;
                    break;
                case 9:
                    ejecutarCasosDePrueba();
                    break;
                default:
                    System.out.println("Opción no válida. Elija un número del 1 al 9.");
            }
            System.out.println();
        }
    }

    private static void mostrarMenu() {
        System.out.println("========== ORGANIZACIÓN DE DEPARTAMENTOS ==========");
        System.out.println("1. Crear el árbol inicial");
        System.out.println("2. Visualizar el árbol");
        System.out.println("3. Consultar el número de nodos");
        System.out.println("4. Consultar el número de hojas");
        System.out.println("5. Consultar la altura");
        System.out.println("6. Consultar el grado de un nodo");
        System.out.println("7. Verificar si un nodo es hoja");
        System.out.println("8. Salir");
        System.out.println("9. Ejecutar casos de prueba obligatorios");
    }

    private static void crearArbolInicial() {
        arbol = construirArbolInicial();
        System.out.println("Árbol inicial creado correctamente.");
    }

    private static ArbolBinario<String> construirArbolInicial() {
        ArbolBinario<String> nuevoArbol = new ArbolBinario<>();
        Nodo<String> universidad = nuevoArbol.crearRaiz("UNIVERSIDAD");
        Nodo<String> fisei = nuevoArbol.agregarIzquierdo(universidad, "FISEI");
        Nodo<String> dide = nuevoArbol.agregarDerecho(universidad, "DIDE");
        Nodo<String> software = nuevoArbol.agregarIzquierdo(fisei, "SOFTWARE");
        nuevoArbol.agregarDerecho(fisei, "TI");
        nuevoArbol.agregarIzquierdo(dide, "INVESTIGACIÓN");
        nuevoArbol.agregarDerecho(dide, "VINCULACIÓN");
        nuevoArbol.agregarIzquierdo(software, "NIVEL1");
        nuevoArbol.agregarDerecho(software, "NIVEL3");
        return nuevoArbol;
    }

    private static void ejecutarCasosDePrueba() {
        System.out.println("=== CASOS DE PRUEBA OBLIGATORIOS ===");

        ArbolBinario<String> vacio = new ArbolBinario<>();
        System.out.println("[Caso 1] Árbol vacío");
        System.out.println("esVacio(): " + vacio.esVacio());
        System.out.println("nodos: " + vacio.contarNodos() + " (esperado 0)");
        System.out.println("altura: " + vacio.altura() + " (esperado -1)");

        ArbolBinario<String> unNodo = new ArbolBinario<>();
        unNodo.crearRaiz("PRUEBA");
        System.out.println("[Caso 2] Árbol con un solo nodo");
        System.out.println("nodos: " + unNodo.contarNodos() + " (esperado 1)");
        System.out.println("hojas: " + unNodo.contarHojas() + " (esperado 1)");
        System.out.println("altura: " + unNodo.altura() + " (esperado 0)");

        ArbolBinario<String> arbolPrueba = construirArbolInicial();
        System.out.println("[Caso 3] Árbol inicial");
        arbolPrueba.mostrarArbol();
        System.out.println("nodos: " + arbolPrueba.contarNodos() + " (esperado 9)");
        System.out.println("hojas: " + arbolPrueba.contarHojas() + " (esperado 5)");
        System.out.println("altura: " + arbolPrueba.altura() + " (esperado 3)");

        System.out.println("[Caso 4] Intento de insertar un hijo en una posición ocupada");
        Nodo<String> fisei = arbolPrueba.buscar("FISEI");
        int nodosAntes = arbolPrueba.contarNodos();
        try {
            arbolPrueba.agregarIzquierdo(fisei, "NUEVO");
            System.out.println("ERROR: la inserción debió ser rechazada.");
        } catch (IllegalStateException e) {
            System.out.println("Inserción rechazada: " + e.getMessage());
        }
        System.out.println("nodos tras el intento: " + arbolPrueba.contarNodos() + " (sin cambios: "
                + (nodosAntes == arbolPrueba.contarNodos()) + ")");

        System.out.println("[Caso 5] Grado de FISEI y SOFTWARE");
        System.out.println("grado(FISEI): " + fisei.grado() + " (esperado 2)");
        System.out.println("grado(SOFTWARE): " + arbolPrueba.buscar("SOFTWARE").grado() + " (esperado 2)");
    }

    private static void consultarGrado() {
        Nodo<String> nodo = buscarNodoSolicitado();
        if (nodo != null) {
            System.out.println("El grado de '" + nodo.getDato() + "' es: " + nodo.grado());
        }
    }

    private static void consultarHoja() {
        Nodo<String> nodo = buscarNodoSolicitado();
        if (nodo != null) {
            System.out.println("'" + nodo.getDato() + "' " + (nodo.esHoja() ? "SÍ" : "NO") + " es una hoja.");
        }
    }

    private static Nodo<String> buscarNodoSolicitado() {
        String nombre = leerTexto("Ingrese el nombre del nodo: ");
        Nodo<String> nodo = arbol.buscar(nombre);
        if (nodo == null) {
            System.out.println("No existe un nodo llamado '" + nombre + "'.");
        }
        return nodo;
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = SCANNER.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return SCANNER.nextLine().trim();
    }
}
