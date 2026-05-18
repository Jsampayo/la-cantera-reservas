package com.la_cantera_reservas.ui;

import static com.la_cantera_reservas.services.ServicioReserva.getReservasActivas;

import java.util.Scanner;

import com.la_cantera_reservas.excepciones.MensajeAdmin;
import com.la_cantera_reservas.model.Admin;
import com.la_cantera_reservas.model.Reserva;
import com.la_cantera_reservas.services.ServicioAdmin;


public class VistaAdmin {
    
    public static void CredencialesAdmin() {
        Scanner input = new Scanner(System.in);

        Admin credenciales = ServicioAdmin.Administrador(input);
        if (credenciales.getIdAdmin() == Admin.getAdmin().getIdAdmin() && credenciales.getPasswordAdmin().equals(Admin.getAdmin().getPasswordAdmin())){ 
           MensajeAdmin.AutenticadoA();

          int OpcionAdmin = ServicioAdmin.Opciones(input);{
            switch (OpcionAdmin) {
                case 1:
                int idConsulta = ServicioAdmin.IdConsultar(input);
    
                    for (Integer id: getReservasActivas().keySet()){
                        if(idConsulta == id){
                           Reserva r = getReservasActivas().get(id);
                           System.out.println(r.toString());

                        }
                    }
                    
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
