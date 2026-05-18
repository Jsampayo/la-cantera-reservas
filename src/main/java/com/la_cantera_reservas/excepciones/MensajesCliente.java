package com.la_cantera_reservas.excepciones;

import javax.swing.JOptionPane;

public class MensajesCliente {
    // ✅ Todos static (antes mezclaba static e instancia)
    public static void autenticado(String nombre) {
        JOptionPane.showMessageDialog(null, "¡Bienvenido, " + nombre + "!", "Acceso", JOptionPane.INFORMATION_MESSAGE);
    }
    public static void noAutenticado() {
        JOptionPane.showMessageDialog(null, "Credenciales incorrectas.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    public static void reservaExitosa() {
        JOptionPane.showMessageDialog(null, "¡Su reserva ha sido registrada!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    public static void reservaOcupada() {
        JOptionPane.showMessageDialog(null, "Esta cancha ya ha sido reservada.", "No disponible", JOptionPane.WARNING_MESSAGE);
    }
    public static void reservaError() {
        JOptionPane.showMessageDialog(null, "Por favor ingrese todos los datos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}