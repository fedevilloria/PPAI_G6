package org.example.Gestores;

import org.example.Modelos.*;
import org.example.Modelos.MotivoTipo;

import java.time.LocalDateTime;
import java.util.*;

public class GestorRI {
    private Sesion sesion;
    private List<OrdenDeInspeccion> ordenesDeInspeccion;
    private OrdenDeInspeccion ordenSeleccionada;
    private String observacionCierre;
    private Map<MotivoTipo, String> motivosYComentarios;
    private List<Estado> estadosDisponibles;
    private List<MotivoTipo> motivosDisponibles; //Viki: Se agregan motivos disponibles
    private boolean situacionSismografoHabilitada = false;

    public GestorRI() {
        this.ordenesDeInspeccion = new ArrayList<>();
        this.motivosYComentarios = new HashMap<>();
    }

    public GestorRI(Sesion sesion, List<OrdenDeInspeccion> ordenes) {
        this();
        this.sesion = sesion;
        this.ordenesDeInspeccion = ordenes;
    }

    public void setMotivosDisponibles(List<MotivoTipo> motivosDisponibles) {
        this.motivosDisponibles = motivosDisponibles;
    }

    public List<OrdenDeInspeccion> getOrdenesDeInspeccion() {
        return ordenesDeInspeccion;
    }

    public void tomarSeleccionOrden(OrdenDeInspeccion orden) {
        this.ordenSeleccionada = orden;
    }
    public void habilitarActualizarSituacionSismografo() {
        if (ordenSeleccionada == null) {
            throw new IllegalStateException("No hay una orden seleccionada");
        }

        // Por ahora simplemente marcamos un flag interno para indicar que se habilitó
        this.situacionSismografoHabilitada = true;
    }


    //Paso 6-Viki: Pantalla consulta motivos disponibles al Gestor - Faltaba - Self del Gestor
    public List<MotivoTipo> buscarTiposDeMotivos() {
        return this.motivosDisponibles != null ? this.motivosDisponibles : new ArrayList<>();
    }

    public List<Estado> getEstadosDisponibles() {
        return estadosDisponibles;
    }

    public void setEstadosDisponibles(List<Estado> estadosDisponibles) {
        this.estadosDisponibles = estadosDisponibles;
    }

    //Paso 7-a-Viki: Vista llama al GestorRI pasando los motivos seleccionados
    public void tomarSeleccionMotivosTipos(List<MotivoTipo> motivos) {
        Map<MotivoTipo, String> vacios = new HashMap<>();
        for (MotivoTipo m : motivos) {
            vacios.put(m, "");
        }
        this.motivosYComentarios = vacios;
    }

    //Paso 7-b-Viki: Vista llama al GestorRI pasando los comentarios por motivo
    public void tomarIngresoComentarioMotivo(Map<MotivoTipo, String> motivosYComentarios) {
        this.motivosYComentarios = motivosYComentarios;
    }

    public void tomarObservacionCierre(String observacion) {
        this.observacionCierre = observacion;
    }

    public void confirmarCierre() {
        if (ordenSeleccionada != null) {
            for (Map.Entry<MotivoTipo, String> entry : motivosYComentarios.entrySet()) {
                MotivoTipo motivo = entry.getKey();
                String comentario = entry.getValue();
                motivo.setComentario(comentario); // Asignar comentario al motivo
            }

            ordenSeleccionada.setObservacionCierre(observacionCierre); // Observación final
        }
    }

    public OrdenDeInspeccion getOrdenSeleccionada() {
        return ordenSeleccionada;
    }
}
