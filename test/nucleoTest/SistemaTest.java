package nucleoTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import administrador.CategoriaInmuebleEstado;
import administrador.CategoriaInquilinoCuidado;
import administrador.CategoriaPropietarioAtencion;
import administrador.Categoria;
import inmuebleYUsuario.FormaDePago;
import inmuebleYUsuario.RankingInmueble;
import inmuebleYUsuario.RankingUsuario;
import nucleo.Sistema;
import observer.Inmueble;
import observer.Reserva;
import observer.Usuario;
import politicasDeCancelacion.CancelacionGratuita;
import politicasDeCancelacion.PoliticaDeCancelacion;

class SistemaTest {
    // DOC 
    Usuario propietario1		     = mock(Usuario.class);
    Usuario propietario2 		     = mock(Usuario.class);
    Usuario inquilino1 				 = mock(Usuario.class);
    Usuario inquilino2 				 = mock(Usuario.class);
    
    RankingInmueble rankingInmueble1 = mock(RankingInmueble.class);
    RankingInmueble rankingInmueble2 = mock(RankingInmueble.class);
    RankingUsuario rankingUsuario 	 = mock(RankingUsuario.class);
    RankingUsuario rankingInquilino  = mock(RankingUsuario.class);
    
    List<Integer> puntajes     = Arrays.asList(0);
    List<Categoria> categorias = Arrays.asList(new CategoriaInmuebleEstado(puntajes));
    
    //PARAMETROS Inmueble1
    List<String> fotos     	 = Arrays.asList("Foto1","Foto2");
    List<String> comentarios = new ArrayList<String>();
	LocalDateTime checkIn  	 = LocalDateTime.of(2024, 11, 12, 10, 00); // Año, mes, día, hora, minuto
	LocalDateTime checkOut 	 = LocalDateTime.of(2024, 12, 12, 10, 00); // Año, mes, día, hora, minuto
	RankingInmueble rankingInmueble = new RankingInmueble(categorias);
	List<FormaDePago> formaPagoValidas = Arrays.asList(FormaDePago.values()); //Las tres formas de pago son validas
	PoliticaDeCancelacion politicaCancel = new CancelacionGratuita();
    
    Inmueble inmueble1 = new Inmueble(	propietario1,   //Usuario propietario
										0,				//int vecesAlquilado
										"casa",			//String tipoDeInmueble
										200,			//int superficie
										"Argentina",	//String pais
										"BsAs",			//String ciudad
										"6474",			//String direccion
										10,				//int capacidad
										fotos,			//List<String> fotos
										checkIn,		//LocalDateTime horarioCheckIn
										checkOut,		//LocalDateTime horarioCheckOut
										comentarios,			//List<String> comentarios
										rankingInmueble,//Ranking ranking
										formaPagoValidas,//List<FormaDePago> formaPagoValidas
										1000F,			//float precioPorDia
										null,			//List<Evento> eventos
										politicaCancel);//PoliticaDeCancelacion politicaDeCancelacion
    
  //PARAMETROS Inmueble2
 
	LocalDateTime checkIn2  = LocalDateTime.of(2024, 11, 12, 10, 00); // Año, mes, día, hora, minuto
	LocalDateTime checkOut2 = LocalDateTime.of(2025, 12, 12, 10, 00); // Año, mes, día, hora, minuto

    Inmueble inmueble2 = new Inmueble(	propietario2,   //Usuario propietario
										0,				//int vecesAlquilado
										"depto",		//String tipoDeInmueble
										200,			//int superficie
										"Argentina",	//String pais
										"BsAs",			//String ciudad
										"6474",			//String direccion
										10,				//int capacidad
										fotos,			//List<String> fotos
										checkIn2,		//LocalDateTime horarioCheckIn
										checkOut2,		//LocalDateTime horarioCheckOut
										comentarios,	//List<String> comentarios
										rankingInmueble,//Ranking ranking
										formaPagoValidas,//List<FormaDePago> formaPagoValidas
										1000F,			//float precioPorDia
										null,			//List<Evento> eventos
										politicaCancel);//PoliticaDeCancelacion politicaDeCancelacion
    
    
    Reserva reservaI1 = mock(Reserva.class);
    Reserva reservaI2 = mock(Reserva.class);
    
    // SUT
    Sistema sistema;
    LocalDateTime fechaFija;
    
    
    @BeforeEach
    void setUp() {
    	
    	CategoriaInmuebleEstado categoria1 = new CategoriaInmuebleEstado(List.of(1,2));
    	CategoriaInmuebleEstado categoria2 = new CategoriaInmuebleEstado(List.of(1,2));
    	
    	CategoriaPropietarioAtencion categoriaPropietario = new CategoriaPropietarioAtencion(List.of(1,2));
    	CategoriaInquilinoCuidado categoriaInquilino = new CategoriaInquilinoCuidado(List.of(1,2));
    	
    	
    	
        sistema = new Sistema();
        fechaFija = LocalDateTime.of(2023, 11, 13, 12, 0);
        sistema.setFechaActual(fechaFija);
        
        
     
        when(rankingInmueble1.getCategorias()).thenReturn(List.of(categoria1, categoria2));
        when(rankingInmueble2.getCategorias()).thenReturn(List.of(categoria1, categoria2));
        
        when(inquilino1.puntuar()).thenReturn(4);
        when(inquilino2.puntuar()).thenReturn(3);
        
        when(inquilino1.getRanking()).thenReturn(rankingUsuario);
        when(inquilino2.getRanking()).thenReturn(rankingUsuario);

        
        when(propietario1.getRanking()).thenReturn(rankingUsuario);
        when(propietario2.getRanking()).thenReturn(rankingUsuario);
        
        when(rankingUsuario.getCategoriasPropietario()).thenReturn(List.of(categoriaPropietario));
        
        when(rankingInquilino.getCategoriasInquilino()).thenReturn(List.of(categoriaInquilino));
        
        when(propietario1.puntuar()).thenReturn(3);
        when(propietario2.puntuar()).thenReturn(3);
        
        when(inquilino1.generarComentario()).thenReturn("comentario");
        when(inquilino2.generarComentario()).thenReturn("comentario");
        

        
        when(propietario1.getInmuebles()).thenReturn(List.of(inmueble1, inmueble2));
        when(propietario2.getInmuebles()).thenReturn(List.of(inmueble1, inmueble2));
        
  
        
        doNothing().when(inquilino1).incrementarVecesQueAlquilo();
        doNothing().when(inquilino2).incrementarVecesQueAlquilo();
        
        
        inmueble1.addReservaActiva(reservaI1);
        inmueble2.addReservaActiva(reservaI2);
        
        when(reservaI1.getCheckOut()).thenReturn(fechaFija);
        when(reservaI2.getCheckOut()).thenReturn(fechaFija.plusDays(1)); // Check-out al día siguiente
       
        when(reservaI1.getInmueble()).thenReturn(inmueble1);
        when(reservaI2.getInmueble()).thenReturn(inmueble2);
        
        when(reservaI1.getInquilino()).thenReturn(inquilino1);
        when(reservaI2.getInquilino()).thenReturn(inquilino2);
        
        sistema.addAlta(inmueble1);
        sistema.addAlta(inmueble2);
    }
    
    @Test
    void testActualizar() {
        sistema.Actualizar();

        assertTrue(inmueble1.getReservasActivas().isEmpty(), "Debería eliminarse la reservaI1 de inmueble1");
        assertTrue(!inmueble2.getReservasActivas().isEmpty(), "Debería permanecer la reservaI2 de inmueble2");
    }
}

