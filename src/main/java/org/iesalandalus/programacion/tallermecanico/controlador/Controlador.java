package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.vista.texto.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;

import java.util.Objects;

public class Controlador implements IControlador {
    private Modelo modelo;
    private Vista vista;

    public Controlador(Modelo modelo, Vista vista) {
        Objects.requireNonNull(vista,"La vista no puede ser nula");
        Objects.requireNonNull(modelo,"El modelo no puede ser nulo");
        vista.getGestorEventos().suscribir(this,Evento.values());
        this.modelo = modelo;
        this.vista = vista;
    }

    public void comenzar(){
        modelo.comenzar();
        vista.comenzar();
    }

    public void terminar(){
        modelo.terminar();
        vista.terminar();
    }

    @Override
    public void actualizar(Evento evento) {
        try {
            switch (evento){
                case INSERTAR_CLIENTE -> { modelo.insertar(vista.leerCliente()); vista.notificarResultado(Evento.INSERTAR_CLIENTE, "Cliente insertado correctamente",true);}
                case INSERTAR_VEHICULO -> { modelo.insertar(vista.leerVehiculo()); vista.notificarResultado(Evento.INSERTAR_VEHICULO, "Vehiculo insertado correctamente",true);}
                case INSERTAR_REVISION -> { modelo.insertar(vista.leerRevision()); vista.notificarResultado(Evento.INSERTAR_REVISION, "Revisión insertada correctamente",true);}
                case INSERTAR_MECANICO -> { modelo.insertar(vista.leerMecanico()); vista.notificarResultado(Evento.INSERTAR_MECANICO, "Mecánico insertado correctamente",true);}
                case BUSCAR_CLIENTE -> { modelo.buscar(vista.leerCliente()); vista.notificarResultado(Evento.BUSCAR_CLIENTE, "Cliente buscado correctamente",true);}
                case BUSCAR_VEHICULO -> { modelo.buscar(vista.leerVehiculo()); vista.notificarResultado(Evento.BUSCAR_VEHICULO, "Vehiculo buscado correctamente",true);}
                case BUSCAR_TRABAJO -> { modelo.buscar(vista.leerTrabajoVehiculo()); vista.notificarResultado(Evento.BUSCAR_TRABAJO, "Trabajo buscado correctamente",true);}
                case MODIFICAR_CLIENTE -> { modelo.modificar(vista.leerCliente(),vista.leerNuevoNombre(), vista.leerNuevoTelefono()); vista.notificarResultado(Evento.MODIFICAR_CLIENTE, "Cliente modificado correctamente",true);}
                case ANADIR_HORAS_TRABAJO -> { modelo.anadirHoras(vista.leerTrabajoVehiculo(),vista.leerHoras()); vista.notificarResultado(Evento.ANADIR_HORAS_TRABAJO, "Horas añadidas correctamente",true);}
                case ANADIR_PRECIO_MATERIAL_TRABAJO -> { modelo.anadirPrecioMaterial(vista.leerTrabajoVehiculo(),vista.leerPrecioMaterial()); vista.notificarResultado(Evento.ANADIR_PRECIO_MATERIAL_TRABAJO, "Precio añadido correctamente",true);}
                case CERRAR_TRABAJO -> { modelo.cerrar(vista.leerTrabajoVehiculo(),vista.leerFechaCierre()); vista.notificarResultado(Evento.CERRAR_TRABAJO, "Trabajo cerrado correctamente",true);}
                case BORRAR_CLIENTE -> { modelo.borrar(vista.leerCliente()); vista.notificarResultado(Evento.BORRAR_CLIENTE, "Cliente borrado correctamente",true);}
                case BORRAR_VEHICULO -> { modelo.borrar(vista.leerVehiculo()); vista.notificarResultado(Evento.BORRAR_VEHICULO, "Vehiculo borrado correctamente",true);}
                case BORRAR_TRABAJO -> { modelo.borrar(vista.leerTrabajoVehiculo()); vista.notificarResultado(Evento.BORRAR_TRABAJO, "Trabajo borrado correctamente",true);}
                case LISTAR_CLIENTE -> { modelo.getClientes(); vista.notificarResultado(Evento.LISTAR_CLIENTE, "Clientes listados correctamente",true);}
                case LISTAR_VEHICULO -> { modelo.getVehiculos(); vista.notificarResultado(Evento.LISTAR_VEHICULO, "Vehículos listados correctamente",true);}
                case LISTAR_TRABAJO -> { modelo.getTrabajos(); vista.notificarResultado(Evento.LISTAR_TRABAJO, "Trabajos listados correctamente",true);}
                case LISTAR_TRABAJO_CLIENTE -> { modelo.getTrabajos(vista.leerCliente()); vista.notificarResultado(Evento.LISTAR_TRABAJO_CLIENTE, "Trabajos de un cliente listados correctamente",true);}
                case LISTAR_TRABAJO_VEHICULO -> { modelo.getTrabajos(vista.leerVehiculo()); vista.notificarResultado(Evento.LISTAR_TRABAJO_VEHICULO, "Trabajos de un vehiculo listados correctamente",true);}
                case SALIR -> terminar();
            }
        } catch (Exception e){
            vista.notificarResultado(evento, e.getMessage(), false);
        }
    }
}