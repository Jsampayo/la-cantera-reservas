package com.la_cantera_reservas;

import java.util.Scanner;

import com.la_cantera_reservas.ui.MenuPrincipal;
import com.la_cantera_reservas.ui.VistaCliente;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Menu principal
        byte inicio = MenuPrincipal.menuInicio(scanner);
        if (inicio == 1) {
            VistaCliente.Menu_cliente(scanner);

        }
    }

}
