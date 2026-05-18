package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Clientes implements IClientes {

    public static final String FICHERO_CLIENTES = String.format("%s%s%s", "datos", File.separator, "clientes.xml");
    public static final String RAIZ = "clientes";
    public static final String CLIENTE = "cliente";
    public static final String NOMBRE = "nombre";
    public static final String DNI = "dni";
    public static final String TELEFONO = "telefono";

    private final List<Cliente> coleccionClientes;
    private static Clientes instancia;

    public Clientes() {
        coleccionClientes = new ArrayList<>();
    }

    static Clientes getInstancia() {
        if (instancia == null) {
            instancia = new Clientes();
        }
        return instancia;
    }

    @Override
    public void comenzar() {
        Document documentoXml = UtilidadesXml.leerDocumentoXml(FICHERO_CLIENTES);
        if (documentoXml != null) {
            procesarDocumentoXml(documentoXml);
            System.out.printf("Fichero %s leído correctamente.%n", FICHERO_CLIENTES);
        }
    }

    private void procesarDocumentoXml(Document documentoXml) {
        NodeList clientes = documentoXml.getElementsByTagName(CLIENTE);
        for (int i = 0; i < clientes.getLength(); i++) {
            Node cliente = clientes.item(i);
            try {
                if (cliente.getNodeType() == Node.ELEMENT_NODE) {
                    insertar(getCliente((Element) cliente));
                }
            } catch (TallerMecanicoExcepcion | IllegalArgumentException | NullPointerException e) {
                System.out.printf("Error al leer el cliente %d. --> %s%n", i, e.getMessage());
            }
        }
    }

    private Cliente getCliente(Element elemento) {
        String nombre = elemento.getAttribute(NOMBRE);
        String dni = elemento.getAttribute(DNI);
        String telefono = elemento.getAttribute(TELEFONO);
        return new Cliente(nombre, dni, telefono);
    }

    @Override
    public void terminar() {
        Document documentoXml = crearDocumentoXml();
        UtilidadesXml.escribirDocumentoXml(documentoXml, FICHERO_CLIENTES);
    }

    private Document crearDocumentoXml() {
        DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
        Document documentoXml = null;
        if (constructor != null) {
            documentoXml = constructor.newDocument();
            documentoXml = appendChild(documentoXml.createElement(RAIZ));
            for (Cliente cliente : coleccionClientes) {
                Element elemento = getElemento(documentoXml, cliente);
                documentoXml.getDocumentElement().appendChild(elemento);
            }
        }
        return documentoXml;
    }

    private Element getElemento(Document documentoXml, Cliente cliente) {
        Element elemento = documentoXml.createElement(CLIENTE);
        elemento.setAttribute(NOMBRE, cliente.getNombre());
        elemento.getAttribute(DNI, cliente.getDni());
        elemento.getAttribute(TELEFONO, cliente.getTelefono());
        return elemento;
    }

    @Override
    public List<Cliente> get() {
        return coleccionClientes;
    }

    @Override
    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "No se puede insertar un cliente nulo.");
        if (coleccionClientes.contains(cliente)) {
            throw new TallerMecanicoExcepcion("Ya existe un cliente con ese DNI.");
        }

        coleccionClientes.add(cliente);
    }

    @Override
    public Cliente modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "No se puede modificar un cliente nulo.");

        Cliente buscado = buscar(cliente);

        if (!coleccionClientes.contains(buscado)) {
            throw new TallerMecanicoExcepcion("No existe ningún cliente con ese DNI.");
        }

        if (nombre != null && !nombre.isEmpty()) {
            buscado.setNombre(nombre);
        }

        if (telefono != null && !telefono.isEmpty()) {
            buscado.setTelefono(telefono);
        }

        return buscado;
    }

    @Override
    public Cliente buscar(Cliente cliente) {
        Objects.requireNonNull(cliente, "No se puede buscar un cliente nulo.");
        int indice = coleccionClientes.indexOf(cliente);
        return (indice != -1 ? coleccionClientes.get(indice) : null);
    }

    @Override
    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "No se puede borrar un cliente nulo.");
        Cliente buscado = buscar(cliente);

        if (!coleccionClientes.contains(buscado)) {
            throw new TallerMecanicoExcepcion("No existe ningún cliente con ese DNI.");
        }

        coleccionClientes.remove(buscado);
    }
}
