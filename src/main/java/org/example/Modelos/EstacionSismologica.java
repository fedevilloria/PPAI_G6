package org.example.Modelos;

import java.time.LocalDate;

public class EstacionSismologica {

    private Integer codEstacion;
    private LocalDate fechaSolicitudCertificacion;
    private boolean certificacionDeAdquisicion;
    private double latitud;
    private double longitud;
    private String nombreEstacionSismologica;
    private Integer nroCertificacionAdquisicion;
    private Sismografo sismografo;

    public EstacionSismologica() {
    }

    public EstacionSismologica(Integer codEstacion, LocalDate fechaSolicitudCertificacion, boolean certificacionDeAdquisicion, double latitud, double longitud, String nombreEstacionSismologica, Integer nroCertificacionAdquisicion, Sismografo sismografo) {
        this.codEstacion = codEstacion;
        this.fechaSolicitudCertificacion = fechaSolicitudCertificacion;
        this.certificacionDeAdquisicion = certificacionDeAdquisicion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombreEstacionSismologica = nombreEstacionSismologica;
        this.nroCertificacionAdquisicion = nroCertificacionAdquisicion;
        this.sismografo = sismografo;
    }

    public Integer getCodEstacion() {
        return codEstacion;
    }

    public void setCodEstacion(Integer codEstacion) {
        this.codEstacion = codEstacion;
    }

    public LocalDate getFechaSolicitudCertificacion() {
        return fechaSolicitudCertificacion;
    }

    public void setFechaSolicitudCertificacion(LocalDate fechaSolicitudCertificacion) {
        this.fechaSolicitudCertificacion = fechaSolicitudCertificacion;
    }

    public boolean isCertificacionDeAdquisicion() {
        return certificacionDeAdquisicion;
    }

    public void setCertificacionDeAdquisicion(boolean certificacionDeAdquisicion) {
        this.certificacionDeAdquisicion = certificacionDeAdquisicion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getNombreEstacionSismologica() {
        return nombreEstacionSismologica;
    }

    public void setNombre(String nombre) {
        this.nombreEstacionSismologica = nombre;
    }

    public Integer getNroCertificacionAdquisicion() {
        return nroCertificacionAdquisicion;
    }

    public void setNroCertificacionAdquisicion(Integer nroCertificacionAdquisicion) {
        this.nroCertificacionAdquisicion = nroCertificacionAdquisicion;
    }

    public Sismografo getSismografo() {
        return sismografo;
    }

    public void setSismografo(Sismografo sismografo) {
        this.sismografo = sismografo;
    }
}
