package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import java.util.List;
import java.util.Objects;

public class Vista {
    private Controlador controlador;

    public void setControlador(Controlador controlador) {
        Objects.requireNonNull(controlador, "ERROR: El controlador no puede ser nulo.");
        this.controlador = controlador;
    }

    public void comenzar() {
        Opcion opcion;
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            ejecutar(opcion);
        } while (opcion != Opcion.SALIR);
        controlador.terminar();
    }

    public void terminar() {
        System.out.println("¡¡¡Hasta luego Lucasss!!!");
    }

    private void ejecutar(Opcion opcion) {
        try {
            switch (opcion) {
                case INSERTAR_CLIENTE -> insertarCliente();
                case INSERTAR_VEHICULO -> insertarVehiculo();
                case INSERTAR_REVISION -> insertarRevision();
                case BUSCAR_CLIENTE -> buscarCliente();
                case BUSCAR_VEHICULO -> buscarVehiculo();
                case BUSCAR_REVISION -> buscarRevision();
            }
        } catch (Exception e) {
            System.out.printf("ERROR: %s%n", e.getMessage());
        }
    }

    private void insertarCliente() throws TallerMecanicoExcepcion {
        Consola.mostrarCabecera("Insertar Cliente");
        controlador.insertar(Consola.leerCliente());
        System.out.println("Cliente insertado correctamente.");
    }

    private void insertarVehiculo() throws TallerMecanicoExcepcion {
        Consola.mostrarCabecera("Insertar Vehículo");
        controlador.insertar(Consola.leerVehiculo());
        System.out.println("Vehículo insertado correctamente.");
    }

    private void insertarRevision() throws TallerMecanicoExcepcion {
        Consola.mostrarCabecera("Insertar Revisión");
        controlador.insertar(Consola.leerRevision());
        System.out.println("Revisión insertado correctamente.");
    }

    private void buscarCliente() throws TallerMecanicoExcepcion {
        Consola.mostrarCabecera("Buscar Cliente");
        Cliente cliente = controlador.buscar(Consola.leerClienteDni());
        System.out.println((cliente != null) ? cliente : "No existe ningún cliente con dicho DNI");
    }

    private void buscarVehiculo() throws TallerMecanicoExcepcion {
        Consola.mostrarCabecera("Buscar Vehiculo");
        Vehiculo vehiculo = controlador.buscar(Consola.leerVehiculoMatricula());
        System.out.println((vehiculo != null) ? vehiculo : "No existe ningún vehículo con dicha matrícula.");
    }

    private void buscarRevision() throws TallerMecanicoExcepcion {
        Consola.mostrarCabecera("Buscar Revisión");
        Trabajo trabajo = controlador.buscar(Consola.leerRevision());
        System.out.println((trabajo != null) ? trabajo : "No existe ninguna revisión para ese cliente, vehiculo.");
    }

    private void modificarCliente() throws TallerMecanicoExcepcion {
        Consola.mostrarCabecera("Modificar Cliente");
        controlador.modificar(Consola.leerClienteDni(), Consola.leerNuevoNombre(), Consola.leerNuevoTelefono());
        System.out.println("El cliente se ha modificado correctamente.");
    }

    private void anadirHoras() throws TallerMecanicoExcepcion {
        Consola.mostrarCabecera("Añadir Horas Revisión");
        controlador.anadirHoras(Consola.leerRevision(), Consola.leerHoras());
        System.out.println("Horas añadidas correctamente.");
    }

    private void anadirPrecioMaterial() throws TallerMecanicoExcepcion {
        Consola.mostrarCabecera("Añadir Precio Material Revisión");
        controlador.anadirPrecioMaterial(Consola.leerRevision(), Consola.leerPrecioMaterial());
        System.out.println("Precio material añadido correctamente.");
    }

    private void cerrarRevision() throws TallerMecanicoExcepcion {
        Consola.mostrarCabecera("Cerrar Revisión");
        controlador.cerrar(Consola.leerRevision(), Consola.leerFechaCierre());
        System.out.println("Revisión cerrada correctamente.");
    }

    private void borrarCliente() throws TallerMecanicoExcepcion {
        Consola.mostrarCabecera("Borrar Cliente");
        controlador.borrar(Consola.leerClienteDni());
        System.out.println("Cliente borrado correctamente.");
    }

    private void borrarVehiculo() throws TallerMecanicoExcepcion {
        Consola.mostrarCabecera("Borrar Vehículo");
        controlador.borrar(Consola.leerVehiculoMatricula());
        System.out.println("Vehículo borrado correctamente.");
    }

    private void borrarRevision() throws TallerMecanicoExcepcion {
        Consola.mostrarCabecera("Borrar revisión");
        controlador.borrar(Consola.leerRevision());
        System.out.println("Revisión borrada correctamente.");
    }

    private void listarClientes() {
        Consola.mostrarCabecera("Listar Clientes");
        List<Cliente> clientes = controlador.listarClientes();
        if (!clientes.isEmpty()) {
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }
        } else {
            System.out.println("No hay clientes que mostrar.");
        }
    }

    private void listarVehiculos() {
        Consola.mostrarCabecera("Listar Vehículos");
        List<Vehiculo> vehiculos = controlador.listarVehiculos();
        if (!vehiculos.isEmpty()) {
            for (Vehiculo vehiculo : vehiculos) {
                System.out.println(vehiculo);
            }
        } else {
            System.out.println("No hay vehículos que mostrar.");
        }
    }

    private void listarRevisiones() {
        Consola.mostrarCabecera("Listar Revisiones");
        List<Trabajo> revisiones = controlador.listarRevisionesVehiculo();
        if (!revisiones.isEmpty()) {
            for (Trabajo trabajo : revisiones) {
                System.out.println(trabajo);
            }
        } else {
            System.out.println("No hay revisiones que mostrar.");
        }
    }

    private void listarRevisionesCliente() {
        Consola.mostrarCabecera("Listar Revisiones Cliente");
        List<Trabajo> revisionesCliente = controlador.listarRevisionesVehiculo(Consola.leerClienteDni());
        if (!revisionesCliente.isEmpty()) {
            for (Trabajo trabajo : revisionesCliente) {
                System.out.println(trabajo);
            }
        } else {
            System.out.println("No hay revisiones que mostrar para dicho cliente.");
        }
    }

    private void listarRevisionesVehiculo() {
        Consola.mostrarCabecera("Listar Revisiones Vehículo");
        List<Trabajo> revisionesVehiculo = controlador.listarRevisionesVehiculo(Consola.leerVehiculoMatricula());
        if (!revisionesVehiculo.isEmpty()) {
            for (Trabajo trabajo : revisionesVehiculo) {
                System.out.println(trabajo);
            }
        } else {
            System.out.println("No hay revisiones que mostrar para dicho vehículo.");
        }
    }

    private void salir() {

    }

}
