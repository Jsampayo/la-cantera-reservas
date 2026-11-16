package com.la_cantera_reservas.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.la_cantera_reservas.model.Cliente;
import com.la_cantera_reservas.services.ServicioCliente;
import com.la_cantera_reservas.services.ServicioReserva;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    private final ServicioCliente servicioCliente;
    private final ServicioReserva servicioReserva;

    public ClienteController(ServicioCliente servicioCliente, ServicioReserva servicioReserva) {
        this.servicioCliente = servicioCliente;
        this.servicioReserva = servicioReserva;
    }

    @GetMapping
    public String entrar(HttpSession session) {
        if (session.getAttribute("clienteId") != null) {
            return "redirect:/cliente/reservar";
        }
        return "cliente";
    }

    @PostMapping("/login")
    public String login(@RequestParam String correo, @RequestParam String contrasena,
                        HttpSession session, RedirectAttributes redirect) {
        Cliente cliente = servicioCliente.validarCredenciales(correo, contrasena);
        if (cliente == null) {
            redirect.addFlashAttribute("error", "Credenciales incorrectas.");
            return "redirect:/cliente";
        }
        session.setAttribute("clienteId", cliente.getId());
        redirect.addFlashAttribute("mensaje", "¡Bienvenido, " + cliente.getNombre() + "!");
        return "redirect:/cliente/reservar";
    }

    @GetMapping("/registro")
    public String registro() {
        return "cliente-registro";
    }

    @PostMapping("/registro")
    public String registrar(@RequestParam String nombre, @RequestParam String correo,
                            @RequestParam String telefono, @RequestParam String contrasena,
                            @RequestParam String confirmar, RedirectAttributes redirect) {
        try {
            Cliente cliente = servicioCliente.registrarCliente(nombre, correo, telefono,
                    contrasena, confirmar);
            redirect.addFlashAttribute("mensaje", "Registro exitoso. Tu ID de usuario es "
                    + cliente.getId() + ". Ya puedes iniciar sesión con tu correo y tu contraseña.");
            return "redirect:/cliente";
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("error", e.getMessage());
            redirect.addFlashAttribute("nombre", nombre);
            redirect.addFlashAttribute("correo", correo);
            redirect.addFlashAttribute("telefono", telefono);
            return "redirect:/cliente/registro";
        }
    }

    @GetMapping("/reservar")
    public String buscar(@RequestParam(required = false)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                         HttpSession session, Model model) {
        Cliente cliente = clienteActual(session);
        if (cliente == null) {
            return "redirect:/cliente";
        }
        if (fecha == null) {
            fecha = servicioReserva.hoy();
        }
        model.addAttribute("cliente", cliente);
        model.addAttribute("fecha", fecha);
        model.addAttribute("hoy", servicioReserva.hoy());
        model.addAttribute("disponibles", servicioReserva.disponiblesDelDia(fecha));
        return "cliente-reservar";
    }

    @PostMapping("/reservar/{idReserva}")
    public String reservar(@PathVariable int idReserva, @RequestParam String fecha,
                           HttpSession session, RedirectAttributes redirect) {
        Cliente cliente = clienteActual(session);
        if (cliente == null) {
            return "redirect:/cliente";
        }
        if (servicioReserva.reservar(idReserva, cliente.getId())) {
            redirect.addFlashAttribute("mensaje", "¡Su reserva ha sido registrada!");
        } else {
            redirect.addFlashAttribute("error", "Esta cancha ya ha sido reservada.");
        }
        redirect.addAttribute("fecha", fecha);
        return "redirect:/cliente/reservar";
    }

    @GetMapping("/mis-reservas")
    public String misReservas(HttpSession session, Model model) {
        Cliente cliente = clienteActual(session);
        if (cliente == null) {
            return "redirect:/cliente";
        }
        model.addAttribute("cliente", cliente);
        model.addAttribute("misReservas", servicioReserva.getReservasDeCliente(cliente.getId()));
        return "cliente-mis-reservas";
    }

    @PostMapping("/reservas/{idReserva}/cancelar")
    public String cancelar(@PathVariable int idReserva, HttpSession session,
                           RedirectAttributes redirect) {
        Cliente cliente = clienteActual(session);
        if (cliente == null) {
            return "redirect:/cliente";
        }
        try {
            servicioReserva.cancelarReservaDeCliente(idReserva, cliente.getId());
            redirect.addFlashAttribute("mensaje", "Tu reserva fue cancelada.");
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/cliente/mis-reservas";
    }

    @GetMapping("/salir")
    public String salir(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    private Cliente clienteActual(HttpSession session) {
        Integer clienteId = (Integer) session.getAttribute("clienteId");
        if (clienteId == null) {
            return null;
        }
        Cliente cliente = servicioCliente.buscar(clienteId);
        if (cliente == null) {
            session.invalidate();
        }
        return cliente;
    }
}
