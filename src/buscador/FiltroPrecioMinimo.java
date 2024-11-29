package buscador;
import java.time.LocalDateTime;
import java.util.List;
import observer.Inmueble;

public class FiltroPrecioMinimo extends Filtro {

    // Atributo
    private double precioMinimo;

    // Constructor
    public FiltroPrecioMinimo(double precioMinimo) {
        this.precioMinimo = precioMinimo;
    }

    // Método para filtrar los inmuebles según la ciudad, fecha de check-in, check-out y precio mínimo
    @Override
    public List<Inmueble> filtrar(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> altas) {
        List<Inmueble> resultado;

        // Aplico los tres filtros obligatorios (ciudad, checkIn y checkOut) a List<Inmueble> altas;
        resultado = super.filtrosObligatorios(ciudad, checkIn, checkOut, altas);


        // Aplica el filtro de precio mínimo
        return this.filtro(ciudad, checkIn, checkOut, resultado);
    }

  

	@Override
	public List<Inmueble> filtro(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> inmuebles) {
	
		return inmuebles.stream()
                	    .filter(inmueble -> inmueble.getPrecioTotal() >= precioMinimo) // Filtra por precio mínimo
                	    .toList();
	}
}
