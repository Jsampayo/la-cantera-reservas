package com.la_cantera_reservas.ui;

import com.la_cantera_reservas.excepciones.MensajeAdmin;
import com.la_cantera_reservas.model.Cancha;
import com.la_cantera_reservas.model.Reserva;
import com.la_cantera_reservas.services.ServicioAdmin;
import com.la_cantera_reservas.services.ServicioReserva;
import java.awt.*;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.la_cantera_reservas.services.ServicioCliente;
import java.util.List;


public class PanelAdmin extends JPanel {

    private boolean logueado = false;
    private CardLayout cl = new CardLayout();
    private JPanel contenedor;

    public PanelAdmin(VentanaPrincipal ventana) {
        setLayout(new BorderLayout());

        contenedor = new JPanel(cl);
        contenedor.add(crearVistaLogin(), "login");
        contenedor.add(crearVistaTabs(ventana), "panel");

        cl.show(contenedor, "login");

        add(contenedor, BorderLayout.CENTER);
    }

    private JPanel crearVistaLogin() {
        JPanel wrapper = new JPanel(new GridBagLayout());

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Acceso Administrador"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtId = new JTextField(14);
        JPasswordField txtPw = new JPasswordField(14);
        JLabel lblError = new JLabel(" ", SwingConstants.CENTER);
        lblError.setForeground(Color.RED);
        JButton btnLogin = new JButton("Iniciar sesión");

        g.gridx = 0;
        g.gridy = 0;
        form.add(new JLabel("ID Admin:"), g);
        g.gridx = 1;
        form.add(txtId, g);
        g.gridx = 0;
        g.gridy = 1;
        form.add(new JLabel("Contraseña:"), g);
        g.gridx = 1;
        form.add(txtPw, g);
        g.gridx = 0;
        g.gridy = 2;
        g.gridwidth = 2;
        form.add(btnLogin, g);
        g.gridy = 3;
        form.add(lblError, g);

        btnLogin.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                String pw = new String(txtPw.getPassword());
                logueado = ServicioAdmin.validarAdmin(id, pw);

                if (logueado) {
                    MensajeAdmin.autenticado();
                    cl.show(contenedor, "panel");
                    txtId.setText("");
                    txtPw.setText("");
                    lblError.setText(" ");
                } else {
                    lblError.setText("Credenciales incorrectas.");
                    MensajeAdmin.noAutenticado();
                }
            } catch (NumberFormatException ex) {
                lblError.setText("El ID debe ser un número.");
            }
        });

        wrapper.add(form);
        return wrapper;
    }

    private JPanel crearVistaTabs(VentanaPrincipal ventana) {
        JPanel p = new JPanel(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("🔍 Consultar", crearTabConsultar());
        tabs.addTab("➕ Crear reserva", crearTabCrear());
        tabs.addTab("🗑️  Eliminar reserva", crearTabEliminar());
        tabs.addTab("📋 Asignar reserva", crearTabAsignar());
        tabs.addTab("👤 Usuarios", crearTabUsuarios());

        JButton btnSalir = new JButton("← Cerrar sesión");
        btnSalir.addActionListener(e -> {
            logueado = false;
            cl.show(contenedor, "login");
            ventana.mostrar("inicio");
        });

        p.add(tabs, BorderLayout.CENTER);
        p.add(btnSalir, BorderLayout.SOUTH);
        return p;
    }

    private JPanel crearTabConsultar() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtIdCliente = new JTextField(8);
        JButton btnBuscar = new JButton("Buscar");
        top.add(new JLabel("ID del cliente:"));
        top.add(txtIdCliente);
        top.add(btnBuscar);
        p.add(top, BorderLayout.NORTH);

        DefaultTableModel modelo = tablaVacia("ID Reserva", "Cancha", "Hora", "Fecha", "Capacidad");
        JTable tabla = new JTable(modelo);
        p.add(new JScrollPane(tabla), BorderLayout.CENTER);

        JButton btnCancelar = new JButton("Cancelar reserva activa de este cliente");
        btnCancelar.setEnabled(false);
        p.add(btnCancelar, BorderLayout.SOUTH);

        btnBuscar.addActionListener(e -> {
            modelo.setRowCount(0);
            btnCancelar.setEnabled(false);
            try {
                int id = Integer.parseInt(txtIdCliente.getText().trim());
                List<Reserva> lista = ServicioReserva.getReservasDeCliente(id);
if (!lista.isEmpty()) {
    for (Reserva r : lista)
        modelo.addRow(new Object[]{r.getIdReserva(), r.getCancha(), r.getHora(), r.getFecha(), r.getCapacidad()});
    btnCancelar.setEnabled(true);
    btnCancelar.putClientProperty("idCliente", id);
} else {
    MensajeAdmin.clienteNoExiste();
}
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> {
            int idCliente = (int) btnCancelar.getClientProperty("idCliente");
            if (JOptionPane.showConfirmDialog(this,
                    "¿Cancelar la reserva del cliente " + idCliente + "?",
                    "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (ServicioReserva.cancelarReservaActiva(idCliente)) {
                    JOptionPane.showMessageDialog(this, "Reserva cancelada y devuelta al pool.");
                    modelo.setRowCount(0);
                    btnCancelar.setEnabled(false);
                }
            }
        });
        return p;
    }

    private JPanel crearTabCrear() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        JComboBox<Cancha> cmbCancha = new JComboBox<>(Cancha.getCanchas().toArray(new Cancha[0]));
        JComboBox<String> cmbHora = new JComboBox<>(ServicioReserva.horarioCancha);
        JSpinner spinAnio = new JSpinner(new SpinnerNumberModel(2026, 2026, 2030, 1));
        JSpinner spinMes = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
        JSpinner spinDia = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
        JTextField txtCap = new JTextField(6);
        txtCap.setEditable(false);
        actualizarCapacidad(cmbCancha, txtCap);
        cmbCancha.addActionListener(e -> actualizarCapacidad(cmbCancha, txtCap));

        JButton btnCrear = new JButton("Crear reserva disponible");

        g.gridx = 0;
        g.gridy = 0;
        form.add(new JLabel("Cancha:"), g);
        g.gridx = 1;
        g.gridwidth = 3;
        form.add(cmbCancha, g);
        g.gridwidth = 1;
        g.gridx = 0;
        g.gridy = 1;
        form.add(new JLabel("Hora:"), g);
        g.gridx = 1;
        g.gridwidth = 3;
        form.add(cmbHora, g);
        g.gridwidth = 1;
        g.gridx = 0;
        g.gridy = 2;
        form.add(new JLabel("Fecha:"), g);
        g.gridx = 1;
        form.add(spinAnio, g);
        g.gridx = 2;
        form.add(spinMes, g);
        g.gridx = 3;
        form.add(spinDia, g);
        g.gridx = 1;
        g.gridy = 3;
        form.add(new JLabel("año"), g);
        g.gridx = 2;
        form.add(new JLabel("mes"), g);
        g.gridx = 3;
        form.add(new JLabel("día"), g);
        g.gridx = 0;
        g.gridy = 4;
        form.add(new JLabel("Capacidad:"), g);
        g.gridx = 1;
        g.gridwidth = 3;
        form.add(txtCap, g);
        g.gridx = 0;
        g.gridy = 5;
        g.gridwidth = 4;
        form.add(btnCrear, g);
        p.add(form, BorderLayout.NORTH);

        DefaultTableModel modeloTabla = tablaVacia("ID", "Cancha", "Hora", "Fecha", "Capacidad");
        JTable tabla = new JTable(modeloTabla);
        JButton btnRef = new JButton("🔄 Actualizar lista");
        btnRef.addActionListener(e -> refrescarTablaDisp(modeloTabla));
        JPanel centro = new JPanel(new BorderLayout(4, 4));
        centro.add(new JLabel("Reservas disponibles actuales:"), BorderLayout.NORTH);
        centro.add(new JScrollPane(tabla), BorderLayout.CENTER);
        centro.add(btnRef, BorderLayout.SOUTH);
        p.add(centro, BorderLayout.CENTER);

        btnCrear.addActionListener(e -> {
            try {
                Cancha cancha = (Cancha) cmbCancha.getSelectedItem();
                String hora = (String) cmbHora.getSelectedItem();
                String fecha = String.format("%04d-%02d-%02d",
                        (int) spinAnio.getValue(), (int) spinMes.getValue(), (int) spinDia.getValue());
                int cap = Integer.parseInt(txtCap.getText().trim());

                if (ServicioReserva.crearReserva(hora, fecha, cap, cancha.getNombre())) {
                    JOptionPane.showMessageDialog(this, "Reserva creada:\n" + cancha + " | " + hora + " | " + fecha);
                    refrescarTablaDisp(modeloTabla);
                } else {
                    JOptionPane.showMessageDialog(this, "Datos inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Capacidad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return p;
    }

    private JPanel crearTabEliminar() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        DefaultTableModel modeloDisp = tablaVacia("ID", "Cancha", "Hora", "Fecha", "Capacidad");
        JTable tablaDisp = new JTable(modeloDisp);
        JButton btnRefDisp = new JButton("🔄 Actualizar disponibles");
        btnRefDisp.addActionListener(e -> refrescarTablaDisp(modeloDisp));
        JPanel panDisp = new JPanel(new BorderLayout(4, 4));
        panDisp.setBorder(BorderFactory.createTitledBorder("Reservas DISPONIBLES"));
        panDisp.add(new JScrollPane(tablaDisp), BorderLayout.CENTER);
        panDisp.add(btnRefDisp, BorderLayout.SOUTH);

        DefaultTableModel modeloActivas = tablaVacia("ID Cliente", "Cancha", "Hora", "Fecha", "Capacidad");
        JTable tablaActivas = new JTable(modeloActivas);
        JButton btnRefActivas = new JButton("🔄 Actualizar activas");
        btnRefActivas.addActionListener(e -> refrescarTablaActivas(modeloActivas));
        JPanel panActivas = new JPanel(new BorderLayout(4, 4));
        panActivas.setBorder(BorderFactory.createTitledBorder("Reservas ACTIVAS (por cliente)"));
        panActivas.add(new JScrollPane(tablaActivas), BorderLayout.CENTER);
        panActivas.add(btnRefActivas, BorderLayout.SOUTH);

        JButton btnEliminarDisp = new JButton("🗑️ Eliminar disponible seleccionada");
        JButton btnCancelarActiva = new JButton("❌ Cancelar activa seleccionada");

        btnEliminarDisp.addActionListener(e -> {
            int fila = tablaDisp.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona una fila.");
                return;
            }
            int id = (int) modeloDisp.getValueAt(fila, 0);
            if (JOptionPane.showConfirmDialog(this, "¿Eliminar reserva #" + id + "?",
                    "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (ServicioReserva.eliminarReservaDisponible(id)) {
                    refrescarTablaDisp(modeloDisp);
                } else {
                    MensajeAdmin.reservaError();
                }
            }
        });

        btnCancelarActiva.addActionListener(e -> {
            int fila = tablaActivas.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona una fila.");
                return;
            }
            int idCliente = (int) modeloActivas.getValueAt(fila, 0);
            if (JOptionPane.showConfirmDialog(this, "¿Cancelar reserva del cliente " + idCliente + "?",
                    "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (ServicioReserva.cancelarReservaActiva(idCliente)) {
                    refrescarTablaActivas(modeloActivas);
                    refrescarTablaDisp(modeloDisp);
                } else {
                    MensajeAdmin.reservaError();
                }
            }
        });

        JPanel botones = new JPanel(new GridLayout(2, 1, 4, 4));
        botones.add(btnEliminarDisp);
        botones.add(btnCancelarActiva);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panDisp, panActivas);
        split.setResizeWeight(0.5);
        p.add(split, BorderLayout.CENTER);
        p.add(botones, BorderLayout.SOUTH);
        return p;
    }

    private void actualizarCapacidad(JComboBox<Cancha> cmb, JTextField txt) {
        Cancha c = (Cancha) cmb.getSelectedItem();
        if (c == null) {
            return;
        }
        int cap;
        if (c.getTipo().equals("Fútbol 7")) {
            cap = 14;
        } else if (c.getTipo().equals("Fútbol 11")) {
            cap = 22;
        } else {
            cap = 10;
        }
        txt.setText(String.valueOf(cap));
    }

    private DefaultTableModel tablaVacia(String... cols) {
        return new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
    }

    private void refrescarTablaDisp(DefaultTableModel m) {
        m.setRowCount(0);
        for (Reserva r : ServicioReserva.getReservasDisponibles()) {
            m.addRow(new Object[]{r.getIdReserva(), r.getCancha(), r.getHora(), r.getFecha(), r.getCapacidad()});
        }
    }

    private void refrescarTablaActivas(DefaultTableModel m) {
    m.setRowCount(0);
    for (Map.Entry<Integer, List<Reserva>> e : ServicioReserva.getReservasActivas().entrySet()) {
        for (Reserva r : e.getValue()) {
            m.addRow(new Object[]{e.getKey(), r.getCancha(), r.getHora(), r.getFecha(), r.getCapacidad()});
        }
    }
}

    private JPanel crearTabUsuarios() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        DefaultTableModel modelo = tablaVacia("ID", "Nombre");
        JTable tabla = new JTable(modelo);

        JButton btnRefrescar = new JButton("🔄 Actualizar lista");
        btnRefrescar.addActionListener(e -> {
            modelo.setRowCount(0);
            for (var entry : ServicioCliente.getClientesRegistrados().entrySet()) {
                modelo.addRow(new Object[]{entry.getKey(), entry.getValue().getNombre()});
            }
        });

        JButton btnEliminar = new JButton("🗑️ Eliminar usuario seleccionado");
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona un usuario.");
                return;
            }
            int id = (int) modelo.getValueAt(fila, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Eliminar al usuario con ID " + id + "? Esto cancelará su reserva si tiene una.",
                    "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                ServicioReserva.cancelarReservaActiva(id);
                if (ServicioCliente.eliminarCliente(id)) {
                    JOptionPane.showMessageDialog(this, "Usuario eliminado.");
                    modelo.setRowCount(0);
                    for (var entry : ServicioCliente.getClientesRegistrados().entrySet()) {
                        modelo.addRow(new Object[]{entry.getKey(), entry.getValue().getNombre()});
                    }
                }
            }
        });

        JPanel sur = new JPanel(new GridLayout(1, 2, 4, 4));
        sur.add(btnRefrescar);
        sur.add(btnEliminar);

        p.add(new JLabel("Usuarios registrados:"), BorderLayout.NORTH);
        p.add(new JScrollPane(tabla), BorderLayout.CENTER);
        p.add(sur, BorderLayout.SOUTH);
        return p;
    }

    private JPanel crearTabAsignar() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        DefaultTableModel modeloDisp = tablaVacia("ID", "Cancha", "Hora", "Fecha", "Capacidad");
        JTable tablaDisp = new JTable(modeloDisp);

        JButton btnRef = new JButton("🔄 Actualizar disponibles");
        btnRef.addActionListener(e -> refrescarTablaDisp(modeloDisp));

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtIdCliente = new JTextField(8);
        JButton btnAsignar = new JButton("✅ Asignar reserva seleccionada a este cliente");
        form.add(new JLabel("ID del cliente:"));
        form.add(txtIdCliente);
        form.add(btnAsignar);

        btnAsignar.addActionListener(e -> {
            int fila = tablaDisp.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona una reserva disponible.");
                return;
            }
            try {
                int idCliente = Integer.parseInt(txtIdCliente.getText().trim());
                int idReserva = (int) modeloDisp.getValueAt(fila, 0);

                if (!ServicioCliente.getClientesRegistrados().containsKey(idCliente)) {
                    JOptionPane.showMessageDialog(this, "No existe un cliente con ese ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (ServicioReserva.reservarParaCliente(idReserva, idCliente)) {
                    JOptionPane.showMessageDialog(this, "Reserva asignada con éxito al cliente " + idCliente + ".");
                    refrescarTablaDisp(modeloDisp);
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo asignar la reserva.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID del cliente debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        p.add(form, BorderLayout.NORTH);
        p.add(new JScrollPane(tablaDisp), BorderLayout.CENTER);
        p.add(btnRef, BorderLayout.SOUTH);
        return p;
    }

}
