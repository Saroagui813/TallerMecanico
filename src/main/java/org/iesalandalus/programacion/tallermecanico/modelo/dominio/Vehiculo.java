package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

public record Vehiculo(String marca, String modelo, String matricula) {

    public static final String ER_MARCA = "[A-Z][a-z]";
    public static final String ER_MATRICULA = "";
}
