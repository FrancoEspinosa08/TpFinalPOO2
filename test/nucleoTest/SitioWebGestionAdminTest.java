package nucleoTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import administrador.Configuracion;
import buscador.Buscador;
import nucleo.Sistema;
import nucleo.SitioWeb;
import observer.Inmueble;
import observer.Usuario;

class SitioWebGestionAdminTest {

	//DOC
	Sistema sistema 	 = new Sistema();	 
	Configuracion config = mock(Configuracion.class); // stub
	Buscador buscador    = mock(Buscador.class); 	  // dummy
	
	Inmueble disponible   = mock(Inmueble.class);
	Inmueble NoDisponible = mock(Inmueble.class);
	
	Usuario u001  = mock(Usuario.class);
	Usuario u002  = mock(Usuario.class);
	Usuario u003  = mock(Usuario.class);
	Usuario u004  = mock(Usuario.class);
	Usuario u005  = mock(Usuario.class);
	Usuario u006  = mock(Usuario.class);
	Usuario u007  = mock(Usuario.class);
	Usuario u008  = mock(Usuario.class);
	Usuario u009  = mock(Usuario.class);
	Usuario u010  = mock(Usuario.class);
	Usuario u011  = mock(Usuario.class);
	
	
	//SUT
	SitioWeb sitioWeb  = new SitioWeb(sistema, buscador, config);
	
	/*
	public double tasaDeOcupacion(){ // inmuebles alquilados sobre total de inmuebles
		
		   long totalDeInmuebles 			= this.getSistema().getAltas().size();
		   long cantidadInmueblesAlquilados = this.getSistema().getAltas().stream().filter(inmueble -> inmueble.getEsReservado()).count();

		return (double) cantidadInmueblesAlquilados / totalDeInmuebles;

	}
	*/
	@BeforeEach
	void setUp() throws Exception {
		
		//VecesQueAlquilo TOP TEN
		when(u001.getVecesQueAlquilo()).thenReturn(1);
		when(u002.getVecesQueAlquilo()).thenReturn(2);
		when(u003.getVecesQueAlquilo()).thenReturn(3);
		when(u004.getVecesQueAlquilo()).thenReturn(4);
		when(u005.getVecesQueAlquilo()).thenReturn(5);
		when(u006.getVecesQueAlquilo()).thenReturn(6);
		when(u007.getVecesQueAlquilo()).thenReturn(7);
		when(u008.getVecesQueAlquilo()).thenReturn(8);
		when(u009.getVecesQueAlquilo()).thenReturn(9);
		when(u010.getVecesQueAlquilo()).thenReturn(10);
		when(u011.getVecesQueAlquilo()).thenReturn(11);
		
		//Registramos los usuarios en el sistema
		sistema.addUsuario(u001);
		sistema.addUsuario(u002);
		sistema.addUsuario(u003);
		sistema.addUsuario(u004);
		sistema.addUsuario(u005);
		sistema.addUsuario(u006);
		sistema.addUsuario(u007);
		sistema.addUsuario(u008);
		sistema.addUsuario(u009);
		sistema.addUsuario(u010);
		sistema.addUsuario(u011);
		
		//Damos de alta 2 inmuebles
		sistema.addAlta(NoDisponible);
		sistema.addAlta(disponible);
		
		when(NoDisponible.getEsReservado()).thenReturn(true);
		when(disponible.getEsReservado()).thenReturn(false);
	}
	////////////////////////////// test //////////////////////////
	
	
	@Test
	void testTasaDeOcupacion() {
		
		Inmueble NoDisponible01   = mock(Inmueble.class);
		when(NoDisponible01.getEsReservado()).thenReturn(true);
		
		Inmueble NoDisponible02   = mock(Inmueble.class);
		when(NoDisponible02.getEsReservado()).thenReturn(true);
		
		sistema.addAlta(NoDisponible01);
		sistema.addAlta(NoDisponible02);
		
		assertEquals(0.75, sitioWeb.tasaDeOcupacion());
			
	}

	
	@Test
	void testTodosLosInmueblesLibresDelSistema() {
		
		List<Inmueble> lista = Arrays.asList(disponible);
		
		assertEquals(lista, sitioWeb.obtenerTodosLosInmueblesLibres());
			
	}

	
	@Test
	void testTopTenVecesQueAlquilaron() {
			
		assertEquals(10, sitioWeb.topTenUsuariosConMasAlquileres().size());
			
	}

}
