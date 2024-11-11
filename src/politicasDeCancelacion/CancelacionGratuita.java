package politicasDeCancelacion;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import observer.Inmueble;

public class CancelacionGratuita extends PoliticaDeCancelacion{

	@Override
	public float aplicarPenalidad(Inmueble reservaACancelar, LocalDateTime diaDeLaCancelacion) {
		
		long diasDeAntelacion = ChronoUnit.DAYS.between(diaDeLaCancelacion, reservaACancelar.getFechaCheckIn());
		
		if (diasDeAntelacion >= 10) {
			return 0; // Cancelación gratuita
		} else {
			return reservaACancelar.getPrecioPorDia() * 2; // Penalidad de dos días
		}	
	}

}
