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

    public void ponerEnReparacion(Estado nuevoEstado, Empleado empleado, List<MotivoFueraDeServicio> motivos) {
        LocalDateTime fechaHoraActual = LocalDateTime.now();

        // Finalizar el cambio de estado actual
        for (CambioEstado cambio : historialEstados) {
            if (cambio.esActual()) {
                cambio.setFechaHoraFin(fechaHoraActual);
                break;
            }
        }

        // Crear el nuevo CambioEstado
        CambioEstado nuevoCambio = new CambioEstado(
                nuevoEstado,
                fechaHoraActual,
                null, // el nuevo cambio de estado aún está activo
                motivos,
                empleado
        );

        // Agregar a historial y actualizar puntero
        historialEstados.add(nuevoCambio);
        this.ultimoCambioEstado = nuevoCambio;
    }




}
