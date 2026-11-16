package com.la_cantera_reservas.model;

import java.time.LocalDate;
import java.util.List;

import com.la_cantera_reservas.config.ConfiguracionTiempo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservas")
public class Reserva {

    public static final String DISPONIBLE = "Disponible";
    public static final String MANTENIMIENTO = "En mantenimiento";
    public static final String NO_OCUPABLE = "No se puede ocupar";
    public static final List<String> ESTADOS = List.of(DISPONIBLE, MANTENIMIENTO, NO_OCUPABLE);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cancha_id")
    private Cancha cancha;

    private LocalDate fecha;
    private String hora;
    private Integer capacidad;
    private String estado = DISPONIBLE;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public Reserva() {
    }

    public Reserva(Cancha cancha, LocalDate fecha, String hora, int capacidad) {
        this.cancha = cancha;
        this.fecha = fecha;
        this.hora = hora;
        this.capacidad = capacidad;
    }

    public boolean isCancelable() {
        return fecha != null && !LocalDate.now(ConfiguracionTiempo.ZONA).plusDays(1).isAfter(fecha);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cancha getCancha() {
        return cancha;
    }

    public void setCancha(Cancha cancha) {
        this.cancha = cancha;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
