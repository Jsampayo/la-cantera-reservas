package com.la_cantera_reservas.excepciones;

import javax.swing.JOptionPane;

public class MensajeAdmin {
   public static void autenticado() {
        JOptionPane.showMessageDialog(null, "¡Bienvenido, Administrador!", "Acceso", JOptionPane.INFORMATION_MESSAGE);
    }
    public static void noAutenticado() {
        JOptionPane.showMessageDialog(null, "Credenciales incorrectas.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    public static void cambioReservaOk() {
        JOptionPane.showMessageDialog(null, "La reserva fue modificada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    public static void reservaError() {
        JOptionPane.showMessageDialog(null, "Esta reserva no pudo ser modificada.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    public static void clienteNoExiste() {
        JOptionPane.showMessageDialog(null, "Este usuario no existe.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}