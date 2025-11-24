package com.la_cantera_reservas;

import com.la_cantera_reservas.model.Cliente;
import static com.la_cantera_reservas.services.ServicioCliente.getClientesRegistrados;
import com.la_cantera_reservas.services.ServicioReserva;
import com.la_cantera_reservas.ui.MenuPrincipal;
import com.la_cantera_reservas.ui.VistaCliente;

public class App {

    public static void main(String[] args) {
        getClientesRegistrados().put(123, new Cliente("pacho", "123", 123));
        ServicioReserva.generarReservasDisponibles();

        while (true) {
            byte opcion = MenuPrincipal.menuInicio(MenuPrincipal.input);

            if (opcion == 1) {
                VistaCliente.validadorDeOpcion();
            } else if (opcion == 2) {
                System.out.println("Módulo administrador en desarrollo...\n");
            } else if (opcion == 3) {
                System.out.println("¡Gracias por usar La Cantera Reservas!");
                break;
            } else {
                System.out.println("Opción inválida.\n");
            }
        }
    }
}