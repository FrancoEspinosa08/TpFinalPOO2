package buscadorTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import buscador.FiltroCheckOut;
import observer.Inmueble;

class FiltroCheckOutTest {
    // DOC
    Inmueble casa = mock(Inmueble.class);
    Inmueble depto = mock(Inmueble.class);
    Inmueble quincho = mock(Inmueble.class);

    // Lista de inmuebles de prueba
    List<Inmueble> inmuebles;

    // Instancia de FiltroCheckOut
    FiltroCheckOut filtroCheckOut;

    @BeforeEach
    void setUp() {
        filtroCheckOut = new FiltroCheckOut();
        inmuebles = new ArrayList<>();
        inmuebles.add(casa);
        inmuebles.add(depto);
        inmuebles.add(quincho);
    }

    @Test
    void testFiltrarPorCheckOut() {
        // Configuramos fechas de check-out para los inmuebles
        LocalDateTime checkOutEsperado = LocalDateTime.of(2023, 12, 30, 10, 0);
        LocalDateTime checkOutDiferente = LocalDateTime.of(2023, 12, 31, 10, 0);

        when(casa.getFechaCheckOut()).thenReturn(checkOutEsperado);
        when(depto.getFechaCheckOut()).thenReturn(checkOutEsperado);
        when(quincho.getFechaCheckOut()).thenReturn(checkOutDiferente);

        // Ejecutamos el método filtrar
        List<Inmueble> resultado = filtroCheckOut.filtrar(null, null, checkOutEsperado, inmuebles);

        // Verificamos que solo los inmuebles con el check-out esperado estén en el resultado
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(casa));
        assertTrue(resultado.contains(depto));
        assertFalse(resultado.contains(quincho));
    }
}