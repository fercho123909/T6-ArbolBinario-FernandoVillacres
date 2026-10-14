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
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida. Elija un número del 1 al 8.");
            }
            System.out.println();
        }
        System.out.println("Programa finalizado.");
    }

    private static void mostrarMenu() {
        System.out.println("=== Árbol binario de departamentos ===");
        System.out.println("1. Crear/reiniciar árbol inicial");
        System.out.println("2. Visualizar árbol");
        System.out.println("3. Consultar número de nodos");
        System.out.println("4. Consultar número de hojas");
        System.out.println("5. Consultar altura");
        System.out.println("6. Consultar grado de un nodo");
        System.out.println("7. Verificar si un nodo es hoja");
        System.out.println("8. Salir");
    }

    private static void crearArbolInicial() {
        arbol = new ArbolBinario<>();
        Nodo<String> universidad = arbol.crearRaiz("UNIVERSIDAD");
        Nodo<String> fisei = arbol.agregarIzquierdo(universidad, "FISEI");
        Nodo<String> dide = arbol.agregarDerecho(universidad, "DIDE");
        Nodo<String> software = arbol.agregarIzquierdo(fisei, "SOFTWARE");
        arbol.agregarDerecho(fisei, "TI");
        arbol.agregarIzquierdo(dide, "INVEST.");
        arbol.agregarDerecho(dide, "VINCULACIÓN");
        arbol.agregarIzquierdo(software, "NIVEL1");
        arbol.agregarDerecho(software, "NIVEL3");

        System.out.println("Árbol inicial creado.");
        mostrarCasosDePrueba(fisei);
    }

    private static void mostrarCasosDePrueba(Nodo<String> fisei) {
        ArbolBinario<String> vacio = new ArbolBinario<>();
        System.out.println("\n-- Casos de prueba obligatorios --");
        System.out.println("Árbol vacío: " + vacio.contarNodos() + " nodos, altura " + vacio.altura());

        ArbolBinario<String> unNodo = new ArbolBinario<>();
        unNodo.crearRaiz("PRUEBA");
        System.out.println("Un nodo: " + unNodo.contarNodos() + " nodo, " + unNodo.contarHojas()
                + " hoja, altura " + unNodo.altura());
        System.out.println("Árbol inicial: " + arbol.contarNodos() + " nodos, " + arbol.contarHojas()
                + " hojas, altura " + arbol.altura());
        System.out.println("Grado de FISEI: " + fisei.grado());
        System.out.println("Grado de SOFTWARE: " + arbol.buscar("SOFTWARE").grado());

        try {
            arbol.agregarIzquierdo(fisei, "NUEVO");
            System.out.println("ERROR: se permitió insertar en una posición ocupada.");
        } catch (IllegalStateException e) {
            System.out.println("Inserción en posición ocupada: rechazada correctamente (" + e.getMessage() + ")");
        }
    }

    private static void consultarGrado() {
        Nodo<String> nodo = buscarNodoSolicitado();
        if (nodo != null) {
            System.out.println("Grado de " + nodo.getDato() + ": " + nodo.grado());
        }
    }

    private static void consultarHoja() {
        Nodo<String> nodo = buscarNodoSolicitado();
        if (nodo != null) {
            System.out.println(nodo.getDato() + (nodo.esHoja() ? " es una hoja." : " no es una hoja."));
        }
    }

    private static Nodo<String> buscarNodoSolicitado() {
        String nombre = leerTexto("Nombre exacto del departamento: ");
        Nodo<String> nodo = arbol.buscar(nombre);
        if (nodo == null) {
            System.out.println("No se encontró el nodo \"" + nombre + "\".");
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
