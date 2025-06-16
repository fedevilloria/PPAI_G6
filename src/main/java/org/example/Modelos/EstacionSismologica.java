package org.example.Modelos;

import java.time.LocalDate;

public class EstacionSismologica {

    private Integer codEstacion;
    private LocalDate fechaSolicitudCertificacion;
    // atributo Certificación de Adquisición, preguntar profe.
    private double latitud;
    private double longitud;
    private String nombre;
    private Integer nroCertificacionAdquisicion;

    public EstacionSismologica() {
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNroCertificacionAdquisicion() {
        return nroCertificacionAdquisicion;
    }

    public void setNroCertificacionAdquisicion(Integer nroCertificacionAdquisicion) {
        this.nroCertificacionAdquisicion = nroCertificacionAdquisicion;
    }
}
