package com.la_cantera_reservas.services;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.la_cantera_reservas.model.Cliente;
import com.la_cantera_reservas.repository.ClienteRepository;

@Service
public class ServicioCliente {

    private static final Pattern CORREO = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    private static final Pattern TELEFONO = Pattern.compile("^[0-9]{7,15}$");
    private static final int CONTRASENA_MINIMA = 6;
    private static final int CONTRASENA_MAXIMA = 64;

    private final ClienteRepository clienteRepository;
    private final ServicioReserva servicioReserva;

    public ServicioCliente(ClienteRepository clienteRepository, ServicioReserva servicioReserva) {
        this.clienteRepository = clienteRepository;
        this.servicioReserva = servicioReserva;
    }

    /** Devuelve el cliente si el correo y la contraseña son correctos; si no, null. */
    public Cliente validarCredenciales(String correo, String contrasena) {
        if (correo == null || contrasena == null) {
            return null;
        }
        Cliente cliente = clienteRepository.findByCorreoIgnoreCase(correo.trim()).orElse(null);
        if (cliente != null && cliente.isTieneContrasena()
                && contrasena.equals(cliente.getContrasena())) {
            return cliente;
        }
        return null;
    }

    public Cliente registrarCliente(String nombre, String correo, String telefono,
                                    String contrasena, String confirmacion) {
        if (nombre == null || nombre.isBlank() || correo == null || correo.isBlank()
                || telefono == null || telefono.isBlank() || contrasena == null || contrasena.isEmpty()) {
            throw new IllegalArgumentException("Por favor ingrese todos los datos.");
        }
        nombre = nombre.trim();
        correo = correo.trim().toLowerCase();
        telefono = telefono.trim();

        if (!CORREO.matcher(correo).matches()) {
            throw new IllegalArgumentException("El correo no es válido.");
        }
        if (!TELEFONO.matcher(telefono).matches()) {
            throw new IllegalArgumentException("El teléfono debe tener solo números (entre 7 y 15 dígitos).");
        }
        validarContrasena(contrasena);
        if (!contrasena.equals(confirmacion)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden.");
        }
        if (clienteRepository.existsByCorreoIgnoreCase(correo)) {
            throw new IllegalArgumentException("Ese correo ya está registrado.");
        }
        return clienteRepository.save(new Cliente(nombre, correo, telefono, contrasena));
    }

    /** El admin asigna una contraseña nueva a una cuenta (por ejemplo, si el cliente la olvidó). */
    @Transactional
    public void restablecerContrasena(int id, String nuevaContrasena) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Este usuario no existe."));
        validarContrasena(nuevaContrasena);
        cliente.setContrasena(nuevaContrasena);
        clienteRepository.save(cliente);
    }

    private void validarContrasena(String contrasena) {
        if (contrasena == null || contrasena.length() < CONTRASENA_MINIMA) {
            throw new IllegalArgumentException(
                    "La contraseña debe tener al menos " + CONTRASENA_MINIMA + " caracteres.");
        }
        if (contrasena.length() > CONTRASENA_MAXIMA) {
            throw new IllegalArgumentException(
                    "La contraseña no puede tener más de " + CONTRASENA_MAXIMA + " caracteres.");
        }
    }

    @Transactional
    public boolean eliminarCliente(int id) {
        if (!clienteRepository.existsById(id)) {
            return false;
        }
        servicioReserva.cancelarReservasDeCliente(id);
        clienteRepository.deleteById(id);
        return true;
    }

    public Cliente buscar(int id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public boolean existe(int id) {
        return clienteRepository.existsById(id);
    }

    public List<Cliente> listar() {
        return clienteRepository.findAll(Sort.by("id"));
    }
}