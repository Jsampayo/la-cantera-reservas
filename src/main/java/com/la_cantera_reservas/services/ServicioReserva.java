package com.la_cantera_reservas.services;

import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.la_cantera_reservas.model.Cancha;
import com.la_cantera_reservas.model.Cliente;
import com.la_cantera_reservas.model.Reserva;
import com.la_cantera_reservas.repository.CanchaRepository;
import com.la_cantera_reservas.repository.ClienteRepository;
import com.la_cantera_reservas.repository.ReservaRepository;

@Service
public class ServicioReserva {

    public static final List<String> HORARIOS = List.of(
            "4 PM - 6 PM", "6 PM - 8 PM", "8 PM - 10 PM", "10 PM - 12 AM");

    public static final int DIAS_ADELANTE = 30;

    private final ReservaRepository reservaRepository;
    private final CanchaRepository canchaRepository;
    private final ClienteRepository clienteRepository;
    private final Clock clock;

    public ServicioReserva(ReservaRepository reservaRepository,
                           CanchaRepository canchaRepository,
                           ClienteRepository clienteRepository,
                           Clock clock) {
        this.reservaRepository = reservaRepository;
        this.canchaRepository = canchaRepository;
        this.clienteRepository = clienteRepository;
        this.clock = clock;
    }

 
    public LocalDate hoy() {
        return LocalDate.now(clock);
    }

   
    @Transactional
    public void generarReservasDisponibles() {
        LocalDate hoy = hoy();
        LocalDate hasta = hoy.plusDays(DIAS_ADELANTE - 1L);
        List<Cancha> canchas = canchaRepository.findAll();

        Set<String> existentes = new HashSet<>();
        for (Reserva r : reservaRepository.findByFechaBetween(hoy, hasta)) {
            existentes.add(clave(r.getCancha().getId(), r.getFecha(), r.getHora()));
        }

        List<Reserva> nuevas = new ArrayList<>();
        for (LocalDate dia = hoy; !dia.isAfter(hasta); dia = dia.plusDays(1)) {
            for (Cancha cancha : canchas) {
                for (String hora : HORARIOS) {
                    if (!existentes.contains(clave(cancha.getId(), dia, hora))) {
                        nuevas.add(new Reserva(cancha, dia, hora, cancha.getCapacidad()));
                    }
                }
            }
        }
        reservaRepository.saveAll(nuevas);
    }

    private String clave(Integer canchaId, LocalDate fecha, String hora) {
        return canchaId + "|" + fecha + "|" + hora;
    }

    public List<Cancha> getCanchas() {
        return canchaRepository.findAll(Sort.by("id"));
    }

    public List<Reserva> disponiblesDelDia(LocalDate fecha) {
        return ordenar(reservaRepository.findByFechaAndClienteIsNullAndEstado(fecha, Reserva.DISPONIBLE));
    }

    public List<Reserva> sinClienteDelDia(LocalDate fecha) {
        return ordenar(reservaRepository.findByFechaAndClienteIsNull(fecha));
    }

    public List<Reserva> activas(Integer idCliente) {
        if (idCliente == null) {
            return ordenar(reservaRepository.findByClienteIsNotNull());
        }
        return ordenar(reservaRepository.findByClienteId(idCliente));
    }

    public List<Reserva> getReservasDeCliente(int idCliente) {
        return ordenar(reservaRepository.findByClienteId(idCliente));
    }

    @Transactional
    public boolean reservar(int idReserva, int idCliente) {
        Reserva reserva = reservaRepository.findById(idReserva).orElse(null);
        Cliente cliente = clienteRepository.findById(idCliente).orElse(null);
        if (reserva == null || cliente == null) {
            return false;
        }
        if (reserva.getCliente() != null || !Reserva.DISPONIBLE.equals(reserva.getEstado())) {
            return false;
        }
        reserva.setCliente(cliente);
        reservaRepository.save(reserva);
        return true;
    }

    public void crearReserva(int idCancha, String hora, LocalDate fecha, Integer capacidad) {
        Cancha cancha = canchaRepository.findById(idCancha)
                .orElseThrow(() -> new IllegalArgumentException("La cancha no existe."));
        if (fecha == null || hora == null || hora.isBlank()) {
            throw new IllegalArgumentException("Por favor ingrese todos los datos.");
        }
        if (!HORARIOS.contains(hora)) {
            throw new IllegalArgumentException("El horario no es válido.");
        }
        if (capacidad != null && capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");
        }
        if (reservaRepository.existsByCanchaIdAndFechaAndHora(idCancha, fecha, hora)) {
            throw new IllegalArgumentException("Esa cancha ya tiene una reserva en ese día y hora.");
        }
        int cap = (capacidad == null) ? cancha.getCapacidad() : capacidad;
        reservaRepository.save(new Reserva(cancha, fecha, hora, cap));
    }

    public boolean cambiarEstado(int idReserva, String estado) {
        if (!Reserva.ESTADOS.contains(estado)) {
            return false;
        }
        Reserva reserva = reservaRepository.findById(idReserva).orElse(null);
        if (reserva == null || reserva.getCliente() != null) {
            return false;
        }
        reserva.setEstado(estado);
        reservaRepository.save(reserva);
        return true;
    }

    public boolean cancelarReserva(int idReserva) {
        Reserva reserva = reservaRepository.findById(idReserva).orElse(null);
        if (reserva == null || reserva.getCliente() == null) {
            return false;
        }
        reserva.setCliente(null);
        reservaRepository.save(reserva);
        return true;
    }

    @Transactional
    public void cancelarReservaDeCliente(int idReserva, int idCliente) {
        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new IllegalArgumentException("La reserva no existe."));
        if (reserva.getCliente() == null || reserva.getCliente().getId() != idCliente) {
            throw new IllegalArgumentException("Esa reserva no te pertenece.");
        }
        if (!reserva.isCancelable()) {
            throw new IllegalArgumentException("Solo puedes cancelar una reserva hasta un día antes de su fecha.");
        }
        reserva.setCliente(null);
        reservaRepository.save(reserva);
    }

    @Transactional
    public boolean cancelarReservasDeCliente(int idCliente) {
        List<Reserva> lista = reservaRepository.findByClienteId(idCliente);
        if (lista.isEmpty()) {
            return false;
        }
        for (Reserva reserva : lista) {
            reserva.setCliente(null);
        }
        reservaRepository.saveAll(lista);
        return true;
    }

    private List<Reserva> ordenar(List<Reserva> lista) {
        return lista.stream()
                .sorted(Comparator.comparing(Reserva::getFecha)
                        .thenComparing(r -> HORARIOS.indexOf(r.getHora()))
                        .thenComparing(r -> r.getCancha().getId()))
                .collect(Collectors.toList());
    }
}
