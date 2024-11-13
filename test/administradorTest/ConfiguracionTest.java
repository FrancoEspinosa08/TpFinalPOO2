package administradorTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import administrador.Categoria;
import administrador.CategoriaInmueblePrecio;
import administrador.CategoriaInmuebleEstado;
import administrador.CategoriaInmuebleUbicacion;
import administrador.CategoriaInquilinoCuidado;
import administrador.CategoriaInquilinoCumplimiento;
import administrador.CategoriaInquilinoResponsabilidad;
import administrador.CategoriaPropietarioAtencion;
import administrador.CategoriaPropietarioComunicacion;
import administrador.CategoriaPropietarioDisponibilidad;
import administrador.Configuracion;
import administrador.Servicio;

public class ConfiguracionTest {

    // SUT
    private Configuracion configuracion;

    // DOC
    private Categoria categoriaInmueblePrecio = mock(CategoriaInmueblePrecio.class);
    private Categoria categoriaInmuebleEstado = mock(CategoriaInmuebleEstado.class);
    private Categoria categoriaInmuebleUbicacion = mock(CategoriaInmuebleUbicacion.class);

    private Categoria categoriaInquilinoCuidado = mock(CategoriaInquilinoCuidado.class);
    private Categoria categoriaInquilinoCumplimiento = mock(CategoriaInquilinoCumplimiento.class);
    private Categoria categoriaInquilinoResponsabilidad = mock(CategoriaInquilinoResponsabilidad.class);

    private Categoria categoriaPropietarioAtencion = mock(CategoriaPropietarioAtencion.class);
    private Categoria categoriaPropietarioComunicacion = mock(CategoriaPropietarioComunicacion.class);
    private Categoria categoriaPropietarioDisponibilidad = mock(CategoriaPropietarioDisponibilidad.class);

    @BeforeEach
    void setUp() {
        List<Categoria> categoriasInmueble = List.of(categoriaInmueblePrecio, categoriaInmuebleEstado);
        List<Categoria> categoriasInquilino = List.of(categoriaInquilinoCuidado, categoriaInquilinoCumplimiento);
        List<Categoria> categoriasPropietario = List.of(categoriaPropietarioAtencion, categoriaPropietarioComunicacion);

        List<String> tiposInmuebles = List.of("Casa", "Departamento", "Local");
        List<Servicio> serviciosValidos = List.of(mock(Servicio.class), mock(Servicio.class));

        configuracion = new Configuracion(categoriasPropietario, categoriasInquilino, categoriasInmueble, tiposInmuebles, serviciosValidos);
    }

    @Test
    void testGetCategoriasPropietario() {
        assertEquals(2, configuracion.getCategoriasPropietario().size());
    }

    @Test
    void testGetCategoriasInquilino() {
        assertEquals(2, configuracion.getCategoriasInquilinos().size());
    }

    @Test
    void testGetCategoriasInmueble() {
        assertEquals(2, configuracion.getCategoriasInmueble().size());
    }

    @Test
    void testGetTipoInmuebles() {
        assertEquals(3, configuracion.getTipoInmuebles().size());
    }

    @Test
    void testGetServiciosValidos() {
        assertEquals(2, configuracion.getServiciosValidos().size());
    }
    
    @Test
    void testSetCategoriasPropietario() {
    	configuracion.setCategoriasPropietario(List.of(categoriaPropietarioAtencion, categoriaPropietarioComunicacion, categoriaPropietarioDisponibilidad));
        assertEquals(3, configuracion.getCategoriasPropietario().size());
    }
    
    @Test
    void testSetCategoriasInquilino() {
    	configuracion.setCategoriasInquilinos(List.of(categoriaInquilinoCuidado, categoriaInquilinoCumplimiento, categoriaInquilinoResponsabilidad));
        assertEquals(3, configuracion.getCategoriasInquilinos().size());
    }
    
    @Test
    void testSetTiposInmuebles() {
    	configuracion.setTiposInmueble(List.of("Casa", "Departamento", "Local"));
    	assertEquals(3, configuracion.getTipoInmuebles().size());
    }
    
    @Test
    void testSetSeviciosValidos() {
    	configuracion.setSeviciosValidos(List.of(mock(Servicio.class), mock(Servicio.class)));
    	assertEquals(2, configuracion.getServiciosValidos().size());
    }
    
}
