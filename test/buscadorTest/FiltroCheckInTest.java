package buscadorTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import buscador.FiltroCheckIn;
import observer.Inmueble;

class FiltroCheckInTest {
    // DOC
    Inmueble casa = mock(Inmueble.class);
    Inmueble depto = mock(Inmueble.class);
    Inmueble quincho = mock(Inmueble.class);

    // Lista de inmuebles de prueba
    List<Inmueble> inmuebles;

    // Instancia de FiltroCheckIn
    FiltroCheckIn filtroCheckIn;

    @BeforeEach
    void setUp() {
        filtroCheckIn = new FiltroCheckIn();
        inmuebles = new ArrayList<>();
        inmuebles.add(casa);
        inmuebles.add(depto);
        inmuebles.add(quincho);
    }

    @Test
    void testFiltrarPorCheckIn() {
        // Configuramos fechas de check-in para los inmuebles
        LocalDateTime checkInEsperado = LocalDateTime.of(2023, 12, 25, 14, 0);
        LocalDateTime checkInDiferente = LocalDateTime.of(2023, 12, 26, 14, 0);

        when(casa.getFechaCheckIn()).thenReturn(checkInEsperado);
        when(depto.getFechaCheckIn()).thenReturn(checkInEsperado);
        when(quincho.getFechaCheckIn()).thenReturn(checkInDiferente);

        // Ejecutamos el método filtrar
        List<Inmueble> resultado = filtroCheckIn.filtrar(null, checkInEsperado, null, inmuebles);

        // Verificamos que solo los inmuebles con el check-in esperado estén en el resultado
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(casa));
        assertTrue(resultado.contains(depto));
        assertFalse(resultado.contains(quincho));
    }
}