package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Revisiones {

    private List<Trabajo> coleccionRevisiones;

    public Revisiones() {
        coleccionRevisiones = new ArrayList<>();
    }

    public List<Trabajo> get() {
        return new ArrayList<>(coleccionRevisiones);
    }

    public List<Trabajo> get(Cliente cliente) {
        List<Trabajo> coleccionResultante;
        coleccionResultante = new ArrayList<>();

        for (Trabajo trabajo : coleccionRevisiones) {
            if (trabajo.getCliente().equals(cliente)) {
                coleccionResultante.add(trabajo);
            }
        }

        return coleccionResultante;
    }
    public List<Trabajo> get(Vehiculo vehiculo) {

        List<Trabajo> coleccionResultante;
        coleccionResultante = new ArrayList<>();

        for (Trabajo trabajo : coleccionRevisiones) {
            if (trabajo.getVehiculo().equals(vehiculo)) {
                coleccionResultante.add(trabajo);
            }
        }
        return coleccionResultante;
    }

    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede insertar una revisión nula.");
        comprobarRevision(trabajo.getCliente(), trabajo.getVehiculo(), trabajo.getFechaInicio());
        coleccionRevisiones.add(trabajo);
    }

    private void comprobarRevision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaRevision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");

        for (Trabajo trabajo : coleccionRevisiones) {
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

    private Trabajo getRevision(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Trabajo trabajoResultado = null;
        Objects.requireNonNull(trabajo, "No puedo operar sobre una revisión nula.");
        for (Trabajo trabajo1 : coleccionRevisiones) {
            if (trabajo1.equals(trabajo)) {
                trabajoResultado = trabajo;
            }
        }

        if (trabajoResultado == null) {
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }

        return trabajo;
    }

    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
        Trabajo trabajo1 = getRevision(trabajo);
        trabajo1.anadirHoras(horas);
        return trabajo1;
    }

    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        Trabajo trabajo1 = getRevision(trabajo);
        trabajo1.anadirPrecioMaterial(precioMaterial);
        return trabajo1;
    }

    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Trabajo trabajo1 = getRevision(trabajo);
        trabajo1.cerrar(fechaFin);
        return trabajo1;
    }

    public Trabajo buscar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "No se puede buscar una revisión nula.");
        int indice = coleccionRevisiones.indexOf(trabajo);
        return (indice != 1 ? coleccionRevisiones.get(indice) : null);
    }

    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede borrar una revisión nula.");
        Trabajo buscando = buscar(trabajo);

        if (!coleccionRevisiones.contains(buscando)) {
            throw new TallerMecanicoExcepcion("No existe alguna revisión igual.");
        }

        coleccionRevisiones.remove(trabajo);
    }
}
