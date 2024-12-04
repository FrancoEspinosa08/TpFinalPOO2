package inmuebleYUsuarioTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.time.Duration;
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
import observer.Reserva;
import observer.Usuario;
import politicasDeCancelacion.PoliticaDeCancelacion;

class InmuebleTest {
	private Inmueble inmueble;  // SUT
    private Usuario propietario;
    private Usuario inquilinoActivo;
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
    
    private Reserva reserva;
    
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
        
        
        reserva = Mockito.mock(Reserva.class);
        
        
        // Crear la instancia de Inmueble.
        inmueble = new Inmueble(
                propietario,
                0,
                "Departamento",
                50,
                "Argentina",
                "Buenos Aires",
                "Libertador 123",
                4,
                fotos,
                checkIn,
                checkOut,
                comentarios,
                ranking,
                formaPagoValidas,
                3000,
                eventos,
                politicaDeCancelacion
            );
    }
  
    @Test
    public void testGetPrecioTotal() {
        
        float precioTotal = inmueble.getPrecioTotal();
        
        Duration duracion = Duration.between(inmueble.getFechaCheckIn(), inmueble.getFechaCheckOut());
        
        // Obtener la cantidad de días
        long dias = duracion.toDays();
       
        float precioEsperado = 13 * 3000 + 100 + 30; 
        		//16 * 3000 + (2 * 50) + (1 * 30);

        // Verificar que el precio calculado es correcto
        assertEquals(precioEsperado, precioTotal, "El precio total calculado es incorrecto.");
    }
   
    @Test
    public void testInmuebleCreadoCorrectamente() {
        // Verificar si la instancia de Inmueble no es null
        assertNotNull(inmueble, "El inmueble debe haber sido creado correctamente.");
    }

    @Test
    public void testGetPrecioPorDia() {
        // Verificar si el precio por día se ha asignado correctamente
        assertEquals(3000, inmueble.getPrecioPorDia(), "El precio por día no coincide.");
    }
    
    @Test
    void testAddReservaActiva() {
        inmueble.addReservaActiva(reserva);
        assertTrue(inmueble.getReservasActivas().contains(reserva));
    }
    
    @Test
    void testRemoveReservaActiva() {
        inmueble.addReservaActiva(reserva);
        inmueble.removeReservaActiva(reserva);
        assertFalse(inmueble.getReservasActivas().contains(reserva));
    }
    
    @Test
    void testAddReservaPendiente() {
        inmueble.addReservaPendiente(reserva);
        assertTrue(inmueble.getReservasPendientes().contains(reserva));
    }
    
    @Test
    void testRemoveReservaPendiente() {
        inmueble.addReservaPendiente(reserva);
        inmueble.removeReservaPendiente(reserva);
        assertFalse(inmueble.getReservasPendientes().contains(reserva));
    }
    
    
    @Test
    void testHayReservaActivaEntre() {
        LocalDateTime checkInTest = LocalDateTime.of(2024, 1, 3, 10, 0);
        LocalDateTime checkOutTest = LocalDateTime.of(2024, 1, 5, 10, 0);
        Mockito.when(reserva.getCheckIn()).thenReturn(LocalDateTime.of(2024, 1, 1, 9, 0));
        Mockito.when(reserva.getCheckOut()).thenReturn(LocalDateTime.of(2024, 1, 6, 9, 0));
        inmueble.addReservaActiva(reserva);
        
        boolean resultado = inmueble.hayReservaActivaEntre(checkInTest, checkOutTest);
        assertTrue(resultado);
    }
    
    @Test
    void testHayReservaPendienteEntre() {
        LocalDateTime checkInTest = LocalDateTime.of(2024, 1, 3, 10, 0);
        LocalDateTime checkOutTest = LocalDateTime.of(2024, 1, 5, 10, 0);
        Mockito.when(reserva.getCheckIn()).thenReturn(LocalDateTime.of(2024, 1, 1, 9, 0));
        Mockito.when(reserva.getCheckOut()).thenReturn(LocalDateTime.of(2024, 1, 6, 9, 0));
        inmueble.addReservaPendiente(reserva);
        
        boolean resultado = inmueble.hayReservaPendienteEntre(checkInTest, checkOutTest);
        assertTrue(resultado);
    }
    
    @Test
    void testReservaPendienteEntre() {
        LocalDateTime checkInTest = LocalDateTime.of(2024, 1, 3, 10, 0);
        LocalDateTime checkOutTest = LocalDateTime.of(2024, 1, 5, 10, 0);
        Mockito.when(reserva.getCheckIn()).thenReturn(LocalDateTime.of(2024, 1, 1, 9, 0));
        Mockito.when(reserva.getCheckOut()).thenReturn(LocalDateTime.of(2024, 1, 6, 9, 0));
        inmueble.addReservaPendiente(reserva);
        
        List<Reserva> resultado = inmueble.reservaPendienteEntre(checkInTest, checkOutTest);
        assertFalse(resultado.isEmpty());
    }
    
    @Test
    void testEsReservado() {
        inmueble.addReservaActiva(reserva);
        
        assertTrue(inmueble.esReservado());
    }
    
    @Test
    void testIncrementarVecesAlquilado() {
        int vecesAntes = inmueble.getVecesAlquilado();
        inmueble.incrementarVecesAlquilado();
        assertEquals(vecesAntes + 1, inmueble.getVecesAlquilado());
    }
    
    @Test
    void testAñadirComentario() {
        String comentarioNuevo = "Medio pelo";
        inmueble.añadirComentario(comentarioNuevo);
        assertTrue(inmueble.getComentarios().contains(comentarioNuevo));
    }
    
    
    @Test
    public void testSetPrecioPorDia() {
        // Verificar el comportamiento del setter para precioPorDia.
        inmueble.setPrecioPorDia(90.0f);
        assertEquals(90.0f, inmueble.getPrecioPorDia());
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
    public void testNotificarCancelacionReserva() {
        // Crear y mockear el Observer
        IObserver mockObserver = Mockito.mock(IObserver.class);

        // Agregar el observador
        inmueble.attach(mockObserver);

        // Llamar al método que notifica la cancelación de la reserva
        inmueble.notificarCancelacionReserva();

        // Verificar que el método actuaSiCancelarReserva se haya llamado una vez
        Mockito.verify(mockObserver, Mockito.times(1)).actuaSiCancelarReserva(inmueble);
    }
    
    @Test
    public void testNotificarSeHaceReserva() {
        // Crear y mockear el Observer
        IObserver mockObserver = Mockito.mock(IObserver.class);

        // Agregar el observador
        inmueble.attach(mockObserver);

        // Llamar al método que notifica que se hizo una reserva
        inmueble.notificarSeHaceReserva();

        // Verificar que el método actuaSiSeReserva se haya llamado una vez
        Mockito.verify(mockObserver, Mockito.times(1)).actuaSiSeReserva();
    }
    

    @Test
    public void testGetTipoDeInmueble() {
        assertEquals("Departamento", inmueble.getTipoDeInmueble(), "El tipo de inmueble no coincide.");
    }

    @Test
    public void testGetSuperficie() {
        assertEquals(50, inmueble.getSuperficie(), "La superficie no coincide.");
    }

    @Test
    public void testGetPais() {
        assertEquals("Argentina", inmueble.getPais(), "El país no coincide.");
    }

    @Test
    public void testGetCiudad() {
        assertEquals("Buenos Aires", inmueble.getCiudad(), "La ciudad no coincide.");
    }

    @Test
    public void testGetDireccion() {
        assertEquals("Libertador 123", inmueble.getDireccion(), "La dirección no coincide.");
    }

    @Test
    public void testGetRanking() {
        assertEquals(ranking, inmueble.getRanking());
    }

    @Test
    public void testSetServicios() {
        List<Servicio> nuevosServicios = Arrays.asList(Servicio.GAS, Servicio.WIFI);
        inmueble.setServicios(nuevosServicios);
        assertEquals(nuevosServicios, inmueble.getServicios());
    }

    @Test
    public void testGetCapacidad() {
        assertEquals(4, inmueble.getCapacidad(), "La capacidad no coincide.");
    }

    @Test
    public void testGetFotos() {
        assertEquals(fotos, inmueble.getFotos(), "La lista de fotos no coincide.");
    }

    @Test
    public void testGetFechaCheckIn() {
        assertNotNull(inmueble.getFechaCheckIn(), "La fecha de check-in no debería ser null.");
    }

    @Test
    public void testGetFechaCheckOut() {
        assertNotNull(inmueble.getFechaCheckOut(), "La fecha de check-out no debería ser null.");
    }

    @Test
    public void testGetEventos() {
        assertEquals(eventos, inmueble.getEventos(), "La lista de eventos no coincide.");
    }

    @Test
    public void testGetPoliticaDeCancelacion() {
        assertEquals(politicaDeCancelacion, inmueble.getPoliticaDeCancelacion(), "La política de cancelación no coincide.");
    }

    @Test
    public void testGetPropietario() {
        assertEquals(propietario, inmueble.getPropietario());
    }
    
    @Test
    public void testGetFormasDePago() {
        // Crear la lista esperada de formas de pago
        List<FormaDePago> formasDePagoEsperadas = Arrays.asList(FormaDePago.EFECTIVO, FormaDePago.TARJETADEBITO);
        
        // Verificar que el método getFormasDePago devuelva la lista correcta
        assertEquals(formasDePagoEsperadas, inmueble.getFormasDePago(), "Las formas de pago no coinciden.");
    }
    
    @Test
    public void testSetPoliticaDeCancelacion() {
        // Establecer la política de cancelación
        inmueble.setPoliticaDeCancelacion(politicaDeCancelacion);
        
        // Verificar que la política de cancelación se haya establecido correctamente
        assertEquals(politicaDeCancelacion, inmueble.getPoliticaDeCancelacion(), "La política de cancelación no se estableció correctamente.");
    }
    
   
    
   
}
