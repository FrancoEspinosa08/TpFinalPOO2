package politicasDeCancelacion;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import observer.Inmueble;

public class Intermedia extends PoliticaDeCancelacion{

	@Override
	public float aplicarPenalidad(Inmueble reservaACancelar, LocalDateTime diaDeLaCancelacion) {
		
		
		long diasDeAntelacion = ChronoUnit.DAYS.between(diaDeLaCancelacion, reservaACancelar.getFechaCheckIn());

    	if (diasDeAntelacion >= 20) {
        		return 0; // Cancelación gratuita
    	} else if (diasDeAntelacion >= 10 && diasDeAntelacion < 20) {
        		return (float) (reservaACancelar.getPrecioTotal() * 0.5); // Penalidad del 50%
    	} else {
        		return reservaACancelar.getPrecioTotal() ; // Penalidad total
    	}

	}

}
