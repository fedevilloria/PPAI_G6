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
    private List<MotivoTipo> motivosDisponibles;
    private boolean situacionSismografoHabilitada = false;

    public GestorRI(Sesion sesion, List<OrdenDeInspeccion> ordenesDeInspeccion) {
        this.sesion = sesion;
        this.ordenesDeInspeccion = ordenesDeInspeccion;
        this.motivosYComentarios = new HashMap<>();
    }

    public void setEstadosDisponibles(List<Estado> estados) {
        this.estadosDisponibles = estados;
    }

    public List<Estado> getEstadosDisponibles() {
        return this.estadosDisponibles;
    }

    public OrdenDeInspeccion getOrdenSeleccionada() {
        return ordenSeleccionada;
    }

    // 🔷 Paso 6: habilitar actualización situación del sismógrafo
    public void habilitarActualizarSituacionSismografo() {
        if (ordenSeleccionada == null) {
            throw new IllegalStateException("No hay una orden seleccionada");
        }
        this.situacionSismografoHabilitada = true;
    }

    // 🔷 Paso 6: devolver motivos disponibles
    public void setMotivosDisponibles(List<MotivoTipo> motivosDisponibles) {
        this.motivosDisponibles = motivosDisponibles;
    }

    public List<MotivoTipo> buscarTiposDeMotivos() {
        return this.motivosDisponibles != null ? this.motivosDisponibles : new ArrayList<>();
    }

    // 🔷 Paso 7-a: registrar motivos seleccionados
    public void tomarSeleccionMotivosTipos(List<MotivoTipo> motivos) {
        Map<MotivoTipo, String> vacios = new HashMap<>();
        for (MotivoTipo m : motivos) {
            vacios.put(m, "");
        }
        this.motivosYComentarios = vacios;
    }

    // 🔷 Paso 7-b: registrar motivos y comentarios
    public void tomarIngresoComentarioMotivo(Map<MotivoTipo, String> motivosYComentarios) {
        this.motivosYComentarios = motivosYComentarios;
    }

    public void tomarObservacionCierre(String observacion) {
        this.observacionCierre = observacion;
    }

    public Empleado buscarEmpleadoLogueado() {
        return sesion.obtenerUsuarioLogueado();
    }

    public List<OrdenDeInspeccion> buscarOrdenesDeInspeccion() {
        Empleado empleadoLogueado = sesion.obtenerUsuarioLogueado();
        List<OrdenDeInspeccion> ordenesFiltradas = new ArrayList<>();

        for (OrdenDeInspeccion orden : ordenesDeInspeccion) {
            if (orden.esEmpleado(empleadoLogueado) && orden.esCompletamenteRealizada()) {
                ordenesFiltradas.add(orden);
            }
        }

        for (OrdenDeInspeccion orden : ordenesFiltradas) {
            System.out.println(orden.getDatos());
        }

        ordenarOrdenesDeInspeccion(ordenesFiltradas);

        return ordenesFiltradas;
    }

    private void ordenarOrdenesDeInspeccion(List<OrdenDeInspeccion> ordenesFiltradas) {
        ordenesFiltradas.sort(Comparator.comparing(OrdenDeInspeccion::getFechaHoraFinalizacion));
    }

    public void tomarSelecOrdenDeInspeccion(OrdenDeInspeccion ordenSeleccionada) {
        this.ordenSeleccionada = ordenSeleccionada;
    }

    public void tomarIngresoObservacionCierreInspeccion(String observacionCierre) {
        this.ordenSeleccionada.setObservacionCierre(observacionCierre);
        this.observacionCierre = observacionCierre;
    }

    public boolean estaListoParaMotivos() {
        return ordenSeleccionada != null
                && ordenSeleccionada.getObservacionCierre() != null
                && !ordenSeleccionada.getObservacionCierre().trim().isEmpty();
    }

    public boolean validarExistenciaObservaciones() {
        return ordenSeleccionada != null
                && ordenSeleccionada.getObservacionCierre() != null
                && !ordenSeleccionada.getObservacionCierre().trim().isEmpty();
    }

    public boolean validarMotivosMinimos() {
        if (motivosYComentarios == null || motivosYComentarios.isEmpty()) {
            return false;
        }

        // Validar que tenga un comentario no vacío:
        for (String comentario : motivosYComentarios.values()) {
            if (comentario != null && !comentario.trim().isEmpty()) {
                return true;
            }
        }

        return false;
    }

    /*
     Metodo viejo: integrando tus cambios.
     */
    public boolean confirmarCierreInspeccion(List<Estado> todosLosEstados) {
        if (ordenSeleccionada == null || observacionCierre == null || motivosYComentarios == null || motivosYComentarios.isEmpty()) {
            return false;
        }

        Estado estadoCerrada = buscarEstadoCerradoOrdenInspeccion(todosLosEstados);
        if (estadoCerrada == null) return false;

        LocalDateTime fechaHoraActual = tomarFechaHoraActual();
        cerrarOrdenInspeccion(estadoCerrada, fechaHoraActual);


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
    private Estado buscarEstadoCerradoOrdenInspeccion(List<Estado> estados) {
        for (Estado estado : estados) {
            if (estado.esAmbitoOrdenDeInspeccion() && estado.esCerrado()) {
                return estado;
            }
        }
        return null;
    }

    private LocalDateTime tomarFechaHoraActual() {
        return LocalDateTime.now();
    }

    private void cerrarOrdenInspeccion(Estado estadoCerrado, LocalDateTime fechaHoraActual) {
        if (ordenSeleccionada != null && estadoCerrado != null && fechaHoraActual != null) {
            ordenSeleccionada.cerrar(estadoCerrado, fechaHoraActual);
        }
    }

}