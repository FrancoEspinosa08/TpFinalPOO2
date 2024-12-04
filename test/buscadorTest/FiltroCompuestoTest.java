package buscadorTest;

import static org.junit.jupiter.api.Assertions.*; 
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import buscador.Filtro;
import buscador.FiltroCheckIn;
import buscador.FiltroCheckOut;
import buscador.FiltroCiudad;
import buscador.FiltroCompuesto;
import buscador.FiltroPrecioMaximo;
import buscador.FiltroPrecioMinimo;

import observer.Inmueble;

class FiltroCompuestoTest {

    // Mocks de los inmuebles
    Inmueble casa = mock(Inmueble.class);
    Inmueble depto = mock(Inmueble.class);
    Inmueble quincho = mock(Inmueble.class);

    // Lista de inmuebles de prueba
    List<Inmueble> inmuebles;

    // Filtros a aplicar
    FiltroPrecioMaximo filtroPrecioMaximo;
    FiltroPrecioMinimo filtroPrecioMinimo;
    FiltroCompuesto filtroCompuesto;

    @BeforeEach
    void setUp() {
        // Filtros obligatorios
        List<Filtro> filtrosObligatorios = new ArrayList<>();
        
        filtrosObligatorios.add(new FiltroCiudad());
        filtrosObligatorios.add(new FiltroCheckIn());
        filtrosObligatorios.add(new FiltroCheckOut());

        // Filtro con precio máximo de 200
        filtroPrecioMaximo = new FiltroPrecioMaximo(200.0, filtrosObligatorios);  
        // Filtro con precio mínimo de 100
        filtroPrecioMinimo = new FiltroPrecioMinimo(100.0, filtrosObligatorios);  

        // Lista de inmuebles
        inmuebles = new ArrayList<>();
        inmuebles.add(casa);
        inmuebles.add(depto);
        inmuebles.add(quincho);

        // Configuramos FiltroCompuesto con los filtros
        List<Filtro> filtros = new ArrayList<>();
        filtros.add(filtroPrecioMaximo);
        filtros.add(filtroPrecioMinimo);
        filtros.addAll(filtrosObligatorios);  // Añadimos los filtros obligatorios

        // Instanciamos el FiltroCompuesto
        filtroCompuesto = new FiltroCompuesto(filtros, filtrosObligatorios);
    }

    @Test
    void testFiltro() {
        // Configuramos los precios de los inmuebles
        when(casa.getPrecioTotal()).thenReturn((float) 150.0);  // Precio de 'casa' = 150
        when(depto.getPrecioTotal()).thenReturn((float) 250.0); // Precio de 'depto' = 250
        when(quincho.getPrecioTotal()).thenReturn((float) 120.0); // Precio de 'quincho' = 120

        // Configuramos los mocks para cumplir con las condiciones de los filtros
        when(casa.getCiudad()).thenReturn("Buenos Aires");
        when(depto.getCiudad()).thenReturn("Buenos Aires");
        when(quincho.getCiudad()).thenReturn("Buenos Aires");

        LocalDateTime checkIn = LocalDateTime.of(2023, 12, 25, 14, 0);
        LocalDateTime checkOut = LocalDateTime.of(2023, 12, 30, 10, 0);

        when(casa.getFechaCheckIn()).thenReturn(checkIn);
        when(casa.getFechaCheckOut()).thenReturn(checkOut);

        when(depto.getFechaCheckIn()).thenReturn(checkIn);
        when(depto.getFechaCheckOut()).thenReturn(checkOut);

        when(quincho.getFechaCheckIn()).thenReturn(checkIn);
        when(quincho.getFechaCheckOut()).thenReturn(checkOut);

        // Ejecutamos el filtro compuesto
        List<Inmueble> resultado = filtroCompuesto.filtro("Buenos Aires", checkIn, checkOut, inmuebles);

        // Verificamos que solo los inmuebles que pasan todos los filtros estén en el resultado
        assertEquals(2, resultado.size());  // 'casa' y 'quincho' cumplen con los filtros
        assertTrue(resultado.contains(casa));  // 'casa' cumple con los filtros
        assertTrue(resultado.contains(quincho));  // 'quincho' cumple con los filtros
        assertFalse(resultado.contains(depto));  // 'depto' no pasa el filtro de precio máximo
    }
}