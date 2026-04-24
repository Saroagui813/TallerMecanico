package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public abstract class Trabajo {
    static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final float FACTOR_DIA = 35f;

    private Cliente cliente;
    private Vehiculo vehiculo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int horas;

    protected Trabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio){
        setCliente(cliente);
        setVehiculo(vehiculo);
        setFechaInicio(fechaInicio);
        fechaFin = null;
        horas = 0;
    }

    protected Trabajo(Trabajo trabajo){
        Objects.requireNonNull(trabajo, "La trabajo no puede ser nulo.");
        fechaInicio = trabajo.fechaInicio;
        horas = trabajo.horas;
        cliente = new Cliente(trabajo.cliente);
        vehiculo = trabajo.vehiculo;
        fechaFin = trabajo.fechaFin;
    }

    public static Trabajo copiar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "El trabajo no puede ser nulo.");
        if (trabajo instanceof Revision) {
            return (Revision) trabajo;
        }
        if (trabajo instanceof Mecanico) {
            return (Mecanico) trabajo;
        }
        return trabajo;
    }

    public static Trabajo get(Vehiculo vehiculo) {
        Cliente cliente1 = new Cliente("Samuel", "77652349D", "722111111");
        LocalDate fechaInicio = LocalDate.now();
        Trabajo trabajo = new Trabajo(cliente1, vehiculo, fechaInicio);
        return trabajo;
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

    private void setVehiculo(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
        this.vehiculo = vehiculo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    private void setFechaInicio(LocalDate fechaInicio) {
        Objects.requireNonNull(fechaInicio, "La fecha de inicio no puede ser nula.");
        if (fechaInicio.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de inicion no puede ser futura");
        }
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    private void setFechaFin(LocalDate fechaFin) {
        Objects.requireNonNull(fechaFin, "La fecha de fin no puede ser nula.");
        if (fechaFin.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser futura.");
        }
        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
        this.fechaFin = fechaFin;
    }

    public int getHoras() {
        return horas;
    }

    public void anadirHoras(int horas) throws TallerMecanicoExcepcion{
        if (horas <= 0) {
            throw new IllegalArgumentException("Las horas a añadir deben ser mayores que cero.");
        }
        if (estaCerrado()) {
            throw new TallerMecanicoExcepcion("No se puede añadir horas, ya que la revisión está cerrada.");
        }
        this.horas = horas + getHoras();
    }

    public boolean estaCerrado() {
        return fechaFin != null;
    }

    public void cerrar(LocalDate fechaFin) throws TallerMecanicoExcepcion {
        if (estaCerrado()) {
            throw new TallerMecanicoExcepcion("La revisión ya está cerrada.");
        }
        setFechaFin(fechaFin);
    }

    public float getPrecio() {
        return getPrecioEspecifico() + getPrecioFijo();
    }

    private float getPrecioFijo() {
        return getDias() * 10;
    }

    private float getDias() {
        float dias;
        if (fechaFin == null) {
            dias = 0;
        } else {
            dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        }
        return dias;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Trabajo trabajo = (Trabajo) o;
        return Float.compare(FACTOR_DIA, trabajo.FACTOR_DIA) == 0 && horas == trabajo.horas && Objects.equals(cliente, trabajo.cliente) && Objects.equals(vehiculo, trabajo.vehiculo) && Objects.equals(fechaInicio, trabajo.fechaInicio) && Objects.equals(fechaFin, trabajo.fechaFin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(FACTOR_DIA, cliente, vehiculo, fechaInicio, fechaFin, horas);
    }

    public abstract float getPrecioEspecifico();
}
