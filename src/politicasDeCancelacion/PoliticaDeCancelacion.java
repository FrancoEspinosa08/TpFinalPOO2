package politicasDeCancelacion;

import java.time.LocalDateTime;
import observer.Reserva;

public abstract class PoliticaDeCancelacion {

	public abstract float aplicarPenalidad(Reserva reservaACancelar, LocalDateTime diaDeLaCancelacion);
	
}
