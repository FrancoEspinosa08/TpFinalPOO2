package inmuebleYUsuarioTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import administrador.Servicio;
import inmuebleYUsuario.Comentario;
import inmuebleYUsuario.Email;
import inmuebleYUsuario.Evento;
import inmuebleYUsuario.FormaDePago;
import inmuebleYUsuario.Ranking;
import inmuebleYUsuario.RankingUsuario;
import observer.AppMobile;
import observer.IObserver;
import observer.Inmueble;
import observer.Usuario;
import politicasDeCancelacion.PoliticaDeCancelacion;

class InmuebleTest {
	private Inmueble inmueble;  // SUT
    private Usuario propietario;
    private Usuario inquilinoActivo;
    private List<Usuario> usuariosEnEspera;
    private List<Servicio> servicios;
    private List<String> fotos;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private FormaDePago formaDePago;
    private List<String> comentarios;
    private Ranking ranking;
    private List<FormaDePago> formaPagoValidas;
    private List<Evento> eventos;
    private PoliticaDeCancelacion politicaDeCancelacion;
    private Comentario comentario;
	
    // mocks para inquilinoActivo
	private Email email;
	private List<Inmueble> inmuebles;
	private RankingUsuario rankingParaInquilinoActivo;
    private AppMobile appMobile;
    
    @BeforeEach
    public void setUp() {
    	propietario = Mockito.mock(Usuario.class);
        //inquilinoActivo = Mockito.mock(Usuario.class);
        //usuariosEnEspera = Arrays.asList(Mockito.mock(Usuario.class), Mockito.mock(Usuario.class));
        //servicios = Arrays.asList(Servicio.AIREACONDICIONADO, Servicio.WIFI);
        fotos = Arrays.asList("foto1.jpg", "foto2.jpg", "foto3.png", "foto4.png", "foto5.jpg");
        checkIn = LocalDateTime.of(2024, 1, 1, 9, 0);
        checkOut = LocalDateTime.of(2024, 1, 17, 9, 0);
        formaDePago = FormaDePago.EFECTIVO;
        ranking = Mockito.mock(Ranking.class);
        formaPagoValidas = Arrays.asList(FormaDePago.EFECTIVO, FormaDePago.TARJETADEBITO);
        //eventos = Arrays.asList(Mockito.mock(Evento.class), Mockito.mock(Evento.class));
        politicaDeCancelacion = Mockito.mock(PoliticaDeCancelacion.class);

        // Inicializa la lista de comentarios antes de crear el objeto Comentario
        comentarios = new ArrayList<>(Arrays.asList("Comentario 1", "Comentario 2", "Comentario 3", "Comentario 4", "Comentario 5"));
        comentario = new Comentario(comentarios);
        
        // Crear eventos reales
        Evento evento1 = new Evento("Conferencia", 
                                    LocalDateTime.of(2024, 1, 5, 10, 0),
                                    LocalDateTime.of(2024, 1, 7, 10, 0), // Duración: 2 días
                                    50.0f); // Precio por día

        Evento evento2 = new Evento("Reunión", 
                                    LocalDateTime.of(2024, 1, 8, 10, 0),
                                    LocalDateTime.of(2024, 1, 9, 10, 0), // Duración: 1 día
                                    30.0f); // Precio por día

        // Crear la lista de eventos y asignarla al inmueble
        eventos = Arrays.asList(evento1, evento2);
        
        // Usuario
        email = mock(Email.class);
        inmuebles = Arrays.asList(mock(Inmueble.class)); // Crear un mock de Inmueble
        rankingParaInquilinoActivo = mock(RankingUsuario.class);
        appMobile = mock(AppMobile.class);
        
        inquilinoActivo = new Usuario("Leo Messi", "123456789", email, LocalDateTime.now(), rankingParaInquilinoActivo, inmuebles, comentario,
                3, appMobile);
        
        
        // Crear la instancia de Inmueble.
        inmueble = new Inmueble(propietario, inquilinoActivo, 0, "Apartamento", 50,
                                "Argentina", "Buenos Aires", "Azopardo 250", 4, fotos, checkIn, checkOut,
                                formaDePago, comentarios, ranking, formaPagoValidas, 100.0f, false, eventos,
                                politicaDeCancelacion);
    }
    
    @Test
    public void testEstadoInicialInmueble() {
        assertFalse(inmueble.getEsReservado());
        assertEquals(0, inmueble.getUsuariosEnEspera().size());
        assertEquals(2, inmueble.getEventos().size());
    }
    
