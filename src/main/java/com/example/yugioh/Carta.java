package com.example.yugioh;

public class Carta implements Comparable<Carta> {
    public enum CriterioOrden {
        ATK_ASC, ATK_DESC, DEF_ASC, DEF_DESC
    }

    public static CriterioOrden criterioActual = CriterioOrden.ATK_ASC;

    private String nombre;
    private String atributo;
    private int nivel;
    private String tipo;
    private int atk;
    private int def;
    private String rareza;
    private String descripcion;
    private String id;

    public Carta(String nombre, String atributo, int nivel, String tipo,
                 int atk, int def, String rareza, String descripcion, String id) {
        this.nombre = nombre;
        this.atributo = atributo;
        this.nivel = nivel;
        this.tipo = tipo;
        this.atk = atk;
        this.def = def;
        this.rareza = rareza;
        this.descripcion = descripcion;
        this.id = id;
    }

    @Override
    public int compareTo(Carta otra) {
        switch (criterioActual) {
            case    ATK_ASC:
                return Integer.compare(this.atk, otra.atk);
            case ATK_DESC:
                return Integer.compare(otra.atk, this.atk);
            case DEF_ASC:
                return Integer.compare(this.def, otra.def);
            case DEF_DESC:
                return Integer.compare(otra.def, this.def);
            default:
                return 0;
        }
    }

    public String getUrlImagen() {
        return "https://images.ygoprodeck.com/images/cards/" + id + ".jpg";
    }

    public String getNombre() { return nombre; }
    public String getAtributo() { return atributo; }
    public int getNivel() { return nivel; }
    public String getTipo() { return tipo; }
    public int getAtk() { return atk; }
    public int getDef() { return def; }
    public String getRareza() { return rareza; }
    public String getDescripcion() { return descripcion; }
    public String getId() { return id; }
}