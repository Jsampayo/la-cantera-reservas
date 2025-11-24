package com.la_cantera_reservas.services;

import java.util.HashMap;
import java.util.Scanner;

import com.la_cantera_reservas.excepciones.MensajesCliente;
import com.la_cantera_reservas.model.Cliente;

public class ServicioCliente {
    static Scanner input = new Scanner(System.in);
    private static final HashMap<Integer, Cliente> ClientesRegistrados = new HashMap<>();

    public static HashMap<Integer, Cliente> getClientesRegistrados() {
        return ServicioCliente.ClientesRegistrados;
    }

    public static byte menuPrincipalCliente(Scanner input) {
        System.out.println("1. Iniciar Sesión\n2. Registrarse");
        System.out.print("\nDigite su opcion: ");
        byte opcion = input.nextByte();
        Cliente pacho = new Cliente("pepito", "123", 150);
        getClientesRegistrados().put(pacho.getId(), new Cliente(pacho.getNombre(), pacho.getPassword(), pacho.getId()));

        return opcion;
    }

    // Menu mostrado al elegir la opcion Usuario en el menu principal y acceder
    public static Cliente inicioSesionCliente(Scanner input) {
        System.out.print("Ingrese su id de usuario: ");
        int id = input.nextInt();
        input.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String UserPassword = input.nextLine();
        return new Cliente(id, UserPassword);
    }

    public static boolean validacionCliente(Cliente client) {
        boolean clienteEncontrado = false;

        for (Integer cliente : ServicioCliente.getClientesRegistrados().keySet()) {
            Cliente c = ServicioCliente.getClientesRegistrados().get(cliente);

            if (cliente.equals(client.getId())
                    && c.getPassword().equals(client.getPassword())) {
                clienteEncontrado = true;
                byte opcionMenuCliente = menuCliente();
                if (opcionMenuCliente == 1) {
                    ServicioReserva.filtro();
                }

            }

        }
        if (!clienteEncontrado) {
            MensajesCliente.Noautenticado();
        }

        return clienteEncontrado;
    }

    public static byte menuCliente() {
        System.out.println("\nBienvenido al menu cliente");
        System.out.println("\n1. Realizar una reserva\n2. Ver reservas activas\n3. Salir");
        System.out.print("\nDigite su opcion: ");
        byte opcion = input.nextByte();
        return opcion;

    }

    public static void verReservasActivas() {
       System.out.println("hola");
       
    }

    public static void registrarCliente() {

        System.out.println("");

    }
}
