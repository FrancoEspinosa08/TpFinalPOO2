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

        // Filtra por ciudad
        resultado = this.filtroCiudad(ciudad, altas);

        // Filtra por fecha de check-in
        resultado = this.filtroCheckIn(checkIn, resultado);

        // Filtra por fecha de check-out
        resultado = this.filtroCheckOut(checkOut, resultado);

        // Aplica el filtro de precio mínimo
        return this.filtro(resultado);
    }

    // Método para filtrar los inmuebles según el precio mínimo
    @Override
    public List<Inmueble> filtro(List<Inmueble> inmuebles) {
        return inmuebles.stream()
                        .filter(inmueble -> inmueble.getPrecioTotal() >= precioMinimo) // Filtra por precio mínimo
                        .toList();
    }

	@Override
	public List<Inmueble> filtro(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut,
			List<Inmueble> inmuebles) {
		// TODO Auto-generated method stub
		return null;
	}
}
