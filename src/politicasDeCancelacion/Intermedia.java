package politicasDeCancelacion;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import observer.Reserva;

public class Intermedia extends PoliticaDeCancelacion{

	@Override
	public float aplicarPenalidad(Reserva reservaACancelar, LocalDateTime diaDeLaCancelacion) {
		
		
		long diasDeAntelacion = ChronoUnit.DAYS.between(diaDeLaCancelacion, reservaACancelar.getCheckIn());

    	if (diasDeAntelacion >= 20) {
        		return 0; // Cancelación gratuita
    	} else if (diasDeAntelacion >= 10 && diasDeAntelacion < 20) {
        		return (float) (reservaACancelar.getInmueble().getPrecioTotal() * 0.5); // Penalidad del 50%
    	} else {
        		return reservaACancelar.getInmueble().getPrecioTotal() ; // Penalidad total
    	}

	}

}
