package com.la_cantera_reservas.ui;

import java.util.Scanner;

public class MenuPrincipal {
    public static Scanner input = new Scanner(System.in);

    public static byte menuInicio(Scanner input) {
        System.out.println("\nLa Cantera Reservas");

        System.out.println("\n1. Acceder como Cliente\n2. Acceder como administrador\n3. Salir");

        System.out.print("\nDigite su opcion: ");
        byte opcion = input.nextByte();
        System.out.print("\n");

        return opcion;

    }

}