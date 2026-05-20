package com.la_cantera_reservas.model;

public class Reserva {
    private int    idReserva;
    private final String hora;
    private final String fecha;
    private final int    capacidad;
    private final String cancha;


    public Reserva(int idReserva, String hora, String fecha, int capacidad, String cancha) {
        this.idReserva = idReserva;
        this.hora      = hora;
        this.fecha     = fecha;
        this.capacidad = capacidad;
        this.cancha    = cancha;
    }

   
    public Reserva(int idReserva, String hora, String fecha, int capacidad) {
        this(idReserva, hora, fecha, capacidad, "Sin asignar");
    }

    public int    getIdReserva() { return idReserva; }
    public String getHora()      { return hora;      }
    public String getFecha()     { return fecha;     }
    public int    getCapacidad() { return capacidad; }
    public String getCancha()    { return cancha;    }

    @Override
    public String toString() {
        return String.format("%-5d %-15s %-20s %-15s %-10d",
                idReserva, cancha, hora, fecha, capacidad);
    }
}