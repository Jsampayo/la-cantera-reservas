package com.la_cantera_reservas.model;

import java.util.HashMap;

public class Admin {
    private int idAdmin;
    private String password;
    private final HashMap<Integer, Reserva> reservasCancha = new HashMap<>();

    public Admin(int idAdmin, String password) {
        this.idAdmin = idAdmin;
        this.password = password;
    }

    public int getIdAdmin() {
        return this.idAdmin;
    }

    public String getPasswordAdmin() {
        return this.password;
    }

    public HashMap<Integer, Reserva> getReservasCancha() {
        return this.reservasCancha;
    }

}
