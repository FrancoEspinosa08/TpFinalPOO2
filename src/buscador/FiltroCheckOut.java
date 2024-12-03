package buscador;

import java.time.LocalDateTime;
import java.util.List;
import observer.Inmueble;

public class FiltroCheckOut extends Filtro {

    // Constructor que configura los filtros obligatorios restantes
    public FiltroCheckOut() {
        super();
    }

    @Override
    public List<Inmueble> filtrar(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> inmuebles) {
        // Aplica primero los filtros obligatorios
        List<Inmueble> resultado = super.filtrosObligatorios(ciudad, checkIn, checkOut, inmuebles);

        // Luego aplica el filtro específico de check-out
        return this.filtro(ciudad, checkIn, checkOut, resultado);
    }

    @Override
    public List<Inmueble> filtro(String ciudad, LocalDateTime checkIn, LocalDateTime checkOut, List<Inmueble> inmuebles) {
        // Filtra por check-out
        return inmuebles.stream()
                .filter(inmueble -> inmueble.getFechaCheckOut().equals(checkOut)) // Filtra por fecha de check-out
                .toList();
    }
}
