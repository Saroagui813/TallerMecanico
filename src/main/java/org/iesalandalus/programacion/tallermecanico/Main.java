package org.iesalandalus.programacion.tallermecanico;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.vista.texto.VistaTexto;

public class Main {
    public static void main(String[] args) {
        Modelo modelo = new Modelo();
        VistaTexto vistaTexto = new VistaTexto();
        Controlador controlador = new Controlador(modelo, vistaTexto);
        controlador.comenzar();
    }
}
