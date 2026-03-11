package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

public class Cliente {
    private static final String ER_NOMBRE = "([A-Z][a-z]+)(\\s[A-Z][a-z]+)*";
    private static final String ER_DNI = "[0-9]{8}[A-HJ-NP-TV-Z]";
    private static final String ER_TELEFONO = "[679][0-9]{8}";

    private String nombre;
    private String dni;
    private String telefono;

    public Cliente(String nombre, String dni, String telefono) {
        setNombre(nombre);
        setDni(dni);
        setTelefono(telefono);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre.equals(ER_NOMBRE))
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    private void setDni(String dni) {
        (comprobarLetraDni(dni)
        this.dni = dni;
    }

    private boolean comprobarLetraDni(String dni) {
        char[] letras = {'T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E'};

    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


}
