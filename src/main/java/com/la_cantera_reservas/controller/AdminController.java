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

import com.la_cantera_reservas.model.Reserva;
import com.la_cantera_reservas.services.ServicioAdmin;
import com.la_cantera_reservas.services.ServicioCliente;
import com.la_cantera_reservas.services.ServicioReserva;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ServicioAdmin servicioAdmin;
    private final ServicioReserva servicioReserva;
    private final ServicioCliente servicioCliente;

    public AdminController(ServicioAdmin servicioAdmin, ServicioReserva servicioReserva,
                           ServicioCliente servicioCliente) {
        this.servicioAdmin = servicioAdmin;
        this.servicioReserva = servicioReserva;
        this.servicioCliente = servicioCliente;
    }

    @GetMapping
    public String entrar(HttpSession session) {
        if (esAdmin(session)) {
            return "redirect:/admin/activas";
        }
        return "admin";
    }

    @PostMapping("/login")
    public String login(@RequestParam int id, @RequestParam String password,
                        HttpSession session, RedirectAttributes redirect) {
        if (!servicioAdmin.validarAdmin(id, password)) {
            redirect.addFlashAttribute("error", "Credenciales incorrectas.");
            return "redirect:/admin";
        }
        session.setAttribute("admin", true);
        redirect.addFlashAttribute("mensaje", "¡Bienvenido, Administrador!");
        return "redirect:/admin/activas";
    }

    @GetMapping("/salir")
    public String salir(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/activas")
    public String activas(@RequestParam(required = false) Integer clienteId,
                          HttpSession session, Model model) {
        if (!esAdmin(session)) {
            return "redirect:/admin";
        }
        if (clienteId != null && !servicioCliente.existe(clienteId)) {
            model.addAttribute("error", "Este usuario no existe.");
        }
        model.addAttribute("clienteId", clienteId);
        model.addAttribute("activas", servicioReserva.activas(clienteId));
        return "admin-activas";
    }

    @PostMapping("/reservas/{idReserva}/cancelar")
    public String cancelar(@PathVariable int idReserva,
                           @RequestParam(required = false) String clienteId,
                           HttpSession session, RedirectAttributes redirect) {
        if (!esAdmin(session)) {
            return "redirect:/admin";
        }
        if (servicioReserva.cancelarReserva(idReserva)) {
            redirect.addFlashAttribute("mensaje", "La reserva fue cancelada y quedó disponible de nuevo.");
        } else {
            redirect.addFlashAttribute("error", "Esta reserva no pudo ser modificada.");
        }
        return volver("/admin/activas", "clienteId", clienteId, redirect);
    }

    @GetMapping("/crear")
    public String crearPagina(@RequestParam(required = false)
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                              HttpSession session, Model model) {
        if (!esAdmin(session)) {
            return "redirect:/admin";
        }
        if (fecha == null) {
            fecha = servicioReserva.hoy();
        }
        model.addAttribute("fecha", fecha);
        model.addAttribute("canchas", servicioReserva.getCanchas());
        model.addAttribute("horarios", ServicioReserva.HORARIOS);
        return "admin-crear";
    }

    @PostMapping("/crear")
    public String crear(@RequestParam int canchaId, @RequestParam String hora,
                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                        @RequestParam(required = false) Integer capacidad,
                        HttpSession session, RedirectAttributes redirect) {
        if (!esAdmin(session)) {
            return "redirect:/admin";
        }
        try {
            servicioReserva.crearReserva(canchaId, hora, fecha, capacidad);
            redirect.addFlashAttribute("mensaje", "La reserva fue creada con éxito.");
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return volver("/admin/crear", "fecha", fecha.toString(), redirect);
    }

    @GetMapping("/modificar")
    public String modificarPagina(@RequestParam(required = false)
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                                  HttpSession session, Model model) {
        if (!esAdmin(session)) {
            return "redirect:/admin";
        }
        if (fecha == null) {
            fecha = servicioReserva.hoy();
        }
        model.addAttribute("fecha", fecha);
        model.addAttribute("estados", Reserva.ESTADOS);
        model.addAttribute("reservas", servicioReserva.sinClienteDelDia(fecha));
        return "admin-modificar";
    }

    @PostMapping("/reservas/{idReserva}/estado")
    public String cambiarEstado(@PathVariable int idReserva, @RequestParam String estado,
                                @RequestParam(required = false) String fecha,
                                HttpSession session, RedirectAttributes redirect) {
        if (!esAdmin(session)) {
            return "redirect:/admin";
        }
        if (servicioReserva.cambiarEstado(idReserva, estado)) {
            redirect.addFlashAttribute("mensaje", "La reserva fue modificada con éxito.");
        } else {
            redirect.addFlashAttribute("error", "Esta reserva no pudo ser modificada.");
        }
        return volver("/admin/modificar", "fecha", fecha, redirect);
    }

    @GetMapping("/asignar")
    public String asignarPagina(@RequestParam(required = false)
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                                HttpSession session, Model model) {
        if (!esAdmin(session)) {
            return "redirect:/admin";
        }
        if (fecha == null) {
            fecha = servicioReserva.hoy();
        }
        model.addAttribute("fecha", fecha);
        model.addAttribute("disponibles", servicioReserva.disponiblesDelDia(fecha));
        return "admin-asignar";
    }

    @PostMapping("/reservas/{idReserva}/asignar")
    public String asignar(@PathVariable int idReserva, @RequestParam int clienteId,
                          @RequestParam(required = false) String fecha,
                          HttpSession session, RedirectAttributes redirect) {
        if (!esAdmin(session)) {
            return "redirect:/admin";
        }
        if (!servicioCliente.existe(clienteId)) {
            redirect.addFlashAttribute("error", "Este usuario no existe.");
        } else if (servicioReserva.reservar(idReserva, clienteId)) {
            redirect.addFlashAttribute("mensaje", "La reserva fue modificada con éxito.");
        } else {
            redirect.addFlashAttribute("error", "Esta reserva no pudo ser modificada.");
        }
        return volver("/admin/asignar", "fecha", fecha, redirect);
    }

    @GetMapping("/usuarios")
    public String usuarios(HttpSession session, Model model) {
        if (!esAdmin(session)) {
            return "redirect:/admin";
        }
        model.addAttribute("clientes", servicioCliente.listar());
        return "admin-usuarios";
    }

    @PostMapping("/usuarios/{id}/eliminar")
    public String eliminarUsuario(@PathVariable int id, HttpSession session,
                                  RedirectAttributes redirect) {
        if (!esAdmin(session)) {
            return "redirect:/admin";
        }
        if (servicioCliente.eliminarCliente(id)) {
            redirect.addFlashAttribute("mensaje", "Usuario eliminado. Sus reservas quedaron disponibles.");
        } else {
            redirect.addFlashAttribute("error", "Este usuario no existe.");
        }
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/usuarios/{id}/contrasena")
    public String restablecerContrasena(@PathVariable int id, @RequestParam String nueva,
                                        HttpSession session, RedirectAttributes redirect) {
        if (!esAdmin(session)) {
            return "redirect:/admin";
        }
        try {
            servicioCliente.restablecerContrasena(id, nueva);
            redirect.addFlashAttribute("mensaje", "Contraseña actualizada para el usuario " + id + ".");
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/usuarios";
    }

    private boolean esAdmin(HttpSession session) {
        return session.getAttribute("admin") != null;
    }

    private String volver(String ruta, String parametro, String valor, RedirectAttributes redirect) {
        if (valor != null && !valor.isBlank()) {
            redirect.addAttribute(parametro, valor);
        }
        return "redirect:" + ruta;
    }
}
