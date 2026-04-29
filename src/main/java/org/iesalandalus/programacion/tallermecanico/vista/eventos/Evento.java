package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.HashMap;
import java.util.Map;

public enum Evento {
    INSERTAR_CLIENTE(11, "Insertar cliente."),
    BUSCAR_CLIENTE(12, "Buscar cliente."),
    BORRAR_CLIENTE(13, "Borrar cliente."),
    LISTAR_CLIENTES(14, "Listar clientes."),
    MODIFICAR_CLIENTE(15, "Modificar cliente."),
    INSERTAR_VEHICULO(21, "Insertar vehículo."),
    BUSCAR_VEHICULO(22, "Buscar vehículo."),
    BORRAR_VEHICULO(23, "Borrar vehículo."),
    LISTAR_VEHICULOS(24, "Listar vehículos."),
    INSERTAR_REVISION(31, "Insertar revisión."),
    INSERTAR_MECANICO(31, "Insertar mecánico."),
    BUSCAR_TRABAJO(32, "Buscar trabajo."),
    BORRAR_TRABAJO(33, "Borrar trabajo."),
    LISTAR_TRABAJOS(34, "Listar trabajos."),
    LISTAR_TRABAJOS_CLIENTE(35, "Listar trabajos de un cliente."),
    LISTAR_TRABAJOS_VEHICULO(36, "Listar trabajos de un vehículo."),
    ANADIR_HORAS_TRABAJO(37, "Añadir horas a un trabajo."),
    ANADIR_PRECIO_MATERIAL_TRABAJO(38, "Añadir precio del material a un trabajo."),
    CERRAR_TRABAJO(39, "Cerrar trabajo."),
    SALIR(0, "Salir.");

    private int codigo;
    private String texto;
    private static Map<Integer, Evento> eventos = new HashMap<>();

    static {
        for (Evento e : values()) {
            eventos.put(e.codigo, e);
        }
    }

    Evento(int codigo, String texto) {
        this.codigo = codigo;
        this.texto = texto;
    }

    public static boolean esValido(int codigo) {
        return eventos.containsKey(codigo);
    }

    public static Evento get(int codigo) {
        if (!esValido(codigo)) {
            throw new IllegalArgumentException("El número de la opción no es correcto.");
        }
        return eventos.get(codigo);
    }

    @Override
    public String toString() {
        return texto;
    }
}
