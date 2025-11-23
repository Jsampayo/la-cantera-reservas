package com.la_cantera_reservas.excepciones;

public class MensajesCliente {
public void Autenticado(){
    System.out.println("╔═════════════════════════╗");
    System.out.println("║                         ║");
    System.out.println("║                         ║");
    System.out.println("║        Bienvenido       ║");
    System.out.println("║                         ║");
    System.out.println("║                         ║");
    System.out.println("╚═════════════════════════╝");
}
public void Noautenticado(){
    System.out.println("╔═══════════════════════════╗");
    System.out.println("║                           ║");
    System.out.println("║                           ║");
    System.out.println("║       Credenciales        ║");
    System.out.println("║       Incorrectas         ║");
    System.out.println("║                           ║");
    System.out.println("║                           ║");
    System.out.println("╚═══════════════════════════╝");
}
public void NoContraseña(){
    System.out.println("╔═════════════════════════╗");
    System.out.println("║                         ║");
    System.out.println("║       Contraseña        ║");
    System.out.println("║       Incorrecta        ║");
    System.out.println("║                         ║");
    System.out.println("╚═════════════════════════╝");
}
public void NoUsuario(){
    System.out.println("╔═════════════════════════╗");
    System.out.println("║                         ║");
    System.out.println("║      No existe el       ║");
    System.out.println("║    Nombre de usuario    ║");
    System.out.println("║                         ║");
    System.out.println("╚═════════════════════════╝");
}
public void ReservaExitosa(){
    System.out.println("╔═════════════════════════╗");
    System.out.println("║                         ║");
    System.out.println("║        Su reverva       ║");
    System.out.println("║    A sido registrada    ║");
    System.out.println("║                         ║");
    System.out.println("╚═════════════════════════╝");
}
public void ReservaError(){
    System.out.println("╔═════════════════════════╗");
    System.out.println("║                         ║");
    System.out.println("║     Porfavor dijite     ║");
    System.out.println("║     todos los datos     ║");
    System.out.println("║                         ║");
    System.out.println("╚═════════════════════════╝");
}
public void ReservaOcupada(){
    System.out.println("╔═════════════════════════════╗");
    System.out.println("║                             ║");
    System.out.println("║         Esta cancha         ║");
    System.out.println("║     ya a sido reservada     ║");
    System.out.println("║                             ║");
    System.out.println("╚═════════════════════════════╝");
}

}

