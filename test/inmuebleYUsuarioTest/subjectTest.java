package inmuebleYUsuarioTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import observer.IObserver;
import observer.Subject;
import observer.Usuario;

class subjectTest {

	Subject sub = new Subject();
	
	Usuario user1 = mock(Usuario.class);
	Usuario user2 = mock(Usuario.class);
	
	@BeforeEach
	void setUp() throws Exception {
		
		
	}

	@Test
	void testDetachObservers() {
		List<IObserver> obs = new ArrayList<IObserver>();
		
		obs.add(user1);
		obs.add(user2);
		
		sub.setObservers(obs);
		sub.detach(user1);
		assertEquals(List.of(user2), sub.getObservers());
	}
	
	@Test
	void testSetObservers() {
		List<IObserver> obs = new ArrayList<IObserver>();
		
		obs.add(user1);
		obs.add(user2);
		
		sub.setObservers(obs);
		
		assertEquals(obs, sub.getObservers());
	}

}
