package org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trabajos implements org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos {

    private List<Trabajo> coleccionTrabajos;

    public Trabajos() {
        coleccionTrabajos = new ArrayList<>();
    }

    @Override
    public List<Trabajo> get() {
        return new ArrayList<>(coleccionTrabajos);
    }

    @Override
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
    @Override
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

    @Override
    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede insertar un trabajo nulo.");
        comprobarTrabajo(trabajo.getCliente(), trabajo.getVehiculo(), trabajo.getFechaInicio());
        coleccionTrabajos.add(trabajo);
    }

    private void comprobarTrabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaRevision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");

        for (Trabajo trabajo : coleccionTrabajos) {
            if (!trabajo.estaCerrado()) {
                if (trabajo.getCliente().equals(cliente)) {
                    throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo en curso.");
                }
                if (trabajo.getVehiculo().equals(vehiculo)) {
                    throw new TallerMecanicoExcepcion("El vehículo está actualmente en el taller.");
                }
            }

            if (trabajo.estaCerrado()) {
                if (!trabajo.getFechaFin().isBefore(fechaRevision)) {
                    if (trabajo.getCliente().equals(cliente)) {
                        throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo posterior.");
                    }
                    if (trabajo.getVehiculo().equals(vehiculo)) {
                        throw new TallerMecanicoExcepcion("El vehículo tiene otro trabajo posterior.");
                    }
                }
            }
        }
    }

    @Override
    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No puedo añadir horas a un trabajo nulo.");
        Trabajo trabajo1 = getTrabajoAbierto(trabajo.getVehiculo());
        trabajo1.anadirHoras(horas);
        return trabajo1;
    }

    private Trabajo getTrabajoAbierto(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Trabajo trabajoResultado = null;
        Objects.requireNonNull(vehiculo, "No puedo operar sobre una revisión nula.");
        for (Trabajo trabajo1 : coleccionTrabajos) {
            if (trabajo1.getVehiculo().equals(vehiculo) && !trabajo1.estaCerrado()) {
                trabajoResultado = trabajo1;
            }
        }

        if (trabajoResultado == null) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }

        return trabajoResultado;
    }

    @Override
    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No puedo añadir precio del material a un trabajo nulo.");
        Trabajo trabajo1 = getTrabajoAbierto(trabajo.getVehiculo());
        if (trabajo1 instanceof Mecanico mecanico) {
            mecanico.anadirPrecioMaterial(precioMaterial);
        } else {
            throw new TallerMecanicoExcepcion("No se puede añadir precio al material para este tipo de trabajos.");
        }
        return trabajo1;
    }

    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No puedo cerrar un trabajo nulo.");
        Trabajo trabajo1 = getTrabajoAbierto(trabajo.getVehiculo());
        trabajo1.cerrar(fechaFin);
        return trabajo1;
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "No se puede buscar un trabajo nulo.");
        int indice = coleccionTrabajos.indexOf(trabajo);
        return (indice != -1) ? coleccionTrabajos.get(indice) : null;
    }

    @Override
    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede borrar un trabajo nulo.");
        Trabajo buscando = buscar(trabajo);

        if (!coleccionTrabajos.contains(buscando)) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo igual.");
        }

        coleccionTrabajos.remove(buscando);
    }
}