package nucleoTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import administrador.Categoria;
import administrador.Configuracion;
import administrador.Servicio;
import buscador.Buscador;
import inmuebleYUsuario.Comentario;
import inmuebleYUsuario.Email;
import inmuebleYUsuario.Evento;
import inmuebleYUsuario.FormaDePago;
import inmuebleYUsuario.Ranking;
import inmuebleYUsuario.RankingInmueble;
import inmuebleYUsuario.RankingUsuario;
import nucleo.Sistema;
import nucleo.SitioWeb;
import observer.AppMobile;
import observer.Inmueble;
import observer.Usuario;
import politicasDeCancelacion.CancelacionGratuita;
import politicasDeCancelacion.PoliticaDeCancelacion;

class SitioWebTest {
	
	//DOC
	Sistema sistema 	 = mock(Sistema.class);		  // stub
	Configuracion config = mock(Configuracion.class); // stub
	Buscador buscador    = mock(Buscador.class); 	  // dummy
	Inmueble casa	     = mock(Inmueble.class);	  // stub
	Inmueble depto	     = mock(Inmueble.class);	  // stub
	Inmueble quincho     = mock(Inmueble.class);	  // stub
	
	
	//Inmueble "real"
	/*  Usuario propietario, 
	 * Usuario inquilinoActivo, 
	 * int vecesAlquilado, 
	 * String tipoDeInmueble,
	 *  int superficie,
	   String pais,
	   String ciudad, 
	   String direccion,
	   int capacidad,
	   List<String> fotos,
	   LocalDateTime horarioCheckIn,
	   LocalDateTime horarioCheckOut,
	   FormaDePago formaDePago,
	   List<String> comentarios,
	   Ranking ranking, 
	   List<FormaDePago> formaPagoValidas,
	   float precioPorDia,
	   boolean esReservado,
	   List<Evento> eventos,
	   PoliticaDeCancelacion politicaDeCancelacion*/
	//
	
	
	
	/*Atributos de InmuebleReal*/
	List<String> fotos     = Arrays.asList("Foto1","Foto2");
	LocalDateTime checkIn  = LocalDateTime.of(2024, 11, 12, 10, 00); // Año, mes, día, hora, minuto
	LocalDateTime checkOut = LocalDateTime.of(2024, 12, 12, 10, 00); // Año, mes, día, hora, minuto
	RankingInmueble rankingInmueble = mock(RankingInmueble.class);
	List<FormaDePago> formaPagoValidas = Arrays.asList(FormaDePago.values()); //Las tres formas de pago son validas
	PoliticaDeCancelacion politicaCancel = new CancelacionGratuita();
	
	

	
	/* Atributos de USUARIO --> IVAN*/
	Email email = new Email();
	RankingUsuario ranking = mock(RankingUsuario.class);
	List<Inmueble> inmuebles = Arrays.asList(depto); //No puede ser null, da error.
	Comentario comentario  = mock(Comentario.class);
	AppMobile appMobile  = new AppMobile();
	
	Usuario  Ivan	     = new Usuario("Ivan",
										"123",
										email,
										LocalDateTime.now(),
										ranking,
										inmuebles,
										comentario,
										0,
										appMobile);
	
	Usuario  Franco      = mock(Usuario.class);	//stub
	Usuario  Agustin     = mock(Usuario.class);	//stub
	
	List<String> listaInmueblesValidos = Arrays.asList("casa", "departamento");
	List<Servicio> serviciosValidos    = Arrays.asList(Servicio.AGUA, Servicio.CALEFACCION, Servicio.ELECTRICIDAD);
	
	List<Servicio> serviciosCasa    	  = Arrays.asList(Servicio.AGUA, Servicio.CALEFACCION);
	List<Servicio> serviciosDepto   	  = Arrays.asList(Servicio.ELECTRICIDAD);
	List<Servicio> serviciosQuincho 	  = Arrays.asList(Servicio.GAS); //Servicio invalido
	
	List<Inmueble> listaInmueblesBuscador = Arrays.asList(casa, depto);
	
	Inmueble inmuebleReal = new Inmueble(Franco, null, 0, "casa",200,
				"Argentina","BsAs","6474", 10, fotos, 
				checkIn,checkOut, null , null,
				rankingInmueble, formaPagoValidas, 1000F,
				false, null, politicaCancel);
	
	
	
	List<Inmueble> inmueblesBuscador 	  = Arrays.asList(inmuebleReal); //Para reservar()
	
	
	
	
	//SUT
	SitioWeb sitioWeb;
	
