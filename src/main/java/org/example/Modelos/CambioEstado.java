package org.example.Modelos;
import java.time.LocalDateTime;

public class CambioEstado {

    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private MotivoFueraDeServicio motivoFueraDeServicio;
    private Empleado empleadoResponsable;

    public CambioEstado() {
    }

    public CambioEstado(LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, MotivoFueraDeServicio motivoFueraDeServicio, Empleado empleadoResponsable) {
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.motivoFueraDeServicio = motivoFueraDeServicio;
        this.empleadoResponsable = empleadoResponsable;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public MotivoFueraDeServicio getMotivoFueraDeServicio() {
        return motivoFueraDeServicio;
    }

    public void setMotivoFueraDeServicio(MotivoFueraDeServicio motivoFueraDeServicio) {
        this.motivoFueraDeServicio = motivoFueraDeServicio;
    }

    public void esActual(){

    }

}
