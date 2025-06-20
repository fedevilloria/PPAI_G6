package org.example.Vistas;

import org.example.Gestores.GestorRI;
import org.example.Modelos.MotivoTipo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// Pantalla para ingresar la observación de cierre de la orden seleccionada
public class IngresoObservacionCierre extends JFrame {

    // Componentes de la vista
    private JPanel panelPrincipal;
    private JTextArea txtObservacion;
    private JButton btnConfirmar;
    private GestorRI gestor;
    private List<MotivoTipo> motivosDisponibles;

    // Constructor de la pantalla, recibe el gestor como siempre
    public IngresoObservacionCierre(GestorRI gestor) {
        this.gestor = gestor;

        // Configuración básica de la ventana
        setTitle("Ingreso de Observación de Cierre");
        setSize(400, 300);
        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        // Evento del botón confirmar
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Toma el texto escrito por el usuario
                String observacion = txtObservacion.getText().trim();

                // Validar si está vacía
                if (observacion.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar una observación válida.");
                    return;
                }

                // Si es válida, continuar
                gestor.tomarIngresoObservacionCierreInspeccion(observacion);

                if (gestor.estaListoParaMotivos()) {
                    new SeleccionMotivosYComentarios(gestor, motivosDisponibles);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Ocurrió un error inesperado. Verifique los datos.");
                }
            }
        });
    }
}

