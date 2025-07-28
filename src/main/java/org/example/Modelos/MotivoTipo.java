package org.example.Modelos;

import java.util.Objects;

public class MotivoTipo {

    private String descripcion;

    public MotivoTipo() {}

    public MotivoTipo(String descripcion) {
        this.descripcion = descripcion;
    }

    //Metodo correcto segun el diagrama de secuencia - Viki
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MotivoTipo)) return false;
        MotivoTipo that = (MotivoTipo) o;
        return Objects.equals(descripcion, that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descripcion);
    }

    public void setComentario(String comentario) {
    }
}
