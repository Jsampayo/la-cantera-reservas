package com.la_cantera_reservas.model;

import java.util.List;

public class Cancha {
    private final int    id;
    private final String nombre;
    private final String tipo;    

    public Cancha(int id, String nombre, String tipo) {
        this.id     = id;
        this.nombre = nombre;
        this.tipo   = tipo;
    }

    public int    getId()     { return id;     }
    public String getNombre() { return nombre; }
    public String getTipo()   { return tipo;   }

    @Override
    public String toString() {
        return nombre + " (" + tipo + ")";
    }

    private static final List<Cancha> CANCHAS = List.of(
        new Cancha(1, "Cancha A", "Fútbol 5"),
        new Cancha(2, "Cancha B", "Fútbol 5"),
        new Cancha(3, "Cancha C", "Fútbol 7"),
        new Cancha(4, "Cancha D", "Fútbol 7"),
        new Cancha(5, "Cancha E", "Fútbol 11")
    );

    public static List<Cancha> getCanchas() { return CANCHAS; }
}