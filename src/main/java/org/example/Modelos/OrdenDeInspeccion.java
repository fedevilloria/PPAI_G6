package org.example.Modelos;

import java.time.LocalDateTime;

public class OrdenDeInspeccion {

    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraCierre; // cuando actualiza el estado a cerrada setea su fecha de cierre.
    private LocalDateTime fechaHoraFinalizacion; //para ordenes completamente realizadas
    private Integer numeroDeOrden;
    private String observacionCierre;
    private Empleado empleado;
    private Estado estado;
    private EstacionSismologica estacionSismologica;

    public OrdenDeInspeccion() {
    }

    public OrdenDeInspeccion(LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraCierre, LocalDateTime fechaHoraFinalizacion, Integer numeroDeOrden, String observacionCierre, Empleado empleado, Estado estado, EstacionSismologica estacionSismologica) {
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraCierre = fechaHoraCierre;
        this.fechaHoraFinalizacion = fechaHoraFinalizacion;
        this.numeroDeOrden = numeroDeOrden;
        this.observacionCierre = observacionCierre;
        this.empleado = empleado;
        this.estado = estado;
        this.estacionSismologica = estacionSismologica;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraCierre() {
        return fechaHoraCierre;
    }

    public void setFechaHoraCierre(LocalDateTime fechaHoraCierre) {
        this.fechaHoraCierre = fechaHoraCierre;
    }

    public LocalDateTime getFechaHoraFinalizacion() {
        return fechaHoraFinalizacion;
    }

    public void setFechaHoraFinalizacion(LocalDateTime fechaHoraFinalizacion) {
        this.fechaHoraFinalizacion = fechaHoraFinalizacion;
    }

    public Integer getNumeroDeOrden() {
        return numeroDeOrden;
    }

    public void setNumeroDeOrden(Integer numeroDeOrden) {
        this.numeroDeOrden = numeroDeOrden;
    }

    public String getObservacionCierre() {
        return observacionCierre;
    }

    public void setObservacionCierre(String observacionCierre) {
        this.observacionCierre = observacionCierre;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public boolean esEmpleado(Empleado empleado) {
        return this.getEmpleado().equals(empleado);
    }

    public boolean esCompletamenteRealizada(){
        return estado.esCompletamenteRealizada();
    }

    public String getDatos() {
        // Obtener número de orden y fecha
        Integer numeroOrden = this.getNumeroDeOrden();
        LocalDateTime fechaFin = this.getFechaHoraFinalizacion();

        // Obtener datos desde la estación sismológica asociada
        String nombreEstacion = this.getEstacionSismologica().getNombreEstacionSismologica();
        Integer identificadorSismografo = this.getEstacionSismologica().getSismografo().getIdentificadorSismografo();

        // Texto descriptivo con todos los datos
        return "Orden N°: " + numeroOrden +
                ", Finalizada: " + fechaFin +
                ", Estación: " + nombreEstacion +
                ", Sismógrafo ID: " + identificadorSismografo;
    }

    public EstacionSismologica getEstacionSismologica() {
        return estacionSismologica;
    }

    public void setEstacionSismologica(EstacionSismologica estacionSismologica) {
        this.estacionSismologica = estacionSismologica;
    }

    public void cerrar(Estado estadoCerrado, LocalDateTime fechaHoraActual) {
        setEstado(estadoCerrado);
        setFechaHoraCierre(fechaHoraActual);
    }

    @Override
    public String toString() {
        return "Orden N° " + numeroDeOrden + " - " + fechaHoraFinalizacion.toLocalDate();
    }

}
////// public String getNombreEstacionSismologica() {
//    // Delega la obtención del nombre a la Estación
//    return estacion.getNombre();
//}
//
//public String getIdentificadorSismografo() {
//    // Delega la obtención del identificador a la Estación, que a su vez lo delega al Sismógrafo
//    return estacion.getIdentificadorSismografo();
//}
// public boolean esDeEmpleado(Empleado empleado) {
//    // Asumimos que tienes un atributo 'responsable' de tipo Empleado.
//    // En un sistema real, compararíamos por ID, aquí comparamos la instancia.
//    if (this.responsable == null || empleado == null) {
//        return false;
//    }
//    return this.responsable.equals(empleado);
//}
