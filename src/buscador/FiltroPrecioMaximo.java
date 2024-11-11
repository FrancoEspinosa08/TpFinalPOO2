package buscador;
import java.time.LocalDateTime;
import java.util.List;
import observer.Inmueble;

public class FiltroPrecioMaximo extends Filtro {

    // Atributo
    private double precioMaximo;

    // Constructor
    public FiltroPrecioMaximo(double precioMaximo) {
        this.precioMaximo = precioMaximo;
    }

    // Método para filtrar los inmuebles según la ciudad, fecha de check-in, check-out y precio máximo
    @Override
    public List<Inmueble> filtrar(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> altas) {
        List<Inmueble> resultado;

        // Filtra por ciudad
        resultado = this.filtroCiudad(ciudad, altas);

        // Filtra por fecha de check-in
        resultado = this.filtroCheckIn(checkIn, resultado);

        // Filtra por fecha de check-out
        resultado = this.filtroCheckOut(checkOut, resultado);

        // Aplica el filtro de precio máximo
        return this.filtro(resultado);
    }

    // Método para filtrar los inmuebles según el precio máximo
    @Override
    public List<Inmueble> filtro(List<Inmueble> inmuebles) {
        return inmuebles.stream()
                        .filter(inmueble -> inmueble.getPrecioTotal() <= precioMaximo) // Filtra por precio máximo
                        .toList();
    }
}
