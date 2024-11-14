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
		
		//Inmuebles
		when(casa.getInquilinoActivo()).thenReturn(Ivan);
		when(depto.getInquilinoActivo()).thenReturn(Ivan);
		when(quincho.getInquilinoActivo()).thenReturn(Franco);
	
		when(casa.getFechaCheckIn()).thenReturn(fechaAnterior);
		when(depto.getFechaCheckIn()).thenReturn(fechaPosterior);
		//when(quincho.getFechaCheckIn()).thenReturn(fechaPosterior);
		
		when(casa.getCiudad()).thenReturn("BuenosAires");
		when(depto.getCiudad()).thenReturn("SantaFe");
		when(quincho.getCiudad()).thenReturn("Caracas");
		
		
	}

	@Test
	void testObtenerTodasLasReservasDeUsuario() {	
		assertEquals(List.of(casa,depto),sitioWeb.obtenerTodasLasReservasDe(Ivan));
	
	}
	
	@Test
	void testObtenerReservasFuturasDeUsuario() {	
		assertEquals(List.of(depto),sitioWeb.obtenerReservasFuturasDe(Ivan));
	
	}
	
	@Test
	void testEsFechaAnterior() {	
		assertEquals(true,sitioWeb.esFechaAnterior(fechaAnterior, fechaPosterior));
	
	}
	
	
	@Test
	void testObtenerReservasEnCiudad() {	
		assertEquals(List.of(depto),sitioWeb.obtenerReservasEnCiudad(Ivan,"SantaFe"));
	
	}
	
	
	@Test
	void testObtenerCiudadesConReservaDe() {	
		assertEquals(List.of("BuenosAires", "SantaFe"),sitioWeb.obtenerCiudadesConReservaDe(Ivan));
	
	}
}
