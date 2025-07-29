package org.example.Gestores;

import org.example.Modelos.*;
import org.example.Vistas.Interfaz;
import org.example.Vistas.InterfazEnvioMail;

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
    private List<Sismografo> sismografosDisponibles;
    private Interfaz interfaz;
    private InterfazEnvioMail interfazEnvioMail;
    private List<String> mailsResponsables = new ArrayList<>();
    private List<Empleado> empleados = new ArrayList<>();


    public GestorRI(Sesion sesion, List<OrdenDeInspeccion> ordenesDeInspeccion, Interfaz interfaz) {
        this.sesion = sesion;
        this.ordenesDeInspeccion = ordenesDeInspeccion;
        this.motivosYComentarios = new HashMap<>();
        this.interfaz = interfaz;
    }

    public void setInterfazEnvioMail(InterfazEnvioMail interfazEnvioMail) {
        this.interfazEnvioMail = interfazEnvioMail;
    }

    public void setEstadosDisponibles(List<Estado> estados) {
        this.estadosDisponibles = estados;
    }

    public void setSismografosDisponibles(List<Sismografo> sismografosDisponibles) {
        this.sismografosDisponibles = sismografosDisponibles;
    }

    // Paso 6: habilitar actualización situación del sismógrafo
    public boolean habilitarActualizarSituacionSismografo() {
        if (ordenSeleccionada == null) {
            return false; // Si no hay orden, devuelve false
        }
        this.situacionSismografoHabilitada = true;
        return true; // Si tod va bien, devuelve true
    }

    // Paso 6: devolver motivos disponibles
    public void setMotivosDisponibles(List<MotivoTipo> motivosDisponibles) {
        this.motivosDisponibles = motivosDisponibles;
    }

    public List<MotivoTipo> buscarTiposDeMotivos() {
        return this.motivosDisponibles != null ? this.motivosDisponibles : new ArrayList<>();
    }

    // Paso 7-a: registrar motivos seleccionados
    public void tomarSeleccionMotivosTipos(List<MotivoTipo> motivos) {
        Map<MotivoTipo, String> vacios = new HashMap<>();
        for (MotivoTipo m : motivos) {
            vacios.put(m, "");
        }
        this.motivosYComentarios = vacios;
    }

    // Paso 7-b: registrar motivos y comentarios
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


    public Estado buscarEstadoFueraDeServicio() {
        for (Estado estado : estadosDisponibles) {
            if (estado.esAmbitoSismografo() && estado.esFueraDeServicio()) {
                return estado;
            }
        }
        return null;
    }

    public void cambiarEstadoSismografo() {
        // Buscar el estado 'Fuera de Servicio'
        Estado estadoFS = buscarEstadoFueraDeServicio();

        if (estadoFS == null) {
            throw new IllegalStateException("No se encontró el estado 'Fuera de Servicio'");
        }

        // Obtener el empleado responsable
        Empleado responsable = buscarEmpleadoLogueado();

        // Crear la lista de motivos desde el map
        List<MotivoFueraDeServicio> motivos = new ArrayList<>();
        for (Map.Entry<MotivoTipo, String> entry : motivosYComentarios.entrySet()) {
            motivos.add(new MotivoFueraDeServicio(entry.getValue(), entry.getKey()));
        }

        // Buscar el sismógrafo asociado a la estación seleccionada
        EstacionSismologica estacion = ordenSeleccionada.getEstacionSismologica();
        Sismografo sismografo = buscarSismografoPorEstacion(estacion);

        if (sismografo == null) {
            throw new IllegalStateException("No se encontró el sismógrafo para la estación: " + estacion.getNombreEstacionSismologica());
        }

        // Poner el sismógrafo en reparación
        sismografo.ponerEnReparacion(estadoFS, responsable, motivos);
    }

    public Sismografo buscarSismografoPorEstacion(EstacionSismologica estacion) {
        for (Sismografo sismografo : sismografosDisponibles) {
            if (sismografo.getEstacionSismologica().equals(estacion)) {
                return sismografo;
            }
        }
        return null; // Si no se encontró ninguno
    }

    public void buscarMailsResponsablesDeReparaciones() {
        for (Empleado e : empleados) {
            if (e.esResponsableDeReparacion()) {
                mailsResponsables.add(e.obtenerMail());
            }
        }
    }

    public void publicarEnMail(String contenido) {
    }

    public void publicarEnMonitor(String mensaje) {
        interfaz.mostrarEnMonitor(mensaje);
    }

    private String generarMensajeDeNotificacion() {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Se notifican tareas de reparación para la orden de inspección:\n"); //Crea el encabezado: "Se notifican tareas de reparación para la orden de inspección:\n"
        mensaje.append("- Observación de cierre: ").append(observacionCierre).append("\n"); //Agrega la observación de cierre escrita por el usuario: "- Observación de cierre: [lo que haya escrito el usuario]\n"
        mensaje.append("- Motivos seleccionados:\n"); //Enumera los motivos seleccionados:
        for (Map.Entry<MotivoTipo, String> entry : motivosYComentarios.entrySet()) {
            mensaje.append("  · ").append(entry.getKey().getDescripcion()).append(": ").append(entry.getValue()).append("\n");
        } //getDescripcion() saca la descripción del motivo (ej: "Falla técnica"), entry.getValue() obtiene el comentario asociado a ese motivo (ej: "Se detectó un error en la lectura").
        mensaje.append("- Fecha: ").append(LocalDateTime.now().toString()); //Agrega la fecha actual: "Fecha: 2025-07-17T16:10:00" (esto se obtiene con LocalDateTime.now().toString()).
        return mensaje.toString(); //Devuelve el mensaje completo en forma de String.
    }

    public void enviarMail() {
        // Buscar responsables
        buscarMailsResponsablesDeReparaciones();

        // Generar contenido según Observación 2
        String mensaje = generarMensajeDeNotificacion();

        // Mostrar en monitor
        publicarEnMonitor(mensaje); // con repetición si lo requiere el CU

        // Enviar mail
        interfazEnvioMail.enviarMail(mailsResponsables, mensaje);
    }

}