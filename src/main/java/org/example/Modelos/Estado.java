package org.example.Modelos;

public class Estado {

    private String nombre;
    private String descripcion;
    private String ambito;

    public Estado() {
    }

    public Estado(String nombre, String descripcion, String ambito) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ambito = ambito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public boolean esCerrado() {
        return "Cerrada".equalsIgnoreCase(nombre);
    }

    public boolean esAmbitoOrdenDeInspeccion() {
        return "Orden de Inspeccion".equalsIgnoreCase(ambito);
    }

    public boolean esCompletamenteRealizada() {
        return "Completamente Realizada".equalsIgnoreCase(nombre);
    }

    public boolean esFueraDeServicio() {
        return "Fuera de Servicio".equalsIgnoreCase(nombre);
    }

    public boolean esAmbitoSismografo() {
        return "Sismografo".equalsIgnoreCase(ambito);
    }

}
