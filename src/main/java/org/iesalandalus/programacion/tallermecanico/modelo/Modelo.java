package org.iesalandalus.programacion.tallermecanico.modelo;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Clientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Revisiones;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Vehiculos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Modelo {

    private Clientes clientes;
    private Revisiones revisiones;
    private Vehiculos  vehiculos;

    public Modelo() {

    }

    public void comenzar() {
        this.clientes = new Clientes();
        this.revisiones = new Revisiones();
        this.vehiculos = new Vehiculos();
    }

    public void terminar() {
        System.out.println("El modelo ha terminado.");
    }

    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        clientes.insertar(new Cliente(cliente));
    }

    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
        vehiculos.insertar(vehiculo);
    }

    public void insertar(Revision revision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision, "La revisión no puede ser nula.");
        Cliente clienteReal = clientes.buscar(revision.getCliente());
        Vehiculo vehiculoReal = vehiculos.buscar(revision.getVehiculo());
        revisiones.insertar(new Revision(clienteReal, vehiculoReal, revision.getFechaInicio()));
    }

    public Cliente buscar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        Cliente encontrado = clientes.buscar(cliente);
        return new Cliente(encontrado);
    }

    public Vehiculo buscar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
        return vehiculos.buscar(vehiculo);
    }

    public Revision buscar(Revision revision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision, "La revisión no puede ser nula.");
        Revision encontrada = revisiones.buscar(revision);
        return new Revision(encontrada);
    }

    public Cliente modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        Objects.requireNonNull(nombre, "El nombre no puede ser nulo.");
        Objects.requireNonNull(telefono, "El teléfono no puede ser nulo.");
        return new Cliente(clientes.modificar(cliente, nombre, telefono));
    }

    public Revision anadirHoras(Revision revision, int horas) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision,"La revisión no puede ser nula.");
        return new Revision(revisiones.anadirHoras(revision, horas));
    }

    public Revision anadirPrecioMaterial(Revision revision, float precioMaterial) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision, "La revisión no puede ser nula.");
        return new Revision(revisiones.anadirPrecioMaterial(revision, precioMaterial));
    }

    public Revision cerrar(Revision revision, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision, "La revisión no puede ser nula.");
        return new Revision(revisiones.cerrar(revision, fechaFin));
    }

    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        List<Revision> revisionesCliente = revisiones.get(cliente);
        for (Revision revision : revisionesCliente) {
            revisiones.borrar(revision);
        }
        clientes.borrar(cliente);
    }

    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
        List<Revision> revisionesVehiculo = revisiones.get(vehiculo);
        for (Revision revision : revisionesVehiculo) {
            revisiones.borrar(revision);
        }
        vehiculos.borrar(vehiculo);
    }

    public void borrar(Revision revision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision, "La revisión no puede ser nula.");
        revisiones.borrar(revision);
    }

    public List<Cliente> getClientes() {
        List<Cliente> copia = new ArrayList<>();
        for (Cliente cliente : clientes.get()) {
            copia.add(new Cliente(cliente));
        }
        return copia;
    }

    public List<Vehiculo> getVehiculos() {
        List<Vehiculo> copia = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculos.get()) {
            copia.add(vehiculo);
        }
        return copia;
    }

    public List<Revision> getRevisiones() {
        List<Revision> copia = new ArrayList<>();
        for (Revision revision : revisiones.get()) {
            copia.add(new Revision(revision));
        }
        return copia;
    }

}
