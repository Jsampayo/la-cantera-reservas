package com.la_cantera_reservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.la_cantera_reservas.model.Cancha;

public interface CanchaRepository extends JpaRepository<Cancha, Integer> {
}
