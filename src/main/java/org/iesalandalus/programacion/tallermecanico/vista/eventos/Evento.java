package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.HashMap;
import java.util.Map;

public enum Evento {
    INSERTAR_CLIENTE (11, "Insertar cliente."),
    BUSCAR_CLIENTE (12, "Buscar cliente."),
    BORRAR_CLIENTE (13, "Borrar cliente."),
    LISTAR_CLIENTE (14, "Listar cliente."),
    MODIFICAR_CLIENTE (15,"Modificar cliente."),
    INSERTAR_VEHICULO (21,"Insertar vehiculo."),
    BUSCAR_VEHICULO (22, "Buscar vehiculo."),
    BORRAR_VEHICULO (23,"Borrar vehiculo."),
    LISTAR_VEHICULO (24,"Listar vehiculo."),
    INSERTAR_REVISION (31,"Insertar revisión."),
    INSERTAR_MECANICO (32,"Insertar trabajo mecánico."),
    BUSCAR_TRABAJO (33,"Buscar revisión."),
    BORRAR_TRABAJO (34,"Borrar revisión."),
    LISTAR_TRABAJO (35, "Listar revisión."),
    LISTAR_TRABAJO_CLIENTE (36, "Listar revisión cliente."),
    LISTAR_TRABAJO_VEHICULO (37,"Listar revisión vehiculo."),
    ANADIR_HORAS_TRABAJO (38, "Añadir horas a la revisión."),
    ANADIR_PRECIO_MATERIAL_TRABAJO (39,"Añadir precio material de revisión."),
    CERRAR_TRABAJO (40,"Cerrar revisión."),
    SALIR (1,"Salir.");

    private int numeroOpcion;
    private String mensaje;
    static Map<Integer, Evento> eventos = new HashMap<>();

    static {
        for (Evento evento : values()) {
            eventos.put(evento.numeroOpcion, evento);
        }
    }

    Evento(int numeroOpcion, String mensaje){
        this.numeroOpcion = numeroOpcion;
        this.mensaje = mensaje;
    }

    public static boolean esValida(int numeroOpcion){
        return eventos.containsKey(numeroOpcion);
    }

    public static Evento get(int numeroOpcion){
        if (!esValida(numeroOpcion)){
            throw new IllegalArgumentException("La opción no es válida.");
        }

        return eventos.get(numeroOpcion);
    }

    @Override
    public String toString() {
        return String.format("%d.- %s", numeroOpcion, mensaje);
    }
}
