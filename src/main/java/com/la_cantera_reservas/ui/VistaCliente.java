package com.la_cantera_reservas.ui;

import java.util.Scanner;

import com.la_cantera_reservas.model.Cliente;
import com.la_cantera_reservas.services.ServicioCliente;

public class VistaCliente {
    private static Cliente clienteActual;

    static Scanner input = new Scanner(System.in);

    public static Cliente getClienteActual() {
        return clienteActual;
    }

    public static void setClienteActual(Cliente cliente) {
        clienteActual = cliente;
    }

    public static void validadorDeOpcion() {
        while (true) {
            byte opcion = ServicioCliente.menuPrincipalCliente(input);

            switch (opcion) {
                case 1:
                    setClienteActual(ServicioCliente.inicioSesionCliente(input));
                    ServicioCliente.validacionCliente(clienteActual);
                    break;

                case 2:
                    ServicioCliente.registrarCliente();
                    break;

                case 3:
                    System.out.println("Volviendo al menú principal...\n");
                    return;

                default:
                    System.out.println("Opción no válida.\n");
            }
        }
    }
}