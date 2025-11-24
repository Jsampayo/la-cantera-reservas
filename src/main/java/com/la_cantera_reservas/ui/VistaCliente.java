package com.la_cantera_reservas.ui;

import java.util.Scanner;

import com.la_cantera_reservas.services.ServicioCliente;
import static com.la_cantera_reservas.services.ServicioCliente.inicioSesionCliente;

public class VistaCliente {
    static Scanner input = new Scanner(System.in);

    public static void validadorDeOpcion() {
        byte opcion = ServicioCliente.menuPrincipalCliente(input);

        switch (opcion) {
            case 1:
                input.nextLine();
                boolean clienteValido = ServicioCliente.validacionCliente(inicioSesionCliente(input));

                break;

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
