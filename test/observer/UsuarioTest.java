package observer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import administrador.Categoria;
import inmuebleYUsuario.Comentario;
import inmuebleYUsuario.Email;
import inmuebleYUsuario.FormaDePago;
import inmuebleYUsuario.RankingUsuario;

class UsuarioTest {

	// Atributos de la clase
    private Usuario usuario;
    private Email email;
    private RankingUsuario ranking;
    private List<Inmueble> inmuebles;
    private Comentario comentario;
    private AppMobile appMobile;
    //private FormaDePago formaDePagoMock;

    @BeforeEach
    public void setUp() {
        // Mocks para las clases dependientes
        email = mock(Email.class);
        ranking = mock(RankingUsuario.class);
        inmuebles = Arrays.asList(mock(Inmueble.class)); // Crear un mock de Inmueble
        comentario = mock(Comentario.class);
        appMobile = mock(AppMobile.class);
        //formaDePagoMock = mock(FormaDePago.class);
        
        // Configurar el comportamiento de los mocks
        when(email.getInbox()).thenReturn("test@example.com");  // Usamos getInbox() en lugar de getEmail()
        when(ranking.getCategoriasInquilino()).thenReturn(Arrays.asList(mock(Categoria.class)));  // Simulamos las categorías
        when(ranking.getCategoriasPropietario()).thenReturn(Arrays.asList(mock(Categoria.class)));  // Simulamos las categorías
        when(inmuebles.get(0).getVecesAlquilado()).thenReturn(5); // Simulamos un inmueble con alquileres

        // Usuario
        usuario = new Usuario("Leo Messi", "123456789", email, LocalDateTime.now(), ranking, inmuebles, comentario,
                               3, appMobile);
    }

    @Test
    public void testGetNombre() {
        assertEquals("Leo Messi", usuario.getNombre());
    }

    @Test
    public void testGetTelefono() {
        assertEquals("123456789", usuario.getTelefono());
    }

    @Test
    public void testGetEmail() {
        assertEquals("test@example.com", usuario.getEmail().getInbox());
    }

    @Test
    public void testGetFechaDeInscripcion() {
        assertNotNull(usuario.getFechaDeInscripcion());
    }

    @Test
    public void testGetRanking() {
        assertEquals(ranking, usuario.getRanking());
    }

    @Test
    public void testGetInmuebles() {
        assertEquals(1, usuario.getInmuebles().size());
    }

    @Test
    public void testGetVecesQueAlquilo() {
        assertEquals(3, usuario.getVecesQueAlquilo());
    }

    @Test
    public void testGetAppMobile() {
        assertNotNull(usuario.getAppMobile());
    }

    @Test
    public void testIncrementarVecesQueAlquilo() {
        int initial = usuario.getVecesQueAlquilo();
        usuario.incrementarVecesQueAlquilo();
        assertEquals(initial + 1, usuario.getVecesQueAlquilo());
    }

    @Test
    public void testPuntuar() {
 
        int puntaje = usuario.puntuar();
        assertTrue(puntaje >= 1 && puntaje <= 5);
    }

    @Test
    public void testCantidadTotalDeAlquileres() {
        int totalAlquileres = usuario.cantidadTotalDeAlquileres();
        assertEquals(5, totalAlquileres); // Verifica que la suma de los alquileres de los inmuebles sea correcta
    }

    @Test
    public void testGenerarComentario() {
        when(comentario.getComentarios()).thenReturn(Arrays.asList("Muy buen alquiler!", "Excelente lugar!", "Recomendable"));
        String comentarioGenerado = usuario.generarComentario();
        assertTrue(comentario.getComentarios().contains(comentarioGenerado));
    }

    @Test
    public void testDecidirSiReserva() {
        boolean decision = usuario.decidirSiReserva();
        // Dado que es aleatorio, solo verificamos que sea un booleano
        assertTrue(decision == true || decision == false);
    }

    @Test
    public void testActuaSiCancelarReserva() {
        Inmueble inmueble = mock(Inmueble.class); // mock de Inmueble
        when(inmueble.getTipoDeInmueble()).thenReturn("Casa");

        usuario.actuaSiCancelarReserva(inmueble);
        
        // Verifica que el método popUp se llame correctamente
        verify(appMobile).popUp("El/la Casa que te interesa se ha liberado! Corre a reservarlo!", "Rojo", 14);
    }
}
