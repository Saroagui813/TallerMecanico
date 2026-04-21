package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Revision {
    public static final float PRECIO_HORA = 30F;
    public static final float PRECIO_DIA = 10F;
    public static final float PRECIO_MATERIAL = 1.5F;
    static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private Cliente cliente;
    private Vehiculo vehiculo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int horas;
    private float precioMaterial;

    public Revision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio){
        setCliente(cliente);
        setVehiculo(vehic);

    }

    public Revision(Revision revision){

    }

    public Cliente getCliente(){
        return cliente;
    }

    private void setCliente(Cliente cliente){
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        this.cliente = cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    private void setVehiculo() {

    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    private void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    private void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getHoras() {
        return horas;
    }

    public void anadirHoras(int horas) {

    }

    public float getPrecioMaterial() {
        return precioMaterial;
    }

    public void anadirPrecioMaterial(float precioMaterial) {

    }

    public boolean estaCerrada() {
        return true;
    }

    public void cerrar(LocalDate fechaFin) {

    }

    public float getPrecio() {

    }

    private float getDias() {

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Revision revision = (Revision) o;
        return horas == revision.horas && Float.compare(precioMaterial, revision.precioMaterial) == 0 && Objects.equals(cliente, revision.cliente) && Objects.equals(vehiculo, revision.vehiculo) && Objects.equals(fechaInicio, revision.fechaInicio) && Objects.equals(fechaFin, revision.fechaFin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cliente, vehiculo, fechaInicio, fechaFin, horas, precioMaterial);
    }

    @Override
    public String toString() {
        return String.format("Revision (cliente=%s, vehiculo=%s, fechaInicio=%s, fechaFin=%s, horas=%s, precioMaterial=%s)", cliente, vehiculo, fechaInicio, fechaFin, horas, precioMaterial);
    }
}
