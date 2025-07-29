package org.example.Modelos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sismografo {

    private LocalDate fechaAdquisicion;
    private Integer identificadorSismografo;
    private Integer nroSerie;
    private List<CambioEstado> historialEstados = new ArrayList<>();
    private CambioEstado ultimoCambioEstado;
    private EstacionSismologica estacionSismologica;

    public Sismografo() {
    }

    public Sismografo(LocalDate fechaAdquisicion, Integer identificadorSismografo, Integer nroSerie, List<CambioEstado> historialEstados) {
        this.fechaAdquisicion = fechaAdquisicion;
        this.identificadorSismografo = identificadorSismografo;
        this.nroSerie = nroSerie;
        this.historialEstados = historialEstados;
    }

    public EstacionSismologica getEstacionSismologica() {
        return estacionSismologica;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public Integer getIdentificadorSismografo() {
        return identificadorSismografo;
    }

    public void setIdentificadorSismografo(Integer identificadorSismografo) {
        this.identificadorSismografo = identificadorSismografo;
    }

    public Integer getNroSerie() {
        return nroSerie;
    }

    public void setNroSerie(Integer nroSerie) {
        this.nroSerie = nroSerie;
    }

    public List<CambioEstado> getHistorialEstados() {
        return historialEstados;
    }

    public void setHistorialEstados(List<CambioEstado> historialEstados) {
        this.historialEstados = historialEstados;
    }

    public CambioEstado getUltimoCambioEstado() {
        return ultimoCambioEstado;
    }

    public void setUltimoCambioEstado(CambioEstado cambio) {
        this.ultimoCambioEstado = cambio;
    }

    public void ponerEnReparacion(Estado estado, Empleado responsable, List<MotivoFueraDeServicio> motivos) {
        LocalDateTime fechaHoraActual = LocalDateTime.now();

        // Finalizar el cambio de estado actual
        CambioEstado cambioActual = this.buscarUltimoCambioEstado();
        if (cambioActual != null) {
            cambioActual.setFechaHoraFin(fechaHoraActual);
        }

        // Crear nuevo cambio de estado
        CambioEstado nuevoCambio = new CambioEstado();
        nuevoCambio.setEstado(estado);
        nuevoCambio.setFechaHoraInicio(fechaHoraActual);
        nuevoCambio.setEmpleadoResponsable(responsable);
        nuevoCambio.setMotivosFueraDeServicio(motivos);

        historialEstados.add(nuevoCambio);
        this.setUltimoCambioEstado(nuevoCambio);
    }

    public CambioEstado buscarUltimoCambioEstado() {
        for (CambioEstado cambio : historialEstados) {
            if (cambio.esEstadoActual()) {
                return cambio;
            }
        }
        return null;
    }

}
