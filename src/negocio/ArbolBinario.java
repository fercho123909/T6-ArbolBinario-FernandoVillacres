package negocio;

import java.util.Objects;
import modelo.Nodo;

public class ArbolBinario<T> {
    private Nodo<T> raiz;

    public Nodo<T> crearRaiz(T dato) {
        if (raiz != null) {
            throw new IllegalStateException("El árbol ya tiene una raíz: " + raiz.getDato());
        }
        raiz = new Nodo<>(dato);
        return raiz;
    }

    public Nodo<T> agregarIzquierdo(Nodo<T> padre, T dato) {
        validarPadre(padre);
        if (padre.getIzquierdo() != null) {
            throw new IllegalStateException("La posición izquierda de '" + padre.getDato()
                    + "' ya está ocupada por '" + padre.getIzquierdo().getDato() + "'.");
        }
        Nodo<T> hijo = new Nodo<>(dato);
        padre.setIzquierdo(hijo);
        return hijo;
    }

    public Nodo<T> agregarDerecho(Nodo<T> padre, T dato) {
        validarPadre(padre);
        if (padre.getDerecho() != null) {
            throw new IllegalStateException("La posición derecha de '" + padre.getDato()
                    + "' ya está ocupada por '" + padre.getDerecho().getDato() + "'.");
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
            System.out.println("(árbol vacío)");
            return;
        }
        System.out.println(raiz.getDato());
        if (raiz.getIzquierdo() != null) {
            mostrarRama(raiz.getIzquierdo(), "", raiz.getDerecho() == null, "I");
        }
        if (raiz.getDerecho() != null) {
            mostrarRama(raiz.getDerecho(), "", true, "D");
        }
    }

    private void mostrarRama(Nodo<T> nodo, String prefijo, boolean esUltimo, String lado) {
        System.out.println(prefijo + (esUltimo ? "`-- " : "|-- ") + "[" + lado + "] " + nodo.getDato());
        String prefijoHijos = prefijo + (esUltimo ? "    " : "|   ");
        if (nodo.getIzquierdo() != null) {
            mostrarRama(nodo.getIzquierdo(), prefijoHijos, nodo.getDerecho() == null, "I");
        }
        if (nodo.getDerecho() != null) {
            mostrarRama(nodo.getDerecho(), prefijoHijos, true, "D");
        }
    }

    private void validarPadre(Nodo<T> padre) {
        if (padre == null) {
            throw new IllegalArgumentException("El nodo padre no puede ser nulo.");
        }
    }
}
