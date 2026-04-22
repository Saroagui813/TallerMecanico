package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria.Trabajos;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TrabajosTest {

    private static Trabajo trabajo;
    private static Mecanico mecanico;
    private static Trabajo trabajo3;
    private static Cliente cliente1;
    private static Cliente cliente2;
    private static Vehiculo vehiculo1;
    private static Vehiculo vehiculo2;
    private static LocalDate hoy;
    private static LocalDate ayer;
    private static LocalDate anteayer;
    private static LocalDate semanaPasada;
    private ITrabajos trabajos;

    @BeforeAll
    static void setup() {
        hoy = LocalDate.now();
        ayer = hoy.minusDays(1);
        anteayer = hoy.minusDays(2);
        semanaPasada = hoy.minusDays(7);
        cliente1 = mock();
        when(cliente1.getDni()).thenReturn("11223344B");
        cliente2 = mock();
        when(cliente2.getDni()).thenReturn("11111111H");
        vehiculo1 = mock();
        when(vehiculo1.matricula()).thenReturn("1234BCD");
        vehiculo2 = mock();
        when(vehiculo2.matricula()).thenReturn("1111BBB");
    }

    @BeforeEach
    void init() {
        trabajos = new Trabajos();
        trabajo = mock();
        when(trabajo.getCliente()).thenReturn(cliente1);
        when(trabajo.getVehiculo()).thenReturn(vehiculo1);
        when(trabajo.getFechaInicio()).thenReturn(semanaPasada);
        mecanico = mock();
        when(mecanico.getCliente()).thenReturn(cliente1);
        when(mecanico.getVehiculo()).thenReturn(vehiculo2);
        when(mecanico.getFechaInicio()).thenReturn(ayer);
        trabajo3 = mock();
        when(trabajo3.getCliente()).thenReturn(cliente2);
        when(trabajo3.getVehiculo()).thenReturn(vehiculo1);
        when(trabajo3.getFechaInicio()).thenReturn(ayer);
    }

    @Test
    void constructorCreaTrabajosCorrectamente() {
        assertNotNull(trabajos);
        assertEquals(0, trabajos.get().size());
    }

    @Test
    void getDevuelveTrabajosCorrectamente() {
        assertDoesNotThrow(() -> trabajos.insertar(trabajo));
        when(trabajo.getFechaFin()).thenReturn(anteayer);
        when(trabajo.estaCerrado()).thenReturn(true);
        assertDoesNotThrow(() -> trabajos.insertar(trabajo3));
        List<Trabajo> copiaTrabajos = trabajos.get();
        assertEquals(2, copiaTrabajos.size());
        assertEquals(trabajo, copiaTrabajos.get(0));
        assertSame(trabajo, copiaTrabajos.get(0));
        assertEquals(trabajo3, copiaTrabajos.get(1));
        assertSame(trabajo3, copiaTrabajos.get(1));
    }

    @Test
    void getClienteValidoDevuelveTrabajosClienteCorrectamente() {
        assertDoesNotThrow(() -> trabajos.insertar(trabajo));
        when(trabajo.getFechaFin()).thenReturn(anteayer);
        when(trabajo.estaCerrado()).thenReturn(true);
        assertDoesNotThrow(() -> trabajos.insertar(mecanico));
        assertDoesNotThrow(() -> trabajos.insertar(trabajo3));
        List<Trabajo> trabajosCliente = trabajos.get(cliente1);
        assertEquals(2, trabajosCliente.size());
        assertEquals(trabajo, trabajosCliente.get(0));
        assertSame(trabajo, trabajosCliente.get(0));
        assertEquals(mecanico, trabajosCliente.get(1));
        assertSame(mecanico, trabajosCliente.get(1));
    }

    @Test
    void getVehiculoValidoDevuelveTrabajosVehiculoCorrectamente() {
        assertDoesNotThrow(() -> trabajos.insertar(trabajo));
        when(trabajo.getFechaFin()).thenReturn(anteayer);
        when(trabajo.estaCerrado()).thenReturn(true);
        assertDoesNotThrow(() -> trabajos.insertar(mecanico));
        assertDoesNotThrow(() -> trabajos.insertar(trabajo3));
        List<Trabajo> trabajosVehiculo = trabajos.get(vehiculo1);
        assertEquals(2, trabajosVehiculo.size());
        assertEquals(trabajo, trabajosVehiculo.get(0));
        assertSame(trabajo, trabajosVehiculo.get(0));
        assertEquals(trabajo3, trabajosVehiculo.get(1));
        assertSame(trabajo3,trabajosVehiculo.get(1));
    }

    @Test
    void insertarTrabajoValidaInsertaCorrectamente() {
        assertDoesNotThrow(() -> trabajos.insertar(trabajo));
        assertEquals(trabajo, trabajos.buscar(trabajo));
        assertSame(trabajo, trabajos.buscar(trabajo));
    }

    @Test
    void insertarTrabajoNulaLanzaExcepcion() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> trabajos.insertar(null));
        assertEquals("No se puede insertar un trabajo nulo.", npe.getMessage());
    }

    @Test
    void insertarTrabajoClienteTrabajoAbiertaLanzaExcepcion() {
        assertDoesNotThrow(() -> trabajos.insertar(trabajo));
        TallerMecanicoExcepcion tme = assertThrows(TallerMecanicoExcepcion.class, () -> trabajos.insertar(mecanico));
        assertEquals("El cliente tiene otro trabajo en curso.", tme.getMessage());
    }

    @Test
    void insertarTrabajoVehiculoTrabajoAbiertaLanzaExcepcion() {
        assertDoesNotThrow(() -> trabajos.insertar(trabajo));
        TallerMecanicoExcepcion tme = assertThrows(TallerMecanicoExcepcion.class, () -> trabajos.insertar(trabajo3));
        assertEquals("El vehículo está actualmente en el taller.", tme.getMessage());
    }

    @Test
    void insertarTrabajoClienteTrabajoAnteiorLanzaExcepcion() {
        assertDoesNotThrow(() -> trabajos.insertar(trabajo));
        assertDoesNotThrow(() -> trabajos.cerrar(trabajo, anteayer));
        when(trabajo.getFechaInicio()).thenReturn(ayer);
        when(trabajo.getFechaFin()).thenReturn(anteayer);
        when(trabajo.estaCerrado()).thenReturn(true);
        assertDoesNotThrow(() -> trabajos.insertar(trabajo));
        when(trabajo.estaCerrado()).thenReturn(false);
        assertDoesNotThrow(() -> trabajos.cerrar(trabajo, ayer));
        when(trabajo.estaCerrado()).thenReturn(true);
        when(trabajo.getFechaFin()).thenReturn(ayer);
        TallerMecanicoExcepcion tme = assertThrows(TallerMecanicoExcepcion.class, () -> trabajos.insertar(mecanico));
        assertEquals("El cliente tiene otro trabajo posterior.", tme.getMessage());
    }

    @Test
    void insertarTrabajoVehiculoTrabajoAnteriorLanzaExcepcion() {
        assertDoesNotThrow(() -> trabajos.insertar(trabajo));
        assertDoesNotThrow(() -> trabajos.cerrar(trabajo, anteayer));
        when(trabajo.getFechaInicio()).thenReturn(ayer);
        when(trabajo.getFechaFin()).thenReturn(anteayer);
        when(trabajo.estaCerrado()).thenReturn(true);
        assertDoesNotThrow(() -> trabajos.insertar(trabajo));
        when(trabajo.estaCerrado()).thenReturn(false);
        assertDoesNotThrow(() -> trabajos.cerrar(trabajo, ayer));
        when(trabajo.estaCerrado()).thenReturn(true);
        when(trabajo.getFechaFin()).thenReturn(ayer);
        TallerMecanicoExcepcion tme = assertThrows(TallerMecanicoExcepcion.class, () -> trabajos.insertar(trabajo3));
        assertEquals("El vehículo tiene otro trabajo posterior.", tme.getMessage());
    }

    @Test
    void anadirHorasTrabajoValidoHorasValidasAnadeHorasCorrectamente() {
        assertDoesNotThrow(() -> trabajos.insertar(trabajo));
        assertDoesNotThrow(() -> trabajos.anadirHoras(trabajo, 10));
        when(trabajo.getHoras()).thenReturn(10);
        Trabajo trabajo = trabajos.buscar(TrabajosTest.trabajo);
        assertEquals(10, trabajo.getHoras());
    }

    @Test
    void anadirHorasTrabajoNuloHorasValidasLanzaExcepcion() {
        assertDoesNotThrow(() -> trabajos.insertar(trabajo));
        NullPointerException npe = assertThrows(NullPointerException.class, () -> trabajos.anadirHoras(null, 10));
        assertEquals("No puedo añadir horas a un trabajo nulo.", npe.getMessage());
    }

    @Test
    void anadirHorasTrabajoNoExistenteHorasValidasLanzaExcepcion() {
        TallerMecanicoExcepcion tme = assertThrows(TallerMecanicoExcepcion.class, () -> trabajos.anadirHoras(trabajo, 10));
        assertEquals("No existe ningún trabajo abierto para dicho vehículo.", tme.getMessage());
    }

    @Test
    void anadirPrecioMaterialRevisionValidaPrecioMaterialValidoLanzaExcepcion() {
        assertDoesNotThrow(() -> trabajos.insertar(trabajo));
        TallerMecanicoExcepcion tme = assertThrows(TallerMecanicoExcepcion.class, () -> trabajos.anadirPrecioMaterial(trabajo, 100f));
        assertEquals("No se puede añadir precio al material para este tipo de trabajos.", tme.getMessage());
    }

    @Test
    void anadirPrecioMaterialMecancioValidoPrecioMaterialValidoAnadaPrecioMaterialCorrectamente() {
        assertDoesNotThrow(() -> trabajos.insertar(mecanico));
        assertDoesNotThrow(() -> trabajos.anadirPrecioMaterial(mecanico, 100f));
        when(mecanico.getPrecioMaterial()).thenReturn(100f);
        Mecanico trabajo = (Mecanico) trabajos.buscar(mecanico);
        assertEquals(100f, trabajo.getPrecioMaterial());
    }

    @Test
    void anadirPrecioMaterialTrabajoNuloPrecioMaterialValidoLanzaExcepcion() {
        assertDoesNotThrow(() -> trabajos.insertar(trabajo));
        NullPointerException npe = assertThrows(NullPointerException.class, () -> trabajos.anadirPrecioMaterial(null, 100f));
        assertEquals("No puedo añadir precio del material a un trabajo nulo.", npe.getMessage());
    }

    @Test
    void anadirPrecioMaterialTrabajoNoExistentePrecioMaterialValidoLanzaExcepcion() {
        TallerMecanicoExcepcion tme = assertThrows(TallerMecanicoExcepcion.class, () -> trabajos.anadirPrecioMaterial(trabajo, 100f));
        assertEquals("No existe ningún trabajo abierto para dicho vehículo.", tme.getMessage());
    }

    @Test
    void cerrarTrabajoNuloFechaValidaLanzaExcepcion() {
        assertDoesNotThrow(() -> trabajos.insertar(trabajo));
        NullPointerException npe = assertThrows(NullPointerException.class, () -> trabajos.cerrar(null, ayer));
        assertEquals("No puedo cerrar un trabajo nulo.", npe.getMessage());
    }

    @Test
    void cerrarTrabajoNoExistenteFechaValidaLanzaExcepcion() {
        TallerMecanicoExcepcion tme = assertThrows(TallerMecanicoExcepcion.class, () -> trabajos.cerrar(trabajo, hoy));
        assertEquals("No existe ningún trabajo abierto para dicho vehículo.", tme.getMessage());
    }

    @Test
    void cerrarTrabajoValioaFechaValidaCierraCorrectamente() {
        assertDoesNotThrow(() -> trabajos.insertar(trabajo));
        assertDoesNotThrow(() -> trabajos.cerrar(trabajo, ayer));
        when(trabajo.getFechaFin()).thenReturn(ayer);
        Trabajo trabajo = trabajos.buscar(TrabajosTest.trabajo);
        assertEquals(ayer, trabajo.getFechaFin());
    }

    @Test
    void borrarTrabajoExistenteBorraTrabajoCorrectamente() {
        assertDoesNotThrow(() -> trabajos.insertar(trabajo));
        assertDoesNotThrow(() -> trabajos.borrar(trabajo));
        assertNull(trabajos.buscar(trabajo));
    }

    @Test
    void borrarTrabajoNoExistenteLanzaExcepcion() {
        assertDoesNotThrow(() -> trabajos.insertar(trabajo));
        TallerMecanicoExcepcion tme = assertThrows(TallerMecanicoExcepcion.class, () -> trabajos.borrar(mecanico));
        assertEquals("No existe ningún trabajo igual.", tme.getMessage());
    }

    @Test
    void borrarTrabajoNuloLanzaExcepcion() {
        assertDoesNotThrow(() -> trabajos.insertar(trabajo));
        NullPointerException npe = assertThrows(NullPointerException.class, () -> trabajos.borrar(null));
        assertEquals("No se puede borrar un trabajo nulo.", npe.getMessage());
    }

    @Test
    void buscarTrabajoExistenteDevuelveTrabajoCorrectamente() {
        assertDoesNotThrow(() -> trabajos.insertar(trabajo));
        assertEquals(trabajo, trabajos.buscar(trabajo));
        assertSame(trabajo, trabajos.buscar(trabajo));
    }

    @Test
    void busarTrabajoNoExistenteDevuelveTrabajoNula() {
        assertNull(trabajos.buscar(trabajo));
    }

    @Test
    void buscarTrabajoNuloLanzaExcepcion() {
        assertDoesNotThrow(() -> trabajos.insertar(trabajo));
        NullPointerException npe = assertThrows(NullPointerException.class, () -> trabajos.buscar(null));
        assertEquals("No se puede buscar un trabajo nulo.", npe.getMessage());
    }
}