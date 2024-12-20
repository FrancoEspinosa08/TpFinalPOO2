package politicasCancelacionTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import observer.Inmueble;
import observer.Reserva;
import politicasDeCancelacion.CancelacionGratuita;
import politicasDeCancelacion.Intermedia;
import politicasDeCancelacion.SinCancelacion;

class PoliticasTest {
	
	Reserva reserva = mock(Reserva.class);
	Inmueble inmueble = mock(Inmueble.class);
	
	CancelacionGratuita cancelacionGratuita = new CancelacionGratuita();
	Intermedia intermedia  = new Intermedia();
	SinCancelacion sinCancelacion = new SinCancelacion();
	
	@BeforeEach
	void setUp() throws Exception {
		
		when(reserva.getInmueble()).thenReturn(inmueble);
		when(reserva.getCheckIn()).thenReturn(LocalDateTime.of(2024, 12, 3, 0, 0));
		when(inmueble.getPrecioPorDia()).thenReturn((float) 10);
		when(inmueble.getPrecioTotal()).thenReturn((float) 100);
		
	}
	
	@Test
	void testMenosDeDiezDiasAntelacion() {
		
		LocalDateTime diaCancelacion = LocalDateTime.of(2024, 12, 1, 0, 0);
		
		assertEquals(100, intermedia.aplicarPenalidad(reserva, diaCancelacion));
	}
	
	@Test
	void testEntreDiezYVeinteDiasAntelacion() {
		
		LocalDateTime diaCancelacion = LocalDateTime.of(2024, 11, 21, 0, 0);
		
		assertEquals(50, intermedia.aplicarPenalidad(reserva, diaCancelacion));
	}
	
	@Test
	void testIntermediaMasDeVeinteDiasDeAntelacion() {
		
		LocalDateTime diaCancelacion = LocalDateTime.of(2023, 12, 2, 0, 0);
		
		assertEquals(0, intermedia.aplicarPenalidad(reserva, diaCancelacion));
	}
	
	
	@Test
	void testSinCancelacion() {
		
		LocalDateTime diaCancelacion = LocalDateTime.of(2023, 12, 2, 0, 0);
		
		assertEquals(100, sinCancelacion.aplicarPenalidad(reserva, diaCancelacion));
	}
	
	@Test
	void testCancelacionGratuitaEsCero() {
		
		LocalDateTime diaCancelacion = LocalDateTime.of(2023, 12, 2, 0, 0);
		
		assertEquals(0, cancelacionGratuita.aplicarPenalidad(reserva, diaCancelacion));
	}
	
	@Test
	void testCancelacionGratuitaEsVeinte() {
		
		LocalDateTime diaCancelacion = LocalDateTime.of(2024, 12, 2, 0, 0);
		
		assertEquals(20, cancelacionGratuita.aplicarPenalidad(reserva, diaCancelacion));
	}
}
