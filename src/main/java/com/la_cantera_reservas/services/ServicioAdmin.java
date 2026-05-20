package com.la_cantera_reservas.services;

import com.la_cantera_reservas.model.Admin;

public class ServicioAdmin {


    public static boolean validarAdmin(int idAdmin, String password) {
        Admin admin = Admin.getAdmin();
        return admin.getIdAdmin() == idAdmin
            && admin.getPasswordAdmin().equals(password);
    }
}