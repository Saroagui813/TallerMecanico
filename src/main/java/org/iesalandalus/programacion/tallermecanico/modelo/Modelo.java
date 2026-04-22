package org.iesalandalus.programacion.tallermecanico.modelo;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
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

    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "La revisión no puede ser nula.");
        Cliente clienteReal = clientes.buscar(trabajo.getCliente());
        Vehiculo vehiculoReal = vehiculos.buscar(trabajo.getVehiculo());
        revisiones.insertar(new Trabajo(clienteReal, vehiculoReal, trabajo.getFechaInicio()));
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

    public Trabajo buscar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "La revisión no puede ser nula.");
        Trabajo encontrada = revisiones.buscar(trabajo);
        return new Trabajo(encontrada);
    }

    public Cliente modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        Objects.requireNonNull(nombre, "El nombre no puede ser nulo.");
        Objects.requireNonNull(telefono, "El teléfono no puede ser nulo.");
        return new Cliente(clientes.modificar(cliente, nombre, telefono));
    }

    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo,"La revisión no puede ser nula.");
        return new Trabajo(revisiones.anadirHoras(trabajo, horas));
    }

    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "La revisión no puede ser nula.");
        return new Trabajo(revisiones.anadirPrecioMaterial(trabajo, precioMaterial));
    }

    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "La revisión no puede ser nula.");
        return new Trabajo(revisiones.cerrar(trabajo, fechaFin));
    }

    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        List<Trabajo> revisionesCliente = revisiones.get(cliente);
        for (Trabajo trabajo : revisionesCliente) {
            revisiones.borrar(trabajo);
        }
        clientes.borrar(cliente);
    }

    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
        List<Trabajo> revisionesVehiculo = revisiones.get(vehiculo);
        for (Trabajo trabajo : revisionesVehiculo) {
            revisiones.borrar(trabajo);
        }
        vehiculos.borrar(vehiculo);
    }

    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "La revisión no puede ser nula.");
        revisiones.borrar(trabajo);
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

    public List<Trabajo> getRevisiones() {
        List<Trabajo> copia = new ArrayList<>();
        for (Trabajo trabajo : revisiones.get()) {
            copia.add(new Trabajo(trabajo));
        }
        return copia;
    }

}
