package inmuebleYUsuarioTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inmuebleYUsuario.Comentario;

class ComentarioTest {
	
	List<String> coms     = new ArrayList<String>();
			
		
	
	Comentario comentario = new Comentario(coms);
	
	
	@BeforeEach
	void setUp() throws Exception {
		
		//Añadimos los comentarios a la lista
		coms.add("Comentario1");
		coms.add("Comentario2");
		coms.add("Comentario3");
		
		
	}

	@Test
	void testRemoverComentario() {
		
		
		comentario.removeComentario("Comentario1");
		
		List<String> resultado = List.of("Comentario2", "Comentario3");
		
		assertEquals(resultado, comentario.getComentarios());
	}
	
	@Test
	void testAddComentario() {
		
		String miComentario = "Hola";
		
		comentario.addComentario(miComentario);
		
		List<String> resultado = List.of("Comentario1", "Comentario2", "Comentario3", "Hola");
		
		assertEquals(resultado, comentario.getComentarios());
	}
	
	@Test
	void testGetComentario() {
		assertEquals(coms, comentario.getComentarios());
	}

}
