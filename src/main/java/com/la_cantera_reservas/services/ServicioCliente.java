package com.la_cantera_reservas.services;

import com.la_cantera_reservas.excepciones.MensajesCliente;
import com.la_cantera_reservas.model.Cliente;
import java.util.HashMap;

public class ServicioCliente {

    private static final HashMap<Integer, Cliente> clientesRegistrados = new HashMap<>();

    public static HashMap<Integer, Cliente> getClientesRegistrados() {
        return clientesRegistrados;
    }

  
    public static Cliente validarCredenciales(int id, String password) {
        Cliente c = clientesRegistrados.get(id);
        if (c != null && c.getPassword().equals(password)) {
            return c;
        }
        MensajesCliente.noAutenticado();
        return null;
    }

   
    public static boolean registrarCliente(String nombre, String password, int id) {
        if (clientesRegistrados.containsKey(id)) {
            return false;
        }
        clientesRegistrados.put(id, new Cliente(nombre, password, id));
        return true;
    }
    public static boolean eliminarCliente(int id) {
        return clientesRegistrados.remove(id) != null;
    }

}