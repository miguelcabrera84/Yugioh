package com.example.yugioh;

public class GestorCartas {

    public static ListaCircularDoble<Carta> filtrarPorNivel(ListaCircularDoble<Carta> maestra, int nivel) {
        ListaCircularDoble<Carta> resultado = new ListaCircularDoble<>();
        if (maestra.estaVacia()) return resultado;

        NodoDoble<Carta> aux = maestra.getInicio();
        do {
            if (aux.getInfo().getNivel() == nivel) {
                resultado.insertaFinal(aux.getInfo());
            }
            aux = aux.getSig();
        } while (aux != maestra.getInicio());

        return resultado;
    }

    public static ListaCircularDoble<Carta> filtrarPorAtributo(ListaCircularDoble<Carta> maestra, String atributo) {
        ListaCircularDoble<Carta> resultado = new ListaCircularDoble<>();
        if (maestra.estaVacia()) return resultado;

        NodoDoble<Carta> aux = maestra.getInicio();
        do {
            if (aux.getInfo().getAtributo().equalsIgnoreCase(atributo)) {
                resultado.insertaFinal(aux.getInfo());
            }
            aux = aux.getSig();
        } while (aux != maestra.getInicio());

        return resultado;
    }

    public static ListaCircularDoble<Carta> ordenarPorAtaque(ListaCircularDoble<Carta> maestra, boolean lowToHigh) {
        ListaCircularDoble<Carta> resultado = copiarMonstruosConStats(maestra);
        Carta.criterioActual = lowToHigh ? Carta.CriterioOrden.ATK_ASC : Carta.CriterioOrden.ATK_DESC;
        resultado.ordenarLista();
        return resultado;
    }

    public static ListaCircularDoble<Carta> ordenarPorDefensa(ListaCircularDoble<Carta> maestra, boolean lowToHigh) {
        ListaCircularDoble<Carta> resultado = copiarMonstruosConStats(maestra);
        Carta.criterioActual = lowToHigh ? Carta.CriterioOrden.DEF_ASC : Carta.CriterioOrden.DEF_DESC;
        resultado.ordenarLista();
        return resultado;
    }

    private static ListaCircularDoble<Carta> copiarMonstruosConStats(ListaCircularDoble<Carta> maestra) {
        ListaCircularDoble<Carta> copia = new ListaCircularDoble<>();
        if (maestra.estaVacia()) return copia;

        NodoDoble<Carta> aux = maestra.getInicio();
        do {
            if (aux.getInfo().getAtk() >= 0) {
                copia.insertaFinal(aux.getInfo());
            }
            aux = aux.getSig();
        } while (aux != maestra.getInicio());

        return copia;
    }
}