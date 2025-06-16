package org.example.Vistas;

import org.example.Gestores.GestorRI;
import org.example.Modelos.OrdenDeInspeccion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// Esta clase representa la pantalla para seleccionar una orden de inspección
public class SeleccionOrdenDeInspeccion extends JFrame {

    // Componentes de la interfaz
    private JPanel panelPrincipal;
    private JComboBox<OrdenDeInspeccion> comboOrdenes;
    private JButton btnConfirmar;

    // Referencia al gestor, para delegar la lógica de negocio
    private GestorRI gestor;

    // Constructor de la vista. Recibe el gestor como parámetro.
    public SeleccionOrdenDeInspeccion(GestorRI gestor) {
        this.gestor = gestor;

        // Configuración de la ventana
        setTitle("Seleccionar Orden de Inspección");
        setSize(400, 200);
        setContentPane(panelPrincipal);
        setLocationRelativeTo(null); // Centra la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true); // Muestra la ventana

        // Carga las órdenes en el comboBox al iniciar
        cargarOrdenes();

        // Configuro el evento cuando se presiona el botón confirmar
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtengo la orden seleccionada del comboBox
                OrdenDeInspeccion ordenSeleccionada = (OrdenDeInspeccion) comboOrdenes.getSelectedItem();

                // Le informo al gestor la orden seleccionada
                gestor.tomarSelecOrdenDeInspeccion(ordenSeleccionada);

                // Ingresamos a la pantalla de observaciones de cierre
                new IngresoObservacionCierre(gestor);

                // Cierro la pantalla actual de selección de orden
                dispose();
            }
        });
    }

    // Metodo que carga las órdenes filtradas en el comboBox
    private void cargarOrdenes() {
        // Le pido al gestor las órdenes completamente realizadas
        List<OrdenDeInspeccion> ordenes = gestor.buscarOrdenesDelInspeccion();

        // Las agrego al comboBox
        for (OrdenDeInspeccion orden : ordenes) {
            comboOrdenes.addItem(orden);
        }
    }
}
