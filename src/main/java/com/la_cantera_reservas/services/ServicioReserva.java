package com.la_cantera_reservas.services;

import com.la_cantera_reservas.model.Cancha;
import com.la_cantera_reservas.model.Reserva;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ServicioReserva {

    private static final HashMap<Integer, Reserva> reservasActivas     = new HashMap<>();
    private static final ArrayList<Reserva>        reservasDisponibles = new ArrayList<>();

    public static final String[] horarioCancha = {
        "4 PM - 6 PM", "6 PM - 8 PM", "8 PM - 10 PM", "10 PM - 12 AM"
    };
    public static final int[] capacidades = { 10, 14, 16 };

    public static HashMap<Integer, Reserva> getReservasActivas()     { return reservasActivas;     }
    public static ArrayList<Reserva>        getReservasDisponibles() { return reservasDisponibles; }

    // ── Genera reservas cruzando: días × canchas × horarios ─────────
    public static void generarReservasDisponibles() {
        LocalDate hoy    = LocalDate.now();
        LocalDate finMes = hoy.withDayOfMonth(hoy.lengthOfMonth());
        List<LocalDate> dias = hoy.datesUntil(finMes.plusDays(1)).collect(Collectors.toList());

        int indice = 1;
        for (LocalDate dia : dias) {
            for (Cancha cancha : Cancha.getCanchas()) {          // ← itera canchas
                for (String hora : horarioCancha) {
                    // La capacidad la define el tipo de cancha
                    int cap = capacidadSegunTipo(cancha.getTipo());
                    reservasDisponibles.add(
                        new Reserva(indice++, hora, dia.toString(), cap, cancha.getNombre())
                    );
                }
            }
        }
    }

    private static int capacidadSegunTipo(String tipo) {
        return switch (tipo) {
            case "Fútbol 5"  -> 10;
            case "Fútbol 7"  -> 14;
            case "Fútbol 11" -> 22;
            default          -> 10;
        };
    }

    public static List<Reserva> filtrarPorDia(String dia) {
        String fecha = LocalDate.now().toString().substring(0, 8) + dia;
        return reservasDisponibles.stream()
                .filter(r -> r.getFecha().equals(fecha))
                .collect(Collectors.toList());
    }

    public static List<Reserva> filtrarPorCancha(String nombreCancha) {
        return reservasDisponibles.stream()
                .filter(r -> r.getCancha().equals(nombreCancha))
                .collect(Collectors.toList());
    }

    public static boolean crearReserva(String hora, String fecha, int capacidad, String cancha) {
        if (hora.isBlank() || fecha.isBlank() || cancha.isBlank() || capacidad <= 0) return false;
        int nuevoId = reservasDisponibles.stream()
                .mapToInt(Reserva::getIdReserva).max().orElse(0) + 1;
        reservasDisponibles.add(new Reserva(nuevoId, hora, fecha, capacidad, cancha));
        return true;
    }

    public static boolean reservar(int idReserva, int idCliente) {
        for (Reserva r : reservasDisponibles) {
            if (r.getIdReserva() == idReserva) {
                reservasActivas.put(idCliente, r);
                reservasDisponibles.remove(r);
                return true;
            }
        }
        return false;
    }

    public static boolean eliminarReservaDisponible(int idReserva) {
        return reservasDisponibles.removeIf(r -> r.getIdReserva() == idReserva);
    }

    public static boolean cancelarReservaActiva(int idCliente) {
        Reserva r = reservasActivas.remove(idCliente);
        if (r == null) return false;
        reservasDisponibles.add(r);
        return true;
    }

    public static Reserva getReservaDeCliente(int idCliente) {
        return reservasActivas.get(idCliente);
    }
}
