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

    public void ponerEnReparacion(Estado estadoFueraDeServicio, Empleado responsable, Map<MotivoTipo, String> motivosYComentarios) {
        LocalDateTime fechaHoraActual = LocalDateTime.now();

        //Finalizar el cambio de estado actual
        for (CambioEstado cambio : historialEstados) {
            if (cambio.esActual()) {
                cambio.setFechaHoraFin(fechaHoraActual);
                break;
            }
        }

        //Crear la lista de motivos con sus comentarios
        List<MotivoFueraDeServicio> motivos = new ArrayList<>();

        for (Map.Entry<MotivoTipo, String> entry : motivosYComentarios.entrySet()) {
            MotivoTipo tipo = entry.getKey();
            String comentario = entry.getValue();

            MotivoFueraDeServicio motivo = new MotivoFueraDeServicio();
            motivo.setMotivoTipo(tipo);
            motivo.setComentario(comentario);
            motivos.add(motivo);
        }

        //Crear el nuevo CambioEstado usando el constructor completo
        CambioEstado nuevoCambio = new CambioEstado(
                estadoFueraDeServicio,
                fechaHoraActual,
                null, // fechaHoraFin en null, porque es el estado actual
                motivos,
                responsable
        );

        //Guardar como nuevo estado actual
        historialEstados.add(nuevoCambio);
        this.ultimoCambioEstado = nuevoCambio;
    }



}
