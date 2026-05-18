package com.la_cantera_reservas.ui;

import com.la_cantera_reservas.excepciones.MensajesCliente;
import com.la_cantera_reservas.model.Cliente;
import com.la_cantera_reservas.model.Reserva;
import com.la_cantera_reservas.services.ServicioCliente;
import com.la_cantera_reservas.services.ServicioReserva;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Reemplaza VistaCliente + ServicioCliente (toda la lógica de consola).
 * Usa CardLayout interno para sub-pantallas del cliente.
 */
public class PanelCliente extends JPanel {

    private final VentanaPrincipal ventana;
    private final CardLayout       cl  = new CardLayout();
    private final JPanel           sub = new JPanel(cl);
    private Cliente clienteActual;

    // ─── sub-paneles ───────────────────────────────────────────────
    private JPanel crearSubMenuLogin() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.fill   = GridBagConstraints.HORIZONTAL;
        g.gridx  = 0;

        JLabel lbl = new JLabel("Menú Cliente", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        g.gridy = 0; p.add(lbl, g);

        JButton btnLogin    = new JButton("1. Iniciar Sesión");
        JButton btnRegistro = new JButton("2. Registrarse");
        JButton btnVolver   = new JButton("3. Volver al menú principal");

        btnLogin   .addActionListener(e -> cl.show(sub, "login"));
        btnRegistro.addActionListener(e -> cl.show(sub, "registro"));
        btnVolver  .addActionListener(e -> ventana.mostrar("inicio"));

        g.gridy = 1; p.add(btnLogin,    g);
        g.gridy = 2; p.add(btnRegistro, g);
        g.gridy = 3; p.add(btnVolver,   g);
        return p;
    }

    private JPanel crearSubLogin() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.fill   = GridBagConstraints.HORIZONTAL;

        JLabel   lblId   = new JLabel("ID de usuario:");
        JTextField txtId = new JTextField(15);
        JLabel   lblPw   = new JLabel("Contraseña:");
        JPasswordField txtPw = new JPasswordField(15);
        JButton  btnOk   = new JButton("Ingresar");
        JButton  btnBack = new JButton("Volver");

        g.gridx = 0; g.gridy = 0; p.add(lblId,  g);
        g.gridx = 1;               p.add(txtId,  g);
        g.gridx = 0; g.gridy = 1; p.add(lblPw,  g);
        g.gridx = 1;               p.add(txtPw,  g);
        g.gridx = 0; g.gridy = 2; g.gridwidth = 2; p.add(btnOk,  g);
        g.gridy = 3;               p.add(btnBack, g);

