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
import buscador.Filtro; // Asegúrate de importar la clase Filtro
import observer.Inmueble;

class FiltroPrecioMaximoTest {
    // DOC
    Inmueble casa = mock(Inmueble.class);
    Inmueble depto = mock(Inmueble.class);
    Inmueble quincho = mock(Inmueble.class);

    // Lista de inmuebles de prueba
    List<Inmueble> inmuebles;

    // SUT
    FiltroPrecioMaximo filtroPrecioMaximo;
    
    @BeforeEach
    void setUp() {
        // Inicializamos la lista de inmuebles
        inmuebles = new ArrayList<>();
        inmuebles.add(casa);
        inmuebles.add(depto);
        inmuebles.add(quincho);

        // Configuración de filtros obligatorios (por ejemplo, FiltroCiudad, FiltroCheckIn, FiltroCheckOut)
        List<Filtro> filtrosObligatorios = new ArrayList<>();
        // Aquí podrías crear e incluir los filtros obligatorios como FiltroCiudad, FiltroCheckIn, FiltroCheckOut
        // filtrosObligatorios.add(new FiltroCiudad(...));
        // filtrosObligatorios.add(new FiltroCheckIn(...));
        // filtrosObligatorios.add(new FiltroCheckOut(...));
        
        // Inicializamos el filtro con precio máximo de 150 y los filtros obligatorios
        filtroPrecioMaximo = new FiltroPrecioMaximo(150.0, filtrosObligatorios);  // Precio máximo de 150
    }

    @Test
    void testFiltroPorPrecioMaximo() {
        // Configuramos los precios de cada inmueble
        when(casa.getPrecioTotal()).thenReturn((float) 100.0);  // Precio de 'casa' = 100
        when(depto.getPrecioTotal()).thenReturn((float) 200.0); // Precio de 'depto' = 200
        when(quincho.getPrecioTotal()).thenReturn((float) 150.0); // Precio de 'quincho' = 150

        // Ejecutamos el filtro por precio máximo
        List<Inmueble> resultado = filtroPrecioMaximo.filtro(null, null, null, inmuebles);

        // Verificamos que solo se incluyan los inmuebles con precio <= 150
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(casa));
        assertTrue(resultado.contains(quincho));
        assertFalse(resultado.contains(depto));
    }

    @Test
    void testFiltrarConPrecioMaximo() {
        // Configuramos el precio de cada inmueble
        when(casa.getPrecioTotal()).thenReturn((float) 100.0);  // Precio de 'casa' = 100
        when(depto.getPrecioTotal()).thenReturn((float) 200.0); // Precio de 'depto' = 200
        when(quincho.getPrecioTotal()).thenReturn((float) 150.0); // Precio de 'quincho' = 150

        // Configuramos ciudad y fechas
        String ciudad = "Buenos Aires";
        LocalDateTime checkIn = LocalDateTime.of(2023, 12, 25, 14, 0);
        LocalDateTime checkOut = LocalDateTime.of(2023, 12, 30, 10, 0);

        // Configuramos los mocks para cumplir con las condiciones de los filtros
        when(casa.getCiudad()).thenReturn("Buenos Aires");
        when(casa.getFechaCheckIn()).thenReturn(checkIn);
        when(casa.getFechaCheckOut()).thenReturn(checkOut);

        when(depto.getCiudad()).thenReturn("Buenos Aires");
        when(depto.getFechaCheckIn()).thenReturn(checkIn);
        when(depto.getFechaCheckOut()).thenReturn(checkOut);

        when(quincho.getCiudad()).thenReturn("Buenos Aires");
        when(quincho.getFechaCheckIn()).thenReturn(checkIn);
        when(quincho.getFechaCheckOut()).thenReturn(checkOut);

        // Ejecutamos el filtro con todos los filtros aplicados
        List<Inmueble> resultado = filtroPrecioMaximo.filtrar(ciudad, checkIn, checkOut, inmuebles);

        // Verificamos que solo se incluyan los inmuebles con precio <= 150
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(casa));
        assertTrue(resultado.contains(quincho));
        assertFalse(resultado.contains(depto));  // El departamento no cumple con el precio máximo
    }
}
