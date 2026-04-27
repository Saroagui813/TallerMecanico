package org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trabajos {

    private List<Trabajo> coleccionTrabajos;

    public Trabajos() {
        coleccionTrabajos = new ArrayList<>();
    }

    public List<Trabajo> get() {
        return new ArrayList<>(coleccionTrabajos);
    }

    public List<Trabajo> get(Cliente cliente) {
        List<Trabajo> coleccionResultante;
        coleccionResultante = new ArrayList<>();

        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getCliente().equals(cliente)) {
                coleccionResultante.add(trabajo);
            }
        }

        return coleccionResultante;
    }
    public List<Trabajo> get(Vehiculo vehiculo) {

        List<Trabajo> coleccionResultante;
        coleccionResultante = new ArrayList<>();

        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getVehiculo().equals(vehiculo)) {
                coleccionResultante.add(trabajo);
            }
        }
        return coleccionResultante;
    }

    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede insertar una revisión nula.");
        comprobarTrabajo(trabajo.getCliente(), trabajo.getVehiculo(), trabajo.getFechaInicio());
        coleccionTrabajos.add(trabajo);
    }

    private void comprobarTrabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaRevision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");

        for (Trabajo trabajo : coleccionTrabajos) {
            if (!trabajo.estaCerrado()) {
                if (trabajo.getCliente().equals(cliente)) {
                    throw new TallerMecanicoExcepcion("El cliente tiene otra revisión en curso.");
                }
                if (trabajo.getVehiculo().equals(vehiculo)) {
                    throw new TallerMecanicoExcepcion("El vehículo está actualmente en revisión.");
                }
            }

            if (trabajo.estaCerrado()) {
                if (fechaRevision.isAfter(trabajo.getFechaFin())) {
                    if (trabajo.getCliente().equals(cliente)) {
                        throw new TallerMecanicoExcepcion("El cliente tiene una revisión posterior.");
                    }
                    if (trabajo.getVehiculo().equals(vehiculo)) {
                        throw new TallerMecanicoExcepcion("El vehículo está actualmente en revisión.");
                    }
                }
            }
        }
    }

//    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
//        Trabajo trabajo1 = getTrabajoAbierto(trabajo.getVehiculo());
//        if (trabajo instanceof Revision revision) {
//            trabajo1 = new Revision(revision.anadirHoras(horas));
//        }
//        if (trabajo instanceof Mecanico mecanico) {
//            return new Mecanico(mecanico);
//        }
//        trabajo1.anadirHoras(horas);
//        return trabajo1;
//    }

    private Trabajo getTrabajoAbierto(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Trabajo trabajoResultado = null;
        Objects.requireNonNull(vehiculo, "No puedo operar sobre una revisión nula.");
        for (Trabajo trabajo1 : coleccionTrabajos) {
            if (trabajo1.getVehiculo().equals(vehiculo) && !trabajo1.estaCerrado()) {
                trabajoResultado = trabajo1;
            }
        }

        if (trabajoResultado == null) {
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }

        return trabajoResultado;
    }

    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        Trabajo trabajo1 = trabajo.ge;
        trabajo1.getPrecioEspecifico();
        return trabajo1;
    }

    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Trabajo trabajo1 = getTrabajoAbierto(trabajo);
        trabajo1.cerrar(fechaFin);
        return trabajo1;
    }

    public Trabajo buscar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "No se puede buscar una revisión nula.");
        int indice = coleccionTrabajos.indexOf(trabajo);
        return (indice != 1 ? coleccionTrabajos.get(indice) : null);
    }

    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede borrar una revisión nula.");
        Trabajo buscando = buscar(trabajo);

        if (!coleccionTrabajos.contains(buscando)) {
            throw new TallerMecanicoExcepcion("No existe alguna revisión igual.");
        }

        coleccionTrabajos.remove(trabajo);
    }
}
