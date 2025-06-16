package org.example.Vistas;

import org.example.Gestores.GestorRI;
import org.example.Modelos.MotivoTipo;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

// Pantalla de selección de motivos + ingreso de comentarios
public class SeleccionMotivosYComentarios extends JFrame {

    private JPanel panelPrincipal;
    private JButton btnConfirmar;

    private List<JCheckBox> checkBoxes;               // Lista de checkboxes
    private Map<MotivoTipo, JTextField> camposTexto;  // Relaciona cada motivo con su campo de texto

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

        // Panel con scroll por si hay muchos motivos
        JScrollPane scrollPane = new JScrollPane();
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));

        // Armamos cada fila: Checkbox + Campo de texto
        for (MotivoTipo motivo : motivosDisponibles) {
            JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT));

            JCheckBox checkBox = new JCheckBox(motivo.getDescripcion());
            JTextField textField = new JTextField(20);  // Campo para el comentario

            checkBoxes.add(checkBox);
            camposTexto.put(motivo, textField);

            fila.add(checkBox);
            fila.add(new JLabel("Comentario:"));
            fila.add(textField);

            panelPrincipal.add(fila);
        }

        btnConfirmar = new JButton("Confirmar");
        panelPrincipal.add(btnConfirmar);

        scrollPane.setViewportView(panelPrincipal);
        setContentPane(scrollPane);
        setVisible(true);

        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mapeo final: motivos seleccionados + sus comentarios
                Map<MotivoTipo, String> motivosYComentarios = new HashMap<>();

                for (int i = 0; i < checkBoxes.size(); i++) {
                    JCheckBox checkBox = checkBoxes.get(i);
                    MotivoTipo motivo = motivosDisponibles.get(i);
                    JTextField campo = camposTexto.get(motivo);

                    if (checkBox.isSelected()) {
                        motivosYComentarios.put(motivo, campo.getText());
                    }
                }

                // Validación: debe haber al menos 1 motivo seleccionado
                if (motivosYComentarios.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar al menos un motivo.");
                    return;  // No continúa si la validación falla
                }

                // Si pasa la validación, guardamos en el Gestor
                gestor.tomarMotivosYComentarios(motivosYComentarios);

                // (Acá luego abriremos la pantalla de confirmación de cierre)
                JOptionPane.showMessageDialog(null, "Motivos y comentarios cargados correctamente.");
                dispose();
            }
        });

    }
}
