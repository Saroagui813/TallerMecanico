package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.GestorEventos;

import java.util.List;
import java.util.Objects;

public class VistaTexto {

    private final GestorEventos gestorEventos = new GestorEventos(Evento.values());

    public GestorEventos getGestorEventos() {return gestorEventos};

    public void comenzar() {
        Evento evento;
        do {
            Consola.mostrarMenu();
            evento = Consola.elegirOpcion();
            ejecutar(evento);
        } while (evento != Evento.SALIR);
    }

    private void ejecutar(Evento evento) {
        Consola.mostrarCabecera(evento.toString());
        gestorEventos.notificar(evento);
    }

    public void terminar() {
        System.out.println("¡¡¡Hasta luego Lucasss!!!");
    }

    public Cliente leerClienteDni() {
        return Cliente.get(Consola.leerCadena("Introduce el DNI: "));
    }

    public String LeerNuevoNombre() {
        return Consola.leerCadena("Introduce el nuevo nombre: ");
    }

    public String leerNuevoTelefono() {
        return Consola.leerCadena("Introduce el nuevo teléfono: ");
    }

    public Vehiculo leerVehiculo() {
        String marca = Consola.leerCadena("Introduce la marca: ");
        String modelo = Consola.leerCadena("Introduce el modelo: ");
        String matricula = Consola.leerCadena("Introduce la matrícula: ");
        return new Vehiculo(marca, modelo, matricula);
    }



    private void salir() {

    }

}
