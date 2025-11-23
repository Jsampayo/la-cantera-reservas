package com.la_cantera_reservas.ui;

import java.util.Scanner;

import com.la_cantera_reservas.services.ServicioCliente;

public class VistaCliente {
    public static void Menu_cliente(Scanner input) {
        System.out.println("1. Iniciar Sesión\n2. Registrarse");
        byte index = 1;
        System.out.print("\nDigite su opcion: ");
        byte opcion = input.nextByte();

        switch (opcion) {

            case 1: {
                input.nextLine();
                ServicioCliente.validacionCliente();
                break;
            }

            // case 2: {
            // input.nextLine();
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
    }
}
