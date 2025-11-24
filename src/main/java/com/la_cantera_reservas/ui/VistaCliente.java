package com.la_cantera_reservas.ui;

import java.util.Scanner;

import com.la_cantera_reservas.model.Cliente;
import com.la_cantera_reservas.services.ServicioCliente;
import static com.la_cantera_reservas.services.ServicioCliente.inicioSesionCliente;

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
        byte opcion = ServicioCliente.menuPrincipalCliente(input);

        switch (opcion) {
            case 1:
                setClienteActual(inicioSesionCliente(input));
                // clienteActual = inicioSesionCliente(input);
                boolean clienteValido = ServicioCliente.validacionCliente(clienteActual);
                break;
            case 2:
                System.out.println("1");
                ServicioCliente.verReservasActivas();
                break;

            default:
                // throw new AssertionError();
                System.out.println("hola estoy aqui");
        }

    }}