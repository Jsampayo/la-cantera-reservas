package com.la_cantera_reservas.services;

import java.util.HashMap;
import java.util.Scanner;

import com.la_cantera_reservas.model.Cliente;
import com.la_cantera_reservas.model.Reserva;

public class ServicioCliente {
    static Scanner input = new Scanner(System.in);
    private static final HashMap<Integer, Cliente> ClientesRegistrados = new HashMap<>();
    private final HashMap<Integer, Reserva[]> reservasCliente = new HashMap<>();

    public HashMap<Integer, Reserva[]> getReservasCliente() {
        return this.reservasCliente;
    }

    public static HashMap<Integer, Cliente> getClientesRegistrados() {
        return ServicioCliente.ClientesRegistrados;
    }

    // Menu mostrado al elegir la opcion Usuario en el menu principal y acceder
    public static Cliente Inicio_de_sesion_Cliente(Scanner input) {
        System.out.print("Ingrese su id de usuario: ");
        int id = input.nextInt();
        input.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String UserPassword = input.nextLine();
        return new Cliente(id, UserPassword);
    }

    public static void validacionCliente() {
        boolean clienteEncontrado = false;

        Cliente datosIngresoCliente = Inicio_de_sesion_Cliente(input);

        for (Integer cliente : ServicioCliente.getClientesRegistrados().keySet()) {
            Cliente c = ServicioCliente.getClientesRegistrados().get(cliente);

            if (cliente.equals(datosIngresoCliente.getId())
                    && c.getPassword().equals(datosIngresoCliente.getPassword())) {
                clienteEncontrado = true;
                System.out.println("Inicio de sesion exitoso");
            }

        }
        if (!clienteEncontrado) {
            System.out.println("Credenciales incorrectas");
        }
    }

}
