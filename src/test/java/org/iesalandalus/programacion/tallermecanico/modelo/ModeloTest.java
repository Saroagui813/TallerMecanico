package org.iesalandalus.programacion.tallermecanico.modelo;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria.Clientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria.Trabajos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria.Vehiculos;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ModeloTest {

    @Mock
    private static IClientes clientes;
    @Mock
    private static IVehiculos vehiculos;
    @Mock
    private static ITrabajos trabajos;
    @InjectMocks
    private Modelo modelo = FabricaModelo.CASCADA.crear(FabricaFuenteDatos.MEMORIA);

    private static Cliente cliente;
    private static Vehiculo vehiculo;
    private static Trabajo trabajo;
    private static Mecanico mecanico;

    private AutoCloseable procesadorAnotaciones;
    private MockedConstruction<Cliente> controladorCreacionMockCliente;
    private MockedConstruction<IClientes> controladorCreacionMockClientes;
    private MockedConstruction<IVehiculos> controladorCreacionMockVehiculos;
    private MockedConstruction<Trabajo> controladorCreacionMockRevision;
    private MockedConstruction<Mecanico> controladorCreacionMockMecanico;
    private MockedConstruction<ITrabajos> controladorCreacionMockTrabajos;


    @BeforeAll
    static void setup() {
        cliente = mock();
        when(cliente.getNombre()).thenReturn("Bob Esponja");
        when(cliente.getDni()).thenReturn("11223344B");
        when(cliente.getTelefono()).thenReturn("950112233");
        vehiculo = mock();
        when(vehiculo.marca()).thenReturn("Seat");
        when(vehiculo.modelo()).thenReturn("León");
        when(vehiculo.matricula()).thenReturn("1234BCD");
        trabajo = mock();
        when(trabajo.getCliente()).thenReturn(cliente);
        when(trabajo.getVehiculo()).thenReturn(vehiculo);
        when(trabajo.getFechaInicio()).thenReturn(LocalDate.now().minusDays(1));
        mecanico = mock();
        when(mecanico.getCliente()).thenReturn(cliente);
        when(mecanico.getVehiculo()).thenReturn(vehiculo);
        when(mecanico.getFechaInicio()).thenReturn(LocalDate.now().minusDays(1));
    }

    @BeforeEach
    void init() {
        controladorCreacionMockCliente = mockConstruction(Cliente.class);
        controladorCreacionMockClientes = mockConstruction(Clientes.class);
        controladorCreacionMockVehiculos = mockConstruction(Vehiculos.class);
        controladorCreacionMockRevision = mockConstruction(Trabajo.class);
        controladorCreacionMockMecanico = mockConstruction(Mecanico.class);
        controladorCreacionMockTrabajos = mockConstruction(Trabajos.class);
        procesadorAnotaciones = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void close() throws Exception {
        procesadorAnotaciones.close();
        controladorCreacionMockCliente.close();
        controladorCreacionMockClientes.close();
        controladorCreacionMockVehiculos.close();
        controladorCreacionMockRevision.close();
        controladorCreacionMockMecanico.close();
        controladorCreacionMockTrabajos.close();
    }

    @Test
    void comenzarNoHaceNada() {
        assertDoesNotThrow(() -> modelo.comenzar());
    }

    @Test
    void terminarNoHaceNada() {
        assertDoesNotThrow(() -> modelo.terminar());
    }

    @Test
    void insertarClienteLlamaClientesInsertar() {
        assertDoesNotThrow(() -> modelo.insertar(cliente));
        assertDoesNotThrow(() -> verify(clientes).insertar(any(Cliente.class)));
        assertDoesNotThrow(() -> verify(clientes, times(0)).insertar(cliente));
    }

    @Test
    void insertarVehiculoLlamaVehiculosInsertar() {
        assertDoesNotThrow(() -> modelo.insertar(vehiculo));
        assertDoesNotThrow(() -> verify(vehiculos).insertar(vehiculo));
    }

    @Test
    void insertarTrabajoRevisionLlamaClientesBuscarVehiculosBuscarTrabajosInsertar() {
        InOrder orden = inOrder(clientes, vehiculos, trabajos);
        when(clientes.buscar(cliente)).thenReturn(cliente);
        when(vehiculos.buscar(vehiculo)).thenReturn(vehiculo);
        assertDoesNotThrow(() -> modelo.insertar(trabajo));
        orden.verify(clientes).buscar(cliente);
        orden.verify(vehiculos).buscar(vehiculo);
        assertDoesNotThrow(() -> orden.verify(trabajos).insertar(any(Trabajo.class)));
        assertDoesNotThrow(() -> verify(trabajos, times(0)).insertar(trabajo));
    }

    @Test
    void insertarTrabajoMecanicoLlamaClientesBuscarVehiculosBuscarTrabajosInsertar() {
        InOrder orden = inOrder(clientes, vehiculos, trabajos);
        when(clientes.buscar(cliente)).thenReturn(cliente);
        when(vehiculos.buscar(vehiculo)).thenReturn(vehiculo);
        assertDoesNotThrow(() -> modelo.insertar(mecanico));
        orden.verify(clientes).buscar(cliente);
        orden.verify(vehiculos).buscar(vehiculo);
        assertDoesNotThrow(() -> orden.verify(trabajos).insertar(any(Trabajo.class)));
        assertDoesNotThrow(() -> verify(trabajos, times(0)).insertar(mecanico));
    }

    @Test
    void buscarClienteLlamaClientesBuscar() {
        assertDoesNotThrow(() -> modelo.insertar(cliente));
        when(clientes.buscar(cliente)).thenReturn(cliente);
        Cliente clienteEncontrado = modelo.buscar(cliente);
        verify(clientes).buscar(cliente);
        assertNotSame(cliente, clienteEncontrado);
    }

    @Test
    void buscarVehiculoLlamaVehiculosBuscar() {
        assertDoesNotThrow(() -> modelo.insertar(vehiculo));
        when(vehiculos.buscar(vehiculo)).thenReturn(vehiculo);
        modelo.buscar(vehiculo);
        verify(vehiculos).buscar(vehiculo);
    }

    @Test
    void buscarTrabajoLlamaTrabajosBuscar() {
        assertDoesNotThrow(() -> modelo.insertar(trabajo));
        when(trabajos.buscar(trabajo)).thenReturn(trabajo);
        Trabajo trabajoEncontrada = modelo.buscar(trabajo);
        verify(trabajos).buscar(trabajo);
        assertNotSame(trabajo, trabajoEncontrada);
    }

    @Test
    void modificarClienteLlamaClientesModificar() {
        assertDoesNotThrow(() -> modelo.modificar(cliente, "Patricio Estrella", "950123456"));
        assertDoesNotThrow(() -> verify(clientes).modificar(cliente, "Patricio Estrella", "950123456"));
    }

    @Test
    void anadirHorasLlamaTrabajosAnadirHoras() {
        assertDoesNotThrow(() -> modelo.anadirHoras(trabajo, 10));
        assertDoesNotThrow(() -> verify(trabajos).anadirHoras(trabajo, 10));
    }

    @Test
    void anadirPrecioMateriaLlamaTrabajosAnadirPrecioMaterial() {
        assertDoesNotThrow(() -> modelo.anadirPrecioMaterial(trabajo, 100f));
        assertDoesNotThrow(() -> verify(trabajos).anadirPrecioMaterial(trabajo, 100f));
    }

    @Test
    void cerrarLlamaTrabajosCerrar() {
        assertDoesNotThrow(() -> modelo.cerrar(trabajo, LocalDate.now()));
        assertDoesNotThrow(() -> verify(trabajos).cerrar(trabajo, LocalDate.now()));
    }

    @Test
    void borrarClienteLlamaTrabajosGetClienteTrabajosBorrarClientesBorrar() {
        simularClientesConTrabajos();
        InOrder orden = inOrder(clientes, trabajos);
        assertDoesNotThrow(() -> modelo.borrar(cliente));
        orden.verify(trabajos).get(cliente);
        for (Trabajo trabajo : trabajos.get(cliente)) {
            assertDoesNotThrow(() -> orden.verify(trabajos).borrar(trabajo));
        }
        assertDoesNotThrow(() -> orden.verify(clientes).borrar(cliente));
    }

    private void simularClientesConTrabajos() {
        when(trabajos.get(cliente)).thenReturn(new ArrayList<>(List.of(mock(), mock())));
    }

    @Test
    void borrarVehiculoLlamaTrabajosGetVehiculoTrabajosBorrarVehiculosBorrar() {
        simularVehiculosConTrabajos();
        InOrder orden = inOrder(vehiculos, trabajos);
        assertDoesNotThrow(() -> modelo.borrar(vehiculo));
        orden.verify(trabajos).get(vehiculo);
        for (Trabajo trabajo : trabajos.get(vehiculo)) {
            assertDoesNotThrow(() -> orden.verify(trabajos).borrar(trabajo));
        }
        assertDoesNotThrow(() -> orden.verify(vehiculos).borrar(vehiculo));
    }

    private void simularVehiculosConTrabajos() {
        when(trabajos.get(vehiculo)).thenReturn(new ArrayList<>(List.of(mock(), mock())));
    }

    @Test
    void borrarTrabajoLlamaTrabajosBorrar() {
        assertDoesNotThrow(() -> modelo.borrar(trabajo));
        assertDoesNotThrow(() -> verify(trabajos).borrar(trabajo));
    }

    @Test
    void getClientesLlamaClientesGet() {
        when(clientes.get()).thenReturn(new ArrayList<>(List.of(cliente)));
        List<Cliente> clientesExistentes = modelo.getClientes();
        verify(clientes).get();
        assertNotSame(cliente, clientesExistentes.get(0));
    }

    @Test
    void getVehiculosLlamaVehiculosGet() {
        when(vehiculos.get()).thenReturn(new ArrayList<>(List.of(vehiculo)));
        List<Vehiculo> vehiculosExistentes = modelo.getVehiculos();
        verify(vehiculos).get();
        assertSame(vehiculo, vehiculosExistentes.get(0));
    }

    @Test
    void getTrabajosLlamaTrabajosGet() {
        when(trabajos.get()).thenReturn(new ArrayList<>(List.of(trabajo)));
        List<Trabajo> trabajosExistentes = modelo.getTrabajos();
        verify(trabajos).get();
        assertNotSame(trabajo, trabajosExistentes.get(0));
    }

    @Test
    void getTrabajosClienteLlamaTrabajosGetCliente() {
        when(trabajos.get(cliente)).thenReturn(new ArrayList<>(List.of(trabajo)));
        List<Trabajo> trabajosCliente = modelo.getTrabajos(cliente);
        verify(trabajos).get(cliente);
        assertNotSame(trabajo,trabajosCliente.get(0));
    }

    @Test
    void getTrabajosVehiculoLlamaTrabajosGetVehiculo() {
        when(trabajos.get(vehiculo)).thenReturn(new ArrayList<>(List.of(trabajo)));
        List<Trabajo> trabajosVehiculo = modelo.getTrabajos(vehiculo);
        verify(trabajos).get(vehiculo);
        assertNotSame(trabajo,trabajosVehiculo.get(0));
    }

}