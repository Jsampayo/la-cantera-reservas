package com.la_cantera_reservas.services;

import com.la_cantera_reservas.model.Cliente;
import com.la_cantera_reservas.model.Reserva;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PersistenciaTxt {

    private static final String DIR      = "datos/";
    private static final String CLIENTES = DIR + "clientes.txt";
    private static final String RESERVAS = DIR + "reservas_activas.txt";

    static {
        try { Files.createDirectories(Paths.get(DIR)); }
        catch (IOException e) { System.err.println("No se pudo crear 'datos/': " + e.getMessage()); }
    }

 

    public static void guardarClientes(HashMap<Integer, Cliente> mapa) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CLIENTES))) {
            for (Cliente c : mapa.values()) {
                
                pw.println(c.getId() + "|" + c.getNombre() + "|" + c.getPassword());
            }
        } catch (IOException e) { System.err.println("Error guardando clientes: " + e.getMessage()); }
    }

    public static void guardarReservasActivas(HashMap<Integer, List<Reserva>> mapa) {
    try (PrintWriter pw = new PrintWriter(new FileWriter(RESERVAS))) {
        for (var entry : mapa.entrySet()) {
            for (Reserva r : entry.getValue()) {
                pw.println(entry.getKey() + "|" + r.getIdReserva() + "|"
                        + r.getHora() + "|" + r.getFecha() + "|"
                        + r.getCapacidad() + "|" + r.getCancha());
            }
        }
    } catch (IOException e) { System.err.println("Error guardando reservas: " + e.getMessage()); }
}

  

    public static void cargarClientes(HashMap<Integer, Cliente> mapa) {
        Path p = Paths.get(CLIENTES);
        if (!Files.exists(p)) return;
        try (BufferedReader br = new BufferedReader(new FileReader(p.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 3) continue;
                int    id     = Integer.parseInt(parts[0]);
                String nombre = parts[1];
                String pw     = parts[2];
                mapa.put(id, new Cliente(nombre, pw, id));
            }
        } catch (IOException e) { System.err.println("Error cargando clientes: " + e.getMessage()); }
    }

    public static void cargarReservasActivas(HashMap<Integer, List<Reserva>> mapa) {
    Path p = Paths.get(RESERVAS);
    if (!Files.exists(p)) return;
    try (BufferedReader br = new BufferedReader(new FileReader(p.toFile()))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\\|");
            if (parts.length < 6) continue;
            int    idCliente = Integer.parseInt(parts[0]);
            int    idReserva = Integer.parseInt(parts[1]);
            String hora      = parts[2];
            String fecha     = parts[3];
            int    cap       = Integer.parseInt(parts[4]);
            String cancha    = parts[5];
            mapa.computeIfAbsent(idCliente, k -> new ArrayList<>())
                .add(new Reserva(idReserva, hora, fecha, cap, cancha));
        }
    } catch (IOException e) { System.err.println("Error cargando reservas: " + e.getMessage()); }
}
}