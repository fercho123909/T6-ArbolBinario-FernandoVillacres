package negocio;

import java.util.Objects;
import modelo.Nodo;

public class ArbolBinario<T> {
    private Nodo<T> raiz;

    public Nodo<T> crearRaiz(T dato) {
        if (raiz != null) {
            throw new IllegalStateException("El árbol ya tiene una raíz.");
        }
        raiz = new Nodo<>(dato);
        return raiz;
    }

    public Nodo<T> agregarIzquierdo(Nodo<T> padre, T dato) {
        validarPadre(padre);
        if (padre.getIzquierdo() != null) {
            throw new IllegalStateException("La posición del hijo izquierdo ya está ocupada.");
        }
        Nodo<T> hijo = new Nodo<>(dato);
        padre.setIzquierdo(hijo);
        return hijo;
    }

    public Nodo<T> agregarDerecho(Nodo<T> padre, T dato) {
        validarPadre(padre);
        if (padre.getDerecho() != null) {
            throw new IllegalStateException("La posición del hijo derecho ya está ocupada.");
        }
        Nodo<T> hijo = new Nodo<>(dato);
        padre.setDerecho(hijo);
        return hijo;
    }

    public Nodo<T> getRaiz() {
        return raiz;
    }

    public int contarNodos() {
        return contarNodos(raiz);
    }

    private int contarNodos(Nodo<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + contarNodos(nodo.getIzquierdo()) + contarNodos(nodo.getDerecho());
    }

    public int contarHojas() {
        return contarHojas(raiz);
    }

    private int contarHojas(Nodo<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        if (nodo.esHoja()) {
            return 1;
        }
        return contarHojas(nodo.getIzquierdo()) + contarHojas(nodo.getDerecho());
    }

    public int altura() {
        return altura(raiz);
    }

    private int altura(Nodo<T> nodo) {
        if (nodo == null) {
            return -1;
        }
        return 1 + Math.max(altura(nodo.getIzquierdo()), altura(nodo.getDerecho()));
    }

    public boolean esVacio() {
        return raiz == null;
    }

    public Nodo<T> buscar(T dato) {
        return buscar(raiz, dato);
    }

    private Nodo<T> buscar(Nodo<T> nodo, T dato) {
        if (nodo == null) {
            return null;
        }
        if (Objects.equals(nodo.getDato(), dato)) {
            return nodo;
        }
        Nodo<T> encontrado = buscar(nodo.getIzquierdo(), dato);
        return encontrado != null ? encontrado : buscar(nodo.getDerecho(), dato);
    }

    public void mostrarArbol() {
        if (raiz == null) {
            System.out.println("Árbol vacío.");
            return;
        }
        System.out.println(raiz.getDato());
        if (raiz.getIzquierdo() != null) {
            mostrarRama(raiz.getIzquierdo(), "", raiz.getDerecho() == null);
        }
        if (raiz.getDerecho() != null) {
            mostrarRama(raiz.getDerecho(), "", true);
        }
    }

    private void mostrarRama(Nodo<T> nodo, String prefijo, boolean esUltimo) {
        System.out.println(prefijo + (esUltimo ? "\\-- " : "|-- ") + nodo.getDato());
        String prefijoHijos = prefijo + (esUltimo ? "    " : "|   ");
        if (nodo.getIzquierdo() != null) {
            mostrarRama(nodo.getIzquierdo(), prefijoHijos, nodo.getDerecho() == null);
        }
        if (nodo.getDerecho() != null) {
            mostrarRama(nodo.getDerecho(), prefijoHijos, true);
        }
    }

    private void validarPadre(Nodo<T> padre) {
        if (padre == null) {
            throw new IllegalArgumentException("El nodo padre no puede ser null.");
        }
    }
}
