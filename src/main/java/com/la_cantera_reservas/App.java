package com.la_cantera_reservas;

import java.util.Scanner;

import com.la_cantera_reservas.model.Cliente;
import com.la_cantera_reservas.model.Reserva;
import static com.la_cantera_reservas.services.ServicioCliente.getClientesRegistrados;
import com.la_cantera_reservas.services.ServicioReserva;
import static com.la_cantera_reservas.services.ServicioReserva.getReservasActivas;
import com.la_cantera_reservas.ui.MenuPrincipal;
import com.la_cantera_reservas.ui.VistaCliente;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        getReservasActivas().put(150, new Reserva(1, "hora", "fecha", 12));
        getClientesRegistrados().put(150, new Cliente("pacho", "123", 123));

        // Menu principal
        ServicioReserva.generarReservasDisponibles();

        byte inicio = MenuPrincipal.menuInicio(scanner);
        if (inicio == 1) {
            VistaCliente.validadorDeOpcion();

        }
    }

}
