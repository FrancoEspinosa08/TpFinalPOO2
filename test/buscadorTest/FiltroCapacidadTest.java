package buscadorTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import buscador.FiltroCapacidad;
import buscador.FiltroCheckIn;
import buscador.FiltroCheckOut;
import buscador.FiltroCiudad;
import observer.Inmueble;

class FiltroCapacidadTest {

    private FiltroCapacidad filtroCapacidad;
    private List<Inmueble> inmuebles;
    private Inmueble inmueble1;
    private Inmueble inmueble2;
    private Inmueble inmueble3;
    private FiltroCiudad filtroCiudad;
    private FiltroCheckIn filtroCheckIn;
    private FiltroCheckOut filtroCheckOut;

    @BeforeEach
    void setUp() {
        // Configuramos los mocks de los inmuebles
        inmueble1 = mock(Inmueble.class);
        inmueble2 = mock(Inmueble.class);
        inmueble3 = mock(Inmueble.class);

        // Configuramos las capacidades de los inmuebles
        when(inmueble1.getCapacidad()).thenReturn(4);
        when(inmueble2.getCapacidad()).thenReturn(2);
        when(inmueble3.getCapacidad()).thenReturn(4);

        // Creamos la lista de inmuebles
        inmuebles = new ArrayList<>();
        inmuebles.add(inmueble1);
        inmuebles.add(inmueble2);
        inmuebles.add(inmueble3);

        // Configuración de filtros obligatorios
        filtroCiudad = mock(FiltroCiudad.class);
        filtroCheckIn = mock(FiltroCheckIn.class);
        filtroCheckOut = mock(FiltroCheckOut.class);

        // Configuramos los filtros obligatorios para los inmuebles
        when(filtroCiudad.filtrar(anyString(), any(), any(), eq(inmuebles))).thenReturn(inmuebles);
        when(filtroCheckIn.filtrar(anyString(), any(), any(), eq(inmuebles))).thenReturn(inmuebles);
        when(filtroCheckOut.filtrar(anyString(), any(), any(), eq(inmuebles))).thenReturn(inmuebles);

        // Inicializamos el filtro de capacidad con los filtros obligatorios
        filtroCapacidad = new FiltroCapacidad(4, List.of(filtroCiudad, filtroCheckIn, filtroCheckOut));
    }

    @Test
    void testFiltrarCapacidad() {
        // Configuramos los datos de entrada para el filtro
        String ciudad = "Buenos Aires";
        LocalDateTime checkIn = LocalDateTime.now();
        LocalDateTime checkOut = checkIn.plusDays(3);

        // Ejecutamos el filtro de capacidad
        List<Inmueble> resultado = filtroCapacidad.filtrar(ciudad, checkIn, checkOut, inmuebles);

        // Verificamos que solo los inmuebles con capacidad 4 estén en el resultado
        assertEquals(2, resultado.size());
        assertEquals(inmueble1, resultado.get(0));
        assertEquals(inmueble3, resultado.get(1));
    }
}
