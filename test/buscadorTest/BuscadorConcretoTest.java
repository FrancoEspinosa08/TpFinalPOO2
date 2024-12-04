package buscadorTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import buscador.*;
import nucleo.SitioWeb;
import nucleo.Sistema;
import observer.Inmueble;

class BuscadorConcretoTest {

    private Buscador buscador;
    private SitioWeb mockSitioWeb;
    private Inmueble inmueble1;
    private Inmueble inmueble2;
    private Inmueble inmueble3;

    @BeforeEach
    void setUp() {
        // Mock del sitio web
        mockSitioWeb = mock(SitioWeb.class);

        // Mock de inmuebles
        inmueble1 = mock(Inmueble.class);
        inmueble2 = mock(Inmueble.class);
        inmueble3 = mock(Inmueble.class);

        // Configuración de inmuebles
        when(inmueble1.getCiudad()).thenReturn("Buenos Aires");
        when(inmueble1.getFechaCheckIn()).thenReturn(LocalDateTime.of(2024, 12, 10, 14, 0));
        when(inmueble1.getFechaCheckOut()).thenReturn(LocalDateTime.of(2024, 12, 20, 10, 0));
        when(inmueble1.getPrecioTotal()).thenReturn(5000f);

        when(inmueble2.getCiudad()).thenReturn("Buenos Aires");
        when(inmueble2.getFechaCheckIn()).thenReturn(LocalDateTime.of(2024, 12, 10, 14, 0));
        when(inmueble2.getFechaCheckOut()).thenReturn(LocalDateTime.of(2024, 12, 25, 10, 0));
        when(inmueble2.getPrecioTotal()).thenReturn(15000f);

        when(inmueble3.getCiudad()).thenReturn("Córdoba");
        when(inmueble3.getFechaCheckIn()).thenReturn(LocalDateTime.of(2024, 12, 12, 14, 0));
        when(inmueble3.getFechaCheckOut()).thenReturn(LocalDateTime.of(2024, 12, 22, 10, 0));
        when(inmueble3.getPrecioTotal()).thenReturn(8000f);

        // Mock del sistema y lista de inmuebles
        Sistema mockSistema = mock(Sistema.class);
        when(mockSistema.getAltas()).thenReturn(List.of(inmueble1, inmueble2, inmueble3));
        when(mockSitioWeb.getSistema()).thenReturn(mockSistema);

        // Crear instancia del buscador con el mock del sitio web
        buscador = new Buscador(mockSitioWeb);
    }

    @Test
    void testBuscadorConFiltrosCompuestos() {
        // Crear filtros obligatorios
        Filtro filtroCiudad = new FiltroCiudad();
        Filtro filtroCheckIn = new FiltroCheckIn();
        Filtro filtroCheckOut = new FiltroCheckOut();

        // Crear filtros no obligatorios con los filtros obligatorios
        FiltroPrecioMinimo filtroPrecioMinimo = new FiltroPrecioMinimo(4500, List.of(filtroCiudad, filtroCheckIn, filtroCheckOut));
        FiltroPrecioMaximo filtroPrecioMaximo = new FiltroPrecioMaximo(12000, List.of(filtroCiudad, filtroCheckIn, filtroCheckOut));

        // Crear filtro compuesto
        FiltroCompuesto filtroCompuesto = new FiltroCompuesto(
                List.of(filtroPrecioMinimo, filtroPrecioMaximo),
                List.of(filtroCiudad, filtroCheckIn, filtroCheckOut)
        );

        // Agregar el filtro compuesto al buscador
        buscador.addFiltro(filtroCompuesto);

        // Ejecutar búsqueda
        List<Inmueble> resultados = buscador.buscar(
                "Buenos Aires", // Ciudad
                LocalDateTime.of(2024, 12, 10, 14, 0), // Fecha check-in
                LocalDateTime.of(2024, 12, 20, 10, 0)  // Fecha check-out
        );

        // Validar resultados
        assertEquals(1, resultados.size());
    }
}
