package com.la_cantera_reservas.ui;

import java.util.Scanner;

import com.la_cantera_reservas.model.Cliente;
import com.la_cantera_reservas.services.ServicioCliente;
import static com.la_cantera_reservas.services.ServicioCliente.inicioSesionCliente;
import static com.la_cantera_reservas.services.ServicioCliente.verReservasActivas;

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
                input.nextLine();
                setClienteActual(inicioSesionCliente(input));
                // clienteActual = inicioSesionCliente(input);
                boolean clienteValido = ServicioCliente.validacionCliente(clienteActual);
                break;
            case 2: {
                System.out.println("");
                verReservasActivas();
            }

            default:
                throw new AssertionError();
        }

    }

    // case 2: {
    // String RegistroUser = Inicio_sesion_y_registro.Registrar_Username(input);
    // System.out.println("Register Username: " + RegistroUser);
    // String RegistroPassword = Inicio_sesion_y_registro.Registro_Password(input);
    // System.out.println("Register Password: " + RegistroPassword);

    // if (Usuarios.containsKey(RegistroUser)) {
    // Mensajes.Sesion_en_uso();
    // }

    // else {
    // Usuarios.put(RegistroUser, RegistroPassword);

    // for (String user : Usuarios.keySet()) {
    // System.out.println(index + ". Usuario: " + user);
    // }
    // }
    // break;
    // }

    // default: {
    // Mensajes.Opción_Invalida();
    // Menu_cliente(input);
    // break;
    // }

}
