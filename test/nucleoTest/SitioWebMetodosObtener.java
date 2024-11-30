package nucleoTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import administrador.Configuracion;
import buscador.Buscador;
import nucleo.Sistema;
import nucleo.SitioWeb;
import observer.Inmueble;
import observer.Reserva;
import observer.Usuario;

class SitioWebMetodosObtener {
	
	//DOC
	Sistema sistema      = new Sistema();
	Configuracion config = mock(Configuracion.class); // stub
	Buscador buscador    = mock(Buscador.class); 	  // dummy
	
	Usuario Ivan = mock(Usuario.class);
	Usuario Franco = mock(Usuario.class);
	
	Inmueble casa    = mock(Inmueble.class);
	Inmueble depto   = mock(Inmueble.class);
	Inmueble quincho = mock(Inmueble.class);
	
	Reserva reservaCasa    = mock(Reserva.class); // De Ivan
	Reserva reservaDepto   = mock(Reserva.class); // De Ivan
	Reserva reservaQuincho = mock(Reserva.class); // De Franco
	
	LocalDateTime fechaAnterior  = LocalDateTime.of(2023, 11, 12, 10, 00); // Año, mes, día, hora, minuto
    LocalDateTime fechaPosterior = LocalDateTime.of(2025, 11, 12, 10, 00); // Año, mes, día, hora, minuto
	
    //SUT
	SitioWeb sitioWeb = new SitioWeb(sistema, buscador, config);
	
	@BeforeEach
	void setUp() throws Exception {
		
		//Sistema
		sistema.addAlta(casa);
		sistema.addAlta(depto);
		sistema.addAlta(quincho);
		
		//Reservas
		when(reservaCasa.getInquilino()).thenReturn(Ivan);
		when(reservaDepto.getInquilino()).thenReturn(Ivan);
		when(reservaQuincho.getInquilino()).thenReturn(Franco);
		
		when(reservaCasa.getInmueble()).thenReturn(casa);
		when(reservaDepto.getInmueble()).thenReturn(depto);
		when(reservaQuincho.getInmueble()).thenReturn(quincho);
		
		when(reservaCasa.getCheckIn()).thenReturn(fechaAnterior);
		when(reservaDepto.getCheckIn()).thenReturn(fechaPosterior);
		
		//Inmuebles
		
		
		when(casa.getReservasActivas()).thenReturn(List.of(reservaCasa));
		when(depto.getReservasActivas()).thenReturn(List.of(reservaDepto));
		when(quincho.getReservasActivas()).thenReturn(List.of(reservaQuincho));
		
		when(casa.getCiudad()).thenReturn("BuenosAires");
		when(depto.getCiudad()).thenReturn("SantaFe");
		when(quincho.getCiudad()).thenReturn("Caracas");
		
		
	}

	@Test
	void testObtenerTodasLasReservasDeUsuario() {	
		assertEquals(List.of(reservaCasa,reservaDepto),sitioWeb.obtenerTodasLasReservasDe(Ivan));
	
	}
	
	@Test
	void testObtenerReservasFuturasDeUsuario() {	
		assertEquals(List.of(reservaDepto),sitioWeb.obtenerReservasFuturasDe(Ivan));
	
	}
	
	
	@Test
	void testObtenerReservasEnCiudad() {	
		assertEquals(List.of(reservaDepto),sitioWeb.obtenerReservasEnCiudad(Ivan,"SantaFe"));
	
	}
	
	
	@Test
	void testObtenerCiudadesConReservaDe() {	
		assertEquals(List.of("BuenosAires", "SantaFe"),sitioWeb.obtenerCiudadesConReservaDe(Ivan));
	
	}
}
