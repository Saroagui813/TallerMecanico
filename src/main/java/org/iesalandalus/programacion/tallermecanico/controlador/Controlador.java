package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Controlador {

    private final Modelo modelo;
    private final Vista vista;

    public Controlador(Modelo modelo, Vista vista) {
        Objects.requireNonNull(modelo, "ERROR: El modelo no puede ser nulo.");
        Objects.requireNonNull(vista, "ERROR: La vista no puede ser nula.");
        this.modelo = modelo;
        this.vista = vista;
        vista.setControlador(this);
    }

    public void comenzar() {
        modelo.comenzar();
        vista.comenzar();
    }

    public void terminar() {
        modelo.terminar();
        vista.terminar();
    }

    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
        modelo.insertar(cliente);
    }

    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        modelo.insertar(vehiculo);
    }

    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        modelo.insertar(trabajo);
    }

    public Cliente buscar(Cliente cliente) throws TallerMecanicoExcepcion {
        return modelo.buscar(cliente);
    }

    public Vehiculo buscar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        return modelo.buscar(vehiculo);
    }

    public Trabajo buscar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        return modelo.buscar(trabajo);
    }

    public Cliente modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion {
        return modelo.modificar(cliente, nombre, telefono);
    }

    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
        return modelo.anadirHoras(trabajo, horas);
    }

    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        return modelo.anadirPrecioMaterial(trabajo, precioMaterial);
    }

    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        return modelo.cerrar(trabajo, fechaFin);
    }

    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
        modelo.borrar(cliente);
    }

    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        modelo.borrar(vehiculo);
    }

    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        modelo.borrar(trabajo);
    }

    public List<Cliente> listarClientes() {
        return modelo.getClientes();
    }

    public List<Vehiculo> listarVehiculos() {
        return modelo.getVehiculos();
    }

    public List<Trabajo> listarRevisionesVehiculo() {
        return modelo.getRevisiones();
    }

    public List<Trabajo> listarRevisionesVehiculo(Cliente cliente) {
        return modelo.getRevisiones(cliente);
    }

    public List<Trabajo> listarRevisionesVehiculo(Vehiculo vehiculo) {
        return modelo.getRevisiones(vehiculo);
    }
}
