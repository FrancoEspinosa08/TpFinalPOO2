package inmuebleYUsuarioTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import administrador.Categoria;
import administrador.CategoriaInmuebleEstado;
import administrador.CategoriaInmueblePrecio;
import administrador.CategoriaInmuebleUbicacion;
import inmuebleYUsuario.RankingInmueble;

class RankingInmuebleTest {

    // SUT (System Under Test)
    RankingInmueble ranking;

    @BeforeEach
    void setUp() throws Exception {
        // Inicializamos las categorías de prueba con puntajes simulados, EN ESTE CASO NO USO MOCKITO PORQUE LAS CATEGORIAS TIENEN UNA
    	// IMPLEMENTACION SENCILLA.
        List<Categoria> categorias = List.of(
            new CategoriaInmuebleEstado(List.of(5, 4, 4, 3, 5, 2)),
            new CategoriaInmueblePrecio(List.of(4, 4, 3, 3, 4, 5)),
            new CategoriaInmuebleUbicacion(List.of(3, 3, 3, 4, 4, 5))
        );

        // Creamos un objeto de RankingInmueble
        ranking = new RankingInmueble(categorias);
    }

    @Test
    void testPromedioTotal() {
        // Calculamos el puntaje total promedio
        double resultado = ranking.promedioTotal(ranking.getCategorias());

        int sumaTotal = 5 + 4 + 4 + 3 + 5 + 2 + 4 + 4 + 3 + 3 + 4 + 5 + 3 + 3 + 3 + 4 + 4 + 5;
        
        // El total de puntajes es 18
        double expected = sumaTotal / 18.0; // 18 es el total de puntajes

        // Verificamos que el puntaje promedio calculado sea correcto
        assertEquals(expected, resultado);  
    }

    @Test
    void testSetCategorias() {
        // Lista de nuevas categorías para probar el setter
        List<Categoria> nuevasCategorias = List.of(
            new CategoriaInmuebleEstado(List.of(1, 1, 1)),
            new CategoriaInmueblePrecio(List.of(2, 2, 2)),
            new CategoriaInmuebleUbicacion(List.of(3, 3, 3))
        );

        // Asignamos nuevas categorías al ranking
        ranking.setCategorias(nuevasCategorias);

        // Verificamos que las categorías hayan sido actualizadas correctamente
        assertEquals(nuevasCategorias, ranking.getCategorias());
    }
}
