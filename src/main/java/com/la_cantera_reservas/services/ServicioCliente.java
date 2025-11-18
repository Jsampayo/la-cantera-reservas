package com.la_cantera_reservas.services;

import java.util.HashMap;

import com.la_cantera_reservas.model.Cliente;
import com.la_cantera_reservas.model.Reserva;
import com.la_cantera_reservas.ui.MenuCliente;

public class ServicioCliente {
    private final HashMap<Integer, Cliente> ClientesRegistrados = new HashMap<>();
    private final HashMap<Integer, Reserva> reservasCliente = new HashMap<>();
    
    public HashMap<Integer, Reserva> getReservasCliente() {
        return this.reservasCliente;
    }

    public HashMap<Integer, Cliente> getClientesRegistrados() {
        return this.ClientesRegistrados;
    }

    public static void main(String[] args) {
        MenuCliente.mostrarMenu();

    }

}
