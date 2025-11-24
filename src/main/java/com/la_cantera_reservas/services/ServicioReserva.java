package com.la_cantera_reservas.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.la_cantera_reservas.model.Reserva;
import static com.la_cantera_reservas.services.ServicioCliente.input;

public class ServicioReserva {
    private static final HashMap<Integer, Reserva[]> reservasActivas = new HashMap<>();
    private static final ArrayList<Reserva> reservasDisponibles = new ArrayList<>();

    static String[] horarioCancha = {
            "4 PM - 6 PM",
            "6 PM - 8 PM",
            "8 PM - 10 PM",
            "10 PM - 12 AM",

    };

    static int[] capacidad = { 10, 14, 16 };

    static LocalDate hoy = LocalDate.now();
    static LocalDate finDeMes = hoy.withDayOfMonth(hoy.lengthOfMonth());

    static List<LocalDate> diasRestantes = hoy.datesUntil(finDeMes.plusDays(1)).toList();

    public static HashMap<Integer, Reserva[]> getReservasActivas() {
        return reservasActivas;
    }

    public static ArrayList<Reserva> getReservasDisponibles() {
        return reservasDisponibles;
    }

    public static void generarReservasDisponibles() {
        int indice = 1;
        for (int d = 0; d < diasRestantes.size(); d++) {
            for (int j = 0; j < horarioCancha.length; j++) {
                for (int k = 0; k < capacidad.length; k++) {
                    getReservasDisponibles()
                            .add(new Reserva(indice, horarioCancha[j], diasRestantes.get(d).toString(), capacidad[k]));
                    indice++;

                }

            }

        }

    }

    public static void filtro() {
        String base = hoy.toString().substring(0, 8);
        // String base = "2025-11-";
        input.nextLine();
        System.out.print("Ingresa el dia que deseas filtrar: ");
        String dia = input.nextLine();
        String resultado = base + dia;
        int coincidencias = 0;
        System.out.println(coincidencias + " coincidencias encontradas");
        System.out.printf("%-30s %-20s %-20s %-20s\n", "ID", "HORA DISPONIBLE", "FECHA", "CAPACIDAD\n");

        for (Reserva reserva : getReservasDisponibles()) {
            if (reserva.getFecha().equals(resultado)) {
                System.out.println(reserva.toString());
                coincidencias++;
            }
        }

    }
}
