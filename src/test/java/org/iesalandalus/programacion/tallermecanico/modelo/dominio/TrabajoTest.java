package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrabajoTest {

    private static Cliente cliente;
    private static Vehiculo vehiculo;
    private static LocalDate hoy;
    private static LocalDate ayer;
    private static LocalDate manana;
    private static LocalDate semanaPasada;

    private Trabajo trabajo;

    private MockedConstruction<Cliente> controladorCreacionMockCliente;

    @BeforeAll
    static void setup() {
        hoy = LocalDate.now();
        ayer = hoy.minusDays(1);
        manana = hoy.plusDays(1);
        semanaPasada = hoy.minusDays(7);
    }

    @BeforeEach
    void init() {
        creaComportamientoCliente();
        creaComportamientoVehiculo();
        trabajo = new Trabajo(cliente, vehiculo, ayer);
    }

    @AfterEach
    void close() {
        controladorCreacionMockCliente.close();
    }

    private void creaComportamientoVehiculo() {
        vehiculo = mock();
        when(vehiculo.marca()).thenReturn("Seat");
        when(vehiculo.modelo()).thenReturn("León");
        when(vehiculo.matricula()).thenReturn("1234BCD");
    }

    private void creaComportamientoCliente() {
        cliente = mock();
        controladorCreacionMockCliente = mockConstruction(Cliente.class);
        when(cliente.getNombre()).thenReturn("Bob Esponja");
        when(cliente.getDni()).thenReturn("11223344B");
        when(cliente.getTelefono()).thenReturn("950112233");
    }

    @Test
    void constructorClienteValidoVehiculoValidoFechaInicioValidaCreaRevisionCorrectamente() {
        assertEquals(cliente, trabajo.getCliente());
        assertSame(cliente, trabajo.getCliente());
        assertEquals(vehiculo, trabajo.getVehiculo());
        assertSame(vehiculo, trabajo.getVehiculo());
        assertEquals(ayer, trabajo.getFechaInicio());
        assertNull(trabajo.getFechaFin());
        assertEquals(0, trabajo.getHoras());
        assertEquals(0, trabajo.getPrecio());
        Trabajo revisonSemanaPasada = new Trabajo(cliente, vehiculo, semanaPasada);
        assertEquals(semanaPasada, revisonSemanaPasada.getFechaInicio());
    }

    @Test
    void constructorClienteNuloVehiculoValidoFechaInicioValidaLanzaExcepcion() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> new Trabajo(null, vehiculo, hoy));
        assertEquals("El cliente no puede ser nulo.", npe.getMessage());
    }

    @Test
    void constructorClienteValidoVehiculoNuloFechaInicioValidaLanzaExcepcion() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> new Trabajo(cliente, null, hoy));
        assertEquals("El vehículo no puede ser nulo.", npe.getMessage());
    }

    @Test
    void constructorClienteValidoVehiculoValidoFechaInicioNulaLanzaExcepcion() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> new Trabajo(cliente, vehiculo, null));
        assertEquals("La fecha de inicio no puede ser nula.", npe.getMessage());
    }

    @Test
    void constructorClienteValidoVehiculoValidoFechaInicioNoValidaLanzaExcepcion() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> new Trabajo(cliente, vehiculo, manana));
        assertEquals("La fecha de inicio no puede ser futura.", iae.getMessage());
    }

    @Test
    void constructorTrabajoValidoCopiaTrabajoCorrectamente() {
        assertDoesNotThrow(() -> trabajo.anadirHoras(5));
        assertDoesNotThrow(() -> trabajo.cerrar(hoy));
        Trabajo copiaTrabajo = new Trabajo(trabajo);
        assertNotSame(cliente, copiaTrabajo.getCliente());
        assertSame(vehiculo, copiaTrabajo.getVehiculo());
        assertEquals(ayer, copiaTrabajo.getFechaInicio());
        assertEquals(hoy, copiaTrabajo.getFechaFin());
        assertEquals(5, copiaTrabajo.getHoras());
        Mecanico mecanico = new Mecanico(cliente, vehiculo, ayer);
        assertDoesNotThrow(() -> mecanico.anadirHoras(5));
        assertDoesNotThrow(() -> mecanico.anadirPrecioMaterial(100));
        assertDoesNotThrow(() -> mecanico.cerrar(hoy));
        Mecanico copiaMecanico = new Mecanico(mecanico);
        assertNotSame(cliente, copiaMecanico.getCliente());
        assertSame(vehiculo, copiaMecanico.getVehiculo());
        assertEquals(ayer, copiaMecanico.getFechaInicio());
        assertEquals(hoy, copiaMecanico.getFechaFin());
        assertEquals(5, copiaMecanico.getHoras());
        assertEquals(100, copiaMecanico.getPrecioMaterial());
    }

    @Test
    void constructorTrabajoNuloLanzaExcepcion() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> new Trabajo(null));
        assertEquals("El trabajo no puede ser nulo.", npe.getMessage());
    }

    @Test
    void copiarTrabajoValidoCopiaTrabajoCorrectamente() {
        assertDoesNotThrow(() -> trabajo.anadirHoras(5));
        assertDoesNotThrow(() -> trabajo.cerrar(hoy));
        Trabajo copiaTrabajo = (Trabajo) Trabajo.copiar(trabajo);
        assertNotSame(cliente, copiaTrabajo.getCliente());
        assertSame(vehiculo, copiaTrabajo.getVehiculo());
        assertEquals(ayer, copiaTrabajo.getFechaInicio());
        assertEquals(hoy, copiaTrabajo.getFechaFin());
        assertEquals(5, copiaTrabajo.getHoras());
        Mecanico mecanico = new Mecanico(cliente, vehiculo, ayer);
        assertDoesNotThrow(() -> mecanico.anadirHoras(5));
        assertDoesNotThrow(() -> mecanico.anadirPrecioMaterial(100));
        assertDoesNotThrow(() -> mecanico.cerrar(hoy));
        Mecanico copiaMecanico = (Mecanico) Trabajo.copiar(mecanico);
        assertNotSame(cliente, copiaMecanico.getCliente());
        assertSame(vehiculo, copiaMecanico.getVehiculo());
        assertEquals(ayer, copiaMecanico.getFechaInicio());
        assertEquals(hoy, copiaMecanico.getFechaFin());
        assertEquals(5, copiaMecanico.getHoras());
        assertEquals(100, copiaMecanico.getPrecioMaterial());
    }

    @Test
    void copiarTrabajoNuloLanzaExcepcion() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> Trabajo.copiar(null));
        assertEquals("El trabajo no puede ser nulo.", npe.getMessage());
    }

    @Test
    void getTrabajoVehiculoValidoDevuelveTrabajoConDichoVehiculo() {
        Trabajo trabajo = Trabajo.get(this.trabajo.getVehiculo());
        assertEquals(this.trabajo.getVehiculo(), trabajo.getVehiculo());
    }

    @Test
    void getTrabajoVehiculoNuloLanzaExcepcion() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> Trabajo.get(null));
        assertEquals("El vehículo no puede ser nulo.", npe.getMessage());
    }

    @Test
    void anadirHorasHorasValidasSumaHorasCorrectamente() {
        assertDoesNotThrow(() -> trabajo.anadirHoras(5));
        assertEquals(5, trabajo.getHoras());
        assertDoesNotThrow(() -> trabajo.anadirHoras(5));
        assertEquals(10, trabajo.getHoras());
    }

    @Test
    void anadirHorasHorasNoValidasLanzaExcepcion() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> trabajo.anadirHoras(0));
        assertEquals("Las horas a añadir deben ser mayores que cero.", iae.getMessage());
    }

    @Test
    void anadirHorasTrabajoCerradoLanzaExcepcion() {
        assertDoesNotThrow(() -> trabajo.cerrar(hoy));
        TallerMecanicoExcepcion tme = assertThrows(TallerMecanicoExcepcion.class, () -> trabajo.anadirHoras(5));
        assertEquals("No se puede añadir horas, ya que el trabajo está cerrado.", tme.getMessage());
    }

    @Test
    void cerrarFechaFinValidaCierraCorrectamente() {
        assertFalse(trabajo.estaCerrado());
        assertNull(trabajo.getFechaFin());
        assertDoesNotThrow(() -> trabajo.cerrar(hoy));
        assertTrue(trabajo.estaCerrado());
        assertEquals(hoy, trabajo.getFechaFin());
    }

    @Test
    void cerrarFechaFinNulaLanzaExcepcion() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> trabajo.cerrar(null));
        assertEquals("La fecha de fin no puede ser nula.", npe.getMessage());
    }

    @Test
    void cerrarFechaFinFuturaLanzaExcepcion() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> trabajo.cerrar(manana));
        assertEquals("La fecha de fin no puede ser futura.", iae.getMessage());
    }

    @Test
    void cerrarFechaFinAnteriorFechaInicioLanzaExcepcion() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> trabajo.cerrar(semanaPasada));
        assertEquals("La fecha de fin no puede ser anterior a la fecha de inicio.", iae.getMessage());
    }

    @Test
    void cerrarTrabajonCerradoLanzaExcepcion() {
        assertDoesNotThrow(() -> trabajo.cerrar(hoy));
        TallerMecanicoExcepcion tme = assertThrows(TallerMecanicoExcepcion.class, () -> trabajo.cerrar(hoy));
        assertEquals("El trabajo ya está cerrado.", tme.getMessage());
    }

    @Test
    void equalsHashCodeSeBasanSoloEnClienteVehiculoFechaInicio() {
        Trabajo otraTrabajo = new Trabajo(cliente, vehiculo, ayer);
        assertEquals(trabajo, otraTrabajo);
        assertEquals(trabajo.hashCode(), otraTrabajo.hashCode());
        assertDoesNotThrow(() -> otraTrabajo.cerrar(hoy));
        assertEquals(trabajo, otraTrabajo);
        assertEquals(trabajo.hashCode(), otraTrabajo.hashCode());
    }

}