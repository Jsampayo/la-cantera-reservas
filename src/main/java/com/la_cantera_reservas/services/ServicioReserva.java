package com.la_cantera_reservas.services;

import java.util.ArrayList;

import com.la_cantera_reservas.model.Reserva;

public class ServicioReserva {
    private final ArrayList<Reserva> tablaReservasActivas = new ArrayList<>();

    public ArrayList<Reserva> getTablaReservas() {
        return this.tablaReservasActivas;
    }

}
