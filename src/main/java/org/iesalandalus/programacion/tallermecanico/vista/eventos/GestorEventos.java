package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.*;

public class GestorEventos {
    private Map<Evento, List<ReceptorEventos>> receptores = new EnumMap<>(Evento.class);

    public GestorEventos(Evento... eventos){
        for (Evento evento : eventos) {
            receptores.put(evento, new ArrayList<>());
        }
    }

    public void suscribir(ReceptorEventos receptor,Evento... eventos){
        Objects.requireNonNull(receptor,"El receptor no puede ser nulo.");
        Objects.requireNonNull(eventos,"Los eventos no pueden ser nulos.");
        for (Evento evento : eventos) {
            receptores.get(evento).add(receptor);
        }
    }

    public void desuscribir(ReceptorEventos receptor,Evento... eventos){
        Objects.requireNonNull(receptor,"El receptor no puede ser nulo.");
        Objects.requireNonNull(eventos,"Los eventos no pueden ser nulos.");
        for (Evento evento : eventos) {
            receptores.get(evento).remove(receptor);
        }
    }

    public void notificar(Evento evento){
        for (ReceptorEventos receptor : receptores.get(evento)) {
            receptor.actualizar(evento);
        }
    }
}


