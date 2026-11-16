package com.la_cantera_reservas.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.la_cantera_reservas.services.ServicioReserva;

@Component
public class GeneradorReservas implements CommandLineRunner {

    private final ServicioReserva servicioReserva;

    public GeneradorReservas(ServicioReserva servicioReserva) {
        this.servicioReserva = servicioReserva;
    }

    @Override
    public void run(String... args) {
        servicioReserva.generarReservasDisponibles();
    }

    @Scheduled(cron = "0 0 1 * * *", zone = "America/Bogota")
    public void generarCadaDia() {
        servicioReserva.generarReservasDisponibles();
    }
}
