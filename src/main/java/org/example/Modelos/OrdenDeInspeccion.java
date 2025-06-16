package org.example.Modelos;

import java.time.LocalDateTime;

public class OrdenDeInspeccion {

    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraCierre; // cuando actualiza el estado a cerrada setea su fecha de cierre.
    private LocalDateTime fechaHoraFinalizacion; //para ordenes completamente realizadas
    private Integer nroOrden;
    private String observacionCierre;
    private Empleado empleado;
    private Estado estado;

    public OrdenDeInspeccion() {
    }

    public OrdenDeInspeccion(LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraCierre, LocalDateTime fechaHoraFinalizacion, Integer nroOrden, String observacionCierre, Empleado empleado) {
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraCierre = fechaHoraCierre;
        this.fechaHoraFinalizacion = fechaHoraFinalizacion;
        this.nroOrden = nroOrden;
        this.observacionCierre = observacionCierre;
        this.empleado = empleado;
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

    public Integer getNroOrden() {
        return nroOrden;
    }

    public void setNroOrden(Integer nroOrden) {
        this.nroOrden = nroOrden;
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

    public void esEmpleado(){

    }

    public boolean esCompletamenteRealizada(){
        return estado.esCompletamenteRealizada();
    }

}
