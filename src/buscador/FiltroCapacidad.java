package buscador;
import java.time.LocalDateTime;
import java.util.List;
import observer.Inmueble;

public class FiltroCapacidad extends Filtro {

    private int capacidad;

    // Constructor que recibe los filtros obligatorios
    public FiltroCapacidad(int capacidad, List<Filtro> filtrosObligatorios) {
        this.capacidad = capacidad;
        this.setFiltrosObligatorios(filtrosObligatorios); // Configura los filtros obligatorios
    }

    @Override
    public List<Inmueble> filtrar(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> inmuebles) {
        // Aplica los filtros obligatorios antes de aplicar el filtro de capacidad
        List<Inmueble> resultado = super.filtrosObligatorios(ciudad, checkIn, checkOut, inmuebles);

        // Luego aplica el filtro específico de capacidad
        return this.filtro(ciudad, checkIn, checkOut, resultado);
    }

    @Override
    public List<Inmueble> filtro(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> inmuebles) {
        // Filtra por capacidad
        return inmuebles.stream()
                .filter(inmueble -> inmueble.getCapacidad() == capacidad)
                .toList();
    }
}
