package com.la_cantera_reservas.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.la_cantera_reservas.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    List<Reserva> findByClienteIsNotNull();

    List<Reserva> findByFechaAndClienteIsNull(LocalDate fecha);

    List<Reserva> findByFechaAndClienteIsNullAndEstado(LocalDate fecha, String estado);

    List<Reserva> findByClienteId(Integer clienteId);

    List<Reserva> findByFechaBetween(LocalDate desde, LocalDate hasta);

    boolean existsByCanchaIdAndFechaAndHora(Integer canchaId, LocalDate fecha, String hora);
}
