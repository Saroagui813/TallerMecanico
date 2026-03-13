package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;

import java.util.Objects;

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

    public Cliente(Cliente cliente) {
        Objects.requireNonNull(cliente, "No es posible copiar un cliente nulo.");
        nombre = cliente.nombre;
        dni = cliente.dni;
        telefono = cliente.telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        Objects.requireNonNull(nombre, "El nombre no puede ser nulo.");
        if (nombre.matches(ER_NOMBRE)) {
            this.nombre = nombre;
        }
    }

    public String getDni() {
        return dni;
    }

    private void setDni(String dni) throws TallerMecanicoExcepcion {
        if (comprobarLetraDni(dni)) {
            this.dni = dni;
        } else {
            throw new TallerMecanicoExcepcion("La letra del dni debe ser válida");
        }
    }

    private boolean comprobarLetraDni(String dni) {
        char[] letras = {'T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E'};
        int numero = Integer.parseInt(dni.substring(0, 8));
        char letraDni = letras[numero % 23];
        return letraDni == dni.charAt(8);
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        Objects.requireNonNull("El teléfono no puede ser nulo.");
        this.telefono = telefono;
    }

    public static Cliente get(String dni) {
        return new Cliente("Jaimito", "77650309D", "722271212");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(nombre, cliente.nombre) && Objects.equals(dni, cliente.dni) && Objects.equals(telefono, cliente.telefono);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, dni, telefono);
    }

    @Override
    public String toString() {
        return String.format("Cliente (nombre=%s, dni=%s, telefono=%s)", nombre, dni, telefono);
    }
}
