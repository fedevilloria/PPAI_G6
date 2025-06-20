package org.example.Vistas;

import org.example.Gestores.GestorRI;
import org.example.Modelos.Estado;
import org.example.Modelos.MotivoTipo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuPrincipal extends JFrame {
    private JPanel panelPrincipal;
    private JButton btnCerrarOrden;

    private GestorRI gestor;
    private List<MotivoTipo> motivos;
    private List<Estado> estados;

    public MenuPrincipal(GestorRI gestor, List<MotivoTipo> motivos, List<Estado> estados) {
        this.gestor = gestor;
        this.motivos = motivos;
        this.estados = estados;

        setTitle("Menú Principal");
        setSize(400, 200);
        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        btnCerrarOrden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SeleccionOrdenDeInspeccion(gestor, motivos, estados);
                //dispose();
            }
        });
    }
}