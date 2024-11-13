package buscadorTest;

import static org.junit.jupiter.api.Assertions.*; 
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import buscador.FiltroCapacidad;
import observer.Inmueble;

class FiltroCapacidadTest {
	 //DOC
    Inmueble casa = mock(Inmueble.class);
    Inmueble depto = mock(Inmueble.class);
    Inmueble quincho = mock(Inmueble.class);

    // Lista de inmuebles de prueba
    List<Inmueble> inmuebles;

    // Instancia de FiltroCapacidad
    FiltroCapacidad filtroCapacidad;

    @BeforeEach
    void setUp() {
        filtroCapacidad = new FiltroCapacidad(4);
        inmuebles = new ArrayList<>();
        inmuebles.add(casa);
        inmuebles.add(depto);
        inmuebles.add(quincho);
    }

    @Test
    void testFiltroPorCapacidad() {
        // Configuramos la capacidad de cada inmueble
        when(casa.getCapacidad()).thenReturn(4);
        when(depto.getCapacidad()).thenReturn(4);
        when(quincho.getCapacidad()).thenReturn(2);

        // Ejecutamos el método filtro
        List<Inmueble> resultado = filtroCapacidad.filtro(inmuebles);

        // Verificamos que solo se incluyan los inmuebles con la capacidad especificada (4)
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(casa));
        assertTrue(resultado.contains(depto));
        assertFalse(resultado.contains(quincho));
    }

    @Test
    void testFiltrarConCapacidadCiudadYFechas() {
        // Configuramos los valores de los atributos para los filtros
        String ciudad = "Buenos Aires";
        LocalDateTime checkIn = LocalDateTime.of(2023, 12, 25, 14, 0);
        LocalDateTime checkOut = LocalDateTime.of(2023, 12, 30, 10, 0);

        // Configuramos los mocks para cumplir con las condiciones de los filtros
        when(casa.getCiudad()).thenReturn("Buenos Aires");
        when(casa.getFechaCheckIn()).thenReturn(checkIn);
        when(casa.getFechaCheckOut()).thenReturn(checkOut);
        when(casa.getCapacidad()).thenReturn(4);

        when(depto.getCiudad()).thenReturn("Buenos Aires");
        when(depto.getFechaCheckIn()).thenReturn(checkIn);
        when(depto.getFechaCheckOut()).thenReturn(checkOut);
        when(depto.getCapacidad()).thenReturn(4);

        when(quincho.getCiudad()).thenReturn("Córdoba");
        when(quincho.getFechaCheckIn()).thenReturn(checkIn);
        when(quincho.getFechaCheckOut()).thenReturn(checkOut);
        when(quincho.getCapacidad()).thenReturn(2);

        // Ejecutamos el método filtrar
        List<Inmueble> resultado = filtroCapacidad.filtrar(ciudad, checkIn, checkOut, inmuebles);

        // Verificamos el tamaño y el contenido del resultado
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(casa));
        assertTrue(resultado.contains(depto));
        assertFalse(resultado.contains(quincho));
    }

}
