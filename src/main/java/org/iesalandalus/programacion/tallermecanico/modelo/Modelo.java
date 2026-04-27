package org.iesalandalus.programacion.tallermecanico.modelo;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria.Clientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria.Trabajos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria.Vehiculos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Modelo {

    private IClientes IClientes;
    private ITrabajos trabajos;
    private IVehiculos IVehiculos;

    public Modelo() {

    }

    public void comenzar() {
        this.IClientes = new Clientes();
        this.trabajos = new Trabajos();
        this.IVehiculos = new Vehiculos();
    }

    public void terminar() {
        System.out.println("El modelo ha terminado.");
    }

    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        IClientes.insertar(new Cliente(cliente));
    }

    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
        IVehiculos.insertar(vehiculo);
    }

    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "La revisión no puede ser nula.");
        Cliente clienteReal = IClientes.buscar(trabajo.getCliente());
        Vehiculo vehiculoReal = IVehiculos.buscar(trabajo.getVehiculo());
        trabajos.insertar(new Trabajo(clienteReal, vehiculoReal, trabajo.getFechaInicio()));
    }

    public Cliente buscar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        Cliente encontrado = IClientes.buscar(cliente);
        return new Cliente(encontrado);
    }

    public Vehiculo buscar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
        return IVehiculos.buscar(vehiculo);
    }

    public Trabajo buscar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "La revisión no puede ser nula.");
        Trabajo encontrada = trabajos.buscar(trabajo);
        return new Trabajo(encontrada);
    }

    public Cliente modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        Objects.requireNonNull(nombre, "El nombre no puede ser nulo.");
        Objects.requireNonNull(telefono, "El teléfono no puede ser nulo.");
        return new Cliente(IClientes.modificar(cliente, nombre, telefono));
    }

    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo,"La revisión no puede ser nula.");
        return new Trabajo(trabajos.anadirHoras(trabajo, horas));
    }

    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "La revisión no puede ser nula.");
        return new Trabajo(trabajos.anadirPrecioMaterial(trabajo, precioMaterial));
    }

    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "La revisión no puede ser nula.");
        return new Trabajo(trabajos.cerrar(trabajo, fechaFin));
    }

    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        List<Trabajo> revisionesCliente = trabajos.get(cliente);
        for (Trabajo trabajo : revisionesCliente) {
            trabajos.borrar(trabajo);
        }
        IClientes.borrar(cliente);
    }

    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
        List<Trabajo> revisionesVehiculo = trabajos.get(vehiculo);
        for (Trabajo trabajo : revisionesVehiculo) {
            trabajos.borrar(trabajo);
        }
        IVehiculos.borrar(vehiculo);
    }

    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "La revisión no puede ser nula.");
        trabajos.borrar(trabajo);
    }

    public List<Cliente> getClientes() {
        List<Cliente> copia = new ArrayList<>();
        for (Cliente cliente : IClientes.get()) {
            copia.add(new Cliente(cliente));
        }
        return copia;
    }

    public List<Vehiculo> getVehiculos() {
        List<Vehiculo> copia = new ArrayList<>();
        for (Vehiculo vehiculo : IVehiculos.get()) {
            copia.add(vehiculo);
        }
        return copia;
    }

    public List<Trabajo> getRevisiones() {
        List<Trabajo> copia = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get()) {
            copia.add(new Trabajo(trabajo));
        }
        return copia;
    }

}
