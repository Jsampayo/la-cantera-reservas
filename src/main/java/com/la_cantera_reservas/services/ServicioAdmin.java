package com.la_cantera_reservas.services;

import org.springframework.stereotype.Service;

@Service
public class ServicioAdmin {

    private static final int ID_ADMIN = 123;
    private static final String PASSWORD_ADMIN = "321";

    public boolean validarAdmin(int idAdmin, String password) {
        return idAdmin == ID_ADMIN && PASSWORD_ADMIN.equals(password);
    }
}
