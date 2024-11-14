package nucleoTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import administrador.CategoriaInmuebleEstado;
import administrador.CategoriaInquilinoCuidado;
import administrador.CategoriaPropietarioAtencion;
import inmuebleYUsuario.RankingInmueble;
import inmuebleYUsuario.RankingUsuario;
import nucleo.Sistema;
import observer.Inmueble;
import observer.Usuario;

class SistemaTest {
    // DOC 
    Usuario propietario1 = mock(Usuario.class);
    Usuario propietario2 = mock(Usuario.class);
    Usuario inquilino1 = mock(Usuario.class);
    Usuario inquilino2 = mock(Usuario.class);
    Inmueble inmueble1 = mock(Inmueble.class);
    Inmueble inmueble2 = mock(Inmueble.class);
    RankingInmueble rankingInmueble1 = mock(RankingInmueble.class);
    RankingInmueble rankingInmueble2 = mock(RankingInmueble.class);
    RankingUsuario rankingUsuario = mock(RankingUsuario.class);
    RankingUsuario rankingInquilino = mock(RankingUsuario.class);
    
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
        
        
        when(inmueble1.getFechaCheckOut()).thenReturn(fechaFija);
        when(inmueble1.getEsReservado()).thenReturn(true);
        
        when(inmueble1.getRanking()).thenReturn(rankingInmueble1);
        when(inmueble2.getRanking()).thenReturn(rankingInmueble2);
        
        when(rankingInmueble1.getCategorias()).thenReturn(List.of(categoria1, categoria2));
        when(rankingInmueble2.getCategorias()).thenReturn(List.of(categoria1, categoria2));
        
        when(inmueble1.getInquilinoActivo()).thenReturn(inquilino1);
        when(inmueble2.getInquilinoActivo()).thenReturn(inquilino2);
        
        when(inquilino1.puntuar()).thenReturn(4);
        when(inquilino2.puntuar()).thenReturn(3);
        
        when(inquilino1.getRanking()).thenReturn(rankingUsuario);
        when(inquilino2.getRanking()).thenReturn(rankingUsuario);
        
        when(inmueble1.getPropietario()).thenReturn(propietario1);
        when(inmueble2.getPropietario()).thenReturn(propietario2);
        
        when(propietario1.getRanking()).thenReturn(rankingUsuario);
        when(propietario2.getRanking()).thenReturn(rankingUsuario);
        
        when(rankingUsuario.getCategoriasPropietario()).thenReturn(List.of(categoriaPropietario));
        
        when(rankingInquilino.getCategoriasInquilino()).thenReturn(List.of(categoriaInquilino));
        
        when(propietario1.puntuar()).thenReturn(3);
        when(propietario2.puntuar()).thenReturn(3);
        
        when(inquilino1.generarComentario()).thenReturn("comentario");
        when(inquilino2.generarComentario()).thenReturn("comentario");
        
        when(inmueble2.getFechaCheckOut()).thenReturn(fechaFija.plusDays(1)); // Check-out al día siguiente
        when(inmueble2.getEsReservado()).thenReturn(true);
        
        doNothing().when(inmueble1).añadirComentario("comentario");
        doNothing().when(inmueble2).añadirComentario("comentario");
        
        when(propietario1.getInmuebles()).thenReturn(List.of(inmueble1, inmueble2));
        when(propietario2.getInmuebles()).thenReturn(List.of(inmueble1, inmueble2));
        
        doNothing().when(inmueble1).incrementarVecesAlquilado();
        doNothing().when(inmueble2).incrementarVecesAlquilado();
        
        doNothing().when(inquilino1).incrementarVecesQueAlquilo();
        doNothing().when(inquilino2).incrementarVecesQueAlquilo();
        
        sistema.addAlta(inmueble1);
        sistema.addAlta(inmueble2);
    }
    
    @Test
    void testActualizar() {
        sistema.Actualizar();

        List<Inmueble> altasEsperadas = List.of(inmueble2);
        assertEquals(altasEsperadas, sistema.getAltas(), "Debería eliminarse inmueble1 y permanecer inmueble2");
    }
}

