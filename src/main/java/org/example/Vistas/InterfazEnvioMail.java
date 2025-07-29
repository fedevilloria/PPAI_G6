package org.example.Vistas;

import java.util.List;

public class InterfazEnvioMail {
    public void enviarMail(List<String> destinatarios, String contenido) {
        for (String mail : destinatarios) {
            System.out.println("Enviando a: " + mail);
            System.out.println("Contenido:\n" + contenido);
        }
    }
}
