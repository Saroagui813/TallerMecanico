package org.iesalandalus.programacion.tallermecanico.modelo.cascada;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Mecanico;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.FabricaFuenteDatos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModeloCascada implements Modelo {

    private IClientes clientes;
    private ITrabajos trabajos;
    private IVehiculos vehiculos;

    public ModeloCascada(FabricaFuenteDatos fabricaFuenteDatos) {
        Objects.requireNonNull(fabricaFuenteDatos, "La factoria de la fuente de datos no puede ser nula.");
        IFuenteDatos fuenteDatos = fabricaFuenteDatos.crear();
        clientes = fuenteDatos.crearClientes();
        vehiculos = fuenteDatos.crearVehiculos();
        trabajos = fuenteDatos.crearTrabajos();
    }

    @Override
    public void comenzar() {
        System.out.println("Modelo comenzado.");
    }

    @Override
    public void terminar() {
        System.out.println("Modelo terminado.");
    }

    @Override
    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
        clientes.insertar(new Cliente(cliente));
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        vehiculos.insertar(vehiculo);
    }

    @Override
    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Cliente clienteReal = clientes.buscar(trabajo.getCliente());
        Vehiculo vehiculoReal = vehiculos.buscar(trabajo.getVehiculo());
        if (trabajo instanceof Revision) {
            trabajos.insertar(new Revision(clienteReal, vehiculoReal, trabajo.getFechaInicio()));
        } else if (trabajo instanceof Mecanico) {
            trabajos.insertar(new Mecanico(clienteReal, vehiculoReal, trabajo.getFechaInicio()));
        }
    }

    @Override
    public Cliente buscar(Cliente cliente) {
        Cliente encontrado = clientes.buscar(cliente);
        return (encontrado != null) ? new Cliente(encontrado) : null;
    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
        return vehiculos.buscar(vehiculo);
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        Trabajo encontrado = trabajos.buscar(trabajo);
        return Trabajo.copiar(encontrado);
    }

    @Override
    public Cliente modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        Objects.requireNonNull(nombre, "El nombre no puede ser nulo.");
        Objects.requireNonNull(telefono, "El teléfono no puede ser nulo.");
        return new Cliente(clientes.modificar(cliente, nombre, telefono));
    }

    @Override
    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "La revisión no puede ser nula.");
        return trabajos.anadirHoras(trabajo, horas);
    }

    @Override
    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "La revisión no puede ser nula.");
        return trabajos.anadirPrecioMaterial(trabajo, precioMaterial);
    }

    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "El trabajo no puede ser nulo.");
        return trabajos.cerrar(trabajo, fechaFin);
    }

    @Override
    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        List<Trabajo> revisionesCliente = trabajos.get(cliente);
        for (Trabajo trabajo : revisionesCliente) {
            trabajos.borrar(trabajo);
        }
        clientes.borrar(cliente);
    }

    @Override
    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
        List<Trabajo> revisionesVehiculo = trabajos.get(vehiculo);
        for (Trabajo trabajo : revisionesVehiculo) {
            trabajos.borrar(trabajo);
        }
        vehiculos.borrar(vehiculo);
    }

    @Override
    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "La revisión no puede ser nula.");
        trabajos.borrar(trabajo);
    }

    @Override
    public List<Cliente> getClientes() {
        List<Cliente> copia = new ArrayList<>();
        for (Cliente cliente : clientes.get()) {
            copia.add(new Cliente(cliente));
        }
        return copia;
    }

    @Override
    public List<Vehiculo> getVehiculos() {
        List<Vehiculo> copia = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculos.get()) {
            copia.add(vehiculo);
        }
        return copia;
    }

    @Override
    public List<Trabajo> getTrabajos() {
        List<Trabajo> copia = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get()) {
            copia.add(Trabajo.copiar(trabajo));
        }
        return copia;
    }

    @Override
    public List<Trabajo> getTrabajos(Cliente cliente) {
        List<Trabajo> copia = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(cliente)) {
            copia.add(Trabajo.copiar(trabajo));
        }
        return copia;
    }

    @Override
    public List<Trabajo> getTrabajos(Vehiculo vehiculo) {
        List<Trabajo> copia = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(vehiculo)) {
            copia.add(Trabajo.copiar(trabajo));
        }
        return copia;
    }
}
