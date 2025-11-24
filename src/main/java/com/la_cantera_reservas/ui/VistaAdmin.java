package com.la_cantera_reservas.ui;

import java.util.Scanner;

import com.la_cantera_reservas.excepciones.MensajeAdmin;
import com.la_cantera_reservas.model.Admin;
import com.la_cantera_reservas.services.ServicioAdmin;


public class VistaAdmin {
    
    public static void CredencialesAdmin() {
        Scanner input = new Scanner(System.in);

        Admin credenciales = ServicioAdmin.Administrador(input);
        if (credenciales.getIdAdmin() == Admin.getAdmin().getIdAdmin() && credenciales.getPasswordAdmin().equals(Admin.getAdmin().getPasswordAdmin())){ 
           MensajeAdmin.AutenticadoA();

          int OpciónAdmin = ServicioAdmin.Opciones(input);{
            switch (OpciónAdmin) {
                case 1:
                    int idConsulta = ServicioAdmin.IdConsultar(input);
                    
                    break;
            
                case 2:
                    ServicioAdmin.IdEliminar(input);
                    break;
                
                default:
                System.out.println("Opcion Invalida");

            }
          }
         } 
        
        
        
        
        
        
        
        
        
        
        
         else
        MensajeAdmin.NoautenticadoA();

       }
}
