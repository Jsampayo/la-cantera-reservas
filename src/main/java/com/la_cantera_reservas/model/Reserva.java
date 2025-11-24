package com.la_cantera_reservas.model;

public class Reserva {
    private final int idReserva = 0;
    private final String hora;
    private final String fecha;
    private final int capacidad;

    public Reserva(int idReserva, String hora, String fecha, int capacidad) {
        this.hora = hora;
        this.fecha = fecha;
        this.capacidad = capacidad;
        idReserva++;

    }

    // public Reserva(String hora, String cancha, int capacidad) {
    // this.hora = hora;
    // this.cancha = cancha;
    // this.capacidad = capacidad;

    // }

    public int getIdReserva() {
        return this.idReserva;
    }

    public String getHora() {
        return this.hora;
    }

    public String getCancha() {
        return this.fecha;
    }

    public int getCapacidad() {
        return this.capacidad;
    }

    // @Override
    // public String toString() {
    //     return "Hora: " + this.hora + ", Cancha: " + this.fecha + ", Capacidad: " + this.capacidad;
    // }

    // public void Reservar(Scanner scanner) {

    // System.out.print("Ingresa la hora de tu reserva: ");
    // String hora = scanner.nextLine();
    // System.out.print("Ingresa la cancha de tu reserva: ");
    // String cancha = scanner.nextLine();
    // System.out.print("Ingresa la capacidad de tu reserva: ");
    // int capacidad = scanner.nextInt();

    // getTablaReservas().add(new Reserva(hora, cancha, capacidad));

    // System.out.println(getTablaReservas());

    // }

}
