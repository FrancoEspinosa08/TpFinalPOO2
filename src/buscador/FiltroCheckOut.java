package buscador;

import java.time.LocalDateTime;
import java.util.List;
import observer.Inmueble;

public class FiltroCheckOut extends Filtro {
	
	public List<Inmueble> filtrar(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> altas) {
    	return this.filtro(ciudad, checkIn, checkOut, altas);
    }


    @Override
    public List<Inmueble> filtro(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> inmuebles) {
    	return inmuebles.stream()
    				    .filter(inmueble -> inmueble.getFechaCheckOut().equals(checkOut))// Filtra por fecha de check-Out
    				    .toList();
    }
}
