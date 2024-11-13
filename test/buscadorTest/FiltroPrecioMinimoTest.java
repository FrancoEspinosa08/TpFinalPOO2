package buscadorTest;

import static org.junit.jupiter.api.Assertions.*; 
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import buscador.FiltroPrecioMaximo;
import buscador.FiltroPrecioMinimo;
import observer.Inmueble;


class FiltroPrecioMinimoTest {
    // DOC
    Inmueble casa = mock(Inmueble.class);
    Inmueble depto = mock(Inmueble.class);
    Inmueble quincho = mock(Inmueble.class);

    // Lista de inmuebles de prueba
    List<Inmueble> inmuebles;

    // SUT
    FiltroPrecioMinimo filtroPrecioMinimo;

    @BeforeEach
    void setUp() {
        filtroPrecioMinimo = new FiltroPrecioMinimo(100);  // Establecemos el precio mínimo en 100
        inmuebles = new ArrayList<>();
        inmuebles.add(casa);
        inmuebles.add(depto);
        inmuebles.add(quincho);
    }

    @Test
    void testFiltroPorPrecioMinimo() {
        // Configuramos el precio de cada inmueble
        when(casa.getPrecioTotal()).thenReturn((float) 120.0);
        when(depto.getPrecioTotal()).thenReturn((float) 80.0);
        when(quincho.getPrecioTotal()).thenReturn((float) 150.0);

        // Ejecutamos el filtro
        List<Inmueble> resultado = filtroPrecioMinimo.filtro(inmuebles);

        // Verificamos que solo los inmuebles con un precio >= 100 estén en el resultado
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(casa));  // Casa tiene un precio >= 100
        assertFalse(resultado.contains(depto));  // Departamento tiene un precio < 100
        assertTrue(resultado.contains(quincho));  // Quincho tiene un precio >= 100
    }

    @Test
    void testFiltrarConPrecioMinimoYCiudad() {
        // Configuramos los atributos de los filtros
        String ciudad = "Buenos Aires";
        LocalDateTime checkIn = LocalDateTime.of(2023, 12, 25, 14, 0);
        LocalDateTime checkOut = LocalDateTime.of(2023, 12, 30, 10, 0);

        // Configuramos los mocks para cumplir con las condiciones de los filtros
        when(casa.getCiudad()).thenReturn("Buenos Aires");
        when(casa.getFechaCheckIn()).thenReturn(checkIn);
        when(casa.getFechaCheckOut()).thenReturn(checkOut);
        when(casa.getPrecioTotal()).thenReturn((float) 120.0);

        when(depto.getCiudad()).thenReturn("Buenos Aires");
        when(depto.getFechaCheckIn()).thenReturn(checkIn);
        when(depto.getFechaCheckOut()).thenReturn(checkOut);
        when(depto.getPrecioTotal()).thenReturn((float) 80.0);

        when(quincho.getCiudad()).thenReturn("Buenos Aires");
        when(quincho.getFechaCheckIn()).thenReturn(checkIn);
        when(quincho.getFechaCheckOut()).thenReturn(checkOut);
        when(quincho.getPrecioTotal()).thenReturn((float) 150.0);
        
        // Ejecutamos el método filtrar
        List<Inmueble> resultado = filtroPrecioMinimo.filtrar(ciudad, checkIn, checkOut, inmuebles);

        // Verificamos que los inmuebles que cumplen con el precio mínimo estén en el resultado
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(casa));  // Casa cumple con precio >= 100
        assertFalse(resultado.contains(depto));  // Departamento no cumple (precio < 100)
        assertTrue(resultado.contains(quincho));  // Quincho cumple con precio >= 100
    }
}
