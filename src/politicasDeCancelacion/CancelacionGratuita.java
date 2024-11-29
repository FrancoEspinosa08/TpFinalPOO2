package politicasDeCancelacion;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import observer.Reserva;

public class CancelacionGratuita extends PoliticaDeCancelacion{

	@Override
	public float aplicarPenalidad(Reserva reservaACancelar, LocalDateTime diaDeLaCancelacion) {
		
		long diasDeAntelacion = ChronoUnit.DAYS.between(diaDeLaCancelacion, reservaACancelar.getCheckIn());
		
		if (diasDeAntelacion >= 10) {
			return 0; // Cancelación gratuita
		} else {
			return reservaACancelar.getInmueble().getPrecioPorDia() * 2; // Penalidad de dos días
		}	
	}

}
