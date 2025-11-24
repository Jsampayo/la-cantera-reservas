package com.la_cantera_reservas.excepciones;

public class MensajeAdmin {
    public static void AutenticadoA(){
    System.out.println("╔═════════════════════════╗");
    System.out.println("║                         ║");
    System.out.println("║                         ║");
    System.out.println("║        Bienvenido       ║");
    System.out.println("║       Administrador     ║");
    System.out.println("║                         ║");
    System.out.println("║                         ║");
    System.out.println("╚═════════════════════════╝");
}
public static void NoautenticadoA(){
    System.out.println("╔══════════════════════════════════════════╗");
    System.out.println("║                                          ║");
    System.out.println("║                                          ║");
    System.out.println("║       Credenciales administrativas       ║");
    System.out.println("║              son incorrectas             ║");
    System.out.println("║                                          ║");
    System.out.println("║                                          ║");
    System.out.println("╚══════════════════════════════════════════╝");
}
public static void NoContraseñaA(){
    System.out.println("╔═════════════════════════╗");
    System.out.println("║                         ║");
    System.out.println("║       Contraseña        ║");
    System.out.println("║       Incorrecta        ║");
    System.out.println("║                         ║");
    System.out.println("╚═════════════════════════╝");
}
public static void NoUsuarioA(){
    System.out.println("╔══════════════════════════╗");
    System.out.println("║                          ║");
    System.out.println("║    Este administrador    ║");
    System.out.println("║        No existe.        ║");
    System.out.println("║                          ║");
    System.out.println("╚══════════════════════════╝");
}
public static void CambioReservaA(){
    System.out.println("╔═══════════════════════════════════╗");
    System.out.println("║                                   ║");
    System.out.println("║         La reserva a sido         ║");
    System.out.println("║       Modificada con exito        ║");
    System.out.println("║                                   ║");
    System.out.println("╚═══════════════════════════════════╝");
}
public static void ReservaErrorA(){
    System.out.println("╔═════════════════════════════════╗");
    System.out.println("║                                 ║");
    System.out.println("║          Esta reserva           ║");
    System.out.println("║     No pudo ser modificada      ║");
    System.out.println("║                                 ║");
    System.out.println("╚═════════════════════════════════╝");
}

public static void ClineteErrorA(){
    System.out.println("╔═════════════════════════════╗");
    System.out.println("║                             ║");
    System.out.println("║         Este usurio         ║");
    System.out.println("║          No existe          ║");
    System.out.println("║                             ║");
    System.out.println("╚═════════════════════════════╝");
}

}
