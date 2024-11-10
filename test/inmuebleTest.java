import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class inmuebleTest {

	@BeforeEach
	void setUp() throws Exception {
		
		
	}

	@Test
	void test() {
		
		Inmueble x = mock(Inmueble.class);
		
		when(x.numero()).thenReturn(5);
		
		
		assertEquals(5, x.numero());
		
	}

}