	@BeforeEach
	void setUp() throws Exception {
		
		sitioWeb = new SitioWeb(sistema, buscador, config);
		
		
		
		//Comportamientos predefinidos
		//Configuracion
		when(config.getTipoInmuebles()).thenReturn(listaInmueblesValidos);
		when(config.getServiciosValidos()).thenReturn(serviciosValidos);
		//Inmuebles
		when(casa.getTipoDeInmueble()).thenReturn("casa");
		when(casa.getServicios()).thenReturn(serviciosCasa);
		
		when(depto.getTipoDeInmueble()).thenReturn("departamento");
		when(depto.getServicios()).thenReturn(serviciosDepto);
		
		when(quincho.getTipoDeInmueble()).thenReturn("quincho");
		when(quincho.getServicios()).thenReturn(serviciosQuincho);
		
		//Buscador
		when(buscador.getResultadoBusqueda()).thenReturn(listaInmueblesBuscador);
		
		//Estado de reservas 
		when(casa.getEsReservado()).thenReturn(false); // Casa es libre
		when(depto.getEsReservado()).thenReturn(true); // Depto tiene reserva
		
		//Propietarios de inmuebles
		when(casa.getPropietario()).thenReturn(Franco);
		when(depto.getPropietario()).thenReturn(Agustin);
		
		//Configuracion inmuebleReal
		inmuebleReal.attach(Agustin); //Agustin sera observer
		inmuebleReal.setServicios(serviciosCasa);
		
		doNothing().when(Agustin).actuaSiSeReserva(); //El observer no hace nada al ser notificado
	}
	
	/*@Test
	void testCancelarReserva() {
		
		Inmueble inmueble = new Inmueble(Ivan, null, 0, "casa",200,
										"Argentina","BsAs","6474", 10, fotos, 
										checkIn,checkOut, null , null,
										rankingInmueble, formaPagoValidas, 1000F,
										false, null, politicaCancel);
		
		inmueble.attach(Franco);
		inmueble.attach(Agustin);
		
		doNothing().when(Franco).actuaSiCancelarReserva(inmueble);
		doNothing().when(Agustin).actuaSiCancelarReserva(inmueble);
		
		sitioWeb.cancelarReserva(inmueble , LocalDateTime.now());
		
		
		assertEquals("Se ha cancelado la reserva!" , Ivan.getEmail().getInbox());
		assertEquals(inmueble ,Ivan.getEmail().getAttachment());
		
		
	}*/
	
	
	@Test
	void testRegistrarUsuario() {
		
		List<Categoria> categorias = new ArrayList<>(); //Lista vacia de categorias que no voy a usar. "dummy"
		
		when(sistema.getFechaActual()).thenReturn(LocalDateTime.now());
		when(config.getCategoriasInquilinos()).thenReturn(categorias);
		when(config.getCategoriasPropietario()).thenReturn(categorias);
		
		
		sitioWeb.registrar(Ivan);
		
		verify(sistema).addUsuario(Ivan);
	}
	
	
	@Test
	void testEncolarUsuarioSiElInmuebleEstaReservado() {
		
		//Seteamos el buscador para que tenga el inmuebleReal
		when(casa.getEsReservado()).thenReturn(true);
		
		sitioWeb.reservar(Ivan, casa); //reservar(Usuario usuario, Inmueble inmueble)
		
		verify(casa).encolarUsuario(Ivan);
	}
	
	@Test
	void testNoDebeReservarInmuebleRealCasaAPartirDelBuscador() {
		
		//Seteamos el buscador para que tenga el inmuebleReal
		when(buscador.getResultadoBusqueda()).thenReturn(inmueblesBuscador);
		
		when(Franco.decidirSiReserva()).thenReturn(false);
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			sitioWeb.reservar(Ivan, 0);  // Llama al método reservar y debe dar una excepción
        });
		
		
		assertEquals("El propietario ha decidido no aprobar la reserva", exception.getMessage());
		
	}
	
	@Test
	void testReservarInmuebleRealCasaAPartirDelBuscador() {
		
		//Seteamos el buscador para que tenga el inmuebleReal
		when(buscador.getResultadoBusqueda()).thenReturn(inmueblesBuscador);
		
		when(Franco.decidirSiReserva()).thenReturn(true);
		
		sitioWeb.reservar(Ivan, 0);
		
		assertEquals("Su reserva fue aprobada!", Ivan.getEmail().getInbox() );	
	}
	
	
	
	
	@Test
	void testLosServiciosSonValidos() {
		
		assertEquals(true, sitioWeb.tieneServiciosValidos(depto));
			
	}
	
	
	@Test
	void testLosServiciosSonInvalidos() {
		
		assertEquals(false, sitioWeb.tieneServiciosValidos(quincho));
			
	}
	
	@Test
	void testLosInmueblesSonValidos() {
		
		assertEquals(true, sitioWeb.esTipoDeInmuebleValido(casa));
			
	}
	
	@Test
	void testLosInmueblesSonInvalidos() {
		
		assertEquals(false, sitioWeb.esTipoDeInmuebleValido(quincho));
			
	}
	
	@Test
	void testPublicarCasa() {
		
		sitioWeb.publicar(casa);
		//Testear que se haya llamado al metodo sistema.addAlta(casa);
		verify(sistema).addAlta(casa);
		
	}
	
	@Test
	void testPublicarDepto() {
		
		sitioWeb.publicar(casa);
		sitioWeb.publicar(depto);
		//Testear que se haya llamado al metodo sistema.addAlta(depto);
		verify(sistema).addAlta(depto);
		
	}
	
	@Test
	void testNoDebePublicarQuincho() {
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			sitioWeb.publicar(quincho);  // Llama al método publicar con el parámetro que debe provocar la excepción
        });
		
		
		assertEquals("El inmueble que se quiere publicar es invalido", exception.getMessage());
		
	}
	
	
}
