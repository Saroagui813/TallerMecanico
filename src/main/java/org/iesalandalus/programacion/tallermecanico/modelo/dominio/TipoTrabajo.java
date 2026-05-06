package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

public enum TipoTrabajo {
    MECANICO ("Mecánico"),
    REVISION ("Revisión");

    private String nombre;

    TipoTrabajo(String nombre) {
        this.nombre = nombre;
    }

    public static TipoTrabajo get(Trabajo trabajo) {
        TipoTrabajo tipo;
        if (trabajo instanceof Revision) {
            tipo = REVISION;
        } else {
            tipo = MECANICO;
        }
        return tipo;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
