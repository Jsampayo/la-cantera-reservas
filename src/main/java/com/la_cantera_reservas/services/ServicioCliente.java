package com.la_cantera_reservas.services;

import java.util.HashMap;
import java.util.Scanner;

import com.la_cantera_reservas.excepciones.MensajesCliente;
import com.la_cantera_reservas.model.Cliente;
import com.la_cantera_reservas.model.Reserva;
import static com.la_cantera_reservas.services.ServicioReserva.getReservasActivas;
import com.la_cantera_reservas.ui.VistaCliente;

public class ServicioCliente {
    static Scanner input = new Scanner(System.in);
    private static final HashMap<Integer, Cliente> ClientesRegistrados = new HashMap<>();

    public static HashMap<Integer, Cliente> getClientesRegistrados() {
        return ClientesRegistrados;
    }

    public static byte menuPrincipalCliente(Scanner input) {
        System.out.println("1. Iniciar Sesión\n2. Registrarse\n3. Volver al menú principal");
        System.out.print("\nDigite su opcion: ");
        byte opcion = input.nextByte();
        return opcion;
    }

    public static Cliente inicioSesionCliente(Scanner input) {
        System.out.print("Ingrese su id de usuario: ");
        int id = input.nextInt();
        input.nextLine(); // limpiar buffer
        System.out.print("Ingrese su contraseña: ");
        String UserPassword = input.nextLine();
        return new Cliente(id, UserPassword);
    }

    public static boolean validacionCliente(Cliente client) {

        for (Integer key : ClientesRegistrados.keySet()) {
            Cliente c = ClientesRegistrados.get(key);
            if (key.equals(client.getId()) && c.getPassword().equals(client.getPassword())) {
                VistaCliente.setClienteActual(c);
                System.out.println("\n¡Bienvenido, " + c.getNombre() + "!\n");

                while (true) {
                    byte opcion = menuCliente();

                    if (opcion == 1) {
                        ServicioReserva.filtro();
                    } else if (opcion == 2) {
                        ServicioCliente.verReservasActivas();
                    } else if (opcion == 3) {
                        System.out.println("Sesión cerrada.\n");
                        VistaCliente.setClienteActual(null);
                        return true;
                    } else {
                        System.out.println("Opción inválida.\n");
                    }
                }
            }
        }
        MensajesCliente.Noautenticado();
        return false;
    }

    public static byte menuCliente() {
        System.out.println("\nBienvenido al menu cliente");
        System.out.println("\n1. Realizar una reserva\n2. Ver reservas activas\n3. Salir");
        System.out.print("\nDigite su opcion: ");
        return input.nextByte();
    }

    public static void verReservasActivas() {
        Cliente cliente = VistaCliente.getClienteActual();

        // Reserva reserva = ServicioReserva.getReservasActivas().get(cliente.getId());

        System.out.println("   TUS RESERVAS ACTIVAS");
        System.out.println("Cliente: " + cliente.getNombre() + " (ID: " + cliente.getId() + ")");

        System.out.printf("%-5s %-20s %-15s %-10s\n", "ID-RESERVA", "HORA", "FECHA", "CAPACIDAD");
        System.out.println("────────────────────────────────────────────────────");

        for (Integer idReserva : getReservasActivas().keySet()) {
            Reserva r = new Reserva(getReservasActivas().get(idReserva).getIdReserva(),
                    getReservasActivas().get(idReserva).getHora(), 
                    getReservasActivas().get(idReserva).getFecha(),
                    getReservasActivas().get(idReserva).getCapacidad());

            System.out.printf("%-5s %-20s %-15s %-10s\n", r.getIdReserva(), r.getHora(), r.getFecha(), r.getCapacidad());

            // System.out.println("\nNo tienes reservas activas por el momento.\n");

            System.out.println("════════════════════════════════\n");
        }
    }

    public static void registrarCliente() {
        System.out.println("\nMódulo de registro en desarrollo...\n");
    }
}
