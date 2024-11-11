package politicasDeCancelacion;

import java.time.LocalDateTime;

import observer.Inmueble;

public abstract class PoliticaDeCancelacion {

	public abstract float aplicarPenalidad(Inmueble reservaACancelar, LocalDateTime diaDeLaCancelacion);
	
}