    @Test
    public void testGetCapacidad() {
        assertEquals(4, inmueble.getCapacidad());
    }
    
    @Test
    public void testGetPropietario() {
        assertEquals(propietario, inmueble.getPropietario());
    }
    
    @Test
    public void testGetRanking() {
        assertEquals(ranking, inmueble.getRanking());
    }
    
    @Test
    public void testGetInquilinoActivo() {
        assertEquals(inquilinoActivo, inmueble.getInquilinoActivo());
    }
    
    @Test
    public void testSetInquilinoActivo() {

        inmueble.setInquilinoActivo(inquilinoActivo);
        assertEquals(inquilinoActivo, inmueble.getInquilinoActivo());
    }
    
    @Test
    public void testSetServicios() {
        List<Servicio> nuevosServicios = Arrays.asList(Servicio.GAS, Servicio.WIFI);
        inmueble.setServicios(nuevosServicios);
        assertEquals(nuevosServicios, inmueble.getServicios());
    }
    
    @Test
    public void testGetSuperficie() {
        assertEquals(50, inmueble.getSuperficie());
    }
    
    @Test
    public void testGetTipoDeInmueble() {
        assertEquals("Apartamento", inmueble.getTipoDeInmueble());
    }

    @Test
    public void testGetPais() {
        assertEquals("Argentina", inmueble.getPais());
    }

    @Test
    public void testGetCiudad() {
        assertEquals("Buenos Aires", inmueble.getCiudad());
    }

    @Test
    public void testGetDireccion() {
        assertEquals("Azopardo 250", inmueble.getDireccion());
    }
    
    @Test
    public void testGetHorarioCheckIn() {
        assertEquals(checkIn, inmueble.getFechaCheckIn());
    }

    @Test
    public void testGetHorarioCheckOut() {
        assertEquals(checkOut, inmueble.getFechaCheckOut());
    } 
    
    @Test
    public void testGetPoliticaDeCancelacion() {
        assertEquals(politicaDeCancelacion, inmueble.getPoliticaDeCancelacion());
    } 
    
    @Test
    public void testPrecioPorDia() {
        // Verificar que el precio por día se haya configurado correctamente.
        assertEquals(100.0f, inmueble.getPrecioPorDia(), 0.001);
    }
    
    @Test
    public void testVecesAlquiladoInicial() {
        // Verificar que el contador de veces alquilado este en 0 inicialmente
        assertEquals(0, inmueble.getVecesAlquilado());
    }
    
    // Sistema clase probar
    @Test
    public void testIncrementarVecesAlquilado() {
        // Incrementar el contador de veces alquilado
        inmueble.incrementarVecesAlquilado();
        assertEquals(1, inmueble.getVecesAlquilado());
    }
    
    @Test
    public void testSetPrecioPorDia() {
        // Verificar el comportamiento del setter para precioPorDia.
        inmueble.setPrecioPorDia(90.0f);
        assertEquals(90.0f, inmueble.getPrecioPorDia());
    }
    
    @Test
    public void testSetEsReservado() {
        inmueble.setEsReservado(true);
        assertTrue(inmueble.getEsReservado());
    }
    
    @Test
    public void testNotificarBajaDePrecio() {
        // Crear y mockear el Observer
        IObserver mockObserver = Mockito.mock(IObserver.class);

        // Agrego un observador
        inmueble.attach(mockObserver);

        // Cambia el precio y salta el mensaje actuaSiBajaPrecio
        inmueble.setPrecioPorDia(90.0f);

        // Verificar que el método actuaSiBajaPrecio se llame.
        Mockito.verify(mockObserver, Mockito.times(1)).actuaSiBajaPrecio(inmueble);
    }
    
    
    @Test
    public void testCalculoPrecioTotal() {

        float precioTotal = inmueble.getPrecioTotal();
        
        assertEquals(1430.0f, precioTotal, 0.001f);
    }
    
    
    @Test
    public void testGetComentarios() {
        assertEquals(comentarios, inmueble.getComentarios());
    }
    
    
    @Test
    public void testAñadirComentario() {
        String nuevoComentario = "Excelente experiencia";
        inmueble.añadirComentario(nuevoComentario);
        assertTrue(inmueble.getComentarios().contains(nuevoComentario));
    }
    
    @Test
    public void testSetFormaDePago() {
        FormaDePago nuevaFormaDePago = FormaDePago.TARJETACREDITO;
        inmueble.setFormaDePago(nuevaFormaDePago);
        assertEquals(nuevaFormaDePago, inmueble.getFormaDePago());
    }
    
}
