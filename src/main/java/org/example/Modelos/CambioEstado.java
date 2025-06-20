package org.example.Modelos;
import java.time.LocalDateTime;
import java.util.List;

public class CambioEstado {

    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private List<MotivoFueraDeServicio> motivosFueraDeServicio;;
    private Empleado empleadoResponsable;
    private Estado estado;

    public CambioEstado() {
    }

    public CambioEstado(Estado estado, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, List<MotivoFueraDeServicio> motivosFueraDeServicio, Empleado empleadoResponsable) {
        this.estado = estado;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.motivosFueraDeServicio = motivosFueraDeServicio;
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

    public List<MotivoFueraDeServicio> getMotivosFueraDeServicio() {
        return motivosFueraDeServicio;
    }

    public void setMotivosFueraDeServicio(List<MotivoFueraDeServicio> motivosFueraDeServicio) {
        this.motivosFueraDeServicio = motivosFueraDeServicio;
    }

    public Empleado getEmpleadoResponsable() {
        return empleadoResponsable;
    }

    public void setEmpleadoResponsable(Empleado empleadoResponsable) {
        this.empleadoResponsable = empleadoResponsable;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void agregarMotivo(MotivoFueraDeServicio motivo) {
        motivosFueraDeServicio.add(motivo);
    }

    public boolean esActual() {
        return fechaHoraFin == null;
    }


}
