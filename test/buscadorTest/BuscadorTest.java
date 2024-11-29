package buscadorTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import administrador.Categoria;
import buscador.Buscador;
import buscador.Filtro;
import nucleo.Sistema;
import nucleo.SitioWeb;
import observer.Inmueble;

class BuscadorTest {
	//DOC
	SitioWeb trivago= mock(SitioWeb.class);
	Filtro filtro= mock(Filtro.class);
	Inmueble casa= mock(Inmueble.class);
	Inmueble depto= mock(Inmueble.class);
	Inmueble quincho= mock(Inmueble.class);
	Sistema sistema = mock(Sistema.class);
	List<Inmueble> inmueblesDisponibles = new ArrayList<>();
	//SUT
	Buscador buscador;

	@BeforeEach
	void setUp() throws Exception {
		
        buscador = new Buscador(inmueblesDisponibles, filtro, trivago);
        // Agregamos los inmuebles simulados a la lista inmueblesDisponibles
        inmueblesDisponibles.add(casa);
        inmueblesDisponibles.add(depto);
        inmueblesDisponibles.add(quincho);
        
     // Configuramos el sistema del sitio web para que devuelva inmueblesDisponibles
        when(trivago.getSistema()).thenReturn(sistema);
        when(sistema.getAltas()).thenReturn(inmueblesDisponibles);
    }

    @Test
    void testPromedioPorCategoria() {
        // Creamos categorías simuladas
        Categoria categoria1 = mock(Categoria.class);
        Categoria categoria2 = mock(Categoria.class);
        
        when(categoria1.getNombre()).thenReturn("Limpieza");
        when(categoria1.promedio()).thenReturn(4.5);
        
        when(categoria2.getNombre()).thenReturn("Ubicación");
        when(categoria2.promedio()).thenReturn(3.8);

        List<Categoria> categorias = List.of(categoria1, categoria2);

        // Llamamos al método promedioPorCategoria
        String resultado = buscador.promedioPorCategoria(categorias);

        // Verificamos el resultado esperado
        String expected = "Limpieza: 4.5\nUbicación: 3.8\n";
        assertEquals(expected, resultado);
    }

    @Test
    void testBuscar() {
        // Configuramos los datos de entrada para el método buscar
        String ciudad = "Buenos Aires";
        LocalDateTime checkIn = LocalDateTime.now();
        LocalDateTime checkOut = checkIn.plusDays(3);

        // Creamos una lista de inmuebles filtrados como resultado esperado
        List<Inmueble> inmueblesFiltrados = List.of(casa, depto);

        // Configuramos el mock del filtro para que devuelva los inmuebles filtrados
        when(filtro.filtrar(ciudad, checkIn, checkOut, inmueblesDisponibles)).thenReturn(inmueblesFiltrados);

        // Ejecutamos el método de búsqueda
        buscador.buscar(ciudad, checkIn, checkOut);

        // Verificamos que el resultado de la búsqueda es el esperado
        assertEquals(inmueblesFiltrados, buscador.getResultadoBusqueda());

        // Verificamos que el filtro fue llamado con los parámetros correctos
        verify(filtro).filtrar(ciudad, checkIn, checkOut, inmueblesDisponibles);
    }
    
    @Test
    void testPuntajePorCategoria() {
        // Creamos categorías simuladas
        Categoria categoria1 = mock(Categoria.class);
        Categoria categoria2 = mock(Categoria.class);
        
        // Simulamos los votos para cada categoría
        when(categoria1.getNombre()).thenReturn("Limpieza");
        when(categoria1.cantidadQuePuntuaronCon(5)).thenReturn(10);
        when(categoria1.cantidadQuePuntuaronCon(4)).thenReturn(5);
        when(categoria1.cantidadQuePuntuaronCon(3)).thenReturn(3);
        when(categoria1.cantidadQuePuntuaronCon(2)).thenReturn(1);
        when(categoria1.cantidadQuePuntuaronCon(1)).thenReturn(0);
        
        when(categoria2.getNombre()).thenReturn("Ubicación");
        when(categoria2.cantidadQuePuntuaronCon(5)).thenReturn(8);
        when(categoria2.cantidadQuePuntuaronCon(4)).thenReturn(4);
        when(categoria2.cantidadQuePuntuaronCon(3)).thenReturn(2);
        when(categoria2.cantidadQuePuntuaronCon(2)).thenReturn(1);
        when(categoria2.cantidadQuePuntuaronCon(1)).thenReturn(0);

        List<Categoria> categorias = List.of(categoria1, categoria2);

        // Llamamos al método puntajePorCategoria
        String resultado = buscador.puntajePorCategoria(categorias);

        // Verificamos el resultado esperado
        String expected = "Limpieza:\n" +
                          "5 Puntos: 10\n" +
                          "4 Puntos: 5\n" +
                          "3 Puntos: 3\n" +
                          "2 Puntos: 1\n" +
                          "1 Punto: 0\n" +
                          "Ubicación:\n" +
                          "5 Puntos: 8\n" +
                          "4 Puntos: 4\n" +
                          "3 Puntos: 2\n" +
                          "2 Puntos: 1\n" +
                          "1 Punto: 0\n";

        assertEquals(expected, resultado);
    }
    
    @Test
    void testAntiguedad() {
        // Creamos fechas simuladas
        LocalDateTime fechaAnterior = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime fechaActual = LocalDateTime.now();

        // Llamamos al método antiguedad
        long resultado = buscador.antiguedad(fechaAnterior, fechaActual);

        // Verificamos el resultado esperado (la antigüedad en días)
        long expected = ChronoUnit.DAYS.between(fechaAnterior, fechaActual);
        assertEquals(expected, resultado);
    }
    
    @Test
    void testTiposDeInmueblesEn() {
        // Creamos inmuebles simulados
        when(casa.getTipoDeInmueble()).thenReturn("Casa");
        when(depto.getTipoDeInmueble()).thenReturn("Departamento");
        when(quincho.getTipoDeInmueble()).thenReturn("Quincho");

        List<Inmueble> inmuebles = List.of(casa, depto, quincho);

        // Llamamos al método tiposDeInmueblesEn
        String resultado = buscador.tiposDeInmueblesEn(inmuebles);

        // Verificamos el resultado esperado
        String expected = "Casa\nDepartamento\nQuincho\n";
        assertEquals(expected, resultado);
    }
    
    @Test
    void testAddFiltro() {
        buscador.addFiltro(filtro);

        assertEquals(filtro, buscador.getFiltro());
    }

    @Test
    void testRemoveFiltro() {
        buscador.addFiltro(filtro);
        buscador.removeFiltro();

        assertEquals(null, buscador.getFiltro());
    }

}
