package org.example.Gestores;

import org.example.Modelos.*;

import java.time.LocalDateTime;
import java.util.*;

public class GestorRI {
    private Sesion sesion;
    private List<OrdenDeInspeccion> ordenesDeInspeccion;
    private OrdenDeInspeccion ordenSeleccionada;
    private String observacionCierre;
    private Map<MotivoTipo, String> motivosYComentarios;
    private List<Estado> estadosDisponibles;

    public GestorRI(Sesion sesion, List<OrdenDeInspeccion> ordenesDeInspeccion) {
        this.sesion = sesion;
        this.ordenesDeInspeccion = ordenesDeInspeccion;
    }

    public void setEstadosDisponibles(List<Estado> estados) {
        this.estadosDisponibles = estados;
    }

    //Busca el empleado logueado y las ordenes de inspeccion en estado completamente realizadas.
    public Empleado buscarEmpleadoLogueado() {
        Usuario usuario = sesion.obtenerUsuarioLogueado();
        return usuario.getEmpleado();
    }

    public List<OrdenDeInspeccion> buscarOrdenesDelInspeccion() {
        List<OrdenDeInspeccion> ordenesFiltradas = new ArrayList<>();
        Empleado empleadoLogueado = buscarEmpleadoLogueado();

        for (OrdenDeInspeccion orden : ordenesDeInspeccion) {
            if (orden.esCompletamenteRealizada() &&
                    orden.getEmpleado().equals(empleadoLogueado)) {
                ordenesFiltradas.add(orden);
            }
        }

        //ordenamos las ordenes por fecha de finalización
        ordenesFiltradas.sort(Comparator.comparing(OrdenDeInspeccion::getFechaHoraFinalizacion));
        return ordenesFiltradas;
    }

    //Toma la orden de inspección seleccionada por el RI
    public void tomarSelecOrdenDeInspeccion(OrdenDeInspeccion ordenSeleccionada) {
        this.ordenSeleccionada = ordenSeleccionada;
    }

    //Toma la observacion de cierre de orden seleccionada
    public void tomarIngresoObservacionCierreInspeccion(String observacionCierre) {
        this.ordenSeleccionada.setObservacionCierre(observacionCierre);
        this.observacionCierre = observacionCierre; // Lo guardamos también por si hace falta validarlo
    }

    //Se verifica si ya puede seleccionarse los motivos.
    public boolean estaListoParaMotivos() {
        return ordenSeleccionada != null
                && ordenSeleccionada.getObservacionCierre() != null
                && !ordenSeleccionada.getObservacionCierre().trim().isEmpty();
    }

    //Toma los motivos con sus comentarios
    public void tomarMotivosYComentarios(Map<MotivoTipo, String> motivosYComentarios) {
        this.motivosYComentarios = motivosYComentarios;
    }

    //Pide la confirmación de cierre de orden de inspección
    public boolean confirmarCierreInspeccion(List<Estado> todosLosEstados) {
        if (ordenSeleccionada == null || observacionCierre == null || motivosYComentarios == null || motivosYComentarios.isEmpty()) {
            return false;
        }

        // Buscar estado 'Cerrada' para la orden
        Estado estadoCerrada = null;
        for (Estado e : todosLosEstados) {
            if (e.getNombre().equalsIgnoreCase("Cerrada") &&
                    e.getAmbito().equalsIgnoreCase("Orden de Inspeccion")) {
                estadoCerrada = e;
                break;
            }
        }

        if (estadoCerrada == null) return false;

        // Asignar estado y fecha de cierre a la orden
        ordenSeleccionada.setEstado(estadoCerrada);
        ordenSeleccionada.setFechaHoraCierre(LocalDateTime.now());

        // Buscar estado 'Fuera de Servicio' para el sismógrafo
        Estado estadoFueraDeServicio = null;
        for (Estado e : todosLosEstados) {
            if (e.getNombre().equalsIgnoreCase("Fuera de Servicio") &&
                    e.getAmbito().equalsIgnoreCase("Sismografo")) {
                estadoFueraDeServicio = e;
                break;
            }
        }

        if (estadoFueraDeServicio == null) return false;

        // Crear lista de motivos
        List<MotivoFueraDeServicio> listaMotivos = new ArrayList<>();
        for (Map.Entry<MotivoTipo, String> entry : motivosYComentarios.entrySet()) {
            MotivoFueraDeServicio motivo = new MotivoFueraDeServicio(entry.getValue(), entry.getKey());
            listaMotivos.add(motivo);
        }

        // Cambiar estado del sismógrafo
        Empleado empleado = buscarEmpleadoLogueado();
        Sismografo sismografo = ordenSeleccionada.getEstacionSismologica().getSismografo();
        sismografo.ponerEnReparacion(estadoFueraDeServicio, empleado, listaMotivos);

        return true;
    }

    public OrdenDeInspeccion getOrdenSeleccionada() {
        return ordenSeleccionada;
    }

    public List<Estado> getEstadosDisponibles() {
        return this.estadosDisponibles;
    }

}
