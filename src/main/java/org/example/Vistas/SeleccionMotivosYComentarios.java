package org.example.Vistas;

import org.example.Gestores.GestorRI;
import org.example.Modelos.MotivoTipo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class SeleccionMotivosYComentarios extends JFrame {

    private JPanel panelPrincipal;
    private JButton btnConfirmar;
    private JButton btnCancelar;

    private List<JCheckBox> checkBoxes;
    private Map<MotivoTipo, JTextField> camposTexto;

    private List<MotivoTipo> motivosDisponibles;
    private GestorRI gestor;

    public SeleccionMotivosYComentarios(GestorRI gestor, List<MotivoTipo> motivosDisponibles) {
        this.gestor = gestor;
        this.motivosDisponibles = motivosDisponibles;
        this.checkBoxes = new ArrayList<>();
        this.camposTexto = new HashMap<>();

        setTitle("Seleccionar Motivos y Cargar Comentarios");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JScrollPane scrollPane = new JScrollPane();
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));

        for (MotivoTipo motivo : this.motivosDisponibles) {
            JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT));

            JCheckBox checkBox = new JCheckBox(motivo.getDescripcion());
            JTextField textField = new JTextField(20);

            checkBoxes.add(checkBox);
            camposTexto.put(motivo, textField);

            fila.add(checkBox);
            fila.add(new JLabel("Comentario:"));
            fila.add(textField);

            panelPrincipal.add(fila);
        }

        btnConfirmar = new JButton("Confirmar");
        btnCancelar = new JButton("Cancelar");

        // Panel de botones separado para mantener el layout ordenado
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCancelar);

        panelPrincipal.add(panelBotones);
        scrollPane.setViewportView(panelPrincipal);
        setContentPane(scrollPane);
        setVisible(true);

        // Acción Confirmar
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<MotivoTipo, String> motivosYComentarios = new HashMap<>();

                for (int i = 0; i < checkBoxes.size(); i++) {
                    JCheckBox checkBox = checkBoxes.get(i);
                    MotivoTipo motivo = SeleccionMotivosYComentarios.this.motivosDisponibles.get(i);
                    JTextField campo = camposTexto.get(motivo);

                    if (checkBox.isSelected()) {
                        motivosYComentarios.put(motivo, campo.getText());
                    }
                }

                if (motivosYComentarios.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar al menos un motivo.");
                    return;
                }

                // Nuevas llamadas al gestor (versión actualizada)
                gestor.tomarSeleccionMotivosTipos(new ArrayList<>(motivosYComentarios.keySet()));
                gestor.tomarIngresoComentarioMotivo(motivosYComentarios);

                new ConfirmacionCierreOrden(gestor, gestor.getEstadosDisponibles());
                dispose();
            }
        });

        // Acción Cancelar
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}