package com.la_cantera_reservas.services;

import static com.la_cantera_reservas.services.ServicioCliente.input;

import java.util.Scanner;

import com.la_cantera_reservas.excepciones.MensajeAdmin;
import com.la_cantera_reservas.model.Admin;

public class ServicioAdmin {

    public static Admin Administrador(Scanner input) {
        System.out.print("Ingrese su id de Administrador: ");
        int idAdmin = input.nextInt();
        input.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String AdminPassword = input.nextLine();

        return new Admin(idAdmin,AdminPassword);
    }

    public static int Opciones(Scanner input){
        System.out.println("Que acción desea realizar");
        System.out.println("1. Consultar Reservas Cliente");
        System.out.println("2. Eliminar Reserva");
        int opcion = input.nextInt();
        return opcion;
    }

    public static int IdConsultar(Scanner input){
        System.out.println("Ingrese el ID del cliente a consultar: ");
        int IdConsulta = input.nextInt();
        return IdConsulta;
    }

    public static int IdEliminar(Scanner input){
        System.out.println("Escoja la reserva a Eliminar: ");
        int IdEliminar = input.nextInt();
        return IdEliminar;
    }
}

