package buscador;
import java.time.LocalDateTime;
import java.util.List;
import observer.Inmueble;

// Filtro que filtra por ciudad
public class FiltroCiudad extends Filtro {
	
	public List<Inmueble> filtrar(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> altas) {
    	return this.filtro(ciudad, checkIn, checkOut, altas);
    }

    @Override
    public List<Inmueble> filtro(List<Inmueble> inmuebles) {
    	return null;
    }

    @Override
    public List<Inmueble> filtro(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> inmuebles) {
        // Redundante aquí, ya que este filtro actúa solo en el atributo ciudad
    	
    	//SETEAR LOS VALORES CHECKIN CHEK OUT NULL
    	return inmuebles.stream()
                .filter(inmueble -> inmueble.getCiudad().equals(ciudad)) // Filtra por ciudad
                .toList();
    }
}
