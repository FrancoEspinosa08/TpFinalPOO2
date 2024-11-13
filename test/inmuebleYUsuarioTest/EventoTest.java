package inmuebleYUsuarioTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inmuebleYUsuario.Evento;

class EventoTest {

    // SUT (System Under Test)
    Evento evento;

    @BeforeEach
    void setUp() throws Exception {
        evento = new Evento("Carnaval", 
            LocalDateTime.of(2024, 11, 1, 10, 0),  
            LocalDateTime.of(2024, 11, 5, 10, 0),
            100.0f);  
    }

    @Test
    void testCalcularPrecioTotal() {
        float resultado = evento.calcularPrecioTotal();
        
        // Duración en días: 5 días
        float expected = 4 * 100.0f;  

        // Verificamos que el precio total sea el esperado
        assertEquals(expected, resultado, 0.01f);  // Usamos una pequeña tolerancia en la comparación de decimales
    }

    @Test
    void testDuracion() { 
    	
        long resultado = evento.duracion();
        
        long expected = 4;  // 2024-11-05 - 2024-11-01 = 4 días

        assertEquals(expected, resultado);
    }
    //En realidad, cuando usas ChronoUnit.DAYS.between(fechaInicio, fechaFin), 
    //el cálculo incluye el primer día (fecha de inicio) y excluye el último día (fecha de fin), 
    //lo que genera una diferencia de 4 días en lugar de 5 días.
}
