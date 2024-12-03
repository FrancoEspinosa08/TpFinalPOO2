package buscador;

import java.time.LocalDateTime;
import java.util.List;
import observer.Inmueble;

public class FiltroPrecioMaximo extends Filtro {

    // Atributo
    private double precioMaximo;

    // Constructor
    public FiltroPrecioMaximo(double precioMaximo, List<Filtro> filtrosObligatorios) {
        this.precioMaximo = precioMaximo;
        this.setFiltrosObligatorios(filtrosObligatorios); // Configura los filtros obligatorios
    }

    // Método para filtrar los inmuebles según la ciudad, fecha de check-in, check-out y precio máximo
    @Override
    public List<Inmueble> filtrar(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> altas) {
        List<Inmueble> resultado;

        // Aplico los filtros obligatorios
        resultado = super.filtrosObligatorios(ciudad, checkIn, checkOut, altas);

        // Aplica el filtro de precio máximo
        return this.filtro(ciudad, checkIn, checkOut, resultado);
    }

    @Override
    public List<Inmueble> filtro(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> inmuebles) {
        return inmuebles.stream()
                .filter(inmueble -> inmueble.getPrecioTotal() <= precioMaximo) // Filtra por precio máximo
                .toList();
    }
}
