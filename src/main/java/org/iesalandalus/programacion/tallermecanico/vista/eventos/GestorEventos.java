package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;

import java.util.*;

public class GestorEventos {
    private Map<Evento, List<ReceptorEventos>> receptores = new EnumMap<>(Evento.class);

    public GestorEventos(Evento... eventos) {
        Objects.requireNonNull(eventos, "La lista de eventos no puede ser nula.");
        for (Evento evento : eventos) {
            receptores.put(evento, new ArrayList<>());
        }
    }

    public void suscribir(ReceptorEventos receptor, Evento... eventos) {
        Objects.requireNonNull(receptor, "El receptor de eventos no puede ser nulo.");
        Objects.requireNonNull(eventos, "Te debes suscribir a algún evento.");
        for (Evento evento : eventos) {
            List<ReceptorEventos> usuarios = receptores.get(evento);
            usuarios.add(receptor);
        }
    }

    public void desuscribir(ReceptorEventos receptor, Evento... eventos) {
        Objects.requireNonNull(receptor, "El receptor de eventos no puede ser nulo");
        Objects.requireNonNull(eventos, "Te debes desuscribir de algún evento.");
        for (Evento evento : eventos) {
            List<ReceptorEventos> usuarios = receptores.get(evento);
            usuarios.remove(receptor);
        }
    }

    public void notificar(Evento evento) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(evento, "No se puede notificar un evento nulo.");
        List<ReceptorEventos> usuarios = receptores.get(evento);
        for (ReceptorEventos receptor : usuarios) {
            receptor.actualizar(evento);
        }
    }
}
