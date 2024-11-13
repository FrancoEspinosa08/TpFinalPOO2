package administradorTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import administrador.CategoriaPropietarioAtencion;
import administrador.CategoriaPropietarioComunicacion;
import administrador.CategoriaPropietarioDisponibilidad;

class CategoriaPropietarioTest {
    
	// SUT
	CategoriaPropietarioAtencion categoria;
	CategoriaPropietarioComunicacion categoria2;
	CategoriaPropietarioDisponibilidad categoria3;

	@BeforeEach
	void setUp() throws Exception {
	// Inicializamos la categoría con una lista de puntajes simulada
		List<Integer> puntajes = List.of(5, 4, 4, 3, 5, 2);
	    categoria = new CategoriaPropietarioAtencion(puntajes);
	    categoria2 = new CategoriaPropietarioComunicacion(puntajes);
	    categoria3 = new CategoriaPropietarioDisponibilidad(puntajes);
	}

	@Test
	void testPromedio() {
	    double resultado = categoria.promedio();

	    double expected = (5 + 4 + 4 + 3 + 5 + 2) / 6.0;

	    // Verificamos que el promedio calculado sea correcto
	    assertEquals(expected, resultado);
	}

	@Test
	void testCantidadQuePuntuaronCon() {
	    // Llamamos al método cantidadQuePuntuaronCon con el puntaje 5
	    int resultado = categoria.cantidadQuePuntuaronCon(5);

	    // Verificamos que el puntaje 5 haya sido dado dos veces en la lista
	    assertEquals(2, resultado);

	    // Llamamos al método cantidadQuePuntuaronCon con el puntaje 3
	    resultado = categoria.cantidadQuePuntuaronCon(3);

	    // Verificamos que el puntaje 3 haya sido dado una vez en la lista
	    assertEquals(1, resultado);

	    // Llamamos al método cantidadQuePuntuaronCon con el puntaje 1
	    resultado = categoria.cantidadQuePuntuaronCon(1);

	    // Verificamos que no haya puntuaciones con el puntaje 1 en la lista
	    assertEquals(0, resultado);
	 }

	 @Test
	 void testAddPuntaje() {
	     // Añadimos un nuevo puntaje a la lista de puntajes
	     categoria.addPuntaje(4);

	     // Verificamos que el tamaño de la lista haya aumentado
	     assertEquals(7, categoria.getPuntaje().size());

	 }
	 
	 @Test
	 void testPromedioCategoria2() {
	     double resultado = categoria2.promedio();
	     double expected = (5 + 4 + 4 + 3 + 5 + 2) / 6.0;
	     assertEquals(expected, resultado);
	 }

	 @Test
	 void testCantidadQuePuntuaronConCategoria2() {
	    int resultado = categoria2.cantidadQuePuntuaronCon(5);
	    assertEquals(2, resultado);
	        
	    resultado = categoria2.cantidadQuePuntuaronCon(3);
	    assertEquals(1, resultado);
	        
	    resultado = categoria2.cantidadQuePuntuaronCon(1);
	    assertEquals(0, resultado);
	 }

	 @Test
	 void testAddPuntajeCategoria2() {
	     categoria2.addPuntaje(4);
	     assertEquals(7, categoria2.getPuntaje().size());
	 }

	 @Test
	 void testPromedioCategoria3() {
	     double resultado = categoria3.promedio();
	     double expected = (5 + 4 + 4 + 3 + 5 + 2) / 6.0;
	     assertEquals(expected, resultado);
	 }

	 @Test
	 void testCantidadQuePuntuaronConCategoria3() {
	     int resultado = categoria3.cantidadQuePuntuaronCon(5);
	     assertEquals(2, resultado);
	        
	     resultado = categoria3.cantidadQuePuntuaronCon(3);
	     assertEquals(1, resultado);
	        
	     resultado = categoria3.cantidadQuePuntuaronCon(1);
	     assertEquals(0, resultado);
	 }

	 @Test
	 void testAddPuntajeCategoria3() {
	     categoria3.addPuntaje(4);
	     assertEquals(7, categoria3.getPuntaje().size());
	 }
}
