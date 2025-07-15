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


    //Busca el empleado logueado
    public Empleado buscarEmpleadoLogueado() {
        return sesion.obtenerUsuarioLogueado();
    }

    //busca las ordenes completamente realizadas
    public List<OrdenDeInspeccion> buscarOrdenesDeInspeccion() {
        // 1. Obtener el empleado logueado
        Empleado empleadoLogueado = sesion.obtenerUsuarioLogueado();

        // 2. Lista para almacenar las órdenes filtradas
        List<OrdenDeInspeccion> ordenesFiltradas = new ArrayList<>();

        // 3. Recorrer todas las órdenes y aplicar filtros
        for (OrdenDeInspeccion orden : ordenesDeInspeccion) {
            // Solo las que pertenezcan al empleado y estén completamente realizadas
            if (orden.esEmpleado(empleadoLogueado) && orden.esCompletamenteRealizada()) {
                ordenesFiltradas.add(orden);
            }
        }

        // 4. Mostrar los datos por consola (luego se usarán en pantalla)
        for (OrdenDeInspeccion orden : ordenesFiltradas) {
            System.out.println(orden.getDatos()); // getDatos devuelve un String con los campos pedidos
        }

        //5. Ordenar por fecha de finalizacion
        ordenarOrdenesDeInspeccion(ordenesFiltradas);

        // 6. Devolver las órdenes filtradas y ordenadas
        return ordenesFiltradas;
    }

    // las ordena por fecha de finalizacion
    private void ordenarOrdenesDeInspeccion(List<OrdenDeInspeccion> ordenesFiltradas) {
        ordenesFiltradas.sort(Comparator.comparing(OrdenDeInspeccion::getFechaHoraFinalizacion));
    }


    //Toma la orden de inspección seleccionada por el RI
    public void tomarSelecOrdenDeInspeccion(OrdenDeInspeccion ordenSeleccionada) {
        this.ordenSeleccionada = ordenSeleccionada;
    }

    public void tomarIngresoObservacionCierreInspeccion(String observacionCierre) {
        this.ordenSeleccionada.setObservacionCierre(observacionCierre);
        this.observacionCierre = observacionCierre; // Lo guardamos también por si hace falta validarlo
    }

    public boolean estaListoParaMotivos() {
        return ordenSeleccionada != null
                && ordenSeleccionada.getObservacionCierre() != null
                && !ordenSeleccionada.getObservacionCierre().trim().isEmpty();
    }

    public void tomarMotivosYComentarios(Map<MotivoTipo, String> motivosYComentarios) {
        this.motivosYComentarios = motivosYComentarios;
    }

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
