package politicasDeCancelacion;

import java.time.LocalDateTime;

import observer.Inmueble;

public class SinCancelacion extends PoliticaDeCancelacion{

	@Override
	public float aplicarPenalidad(Inmueble reservaACancelar, LocalDateTime diaDeLaCancelacion) {
		
		return reservaACancelar.getPrecioTotal(); //paga la totalidad del periodo

	}

}
