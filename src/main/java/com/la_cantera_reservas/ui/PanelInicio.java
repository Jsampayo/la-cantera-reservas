package com.la_cantera_reservas.ui;

import java.awt.*;
import javax.swing.*;

public class PanelInicio extends JPanel {

    public PanelInicio(VentanaPrincipal ventana) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill   = GridBagConstraints.HORIZONTAL;
        gbc.gridx  = 0;

        JLabel titulo = new JLabel("⚽ La Cantera Reservas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridy = 0;
        add(titulo, gbc);

        JButton btnCliente = new JButton("1. Acceder como Cliente");
        JButton btnAdmin   = new JButton("2. Acceder como Administrador");
        JButton btnSalir   = new JButton("3. Salir");

        btnCliente.addActionListener(e -> ventana.mostrar("cliente"));
        btnAdmin  .addActionListener(e -> ventana.mostrar("admin"));
        btnSalir  .addActionListener(e -> System.exit(0));

        gbc.gridy = 1; add(btnCliente, gbc);
        gbc.gridy = 2; add(btnAdmin,   gbc);
        gbc.gridy = 3; add(btnSalir,   gbc);
    }
}
