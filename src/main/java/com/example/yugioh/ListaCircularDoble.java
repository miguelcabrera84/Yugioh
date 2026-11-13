package com.example.yugioh;

public class ListaCircularDoble<T> {
    private NodoDoble<T> inicio;
    private NodoDoble<T> fin;

    public ListaCircularDoble() {
        this.inicio = null;
        this.fin = null;
    }

    public void insertaInicio(T dato) {
        NodoDoble<T> n = new NodoDoble<>(dato);

        if (inicio == null) {
            inicio = n;
            fin = n;
            n.setSig(inicio);
            n.setAnt(inicio);
        } else {
            n.setSig(inicio);
            inicio.setAnt(n);
            inicio = n;
            fin.setSig(inicio);
            n.setAnt(fin);
        }
    }

    public void insertaFinal(T dato) {
        NodoDoble<T> n = new NodoDoble<>(dato);

        if (inicio == null) {
            inicio = n;
            fin = n;
            n.setSig(inicio);
            n.setAnt(inicio);
        } else {
            n.setSig(inicio);
            inicio.setAnt(n);
            fin.setSig(n);
            n.setAnt(fin);
            fin = n;
        }
    }

    public T eliminaInicio() {
        if (inicio == null) {
            return null;
        }

        T dato = inicio.getInfo();

        if (inicio == fin) {
            inicio = null;
            fin = null;
        } else {
            fin.setSig(inicio.getSig());
            inicio = inicio.getSig();
            inicio.setAnt(fin);
        }

        return dato;
    }

    public T eliminaFin() {
        if (inicio == null) {
            return null;
        }

        T dato = fin.getInfo();

        if (inicio == fin) {
            inicio = null;
            fin = null;
        } else {
            NodoDoble<T> r = fin.getAnt();
            r.setSig(inicio);
            inicio.setAnt(r);
            fin = r;
        }

        return dato;
    }

    public void ordenarLista() {
        if (inicio == null || inicio == fin) return;

        boolean cambio;
        do {
            cambio = false;
            NodoDoble<T> aux = inicio;

            while (aux.getSig() != inicio) {
                Comparable<T> p1 = (Comparable<T>) aux.getInfo();
                T p2 = aux.getSig().getInfo();

                if (p1.compareTo(p2) > 0) {
                    T temp = aux.getInfo();
                    aux.setInfo(aux.getSig().getInfo());
                    aux.getSig().setInfo(temp);
                    cambio = true;
                }
                aux = aux.getSig();
            }
        } while (cambio);
    }

    public NodoDoble<T> getInicio() {
        return inicio;
    }

    public boolean estaVacia() {
        return inicio == null;
    }

    public int getTamano() {
        if (inicio == null) return 0;
        int contador = 0;
        NodoDoble<T> aux = inicio;
        do {
            contador++;
            aux = aux.getSig();
        } while (aux != inicio);
        return contador;
    }
}