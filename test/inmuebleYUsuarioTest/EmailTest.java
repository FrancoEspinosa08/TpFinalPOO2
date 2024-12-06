package inmuebleYUsuarioTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inmuebleYUsuario.Email;
import observer.Reserva;

class EmailTest {
	
	Email email = new Email();
	Reserva reserva = mock(Reserva.class);
	
	@BeforeEach
	void setUp() throws Exception {
		
		
		
	}

	@Test
	void testSetAttachment() {
		email.setAttachment(reserva);
		assertEquals(reserva, email.getAttachment());
	}

}
