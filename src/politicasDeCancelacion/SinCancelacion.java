package politicasDeCancelacion;

import java.time.LocalDateTime;

import observer.Inmueble;
import observer.Reserva;

public class SinCancelacion extends PoliticaDeCancelacion{

	@Override
	public float aplicarPenalidad(Reserva reservaACancelar, LocalDateTime diaDeLaCancelacion) {
		
		return reservaACancelar.getInmueble().getPrecioTotal(); //paga la totalidad del periodo

	}

}
