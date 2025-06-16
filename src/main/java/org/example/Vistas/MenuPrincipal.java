package org.example.Vistas;

import org.example.Gestores.GestorRI;
import org.example.Modelos.OrdenDeInspeccion;
import org.example.Modelos.Sesion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuPrincipal extends JFrame {
    private JPanel panelPrincipal;
    private JButton btnCerrarOrdenInspeccion;

    private Sesion sesion; // Sesión actual
    private List<OrdenDeInspeccion> ordenesDeInspeccion; // Datos de prueba

    // Constructor: recibe la sesión y las órdenes como parámetros
    public MenuPrincipal(Sesion sesion, List<OrdenDeInspeccion> ordenesDeInspeccion) {
        this.sesion = sesion;
        this.ordenesDeInspeccion = ordenesDeInspeccion;

        setTitle("Menú Principal");
        setSize(400, 200);
        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        btnCerrarOrdenInspeccion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Se crea el Gestor (NuevoCierreOrdenInspeccion())
                GestorRI gestor = new GestorRI(sesion, ordenesDeInspeccion);

                // Se abre la primera pantalla de selección de orden
                new SeleccionOrdenDeInspeccion(gestor);
            }
        });
    }
}
