package com.la_cantera_reservas.model;

import java.util.Scanner;

public class Cliente {
    Scanner scanner = new Scanner(System.in);

    private int id;
    private String nombre;
    private String password;

    public Cliente(String nombre, String password) {

    }

    public String getNombre() {
        return this.nombre;
    }

    public int getId() {
        return this.id;
    }

    public void setId() {
        this.id = scanner.nextInt();
    } // *****AGREGAR UN SOUT ANTES DE PEDIR EL ID*****

    public String getPassword() {
        return "*".repeat(password.length());
    }

}


