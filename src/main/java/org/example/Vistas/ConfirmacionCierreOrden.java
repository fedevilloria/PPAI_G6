package org.example.Vistas;

import org.example.Gestores.GestorRI;
import org.example.Modelos.Estado;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ConfirmacionCierreOrden extends JFrame {

    private JPanel panelPrincipal;
    private JButton btnConfirmar;
    private GestorRI gestor;
    private List<Estado> listaDeEstados;

    public ConfirmacionCierreOrden(GestorRI gestor, List<Estado> listaDeEstados) {
        this.gestor = gestor;
        this.listaDeEstados = listaDeEstados;

        setTitle("Confirmar Cierre de Orden");
        setSize(400, 200);
        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean exito = gestor.confirmarCierreInspeccion(listaDeEstados);

                if (exito) {
                    JOptionPane.showMessageDialog(null, "✅ Orden cerrada correctamente.");
                    dispose();  // cerramos la pantalla
                } else {
                    JOptionPane.showMessageDialog(null, "❌ No se pudo cerrar la orden. Revisá los datos.");
                }
            }
        });
    }
}

