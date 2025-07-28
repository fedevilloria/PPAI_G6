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

    public SeleccionMotivosYComentarios(GestorRI gestor) {
        this.gestor = gestor;

        //Paso 6-Viki: Pantalla consulta motivos disponibles al Gestor
        this.motivosDisponibles = gestor.buscarTiposDeMotivos();
        this.checkBoxes = new ArrayList<>();
        this.camposTexto = new HashMap<>();

        setTitle("Seleccionar Motivos y Cargar Comentarios");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JScrollPane scrollPane = new JScrollPane();
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));

        for (MotivoTipo motivo : motivosDisponibles) {
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
        panelPrincipal.add(btnConfirmar);
        btnCancelar = new JButton("Cancelar");
        panelPrincipal.add(btnCancelar);

        scrollPane.setViewportView(panelPrincipal);
        setContentPane(scrollPane);
        setVisible(true);

        //CAMBIO-Viki: Agregamos los llamados al GestorRI como indica el diagrama
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<MotivoTipo, String> motivosYComentarios = new HashMap<>();

                for (int i = 0; i < checkBoxes.size(); i++) {
                    JCheckBox checkBox = checkBoxes.get(i);
                    MotivoTipo motivo = motivosDisponibles.get(i);
                    JTextField campo = camposTexto.get(motivo);

                    if (checkBox.isSelected()) {
                        motivosYComentarios.put(motivo, campo.getText());
                    }
                }

                if (motivosYComentarios.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar al menos un motivo.");
                    return;
                }

                // Cambio-Viki: Llamadas del diagrama de secuencia
                // RI -> PantallaRI : tomarSeleccionMotivosTipos()
                //PantallaRI -> GestorRI : tomarSeleccionMotivosTipos()
                //RI -> PantallaRI : tomarIngresoComentarioMotivo()
                //PantallaRI -> GestorRI : tomarIngresoComentarioMotivo()
                //Paso 7a: Vista llama al GestorRI pasando los motivos seleccionados
                gestor.tomarSeleccionMotivosTipos(new ArrayList<>(motivosYComentarios.keySet()));
                //Paso 7b: Vista llama al GestorRI pasando los comentarios por motivo
                gestor.tomarIngresoComentarioMotivo(motivosYComentarios);

                // Continuamos al siguiente paso
                new ConfirmacionCierreOrden(gestor, gestor.getEstadosDisponibles());
                dispose();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
