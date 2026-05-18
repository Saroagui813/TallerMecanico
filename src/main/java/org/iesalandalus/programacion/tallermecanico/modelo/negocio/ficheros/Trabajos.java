package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.text.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Trabajos implements ITrabajos {

    public static final String FICHERO_TRABAJOS = String.format("%s%s%s", "datos", File.separator, "trabajos.xml");
    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final String RAIZ = "trabajos";
    public static final String TRABAJO = "trabajo";
    public static final String CLIENTE = "cliente";
    public static final String VEHICULO = "vehiculo";
    public static final String FECHA_INICIO = "fechaInicio";
    public static final String FECHA_FIN = "fechaFin";
    public static final String HORAS = "horas";
    public static final String PRECIO_MATERIAL = "precioMaterial";
    public static final String TIPO = "tipo";
    public static final String REVISION = "revision";
    public static final String MECANICO = "mecanico";

    private final List<Trabajo> coleccionTrabajos;
    private static Trabajos instancia;

    public Trabajos() {
        coleccionTrabajos = new ArrayList<>();
    }

    static Trabajos getInstancia() {
        if (instancia == null) {
            instancia = new Trabajos();
        }
        return instancia;
    }

    @Override
    public void comenzar() {
        Document documentoXml = UtilidadesXml.leerDocumentoXml(FICHERO_TRABAJOS);
        if (documentoXml != null) {
            procesarDocumentoXml(documentoXml);
            System.out.printf("Fichero %s leído correctamente.%n", FICHERO_TRABAJOS);
        }
    }

    private void procesarDocumentoXml(Document documentoXml) {
        NodeList alquileres = documentoXml.getElementsByTagName(TRABAJO);
        for (int i = 0; i < alquileres.getLength(); i++) {
            Node trabajo = alquileres.item(i);
            try {
                if (trabajo.getNodeType() == Node.ELEMENT_NODE) {
                    insertar(getTrabajo((Element) trabajo));
                }
            } catch (TallerMecanicoExcepcion | IllegalArgumentException | NullPointerException e) {
                System.out.printf("Error al leer el trabajo %d. --> %s%n", i, e.getMessage());
            }
        }
    }

    private Trabajo getTrabajo(Element elemento) throws TallerMecanicoExcepcion {
        Cliente cliente = Cliente.get(elemento.getAttribute(CLIENTE));
        cliente = Clientes.getInstancia().buscar(cliente);
        Vehiculo vehiculo = Vehiculo.get(elemento.getAttribute(VEHICULO));
        vehiculo = Vehiculos.getInstancia().buscar(vehiculo);
        LocalDate fechaInicio = LocalDate.parse(elemento.getAttribute(FECHA_INICIO), FORMATO_FECHA);
        String tipo = elemento.getAttribute(TIPO);
        Trabajo trabajo = null;
        if (tipo.equals(REVISION)) {
            trabajo = new Revision(cliente, vehiculo, fechaInicio);
        } else if (tipo.equals(MECANICO)) {
            trabajo = new Mecanico(cliente, vehiculo, fechaInicio);
            if (elemento.hasAttr)
        }
    }

    @Override
    public void terminar() {
        System.out.println("Fichero trabajos terminado.");
    }

    @Override
    public List<Trabajo> get() {
        return new ArrayList<>(coleccionTrabajos);
    }

    @Override
    public List<Trabajo> get(Cliente cliente) {
        List<Trabajo> coleccionResultante;
        coleccionResultante = new ArrayList<>();

        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getCliente().equals(cliente)) {
                coleccionResultante.add(trabajo);
            }
        }

        return coleccionResultante;
    }
    @Override
    public List<Trabajo> get(Vehiculo vehiculo) {

        List<Trabajo> coleccionResultante;
        coleccionResultante = new ArrayList<>();

        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getVehiculo().equals(vehiculo)) {
                coleccionResultante.add(trabajo);
            }
        }
        return coleccionResultante;
    }

    @Override
    public Map<TipoTrabajo, Integer> getEstadisticasMensuales(LocalDate mes) {
        Objects.requireNonNull(mes, "El mes no puede ser nulo.");
        Map<TipoTrabajo, Integer> estadisticasMensuales = inicializarEstadisticas();
        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getFechaInicio().getMonthValue() == mes.getMonthValue() && trabajo.getFechaInicio().getYear() == mes.getYear()) {
                TipoTrabajo tipo = TipoTrabajo.get(trabajo);
                estadisticasMensuales.put(tipo, estadisticasMensuales.get(tipo) + 1);
            }
        }
        return estadisticasMensuales;
    }

    private Map<TipoTrabajo, Integer> inicializarEstadisticas() {
        Map <TipoTrabajo, Integer> estadisticas = new HashMap<>();
        for (TipoTrabajo tipo : TipoTrabajo.values()) {
            estadisticas.put(tipo, 0);
        }
        return estadisticas;
    }

    @Override
    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede insertar un trabajo nulo.");
        comprobarTrabajo(trabajo.getCliente(), trabajo.getVehiculo(), trabajo.getFechaInicio());
        coleccionTrabajos.add(trabajo);
    }

    private void comprobarTrabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaRevision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");

        for (Trabajo trabajo : coleccionTrabajos) {
            if (!trabajo.estaCerrado()) {
                if (trabajo.getCliente().equals(cliente)) {
                    throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo en curso.");
                }
                if (trabajo.getVehiculo().equals(vehiculo)) {
                    throw new TallerMecanicoExcepcion("El vehículo está actualmente en el taller.");
                }
            }

            if (trabajo.estaCerrado()) {
                if (!trabajo.getFechaFin().isBefore(fechaRevision)) {
                    if (trabajo.getCliente().equals(cliente)) {
                        throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo posterior.");
                    }
                    if (trabajo.getVehiculo().equals(vehiculo)) {
                        throw new TallerMecanicoExcepcion("El vehículo tiene otro trabajo posterior.");
                    }
                }
            }
        }
    }

    @Override
    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No puedo añadir horas a un trabajo nulo.");
        Trabajo trabajo1 = getTrabajoAbierto(trabajo.getVehiculo());
        trabajo1.anadirHoras(horas);
        return trabajo1;
    }

    private Trabajo getTrabajoAbierto(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Trabajo trabajoResultado = null;
        Objects.requireNonNull(vehiculo, "No puedo operar sobre una revisión nula.");
        for (Trabajo trabajo1 : coleccionTrabajos) {
            if (trabajo1.getVehiculo().equals(vehiculo) && !trabajo1.estaCerrado()) {
                trabajoResultado = trabajo1;
            }
        }

        if (trabajoResultado == null) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }

        return trabajoResultado;
    }

    @Override
    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No puedo añadir precio del material a un trabajo nulo.");
        Trabajo trabajo1 = getTrabajoAbierto(trabajo.getVehiculo());
        if (trabajo1 instanceof Mecanico mecanico) {
            mecanico.anadirPrecioMaterial(precioMaterial);
        } else {
            throw new TallerMecanicoExcepcion("No se puede añadir precio al material para este tipo de trabajos.");
        }
        return trabajo1;
    }

    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No puedo cerrar un trabajo nulo.");
        Trabajo trabajo1 = getTrabajoAbierto(trabajo.getVehiculo());
        trabajo1.cerrar(fechaFin);
        return trabajo1;
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "No se puede buscar un trabajo nulo.");
        int indice = coleccionTrabajos.indexOf(trabajo);
        return (indice != -1) ? coleccionTrabajos.get(indice) : null;
    }

    @Override
    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede borrar un trabajo nulo.");
        Trabajo buscando = buscar(trabajo);

        if (!coleccionTrabajos.contains(buscando)) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo igual.");
        }

        coleccionTrabajos.remove(buscando);
    }
}