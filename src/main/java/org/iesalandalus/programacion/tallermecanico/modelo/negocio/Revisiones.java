package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import javax.swing.event.CaretListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Revisiones {

    private List<Revision> coleccionRevisiones;

    public Revisiones() {
        coleccionRevisiones = new ArrayList<>();
    }

    public List<Revision> get() {
        return new ArrayList<>(coleccionRevisiones);
    }

    public List<Revision> get(Cliente cliente) {
        List<Revision> coleccionResultante;
        coleccionResultante = new ArrayList<>();

        for (Revision revision : coleccionRevisiones) {
            if (revision.getCliente().equals(cliente)) {
                coleccionResultante.add(revision);
            }
        }

        return coleccionResultante;
    }
    public List<Revision> get(Vehiculo vehiculo) {

        List<Revision> coleccionResultante;
        coleccionResultante = new ArrayList<>();

        for (Revision revision : coleccionRevisiones) {
            if (revision.getVehiculo().equals(vehiculo)) {
                coleccionResultante.add(revision);
            }
        }
        return coleccionResultante;
    }

    public void insertar(Revision revision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision, "No se puede insertar una revisión nula.");
        comprobarRevision(revision.getCliente(), revision.getVehiculo(), revision.getFechaInicio());
        coleccionRevisiones.add(revision);
    }

    private void comprobarRevision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaRevision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");

        for (Revision revision : coleccionRevisiones) {
            if (!revision.estaCerrada()) {
                if (revision.getCliente().equals(cliente)) {
                    throw new TallerMecanicoExcepcion("El cliente tiene otra revisión en curso.");
                }
                if (revision.getVehiculo().equals(vehiculo)) {
                    throw new TallerMecanicoExcepcion("El vehículo está actualmente en revisión.");
                }
            }

            if (revision.estaCerrada()) {
                if (fechaRevision.isAfter(revision.getFechaFin())) {
                    if (revision.getCliente().equals(cliente)) {
                        throw new TallerMecanicoExcepcion("El cliente tiene una revisión posterior.");
                    }
                    if (revision.getVehiculo().equals(vehiculo)) {
                        throw new TallerMecanicoExcepcion("El vehículo está actualmente en revisión.");
                    }
                }
            }
        }
    }

    private Revision getRevision(Revision revision) throws TallerMecanicoExcepcion {
        Revision revisionResultado = null;
        Objects.requireNonNull(revision, "No puedo operar sobre una revisión nula.");
        for (Revision revision1 : coleccionRevisiones) {
            if (revision1.equals(revision)) {
                revisionResultado = revision;
            }
        }

        if (revisionResultado == null) {
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }

        return revision;
    }

    public Revision anadirHoras(Revision revision, int horas) throws TallerMecanicoExcepcion {
        Revision revision1 = getRevision(revision);
        revision1.anadirHoras(horas);
        return revision1;
    }

    public Revision anadirPrecioMaterial(Revision revision, float precioMaterial) throws TallerMecanicoExcepcion {
        Revision revision1 = getRevision(revision);
        revision1.anadirPrecioMaterial(precioMaterial);
        return revision1;
    }

    public Revision cerrar(Revision revision, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Revision revision1 = getRevision(revision);
        revision1.cerrar(fechaFin);
        return revision1;
    }

    public Revision buscar(Revision revision) {
        Objects.requireNonNull(revision, "No se puede buscar una revisión nula.");
        int indice = coleccionRevisiones.indexOf(revision);
        return (indice != 1 ? coleccionRevisiones.get(indice) : null);
    }

    public void borrar(Revision revision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision, "No se puede borrar una revisión nula.");
        Revision buscando = buscar(revision);

        if (!coleccionRevisiones.contains(buscando)) {
            throw new TallerMecanicoExcepcion("No existe alguna revisión igual.");
        }

        coleccionRevisiones.remove(revision);
    }
}
