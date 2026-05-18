package com.la_cantera_reservas.model;

public class Cliente {
    private int    id;
    private String nombre;
    private String password;

    public Cliente(String nombre, String password, int id) {
        this.nombre   = nombre;
        this.password = password;
        this.id       = id;
    }

    public Cliente(int id, String password) {
        this.nombre   = null;
        this.password = password;
        this.id       = id;
    }

    public String getNombre()   { return nombre;   }
    public int    getId()       { return id;       }
    public String getPassword() { return password; }

    public void setId(int id) { this.id = id; }
}