package inmuebleYUsuarioTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import administrador.Categoria;
import inmuebleYUsuario.RankingUsuario;


class RankingUsuarioTest {

    // SUT (System Under Test)
    RankingUsuario rankingUsuario;

    // DOC
    Categoria categoriaInquilino1 = mock(Categoria.class);
    Categoria categoriaPropietario1 = mock(Categoria.class);

    @BeforeEach
    void setUp() throws Exception {

        when(categoriaInquilino1.getPuntaje()).thenReturn(List.of(5, 4, 4, 3, 5, 2));
        when(categoriaPropietario1.getPuntaje()).thenReturn(List.of(3, 3, 3, 4, 4, 5));

        rankingUsuario = new RankingUsuario(
            List.of(categoriaInquilino1),
            List.of(categoriaPropietario1)
        );
    }

    @Test
    void testPromedioTotal() {
        List<Categoria> todasLasCategorias = new ArrayList<>();
        todasLasCategorias.addAll(rankingUsuario.getCategoriasInquilino());
        todasLasCategorias.addAll(rankingUsuario.getCategoriasPropietario());

        // Calculamos el puntaje total promedio usando ambas listas de categorías
        double resultado = rankingUsuario.promedioTotal(todasLasCategorias);

        int sumaTotal = 5 + 4 + 4 + 3 + 5 + 2 + 3 + 3 + 3 + 4 + 4 + 5; 

        // El total de puntajes es 12 (6 del inquilino + 6 del propietario)
        double expected = sumaTotal / 12.0; 

        assertEquals(expected, resultado);
    }

}
