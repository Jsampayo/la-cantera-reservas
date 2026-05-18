package com.la_cantera_reservas.ui;

import java.awt.*;
import javax.swing.*;

public class VentanaPrincipal extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel     contenedor = new JPanel(cardLayout);

    // Paneles de la aplicación
    private final PanelInicio   panelInicio;
    private final PanelCliente  panelCliente;
    private final PanelAdmin    panelAdmin;

    public VentanaPrincipal() {
        setTitle("La Cantera Reservas");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panelInicio  = new PanelInicio(this);
        panelCliente = new PanelCliente(this);
        panelAdmin   = new PanelAdmin(this);

        contenedor.add(panelInicio,  "inicio");
        contenedor.add(panelCliente, "cliente");
        contenedor.add(panelAdmin,   "admin");

        add(contenedor);
        mostrar("inicio");
    }

    /** Navega a la pantalla indicada */
    public void mostrar(String pantalla) {
        cardLayout.show(contenedor, pantalla);
    }
}