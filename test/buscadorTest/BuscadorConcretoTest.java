package buscadorTest;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import administrador.CategoriaInmuebleEstado;
import administrador.CategoriaPropietarioAtencion;
import administrador.Servicio;
import buscador.*;
import inmuebleYUsuario.Email;
import inmuebleYUsuario.RankingInmueble;
import inmuebleYUsuario.RankingUsuario;
import nucleo.SitioWeb;
import nucleo.Sistema;
import observer.Inmueble;
import observer.Usuario;

class BuscadorConcretoTest {

    private Buscador buscador;
    private SitioWeb mockSitioWeb;
    private Inmueble inmueble1;
    private Inmueble inmueble2;
    private Inmueble inmueble3;
    // Mock del sistema y lista de inmuebles
    Sistema mockSistema;
    
    RankingInmueble ranking;
    RankingUsuario rankUser;
    
    CategoriaInmuebleEstado catEstado;
    
    CategoriaPropietarioAtencion catAtencion;
    
    Usuario propietario;
    
    Email email;
    
    @BeforeEach
    void setUp() {
        // Mock del sitio web
        mockSitioWeb = mock(SitioWeb.class);
       
        //Mock de Sistema
        mockSistema = mock(Sistema.class);
        
        // Mock de inmuebles
        inmueble1 = mock(Inmueble.class);
        inmueble2 = mock(Inmueble.class);
        inmueble3 = mock(Inmueble.class);
        
        //Email
        email = mock(Email.class);
        when(email.toString()).thenReturn("email");
        
        //Categoria propietario
        catAtencion = new CategoriaPropietarioAtencion(List.of(5,5));
        
        //Ranking usuario con categoria
        rankUser = new RankingUsuario(List.of(catAtencion), List.of(catAtencion));
        
        //Propietario del inmueble
        propietario = mock(Usuario.class);
        
        //Categoria
        catEstado = new CategoriaInmuebleEstado(List.of(5,5));
        
       
        //RankingInmueble con categoria
        ranking = new RankingInmueble(List.of(catEstado));
        
        when(propietario.getNombre()).thenReturn("Ivan");
        when(propietario.getTelefono()).thenReturn("123456");
        when(propietario.getEmail()).thenReturn(email);
        when(propietario.getEmail()).thenReturn(email);
        when(propietario.getRanking()).thenReturn(rankUser);
        when(propietario.getFechaDeInscripcion()).thenReturn(LocalDateTime.now().minusDays(10));
        when(propietario.cantidadTotalDeAlquileres()).thenReturn(2);
        when(propietario.getInmuebles()).thenReturn(List.of(inmueble2));
        
      
        // Configuración de inmuebles
        when(inmueble1.getCiudad()).thenReturn("Buenos Aires");
        when(inmueble1.getFechaCheckIn()).thenReturn(LocalDateTime.of(2024, 12, 10, 14, 0));
        when(inmueble1.getFechaCheckOut()).thenReturn(LocalDateTime.of(2024, 12, 20, 10, 0));
        when(inmueble1.getPrecioTotal()).thenReturn(5000f);
        
        when(inmueble1.getTipoDeInmueble()).thenReturn("CASA");
        when(inmueble1.getSuperficie()).thenReturn(100);
        when(inmueble1.getPais()).thenReturn("Argentina");
        when(inmueble1.getDireccion()).thenReturn("123");
        when(inmueble1.getServicios()).thenReturn(List.of(Servicio.AGUA));
        when(inmueble1.getCapacidad()).thenReturn(12);
        when(inmueble1.getFotos()).thenReturn(List.of("F1,F2"));
        when(inmueble1.getPrecioPorDia()).thenReturn(1000F);
        when(inmueble1.getComentarios()).thenReturn(List.of("Comentario1", "Comentario2"));
        when(inmueble1.getRanking()).thenReturn(ranking);
        when(inmueble1.getPropietario()).thenReturn(propietario);
        when(inmueble1.getVecesAlquilado()).thenReturn(0);
      
        
        when(inmueble2.getCiudad()).thenReturn("Buenos Aires");
        when(inmueble2.getFechaCheckIn()).thenReturn(LocalDateTime.of(2024, 12, 10, 14, 0));
        when(inmueble2.getFechaCheckOut()).thenReturn(LocalDateTime.of(2024, 12, 25, 10, 0));
        when(inmueble2.getPrecioTotal()).thenReturn(15000f);
        when(inmueble2.getTipoDeInmueble()).thenReturn("Depto");
        
        when(inmueble3.getCiudad()).thenReturn("Córdoba");
        when(inmueble3.getFechaCheckIn()).thenReturn(LocalDateTime.of(2024, 12, 12, 14, 0));
        when(inmueble3.getFechaCheckOut()).thenReturn(LocalDateTime.of(2024, 12, 22, 10, 0));
        when(inmueble3.getPrecioTotal()).thenReturn(8000f);

        
        when(mockSistema.getAltas()).thenReturn(List.of(inmueble1, inmueble2, inmueble3));
        when(mockSitioWeb.getSistema()).thenReturn(mockSistema);

        // Crear instancia del buscador con el mock del sitio web
        buscador = new Buscador(mockSitioWeb);
    }
    
    
    @Test
    void testVisualizar() {
    	// Crear filtros obligatorios
        Filtro filtroCiudad = new FiltroCiudad();
        Filtro filtroCheckIn = new FiltroCheckIn();
        Filtro filtroCheckOut = new FiltroCheckOut();
        
        // Crear filtro compuesto
        FiltroCompuesto filtroCompuesto = new FiltroCompuesto(
        		List.of(), //Sin filtros especiales
                List.of(filtroCiudad, filtroCheckIn, filtroCheckOut)
        );

        // Agregar el filtro compuesto al buscador
        buscador.addFiltro(filtroCompuesto);
        
    	when(mockSistema.getAltas()).thenReturn(List.of(inmueble1)); //Solo hay un inmueble posible
    	when(mockSitioWeb.getSistema()).thenReturn(mockSistema);
    	
    	String resultado = buscador.visualizar(0, "Buenos Aires", inmueble1.getFechaCheckIn(), inmueble1.getFechaCheckOut());
    	
    	String[] lineas = resultado.split("\n");
    	
    	assertEquals("Tipo de Inmueble: CASA", lineas[0]);
    	assertEquals("Superficie: 100",  lineas[1]);		
    	assertEquals("País: Argentina",  lineas[2]);		
    	assertEquals("Ciudad: Buenos Aires",  lineas[3]);		
    	assertEquals("Dirección: 123",  lineas[4]);
    	assertEquals("Servicios: [AGUA]",  lineas[5]);	
    	assertEquals("Capacidad: 12",  lineas[6]);	
    	assertEquals("Fotos: [F1,F2]",  lineas[7]);	
    	assertEquals("Fecha de ingreso: 2024-12-10T14:00",  lineas[8]);	
    	assertEquals("Fecha de egreso: 2024-12-20T10:00",  lineas[9]);	
    	assertEquals("Precio: 1000.0",  lineas[10]);
    	assertEquals("Comentarios: [Comentario1, Comentario2]",  lineas[11]);
    	assertEquals("Puntajes por categoría: Estado del Inmueble:",  lineas[12]);
    	assertEquals("5 Puntos: 2",  lineas[13]);
    	assertEquals("4 Puntos: 0",  lineas[14]);
    	assertEquals("3 Puntos: 0",  lineas[15]);
    	assertEquals("2 Puntos: 0",  lineas[16]);
    	assertEquals("1 Punto: 0",  lineas[17]);
    	
   
    	
    	
    	/*
    			
    			+ "Nombre: Ivan\r\n"
    			+ "Teléfono: 123456\r\n"
    			+ "Email: email\r\n"
    			+ "Puntajes por categoría: Atención del Propietario:\r\n"
    			+ "5 Puntos: 2\r\n"
    			+ "4 Puntos: 0\r\n"
    			+ "3 Puntos: 0\r\n"
    			+ "2 Puntos: 0\r\n"
    			+ "1 Punto: 0\r\n"
    			+ "\r\n"
    			+ "Promedio por categoría: Atención del Propietario: 5.0\r\n"
    			+ "\r\n"
    			+ "Antigüedad: 10\r\n"
    			+ "Cantidad de veces que se alquiló el inmueble: 0\r\n"
    			+ "Cantidad de veces que alquiló inmuebles: 2\r\n"
    			+ "Inmuebles Alquilados: Depto\r\n"
    			+ "\r\n";
    			
    	*/
    	
    	
    	
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
