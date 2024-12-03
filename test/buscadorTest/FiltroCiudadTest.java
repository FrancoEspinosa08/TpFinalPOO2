package buscadorTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import buscador.FiltroCiudad;
import buscador.FiltroCheckIn;
import buscador.FiltroCheckOut;
import observer.Inmueble;

class FiltroCiudadTest {

    Inmueble casa = mock(Inmueble.class);
    Inmueble depto = mock(Inmueble.class);
    Inmueble quincho = mock(Inmueble.class);
    
    FiltroCheckIn filtroCheckIn = mock(FiltroCheckIn.class);
    FiltroCheckOut filtroCheckOut = mock(FiltroCheckOut.class);

    List<Inmueble> inmuebles;
    FiltroCiudad filtroCiudad;

    @BeforeEach
    void setUp() {
        // Configuramos los filtros obligatorios en FiltroCiudad
        filtroCiudad = new FiltroCiudad();
        filtroCiudad.setFiltrosObligatorios(List.of(filtroCheckIn, filtroCheckOut));

        inmuebles = new ArrayList<>();
        inmuebles.add(casa);
        inmuebles.add(depto);
        inmuebles.add(quincho);
    }

    @Test
    void testFiltrarPorCiudad() {
        // Configuramos las ciudades para los inmuebles
        String ciudadEsperada = "Buenos Aires";
        String ciudadDiferente = "Córdoba";

        when(casa.getCiudad()).thenReturn(ciudadEsperada);
        when(depto.getCiudad()).thenReturn(ciudadEsperada);
        when(quincho.getCiudad()).thenReturn(ciudadDiferente);

        // Creamos las fechas de CheckIn y CheckOut
        LocalDateTime checkIn = LocalDateTime.now().plusDays(1);
        LocalDateTime checkOut = LocalDateTime.now().plusDays(5);

        // Configuramos que los filtros obligatorios (CheckIn y CheckOut) devuelvan la lista de inmuebles sin cambios
        when(filtroCheckIn.filtrar(anyString(), any(), any(), anyList())).thenReturn(inmuebles);
        when(filtroCheckOut.filtrar(anyString(), any(), any(), anyList())).thenReturn(inmuebles);

        // Ejecutamos el método filtrar
        List<Inmueble> resultado = filtroCiudad.filtrar(ciudadEsperada, checkIn, checkOut, inmuebles);

        // Verificamos que solo los inmuebles con la ciudad esperada estén en el resultado
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(casa));
        assertTrue(resultado.contains(depto));
        assertFalse(resultado.contains(quincho));
    }
}