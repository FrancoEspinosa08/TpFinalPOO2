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

        // Filtra por ciudad
        resultado = this.filtroCiudad(ciudad, altas);

        // Filtra por fecha de check-in
        resultado = this.filtroCheckIn(checkIn, resultado);

        // Filtra por fecha de check-out
        resultado = this.filtroCheckOut(checkOut, resultado);

        // Aplica el filtro de capacidad
        return this.filtro(resultado);
    }

    // Método para filtrar los inmuebles según la capacidad
    @Override
    public List<Inmueble> filtro(List<Inmueble> inmuebles) {
        return inmuebles.stream()
                        .filter(inmueble -> inmueble.getCapacidad() == capacidad) // Filtra por capacidad
                        .toList();
    }
}
