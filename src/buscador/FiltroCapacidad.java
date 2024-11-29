package buscador;
import java.time.LocalDateTime;
import java.util.List;
import observer.Inmueble;

public class FiltroCapacidad extends Filtro {

    // Atributo
    private int capacidad;

    // Constructor
    public FiltroCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    // Método para filtrar los inmuebles según la ciudad, fecha de check-in, check-out y capacidad
    @Override
    public List<Inmueble> filtrar(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> altas) {
        
    	List<Inmueble> resultado;

        // Aplico los tres filtros obligatorios (ciudad, checkIn y checkOut) a List<Inmueble> altas;
        resultado = super.filtrosObligatorios(ciudad, checkIn, checkOut, altas);

        // Aplica el filtro de capacidad
        return this.filtro(ciudad, checkIn, checkOut, resultado);
    }

 
	@Override
	public List<Inmueble> filtro(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut,List<Inmueble> inmuebles) {
		
		return inmuebles.stream()
                .filter(inmueble -> inmueble.getCapacidad() == capacidad) // Filtra por capacidad
                .toList();
	}
}
