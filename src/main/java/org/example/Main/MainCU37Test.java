package org.example.Main;
import org.example.Modelos.*;
import org.example.Gestores.GestorRI;
import org.example.Vistas.MenuPrincipal;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.*;

public class MainCU37Test {
    public static void main(String[] args) {

        // Crear estados
        Estado estadoRealizada = new Estado("Completamente Realizada","Todas las tareas tienen registro de resultado de inspección", "Orden de Inspeccion");
        Estado estadoCerrada = new Estado("Cerrada","Tienen que estar la observación de cierre", "Orden de Inspeccion");
        Estado estadoFS = new Estado("Fuera de Servicio","Sismografo puesto en reparación", "Sismografo");
        Estado estadoInhabilitado = new Estado("Inhabilitado por inspeccion","Sismografo inhabilitado", "Sismografo");

        List<Estado> estadosDelSistema = Arrays.asList(estadoRealizada, estadoCerrada, estadoFS);


        // Crear Rol
        Rol rol = new Rol("Empleado responsable del cierre de las ordenes de inspección","Responsable de Inspeccion");

        //Crear empleado y usuario logueado
        Empleado empleado = new Empleado(14952,"Martinez", "Ana", "ana@tecnico.com", "3534278338", rol);
        Usuario usuario = new Usuario("ana123", "1234", empleado);

        LocalDateTime fechaHoraInicioSesion = LocalDateTime.now().minusDays(2);
        Sesion sesion = new Sesion(fechaHoraInicioSesion,null,usuario);

        //Crear sismógrafo con estado inicial disponible
        Sismografo sismografo = new Sismografo();
        CambioEstado cambioInicial = new CambioEstado(estadoInhabilitado, LocalDateTime.now().minusDays(2), null, new ArrayList<>(), empleado);
        cambioInicial.setEstado(estadoInhabilitado);
        sismografo.getHistorialEstados().add(cambioInicial);
        sismografo.setUltimoCambioEstado(cambioInicial);

        //Estación y orden
        EstacionSismologica estacion = new EstacionSismologica();
        estacion.setSismografo(sismografo);

        OrdenDeInspeccion orden = new OrdenDeInspeccion();
        orden.setEstado(estadoRealizada);
        orden.setFechaHoraFinalizacion(LocalDateTime.now().minusHours(5));
        orden.setNroOrden(001);
        orden.setEstacionSismologica(estacion);

        List<OrdenDeInspeccion> ordenes = List.of(orden);

        //Motivos disponibles
        MotivoTipo m1 = new MotivoTipo("Falta calibracion");
        MotivoTipo m2 = new MotivoTipo("Sensor danado");
        List<MotivoTipo> motivos = Arrays.asList(m1, m2);

        // Crear gestor
        GestorRI gestor = new GestorRI(sesion, ordenes);
        gestor.setEstadosDisponibles(estadosDelSistema);

        //Iniciar el menú principal con datos cargados
        SwingUtilities.invokeLater(() -> new MenuPrincipal(gestor, motivos, estadosDelSistema));
    }
}
