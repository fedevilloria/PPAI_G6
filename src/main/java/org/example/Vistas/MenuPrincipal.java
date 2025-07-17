package org.example.Vistas;

import org.example.Gestores.GestorRI;
import org.example.Modelos.*; // Importa todos los modelos para tenerlos disponibles

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuPrincipal extends JFrame {
    // --- Atributos de la UI (enlazados desde el .form) ---
    private JPanel panelPrincipal;
    // Asegúrate de que el "field name" en el diseñador sea "btnCerrarOrden"
    private JButton btnCerrarOrdenInspeccion;

    // --- Atributos para guardar los datos necesarios para el caso de uso ---
    // <-- CAMBIO 1: Todas las declaraciones de atributos van aquí, a nivel de clase.
    private Sesion sesionActual;
    private List<OrdenDeInspeccion> todasLasOrdenes;
    private List<MotivoTipo> motivosDisponibles;
    private List<Estado> estadosDisponibles;

    // <-- CAMBIO 2: El constructor recibe los DATOS, no el gestor.
    public MenuPrincipal(Sesion sesion, List<OrdenDeInspeccion> ordenes, List<MotivoTipo> motivos, List<Estado> estados) {

        // Guardamos los datos recibidos en los atributos de la clase
        this.sesionActual = sesion;
        this.todasLasOrdenes = ordenes;
        this.motivosDisponibles = motivos;
        this.estadosDisponibles = estados;

        // Configuración de la ventana
        setTitle("Menú Principal - Red Sísmica");
        setContentPane(panelPrincipal); // Usa el panel del diseñador
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack(); // Ajusta el tamaño automáticamente
        setLocationRelativeTo(null); // Centra la ventana
        setVisible(true);

        // <-- CAMBIO 3: Añadimos el ActionListener al botón que SÍ existe en el diseñador.
        btnCerrarOrdenInspeccion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cuando se hace clic, se llama al metodo que inicia el caso de uso.
                opcionCerrarOrdenInspeccion();
            }
        });
    }

    /**
     * Este metodo se ejecuta cuando el usuario hace clic en el botón.
     * Representa el flujo que inicia en la pantalla.
     */
    private void opcionCerrarOrdenInspeccion() {
        System.out.println("Pantalla: Se ha presionado la opción para cerrar la orden.");

        // La pantalla crea una nueva instancia del gestor, pasándole los datos que necesita.
        System.out.println("Pantalla: Creando instancia del GestorRI...");
        GestorRI gestor = new GestorRI(this.sesionActual, this.todasLasOrdenes);
        gestor.setEstadosDisponibles(this.estadosDisponibles);

        // La pantalla usa el gestor para buscar los datos.
        System.out.println("Pantalla: Solicitando al gestor las órdenes a mostrar...");
        List<OrdenDeInspeccion> ordenesAMostrar = gestor.buscarOrdenesRealizadasParaUsuarioLogueado();

        // La pantalla abre la siguiente ventana y le pasa los datos.
        System.out.println("Pantalla: Abriendo la ventana de selección de órdenes...");
        SeleccionOrdenDeInspeccion pantallaSeleccion = new SeleccionOrdenDeInspeccion(gestor, ordenesAMostrar);
        // Opcional: Ocultar esta ventana mientras la otra está abierta
        // this.setVisible(false);
    }
}