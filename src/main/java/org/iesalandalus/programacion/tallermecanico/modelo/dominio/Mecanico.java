package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;

import java.time.LocalDate;

public class Mecanico extends Trabajo{

    private static final float FACTOR_HORA = 30f;
    private static final float FACTOR_PRECIO_MATERIAL = 1.5f;

    private float precioMaterial;

    protected Mecanico(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        super(cliente, vehiculo, fechaInicio);
    }

    protected Mecanico(Trabajo trabajo) {
        super(trabajo);
    }

    public float getPrecioMaterial() {
        return precioMaterial;
    }

    public void anadirPrecioMaterial(float precioMaterial) throws TallerMecanicoExcepcion{
        if (estaCerrado()) {
            throw new TallerMecanicoExcepcion("No se puede añadir precio del material, ya que el trabajo mecánico está cerrado.");
        }
        if (precioMaterial <= 0) {
            throw new IllegalArgumentException("El precio del material a añadir debe ser mayor que 0.");
        }
        this.precioMaterial = precioMaterial;
    }

    @Override
    public float getPrecioEspecifico() {
        return getHoras() * FACTOR_HORA + precioMaterial * FACTOR_PRECIO_MATERIAL;
    }
}
