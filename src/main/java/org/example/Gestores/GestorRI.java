package org.example.Gestores;

import org.example.Modelos.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class GestorRI {
    private Sesion sesion;
    private List<OrdenDeInspeccion> ordenesDeInspeccion;
    private OrdenDeInspeccion ordenSeleccionada;
    private String observacionCierre;
    private Map<MotivoTipo, String> motivosYComentarios;

    // Constructor del gestor, recibe la sesión activa
    public GestorRI(Sesion sesion, List<OrdenDeInspeccion> ordenesDeInspeccion) {
        this.sesion = sesion;
        this.ordenesDeInspeccion = ordenesDeInspeccion;
    }

    // Metodo para buscar el empleado logueado
    public Empleado buscarEmpleadoLogueado() {
        // Pide al objeto Sesion el usuario logueado
        Usuario usuario = sesion.obtenerUsuarioLogueado();

        // Pide al Usuario su Empleado
        return usuario.getEmpleado();
    }

    // Metodo para buscar las ordenes de inspeccion completamente realizadas
    public List<OrdenDeInspeccion> buscarOrdenesDelInspeccion() {
        List<OrdenDeInspeccion> ordenesFiltradas = new ArrayList<>();

        for (OrdenDeInspeccion orden : ordenesDeInspeccion) {
            if (orden.esCompletamenteRealizada()) {
                ordenesFiltradas.add(orden);
            }
        }

        // Metodo para ordenar la lista de ordenes de inspeccion por fecha de finalización
        ordenesFiltradas.sort(Comparator.comparing(OrdenDeInspeccion::getFechaHoraFinalizacion));
        return ordenesFiltradas;
    }

    // Metodo que guarda la orden seleccionada cuando el usuario la elige en la pantalla
    public void tomarSelecOrdenDeInspeccion(OrdenDeInspeccion ordenSeleccionada) {
        this.ordenSeleccionada = ordenSeleccionada;
    }

    // Metodo que toma la observación ingresada y la asigna a la orden seleccionada
    public void tomarIngresoObservacionCierreInspeccion(String observacionCierre) {
        this.ordenSeleccionada.setObservacionCierre(observacionCierre);
    }

    //Metodo para validar que la observacion este seleccionada y con su comentario asociado.
    public boolean estaListoParaMotivos() {
        return ordenSeleccionada != null
                && ordenSeleccionada.getObservacionCierre() != null
                && !ordenSeleccionada.getObservacionCierre().trim().isEmpty();
    }


    //Metodo para guardar motivos seleccionados + comentarios
    public void tomarMotivosYComentarios(Map<MotivoTipo, String> motivosYComentarios) {
        this.motivosYComentarios = motivosYComentarios;
    }

    //Metodo para confirmar el cierre de orden de inspección y cambiarle el estado.
    public boolean confirmarCierreInspeccion(List<Estado> todosLosEstados) {
        if (ordenSeleccionada == null || observacionCierre == null || motivosYComentarios == null || motivosYComentarios.isEmpty()) {
            return false;
        }

        Estado estadoCerrada = null;
        for (Estado e : todosLosEstados) {
            if (e.getNombre().equalsIgnoreCase("Cerrada") &&
                    e.getAmbito().equalsIgnoreCase("Orden de Inspeccion")) {
                estadoCerrada = e;
                break;
            }
        }

        if (estadoCerrada == null) return false;

        ordenSeleccionada.setEstado(estadoCerrada);
        ordenSeleccionada.setFechaHoraCierre(LocalDateTime.now());
        return true;
    }

}

