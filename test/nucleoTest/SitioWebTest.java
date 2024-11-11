package nucleoTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import administrador.Configuracion;
import buscador.Buscador;
import nucleo.Sistema;
import nucleo.SitioWeb;
import observer.Inmueble;

class SitioWebTest {
	
	//DOC
	Sistema sistema 	 = mock(Sistema.class);		  // stub
	Configuracion config = mock(Configuracion.class); // stub
	Buscador buscador    = mock(Buscador.class); 	  // dummy
	Inmueble casa	     = mock(Inmueble.class);	  // stub
	Inmueble depto	     = mock(Inmueble.class);	  // stub
	Inmueble quincho     = mock(Inmueble.class);	  // stub
	
	//SUT
	SitioWeb sitioWeb;
	
	@BeforeEach
	void setUp() throws Exception {
		
		sitioWeb = new SitioWeb(sistema, buscador, config);
		
		when(config.getTipoDeInmuebles()).thenReturn("casa");
	}

	@Test
	void testPublicar() {
		sitioWeb.publicar(casa);
		
		
	}
	
	@Test
	void test() {
		fail("Not yet implemented");
	}
}