        btnOk.addActionListener(e -> {
            try {
                int    id = Integer.parseInt(txtId.getText().trim());
                String pw = new String(txtPw.getPassword());
                // ✅ validarCredenciales() reemplaza inicioSesionCliente(Scanner)
                clienteActual = ServicioCliente.validarCredenciales(id, pw);
                if (clienteActual != null) {
                    MensajesCliente.autenticado(clienteActual.getNombre());
                    cl.show(sub, "menuLogueado");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnBack.addActionListener(e -> cl.show(sub, "menuLogin"));
        return p;
    }

    private JPanel crearSubRegistro() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.fill   = GridBagConstraints.HORIZONTAL;

        JTextField   txtNombre = new JTextField(15);
        JTextField   txtId     = new JTextField(15);
        JPasswordField txtPw   = new JPasswordField(15);
        JButton btnOk   = new JButton("Registrar");
        JButton btnBack = new JButton("Volver");

        g.gridx=0; g.gridy=0; p.add(new JLabel("Nombre:"),     g);
        g.gridx=1;             p.add(txtNombre,                  g);
        g.gridx=0; g.gridy=1; p.add(new JLabel("ID:"),          g);
        g.gridx=1;             p.add(txtId,                      g);
        g.gridx=0; g.gridy=2; p.add(new JLabel("Contraseña:"),  g);
        g.gridx=1;             p.add(txtPw,                      g);
        g.gridx=0; g.gridy=3; g.gridwidth=2; p.add(btnOk,       g);
        g.gridy=4;             p.add(btnBack, g);

        btnOk.addActionListener(e -> {
            // ✅ registrarCliente() ya no tiene el doble nextLine() bug
            try {
                String nombre = txtNombre.getText().trim();
                int    id     = Integer.parseInt(txtId.getText().trim());
                String pw     = new String(txtPw.getPassword());
                if (nombre.isEmpty() || pw.isEmpty()) { MensajesCliente.reservaError(); return; }
                if (ServicioCliente.registrarCliente(nombre, pw, id)) {
                    JOptionPane.showMessageDialog(this, "¡Registrado con éxito! Ya puedes iniciar sesión.");
                    cl.show(sub, "menuLogin");
                } else {
                    JOptionPane.showMessageDialog(this, "Ese ID ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnBack.addActionListener(e -> cl.show(sub, "menuLogin"));
        return p;
    }

    private JPanel crearSubMenuLogueado() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.fill   = GridBagConstraints.HORIZONTAL;
        g.gridx  = 0;

        JButton btnReservar   = new JButton("1. Realizar una reserva");
        JButton btnMisReservas = new JButton("2. Ver mis reservas activas");
        JButton btnSalir      = new JButton("3. Cerrar sesión");

        btnReservar   .addActionListener(e -> cl.show(sub, "filtro"));
        btnMisReservas.addActionListener(e -> { actualizarMisReservas(); cl.show(sub, "misReservas"); });
        btnSalir      .addActionListener(e -> { clienteActual = null; cl.show(sub, "menuLogin"); });

        g.gridy=0; p.add(new JLabel("── Menú Cliente ──", SwingConstants.CENTER), g);
        g.gridy=1; p.add(btnReservar,    g);
        g.gridy=2; p.add(btnMisReservas, g);
        g.gridy=3; p.add(btnSalir,       g);
        return p;
    }

    // ─── Filtro de reservas ────────────────────────────────────────
    private DefaultTableModel modeloTabla;
    private JTextField txtDia;

    private JPanel crearSubFiltro() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Día del mes (ej: 18):"));
        txtDia = new JTextField(4);
        JButton btnBuscar = new JButton("Buscar");
        top.add(txtDia); top.add(btnBuscar);
        p.add(top, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Hora", "Fecha", "Capacidad"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modeloTabla);
        p.add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel bot = new JPanel(new FlowLayout());
        JButton btnReservar = new JButton("Reservar seleccionada");
        JButton btnVolver   = new JButton("Volver");
        bot.add(btnReservar); bot.add(btnVolver);
        p.add(bot, BorderLayout.SOUTH);

        // ✅ filtrarPorDia() reemplaza filtro() que usaba Scanner y contaba mal
        btnBuscar.addActionListener(e -> {
            modeloTabla.setRowCount(0);
            String dia = txtDia.getText().trim();
            if (dia.isEmpty()) return;
            List<Reserva> res = ServicioReserva.filtrarPorDia(dia);
            if (res.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay reservas disponibles para ese día.");
            }
            for (Reserva r : res) {
                modeloTabla.addRow(new Object[]{r.getIdReserva(), r.getHora(), r.getFecha(), r.getCapacidad()});
            }
        });

        // ✅ reservar() reemplaza el método recursivo que llamaba validacionCliente()
        btnReservar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) { JOptionPane.showMessageDialog(this, "Selecciona una reserva."); return; }
            int idReserva = (int) modeloTabla.getValueAt(fila, 0);
            int confirm   = JOptionPane.showConfirmDialog(this, "¿Confirmar reserva?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (ServicioReserva.reservar(idReserva, clienteActual.getId())) {
                    MensajesCliente.reservaExitosa();
                    cl.show(sub, "menuLogueado");
                } else {
                    MensajesCliente.reservaOcupada();
                }
            }
        });
        btnVolver.addActionListener(e -> cl.show(sub, "menuLogueado"));
        return p;
    }

    // ─── Mis reservas ──────────────────────────────────────────────
    private DefaultTableModel modeloMisReservas;

    private JPanel crearSubMisReservas() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        modeloMisReservas = new DefaultTableModel(new String[]{"ID", "Hora", "Fecha", "Capacidad"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        p.add(new JScrollPane(new JTable(modeloMisReservas)), BorderLayout.CENTER);
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> cl.show(sub, "menuLogueado"));
        p.add(btnVolver, BorderLayout.SOUTH);
        return p;
    }

    private void actualizarMisReservas() {
        modeloMisReservas.setRowCount(0);
        if (clienteActual == null) return;
        Reserva r = ServicioReserva.getReservaDeCliente(clienteActual.getId());
        if (r != null) {
            modeloMisReservas.addRow(new Object[]{r.getIdReserva(), r.getHora(), r.getFecha(), r.getCapacidad()});
        }
    }

    // ─── Constructor ───────────────────────────────────────────────
    public PanelCliente(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());

        sub.add(crearSubMenuLogin(),    "menuLogin");
        sub.add(crearSubLogin(),        "login");
        sub.add(crearSubRegistro(),     "registro");
        sub.add(crearSubMenuLogueado(), "menuLogueado");
        sub.add(crearSubFiltro(),       "filtro");
        sub.add(crearSubMisReservas(),  "misReservas");

        add(sub, BorderLayout.CENTER);
    }
}
