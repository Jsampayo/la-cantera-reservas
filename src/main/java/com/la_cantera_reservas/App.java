package com.la_cantera_reservas;

import com.la_cantera_reservas.model.Cliente;
import com.la_cantera_reservas.services.PersistenciaTxt;
import com.la_cantera_reservas.services.ServicioCliente;
import com.la_cantera_reservas.services.ServicioReserva;
import com.la_cantera_reservas.ui.VentanaPrincipal;
import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {

        PersistenciaTxt.cargarClientes(ServicioCliente.getClientesRegistrados());
        PersistenciaTxt.cargarReservasActivas(ServicioReserva.getReservasActivas());

        if (ServicioCliente.getClientesRegistrados().isEmpty()) {
            ServicioCliente.getClientesRegistrados().put(123, new Cliente("pacho", "123", 123));
        }

        ServicioReserva.generarReservasDisponibles();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            PersistenciaTxt.guardarClientes(ServicioCliente.getClientesRegistrados());
            PersistenciaTxt.guardarReservasActivas(ServicioReserva.getReservasActivas());
            System.out.println("[App] Datos guardados.");
        }));

        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}